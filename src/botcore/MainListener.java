/* Copyright 2018 Jonas Wischnewski

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package botcore;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.ArrayList;

import featureResource.adminCommand.*;
import featureResource.funCommand.*;
import featureResource.infoCommand.*;

public class MainListener extends ListenerAdapter {
    
    private boolean launched;
    private ExecutorService exePool = Executors.newCachedThreadPool();
    private ArrayList<ChoiceMessageIdentifier> identifierList = new ArrayList<ChoiceMessageIdentifier>();

    public MainListener() {
	launched = true;
    }
    public static MainListener getResponsibleListener(JDA jda) {
	for (Object list : jda.getRegisteredListeners()) {
	    if (list.getClass().equals(MainListener.class)) {
		return (MainListener) list;
	    }
	}
	return null;
    }
    public synchronized void registerIdentifier(ChoiceMessageIdentifier ident) {
	identifierList.add(ident);
    }
    public synchronized void removeIdentifier(ChoiceMessageIdentifier ident) {
	identifierList.remove(ident);
    }
    public synchronized void shutdown() {
	exePool.shutdown();
	try {
	    exePool.awaitTermination(30, TimeUnit.SECONDS);
	} 
	catch (InterruptedException intEx) {
	    exePool.shutdownNow();
	}
    }
    @Override    public void onMessageReceived(MessageReceivedEvent event)
    {	
	if (!launched) {
	    return;
	}
        if (event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }
        Message message = event.getMessage();
        String content = message.getContentRaw(); 
        String prefix = Mainhub.gAdmin.getPrefix(event.getGuild());
        if (!content.startsWith(prefix)) return;
        String command = content.substring(1);
        if (command.contains(" ")) command = content.substring(1, content.indexOf(' '));
        switch (command) {
        case "ping" : 
            MessageChannel channel = event.getChannel();
            channel.sendMessage("pong!  " + String.valueOf(Mainhub.getAPI().getPing()) + "ms").queue();
            break;
        case "vorstellen" : 
            MessageChannel channel1 = event.getChannel();
            channel1.sendMessage("Hallo ich bin Topfi Bot! \nIch werde zurzeit von Sosaas entwickelt. Noch kann man meine Entwicklungsphase als Sub Alpha Minus bezeichnen. Aber da kommt bald mehr " + event.getAuthor().getAsMention()).queue();
            break;
        case "topfi":
            event.getChannel().sendMessage("Tritt der Topfi-Army bei und entdecke den Stiel of Life \nhttps://discord.gg/Q8uWN7B ").queue();
            event.getChannel().sendFile(new File("." + File.separator + "Medien" + File.separator + "thugtopfi.png")).queue();
            break;
        case "link":
            try {
		event.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(event.getGuild()).getTextInLanguage("INFO_JOINLINK") + "\nhttps://discordapp.com/api/oauth2/authorize?client_id=398129656953307136&permissions=0&scope=bot").complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
            break;
        case "help": 
            exePool.execute(new HelpRunnable(event));
            break;
        case "magic8ball":
            exePool.execute(new Magic8BallRunnable(event));
            break;
        case "roll":
            exePool.execute(new RollRunnable(event));
            break;
        case "cat":
            exePool.execute(new CatRunnable(event));
        case "allow":
            break;
        case "deny":
            break;
        case "mute":
            exePool.execute(new MuteRunnable(event));
            break;
        case "demute":
            exePool.execute(new DemuteRunnable(event));
            break;
        case "ban": 
            exePool.execute(new BanRunnable(event));
            break;
        case "unban":
            exePool.execute(new UnbanRunnable(event));
            break;
        case "prune":
            exePool.execute(new PruneRunnable(event));
            break;
        case "warn":
            break;
        default: 
            try {
		event.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(event.getGuild()).getTextInLanguage("INVALID_COMMAND")).queue();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
        }
    }
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
	if (!event.getReactionEmote().getName().equals("\u2705") || identifierList.isEmpty()) {
	    return;
	}
	Message msg = event.getChannel().getMessageById(event.getMessageIdLong()).complete();
	for (ChoiceMessageIdentifier identifier : identifierList) {
	    if (msg.equals(identifier.getChMessage()) && identifier.getRequester().equals(event.getUser())) {
		identifier.choose();
	    }
	}
    }
}
