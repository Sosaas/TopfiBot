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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import gui.TopfiBotControlPanel;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.utils.JDALogger;

public class Mainhub {
    
    public static TopfiBotControlPanel CP;
    private static JDA api;
    public static GuildAdmin gAdmin;

    public static void main(String[] args) {
	try {
	    Path startPath = Paths.get(".", "start.config");
	    if (Files.exists(startPath)) {
	    	BufferedReader input = Files.newBufferedReader(startPath);
	    	String token = input.readLine().substring(1);
	    	String startImmidialty = input.readLine();
	    	String useGUI = input.readLine();
	    	String databaseURL = input.readLine();
	    	String shutdownTime = input.readLine();
	    	input.close();
	    	if (useGUI == "TRUE") {
	    	    CP = new TopfiBotControlPanel();
	    	    CP.setVisible(true);
	    	}
	    	if (startImmidialty == "TRUE") {
		    JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token);
		    builder.setGame(Game.watching("Type +help")).setStatus(OnlineStatus.OFFLINE);
		    gAdmin = new GuildAdmin();
		    api = builder.buildBlocking();
		    api.addEventListener(new MainListener());
		    api.getPresence().setStatus(OnlineStatus.ONLINE);
		    if (CP == null) {
			CP.setDisplayStatus((short) 1);
		    }
	    	}
	    	if (shutdownTime != "NONE" && shutdownTime != null) {
	    	    if (shutdownTime.matches("^20[1[8-9]|[2-9][0-9]]-[0[1-9]|1[1-2]]-[[0-2][1-9]|3[0-1]]T[[0-1][0-9]|2[0-3]]:[0-5][0-9]:[0-5][0-9]$")) {
	    		LocalDateTime shutdownTimestamp = LocalDateTime.parse(shutdownTime);
	    		long inSecond = shutdownTimestamp.toEpochSecond((ZoneOffset) ZoneOffset.systemDefault());
	    		if (inSecond > LocalDateTime.now().toEpochSecond((ZoneOffset) ZoneOffset.systemDefault())) {
	    		    if (CP != null) CP.setDisplayShutdownTime(shutdownTimestamp);
			
	    		}
	    	    }   
	    	}
	    } else {
		Files.createFile(startPath);
	    }
	} 
	catch (IOException IOEx) {
	    if (JDALogger.getLog(Mainhub.class).isErrorEnabled()) {
		JDALogger.getLog(Mainhub.class).error("Failed reading \"start.config\". Token may be unreachable.");
	    } else {
		System.err.println("Failed reading \"start.config\". Token may be unreachable.");
	    }
	    IOEx.printStackTrace();
	} 
	catch (LoginException e) {
	    e.printStackTrace();
	} 
	catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} 
	catch (InterruptedException e) {
	    e.printStackTrace();
	}
	finally {
	    CP = new TopfiBotControlPanel();
	    CP.setVisible(true);
	}

    }
    public static JDA getAPI() {
	return api;
    }
    public static void launch() throws LoginException {
	String token = "";
	try {
	    Path startPath = Paths.get(".", "start.config");
	    String shardCount = "";
	    if (Files.isReadable(startPath)) {
		BufferedReader input = Files.newBufferedReader(startPath);
		token = input.readLine().substring(1);
		input.close();
	    } else {
		throw new LoginException();
	    }
	    JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token);
	    builder.setGame(Game.watching("Type +help")).setStatus(OnlineStatus.OFFLINE);
	    gAdmin = new GuildAdmin();
	    api = builder.buildBlocking();
	    api.addEventListener(new MainListener());
	    api.getPresence().setStatus(OnlineStatus.ONLINE);
	    if (CP == null) {
		CP.setDisplayStatus((short) 1);
	    }
	} 
	catch (IOException | InterruptedException IOEx) {
	    IOEx.printStackTrace();
	}
    }
    public static void shutdown() {
	gAdmin.shutdown();
	MainListener ml = (MainListener) api.getRegisteredListeners().get(0);
	ml.shutdown();
	api.shutdown();
	if (CP == null) {
	    CP.setDisplayStatus((short) 0);
	}
    }
    public static void shutdownNow() {
	    api.shutdownNow();
    }
}
