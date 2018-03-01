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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.dv8tion.jda.core.entities.Guild;

public class GuildConfig {
    
    private String gId;
    private char prefix;
    private String gConfig;

    public GuildConfig(Guild g, String newPrefix, String newConfig) {
	this.gId = g.getId();
	this.prefix = newPrefix.charAt(0);
	this.gConfig = newConfig;
    }
    public GuildConfig(String newId, String newPrefix, String newConfig) {
	this.gId = newId;
	this.prefix = newPrefix.charAt(0);
	this.gConfig = newConfig;
    }
    public String getPrefix() {
	return String.valueOf(prefix);
    }
    public char getPrefixCharacter() {
	return prefix;
    }
    public String getId() {
	return gId;
    }
    public void safeConfig() {
	try {
	    BufferedWriter saveConfig = new BufferedWriter(new FileWriter("." 
	    + File.separator + "guilds.config"));
	    saveConfig.newLine();
	    saveConfig.write(gId);
	    saveConfig.newLine();
	    saveConfig.write(prefix);
	    saveConfig.write(" ");
	    saveConfig.write(gConfig);
	    saveConfig.close();
	} catch (IOException IOEx) {
	    System.err.println("Fehler beim Speichern!");
	    IOEx.printStackTrace();
	}
    }

}
