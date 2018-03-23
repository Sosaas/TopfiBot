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
package featureResource.funCommand;

import java.sql.SQLException;

import botcore.Mainhub;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Magic8BallRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;

    public Magic8BallRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	Double rnd = Math.random();
	String content = messageEvent.getMessage().getContentRaw();
	if (!content.matches("^\\+magic8ball \\S{2}.{0,}$")) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_NO_QUESTION")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.05) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FIRST_NO")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.1) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_SECOND_NO")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.15) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_THIRD_NO")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.2) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FOURTH_NO")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.25) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FIFTH_NO")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.3) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FIRST_MIXED")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.35) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_SECOND_MIXED")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.4) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_THIRD_MIXED")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.45) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FOURTH_MIXED")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.5) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FIFTH_MIXED")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.55) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FIRST_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.6) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_SECOND_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.65) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_THIRD_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.7) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FOURTH_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.75) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_FIFTH_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.8) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_SIXTH_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.85) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_SEVENTH_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.9) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_EIGHTLY_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 0.95) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_NINTH_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}
	if (rnd < 1.0) {
	    try {
		messageEvent.getChannel().sendMessage(Mainhub.gAdmin.getLanguage(messageEvent.getGuild()).getTextInLanguage("MAGIC8BALL_TENTH_YES")).complete();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    return;
	}

    }

}
