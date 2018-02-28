package featureResource;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;

    public HelpRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	String content = messageEvent.getMessage().getContentRaw();
	if (content.contains(" ")) {
	    String parameter = content.substring(content.indexOf(' ') + 1);
	    switch (parameter) {
	    case "long":
		MessageChannel channel = messageEvent.getAuthor().openPrivateChannel().complete();
		channel.sendMessage("Du hast den ausführlichen Hilfstext angefordert. Bald wird er verfügbar sein.").complete();
		break;
	    case "short":
		MessageChannel channel1 = messageEvent.getAuthor().openPrivateChannel().complete();
		channel1.sendMessage("Du hast den knappen Hilfstext angefordert. Bald wird er verfügbar sein.").complete();
		break;
	    case "norm":
		MessageChannel channel2 = messageEvent.getAuthor().openPrivateChannel().complete();
		channel2.sendMessage("Du hast den normalen Hilfstext angefordert. Bald wird er verfügbar sein.").complete();
		break;
	    default: 
		messageEvent.getChannel().sendMessage("Unbekannter Parameter. Zulässig sind \"long\", \"short\" und \"norm\".").complete();
	    }
	} else {
	    MessageChannel channel2 = messageEvent.getAuthor().openPrivateChannel().complete();
	    channel2.sendMessage("Du hast den normalen Hilfstext angefordert. Bald wird er verfügbar sein.").complete();
	}
    }
}
