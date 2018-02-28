package featureResource;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import botcore.ChoiceMessageIdentifier;
import botcore.MainListener;
import botcore.Mainhub;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

public class MuteRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;

    public MuteRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	String content = messageEvent.getMessage().getContentRaw();
	if (content.matches("^\\+mute \\S{1,30}( \\S{1,30}){0,2}( .{1,30}){0,1}$")) {
	    try {
		 ArrayList<Permission> permissionsList = new ArrayList<Permission>();
		 permissionsList.add(Permission.MANAGE_PERMISSIONS);
		 permissionsList.add(Permission.VOICE_MUTE_OTHERS);
		 String[] parameters = content.split(" ");
		 Member parameterMember = null;
		 String timeout = "";
		 Channel channel = null;
		 parameters[0] = "";
		 boolean allVoice = false;
		 for (int i = 1; i < parameters.length; i += 1) {
		     for (Member mem : messageEvent.getGuild().getMemberCache()) {
			 if (mem.getUser().getName().concat("#").concat(mem.getUser().getDiscriminator()).equals(parameters[i])) {
			     parameterMember = mem;
			 }
			 if (mem.getEffectiveName().equals(parameters[i])) {
			     if (messageEvent.getGuild().getMembersByEffectiveName(parameters[i], false).size() < 2) {
				 parameterMember = mem;
			     } else {
				ArrayList<Message> choiceMessages = new ArrayList<Message>();
				ArrayList<ChoiceMessageIdentifier<Member>> identifiers = new ArrayList<ChoiceMessageIdentifier<Member>>();
				Lock choiceLock = new ReentrantLock();
				Condition memberSelectedCond = choiceLock.newCondition();
				for (Member now : messageEvent.getGuild().getMembersByEffectiveName(parameters[i], false)) {
				    choiceMessages.add(messageEvent.getChannel().sendMessage("").complete());
				    choiceMessages.get(choiceMessages.size() - 1).addReaction("\u2705");
				    ChoiceMessageIdentifier<Member> ident = new ChoiceMessageIdentifier<Member>(choiceMessages.get(choiceMessages.size() - 1), messageEvent.getAuthor(), choiceLock, memberSelectedCond, now);
				    MainListener.getResponsibleListener(messageEvent.getJDA()).registerIdentifier(ident);
				    identifiers.add(ident);
				}
				try {
				    choiceLock.lock();
				    if (memberSelectedCond.await(3, TimeUnit.MINUTES)) {
					for (ChoiceMessageIdentifier<Member> ident : identifiers) {
					    if (ident.isChoosen()) {
						parameterMember = ident.getRealtedObject();
					    }
					}
				    }
				} finally {
				    choiceLock.unlock();
				    for (Message now : choiceMessages) {
					now.delete().complete();
				    }
				    for (ChoiceMessageIdentifier<Member> ident : identifiers) {
					MainListener.getResponsibleListener(messageEvent.getJDA()).removeIdentifier(ident);
				    }
				}
			     }
			 }
		     }
		     if (parameters[i].matches("^\\d{1,2}(sec)?(min)?(h)?$")) {
			 timeout = parameters[i];
		     }
		     for (VoiceChannel now : messageEvent.getGuild().getVoiceChannelCache()) {
			 if (now.getName().equals(parameters[i])) {
			     channel = now;
			 }
		     }
		     for (TextChannel now : messageEvent.getGuild().getTextChannelCache()) {
			 if (now.getName().equals(parameters[i])) {
			     channel = now;
			 }
		     }
		     for (Category now : messageEvent.getGuild().getCategoryCache()) {
			 if (now.getName().equals(parameters[i])) {
			     channel = now;
			 }
		     }
		     if (parameters[i].equals("allVoice")) {
			 allVoice = true;
		     }
		 }
		 
		 if (channel != null) {
		     if (messageEvent.getMember().hasPermission(channel , permissionsList) || messageEvent.getMember().hasPermission(Permission.ADMINISTRATOR) || messageEvent.getMember().isOwner()) {
			 if (!parameterMember.isOwner() && messageEvent.getMember().canInteract(parameterMember)) {
			     	if (channel.getPermissionOverride(parameterMember) == null) {
			     	    channel.createPermissionOverride(parameterMember).complete();
			 	}
			 	if (channel.getType() == ChannelType.TEXT) {
			     	    channel.getPermissionOverride(parameterMember).getManager().deny(Permission.MESSAGE_WRITE).complete();
			 	}
			 	if (channel.getType() == ChannelType.VOICE) {
			 	    channel.getPermissionOverride(parameterMember).getManager().deny(Permission.VOICE_SPEAK).complete();
			 	}
			 	if (channel.getType() == ChannelType.CATEGORY) {
			 	   channel.getPermissionOverride(parameterMember).getManager().deny(Permission.MESSAGE_WRITE).complete();
			 	   channel.getPermissionOverride(parameterMember).getManager().deny(Permission.VOICE_SPEAK).complete();
			 	}
			 	messageEvent.getChannel().sendMessage("Mitglied " + parameterMember.getEffectiveName() + " erfolgreich in " + channel.getName() +  " gemuted.").complete();
				logMute(parameterMember, parameters, channel, allVoice);
				setUnmuteTimestamp(parameterMember, timeout);
			 } else {
			     messageEvent.getChannel().sendMessage("Du kannst keine Mitglieder muten die eine höhere oder gleichgestellte Rolle innehaben").complete();
			 }
		     } else {
			 messageEvent.getChannel().sendMessage(messageEvent.getAuthor().getAsMention() + " du hast nicht die Berechtigung diesen Befehl hier auszuführen!").complete();
		     }
		 } else {
		     if (messageEvent.getMember().hasPermission(permissionsList) || messageEvent.getMember().hasPermission(Permission.ADMINISTRATOR) || messageEvent.getMember().isOwner()) {
			 if (!parameterMember.isOwner() && messageEvent.getMember().canInteract(parameterMember)) {
			     for (int i = 0; i < parameterMember.getGuild().getVoiceChannelCache().size(); i += 1) {
				 if (parameterMember.getGuild().getVoiceChannelCache().asList().get(i).getPermissionOverride(parameterMember) == null) {
				     parameterMember.getGuild().getVoiceChannelCache().asList().get(i).createPermissionOverride(parameterMember).complete();
				 }
				 parameterMember.getGuild().getVoiceChannelCache().asList().get(i).getPermissionOverride(parameterMember).getManager().deny(Permission.VOICE_SPEAK).complete();
			     }
			     if (!allVoice) {
				 for (int i = 0; i < parameterMember.getGuild().getTextChannelCache().size(); i += 1) {
				     if (parameterMember.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(parameterMember) == null) {
					 parameterMember.getGuild().getTextChannelCache().asList().get(i).createPermissionOverride(parameterMember).complete();
				     }
				     parameterMember.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(parameterMember).getManager().deny(Permission.MESSAGE_WRITE).complete();				 
				 }
			     }
			     messageEvent.getChannel().sendMessage("Mitglied " + parameterMember.getEffectiveName() + " erfolgreich gemuted.").complete();
			     logMute(parameterMember, parameters, channel, allVoice);
			     setUnmuteTimestamp(parameterMember, timeout);
			 } else {
			     messageEvent.getChannel().sendMessage("Du kannst keine Mitglieder muten die eine höhere oder gleichgestellte Rolle innehaben").complete();
			 }
		     } else {
			 messageEvent.getChannel().sendMessage(messageEvent.getAuthor().getAsMention() + " du hast nicht die Berechtigung diesen Befehl hier auszuführen!").complete();
		     }
		 }
	    } catch (InsufficientPermissionException InPerEx) {
		messageEvent.getChannel().sendMessage("Dem Bot fehlen die nötigen Berechtigungen um diesen Befehl richtig auszuführen. Stelle sicher, dass dem Bot eine Rolle mit ausreichenden Berechtigungen zugewiesen wurde.\n" + InPerEx.getPermission().getName() + " fehlt, um diesen Befehl ausführen zu können.").complete();
	    } catch (Exception ex) {
		messageEvent.getChannel().sendMessage("Es ist ein unbekannter Fehler aufgetreten. Versuche bitte noch einmal den Befehl aufzurufen. Erhälst du weiterhin diese Meldung wende dich an (Platzhalter Ansprechmöglichkeit)").complete();
		ex.printStackTrace();
	    }
	} else {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("INVALID_PARAM")).complete();
	}
    }
    private void logMute(Member muted, String[] reason, Channel channel, boolean allVoice) {
	if (Mainhub.gAdmin.isLogActive(messageEvent.getGuild())) {
	    String reasonOutput = "";
	    String channelErgaenzung = "";
	    if (channel != null) {
		channelErgaenzung = " in #" + channel.getName();
	    }
	    if (allVoice) {
		channelErgaenzung = " in allen Sprachkanälen";
	    }
	    Mainhub.gAdmin.getLogChannel(messageEvent.getGuild()).sendMessage("Aktion: " + "Stummgeschaltet" + channelErgaenzung + "\nBetroffen: " + muted.getEffectiveName() + "\nVerantwortlich: " + messageEvent.getMember().getAsMention() + "\nGrund: " + reasonOutput).complete();
	}
    }
    private void setUnmuteTimestamp(Member toUnmute, String time) {
	
    }

}
