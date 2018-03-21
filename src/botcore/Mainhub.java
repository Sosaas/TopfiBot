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
    private static ArrayList<JDA> api = new ArrayList<JDA>();
    public static GuildAdmin gAdmin;

    public static void main(String[] args) {
	try {
	    Path startPath = Paths.get(".", "start.config");
	    if (Files.exists(startPath)) {
	    	BufferedReader input = Files.newBufferedReader(startPath);
	    	String token = input.readLine().substring(1);
	    	String startImmidialty = input.readLine();
	    	String useGUI = input.readLine();
	    	String shardCount = input.readLine();
	    	String databaseURL = input.readLine();
	    	String shutdownTime = input.readLine();
	    	input.close();
	    	if (useGUI == "TRUE") {
	    	    CP = new TopfiBotControlPanel();
	    	    CP.setVisible(true);
	    	}
	    	if (startImmidialty == "TRUE") {
	    	    try {
	    		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token);
	    		builder.setGame(Game.watching("Type +help")).setStatus(OnlineStatus.OFFLINE);
	    		int count = Integer.parseInt(shardCount);
	    		for (int i = 0; i < count; i =+ 1) {
	    		    api.add(builder.useSharding(i, count).buildAsync());
	    		}
	    		while (api.get(0).getGuilds().size() > 1000) {
	    		    int oldSize = api.size();
	    		    for (JDA now : api) {
	    			now.shutdownNow();
	    			api.remove(now);
	    		    }
	    		    for (int i = 0; i < oldSize + 1; i =+ 1) {
	    			api.add(builder.useSharding(i, oldSize + 1).buildAsync());
	    		    }
	    		}
	    		gAdmin = new GuildAdmin();
	    		for (JDA now : api) {
	    		    now.addEventListener(new MainListener(now.getShardInfo().getShardId()));
	    		    now.getPresence().setStatus(OnlineStatus.ONLINE);
	    		}
	    		if (CP != null) {
	    		    CP.setDisplayStatus((short) 1);
	    		}
	    		
	    	    } 
	    	    catch (NumberFormatException numEx) {
	    		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token);
	    		builder.setGame(Game.watching("Type +help")).setStatus(OnlineStatus.OFFLINE);
	    		api.add(builder.useSharding(0, 1).buildAsync());
	    		while (api.get(0).getGuilds().size() > 1000) {
	    		    int oldSize = api.size();
	    		    for (JDA now : api) {
	    			now.shutdownNow();
	    			api.remove(now);
	    		    }
	    		    for (int i = 0; i < oldSize + 1; i =+ 1) {
	    			api.add(builder.useSharding(i, oldSize + 1).buildAsync());
	    		    }
	    		}
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
	finally {
	    CP = new TopfiBotControlPanel();
	    CP.setVisible(true);
	}

    }
    public static ArrayList<JDA> getAPI() {
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
		input.readLine();
		input.readLine();
		shardCount = input.readLine();
		input.close();
	    } else {
		throw new LoginException();
	    }
	    JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token);
	    builder.setGame(Game.watching("Type +help")).setStatus(OnlineStatus.OFFLINE);
	    int count = Integer.parseInt(shardCount);
	    for (int i = 0; i < count; i += 1) {
		api.add(builder.useSharding(i, count).buildAsync());
	    }
	    while (api.get(0).getGuilds().size() > 1000) {
		int oldSize = api.size();
		for (JDA now : api) {
		    now.shutdownNow();
		    api.remove(now);
		}
		for (int i = 0; i < oldSize + 1; i += 1) {
		    api.add(builder.useSharding(i, oldSize + 1).buildAsync());
		}
	    }
	    gAdmin = new GuildAdmin();
	    for (JDA now : api) {
		now.addEventListener(new MainListener(now.getShardInfo().getShardId()));
		now.getPresence().setStatus(OnlineStatus.ONLINE);
	    }
	    if (CP == null) {
		CP.setDisplayStatus((short) 1);
	    }
	    if (Files.isWritable(startPath)) {
		ArrayList<String> params;
		params = (ArrayList<String>) Files.readAllLines(startPath);
		BufferedWriter output = Files.newBufferedWriter(startPath, StandardOpenOption.WRITE);
		for (int i = 0; i < params.size(); i += 1) {
		    if (i == 3 ) {
			output.write(String.valueOf(api.size()));
		    } else {
			output.write(params.get(i));
		    }
		    output.newLine();
		}
		output.close();
	    }
	} 
	catch (NumberFormatException numEx) {
		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token);
		builder.setGame(Game.watching("Type +help")).setStatus(OnlineStatus.OFFLINE);
		api.add(builder.useSharding(0, 1).buildAsync());
		while (api.get(0).getGuilds().size() > 1000) {
		    int oldSize = api.size();
		    for (JDA now : api) {
			now.shutdownNow();
			api.remove(now);
		    }
		    for (int i = 0; i < oldSize + 1; i += 1) {
			api.add(builder.useSharding(i, oldSize + 1).buildAsync());
		    }
		}
	}
	catch (IOException IOEx) {
	    IOEx.printStackTrace();
	}
    }
    public static void shutdown() {
	for (JDA jda : api) {
	    MainListener ml = (MainListener) jda.getRegisteredListeners().get(0);
	    ml.shutdown();
	    jda.shutdown();
	    api.remove(jda);
	}
	if (CP == null) {
	    CP.setDisplayStatus((short) 0);
	}
    }
    public static void shutdownNow() {
	for (JDA jda : api) {
	    jda.shutdownNow();
	}
    }
}
