package featureResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import botcore.ChoiceMessageIdentifier;
import botcore.MainListener;
import botcore.Mainhub;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild.Ban;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UnbanRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;
    public User userToUnban = null;

    public UnbanRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	if (!messageEvent.getMessage().getContentRaw().matches("^.unban \\S.{1,31}$")) {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("INVALID_PARAM")).complete();
	    return;
	}
	if (!messageEvent.getMember().hasPermission(Permission.BAN_MEMBERS)) {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MISSING_PERMISSIONS")).complete();
	    return;
	}
	if (!messageEvent.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("BOT_LACK_OF_PERMISSION")).complete();
	    return;
	}
	String userToUnbanName;
	if (messageEvent.getMessage().getContentRaw().matches("^.unban \\S.{1,31}#\\d{4}$")) {
	    userToUnbanName = messageEvent.getMessage().getContentRaw().substring(messageEvent.getMessage().getContentRaw().indexOf(" ") + 1, messageEvent.getMessage().getContentRaw().indexOf("#"));
	    String userToUnbanDiscriminator = messageEvent.getMessage().getContentRaw().substring(messageEvent.getMessage().getContentRaw().indexOf("#"));
	    List<Ban> banList = messageEvent.getGuild().getBanList().complete();
	    for (Ban now : banList) {
		if (now.getUser().getName().equals(userToUnbanName) && now.getUser().getDiscriminator().equals(userToUnbanDiscriminator)) {
		    userToUnban = now.getUser();
		    break;
		}
	    }
	    if (userToUnban == null) {
		messageEvent.getChannel().sendMessage("Es ist keinen User mit dem angegebenen Namen auf der Bannliste vermerkt. Überprüfe bitte deine Angaben und versuche es erneut.").complete();
		return;
	    }
	} else {
	    userToUnbanName = messageEvent.getMessage().getContentRaw().substring(messageEvent.getMessage().getContentRaw().indexOf(" ") + 1);
	    List<Ban> banList = messageEvent.getGuild().getBanList().complete();
	    ArrayList<User> userAvailable = new ArrayList<User>();
	    for (Ban banNow : banList) {
		if (banNow.getUser().getName().equals(userToUnbanName)) {
		    if (userToUnban == null) {
			userToUnban = banNow.getUser();
		    } else {
			if (userAvailable.size() < 1) {
			    userAvailable.add(userToUnban);
			}
			userAvailable.add(banNow.getUser());
		    }
		}
	    }
	    if (!userAvailable.isEmpty()) {
		ArrayList<Message> choiceMessages = new ArrayList<Message>();
		ArrayList<ChoiceMessageIdentifier<User>> identifiers = new ArrayList<ChoiceMessageIdentifier<User>>();
		choiceMessages.add(messageEvent.getChannel().sendMessage("Es gibt mehrere Benutzer auf der Bannliste, die denselben Namen tragen. Bitte wähle aus, wen du entbannen möchtest, indem du bei den gewünschten Benutzern das grüne Häckchen anwählst.").complete());
		Lock lockUserToUnban = new ReentrantLock();
		Condition userSelected = lockUserToUnban.newCondition();
		for (User now : userAvailable) {
		    choiceMessages.add(messageEvent.getChannel().sendMessage("**" + now.getName() + "#" + now.getDiscriminator() + "**").complete());
		    choiceMessages.get(choiceMessages.size() - 1).addReaction("\u2705").complete();
		    ChoiceMessageIdentifier<User> ident = new ChoiceMessageIdentifier<User>( choiceMessages.get(choiceMessages.size() - 1), messageEvent.getAuthor(), lockUserToUnban, userSelected, now);
		    MainListener.getResponsibleListener(messageEvent.getJDA()).registerIdentifier(ident);
		    identifiers.add(ident);
		}
		lockUserToUnban.lock();
		try {
		    if (userSelected.await(3, TimeUnit.MINUTES)) {
			for (Message now : choiceMessages) {
			    now.delete().complete();
			}
			for (ChoiceMessageIdentifier<User> ident : identifiers) {
			    if (ident.isChoosen()) {
				userToUnban = ident.getRealtedObject();
			    }
			    MainListener.getResponsibleListener(messageEvent.getJDA()).removeIdentifier(ident);
			}
		    } else {
			for (Message now : choiceMessages) {
			    now.delete().complete();
			}
			messageEvent.getChannel().sendMessage("Es wurde keine Auswahl getroffen.").complete();
			return;
		    }
		} catch (InterruptedException e) {
		    e.printStackTrace();
		} finally {
		    lockUserToUnban.unlock();
		}
	    }
	    if (userToUnban == null) {
		messageEvent.getChannel().sendMessage("Der von dir angegebene User ist hier nicht gebannt.").complete();
		return;
	    }
	}
	messageEvent.getGuild().getController().unban(userToUnban).complete();
	messageEvent.getChannel().sendMessage("Entbannen erfolgreich!").complete();
    }
}
