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
package featureResource.adminCommand;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import botcore.Mainhub;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BanRunnable implements Runnable {
    
    MessageReceivedEvent messageEvent;

    public BanRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	String memberToBanName = messageEvent.getMessage().getContentRaw().substring(messageEvent.getMessage().getContentRaw().indexOf(" ") + 1);
	int delDays = 0;
	String reason = "";
	if (memberToBanName.contains(" ")) {
	    String[] substrings = memberToBanName.split(" ");
	    for (String now : substrings) {
		if (!messageEvent.getGuild().getMembersByEffectiveName(now, false).isEmpty()) {
		    memberToBanName = now;
		} else {
		    if (now.matches("\\d+")) {
			delDays = Integer.parseInt(now);
		    } else {
			reason += now;
		    }
		}
	    }
	}
	Member memberToBan;
	if (!messageEvent.getGuild().getMembersByEffectiveName(memberToBanName, false).isEmpty()) {
	    memberToBan = messageEvent.getGuild().getMembersByEffectiveName(memberToBanName, false).get(0);
	} else {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MEMBER_NOT_FOUND")).complete();
	    return;
	}
	if (!messageEvent.getMember().canInteract(memberToBan)) {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MEMBER_NOT_FOUND")).complete();
	    return;
	}
	if (!messageEvent.getMember().hasPermission(Permission.BAN_MEMBERS) || !messageEvent.getMember().hasPermission(Permission.ADMINISTRATOR) || !messageEvent.getMember().isOwner()) {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MISSING_PERMISSIONS")).complete();
	    return;
	}
	if (!messageEvent.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS) && !messageEvent.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
	    messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("BOT_LACK_OF_PERMISSION")).complete();
	    return;
	}
	if (memberToBan.getJoinDate().plusDays((long) delDays).isAfter(OffsetDateTime.now())) {
	    delDays = (int) memberToBan.getJoinDate().until(OffsetDateTime.now(), ChronoUnit.DAYS);
	}
	messageEvent.getGuild().getController().ban(memberToBan, delDays, reason).complete();
	if (Mainhub.gAdmin.isLogActive(messageEvent.getGuild())) {
	    String zusatz = "";
	    if (delDays != 0) {
		zusatz = Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("BAN_ADDITION1") + String.valueOf(delDays) + Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("BAN_ADDITION2");
	    }
	    Mainhub.gAdmin.getLogChannel(messageEvent.getGuild()).sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("LOG_ACTION") + 
		    Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("BAN") + zusatz +
		    Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("LOG_TARGET") + memberToBanName +
		    Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("LOG_RESPONSIBLE") + messageEvent.getMember().getAsMention() +
		    Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("LOG_REASON") + reason).complete();
	}
	messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MEMBER_BANNED")).complete();
    }

}
