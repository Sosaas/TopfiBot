package featureResource;

import java.util.ArrayList;

import botcore.Mainhub;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DemuteRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;

    public DemuteRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	if (messageEvent.getMessage().getContentDisplay().matches("^\\+demute .{1,30}( \\S{1,30})?$")) {
	    String memberNameToUnmute = messageEvent.getMessage().getContentRaw().substring(messageEvent.getMessage().getContentRaw().indexOf(" ") + 1);
	    String channelNameForUnmute = null;
	    if (memberNameToUnmute.contains(" ")) {
		memberNameToUnmute = messageEvent.getMessage().getContentRaw().substring(messageEvent.getMessage().getContentRaw().indexOf(" "), messageEvent.getMessage().getContentRaw().lastIndexOf(" "));
		channelNameForUnmute = messageEvent.getMessage().getContentRaw().substring(messageEvent.getMessage().getContentRaw().lastIndexOf(" "));
	    }
	    ArrayList<Permission> permissionRequiredToUnmute = new ArrayList<Permission>();
	    permissionRequiredToUnmute.add(Permission.MANAGE_ROLES);
	    permissionRequiredToUnmute.add(Permission.VOICE_MUTE_OTHERS);
	    Member memberToUnmute;
	    if (messageEvent.getGuild().getMembersByEffectiveName(memberNameToUnmute, false) == null) {
		memberToUnmute = messageEvent.getGuild().getMembersByEffectiveName(memberNameToUnmute, false).get(0);
	    } else {
		messageEvent.getChannel().sendMessage("Es gibt kein Mitglied mit diesem Namen auf dem Server.").complete();
		return;
	    }
	    if (messageEvent.getMember().canInteract(memberToUnmute)) {
		if (channelNameForUnmute == null) {
		    if (messageEvent.getMember().hasPermission(permissionRequiredToUnmute) || messageEvent.getMember().isOwner() || messageEvent.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			int failCount = 0;
			for (int i = 0; i < messageEvent.getGuild().getTextChannelCache().size(); i +=1) {
			    if (messageEvent.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(memberToUnmute) != null) { 
				messageEvent.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).getManager().clear(2099200).complete();
				if (messageEvent.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).getAllowed().isEmpty() && messageEvent.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).getDenied().isEmpty()) {
				    messageEvent.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).delete().complete();
				}
			    } else {
				failCount += 1;
			    }
			}
			for (int i = 0; i < messageEvent.getGuild().getVoiceChannelCache().size(); i +=1) {
			    if (messageEvent.getGuild().getVoiceChannelCache().asList().get(i).getPermissionOverride(memberToUnmute) != null) { 
				messageEvent.getGuild().getVoiceChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).getManager().clear(2099200).complete();
				if (messageEvent.getGuild().getVoiceChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).getAllowed().isEmpty() && messageEvent.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).getDenied().isEmpty()) {
				    messageEvent.getGuild().getVoiceChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).delete().complete();
				}
			    } else {
				failCount += 1;
			    }
			}
			for (int i = 0; i < messageEvent.getGuild().getCategoryCache().size(); i +=1) {
			    if (messageEvent.getGuild().getCategoryCache().asList().get(i).getPermissionOverride(memberToUnmute) != null) { 
				messageEvent.getGuild().getCategoryCache().asList().get(i).getPermissionOverride(memberToUnmute).getManager().clear(2099200).complete();
				if (messageEvent.getGuild().getCategoryCache().asList().get(i).getPermissionOverride(memberToUnmute).getAllowed().isEmpty() && messageEvent.getGuild().getTextChannelCache().asList().get(i).getPermissionOverride(memberToUnmute).getDenied().isEmpty()) {
				    messageEvent.getGuild().getCategoryCache().asList().get(i).getPermissionOverride(memberToUnmute).delete().complete();
				}
			    } else {
				failCount += 1;
			    }
			}
			if (messageEvent.getMember().getVoiceState().isGuildMuted()) {
			    messageEvent.getGuild().getController().setMute(memberToUnmute, false);
			} else {
			    failCount += 1;
			}
			if (failCount == messageEvent.getGuild().getCategoryCache().size() + messageEvent.getGuild().getVoiceChannelCache().size() + messageEvent.getGuild().getTextChannelCache().size() + 1) {
			    messageEvent.getChannel().sendMessage("Es konnte keine Stummschaltung aufgehoben werden, da das angegebene Mitglied keiner solchen unterlag. Möchtest du der Rolle eines Mitglieds das Schreiben oder Reden erlauben benutze " + Mainhub.gAdmin.getPrefix(messageEvent.getGuild()) + "allow stattdessen.").complete();
			} else {
			    messageEvent.getChannel().sendMessage("Das angegebene Mitglied kann nun wieder auf allen für seine Rollen freigegebenen Kanälen sprechen.").complete();
			    logUnmute(null, memberToUnmute);
			}
		    } else {
			messageEvent.getChannel().sendMessage(messageEvent.getAuthor().getAsMention() + " du hast nicht die Berechtigung diesen Befehl hier auszuführen! Du benötigst die Berechtigungen \"Rollen verwalten\" und \"Mitglieder stumm schalten\"").complete();
		    }
		} else {
		    Channel channelForUnmute = messageEvent.getGuild().getTextChannelsByName(channelNameForUnmute, false).get(0);
		    if (channelForUnmute == null) {
			channelForUnmute = messageEvent.getGuild().getVoiceChannelsByName(channelNameForUnmute, false).get(0);
		    }
		    if (channelForUnmute == null) {
			channelForUnmute = messageEvent.getGuild().getCategoriesByName(channelNameForUnmute, false).get(0);
		    }
		    if (channelForUnmute == null) {
			messageEvent.getChannel().sendMessage("Der von dir gewählte Kanal existiert nicht.").complete();
		    } else {
			if (messageEvent.getMember().hasPermission(channelForUnmute, permissionRequiredToUnmute) || messageEvent.getMember().isOwner() || messageEvent.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			    if (channelForUnmute.getPermissionOverride(memberToUnmute) != null) {
				channelForUnmute.getPermissionOverride(memberToUnmute).getManager().clear(2099200).complete();
				if (channelForUnmute.getPermissionOverride(memberToUnmute).getAllowed().isEmpty() && channelForUnmute.getPermissionOverride(memberToUnmute).getDenied().isEmpty()) {
				    channelForUnmute.getPermissionOverride(memberToUnmute).delete().complete();
				}
				logUnmute(channelForUnmute, memberToUnmute);
			    } else {
				messageEvent.getChannel().sendMessage("Das von dir angegebene Mitglied ist in dem angegebenen Kanal nicht stumm.").complete();
			    }
			} else {
			    messageEvent.getChannel().sendMessage(messageEvent.getAuthor().getAsMention() + " du hast nicht die Berechtigung diesen Befehl hier auszuführen! Du benötigst die Berechtigungen \"Rollen verwalten\" und \"Mitglieder stumm schalten\"").complete();
			}
		    }
		}
	    } else {
		messageEvent.getChannel().sendMessage("Du kannst keine Mitglieder muten die eine höhere oder gleichgestellte Rolle innehaben").complete();
	    }
	} else {
	    messageEvent.getChannel().sendMessage("Die von dir eingegbenen Parameter sind nicht zulässig. Gib nach dem Befehl durch ein Leerzeichen getrennt ein Mitglied an, dass du entmuten möchtest. Optional kann danach noch der Kanal folgen auf den die Aktion beschränkt ist.").complete();
	}

    }
    private void logUnmute(Channel channel, Member member) {
	if (Mainhub.gAdmin.isLogActive(messageEvent.getGuild())) {
	    String zusatz = "";
	    if (channel != null) {
		zusatz = " in " + channel.getName();
	    }
	    Mainhub.gAdmin.getLogChannel(messageEvent.getGuild()).sendMessage("Aktion: Stummschaltung" + zusatz + " aufgehoben" + "\nBetroffen: " + member.getEffectiveName() + "\nVerantwortlich: " + messageEvent.getMember().getAsMention()).complete();
	}
    }

}
