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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class GuildAdmin {
    
    private String[] configurated;
    private ArrayList<GuildConfig> loaded;

    public GuildAdmin() {
	try {
	    BufferedReader input = new BufferedReader(new FileReader("."+ 
	    File.separator + "guilds.config"));
	    int i = 0;
	    while (input.ready()) {
		configurated[i] = input.readLine();
		input.readLine();
		i =+ 1;
	    }
	    input.close();
	} catch (IOException IOEx) {
	    System.err.println("Guilds-Config konnte nicht geladen werden!");
	    IOEx.printStackTrace();
	}
    }
    // TODO Speicherstrategie definieren
    public void setPrefix(Guild g) {
	// TODO Methode zum Speichern des jeweiligen Prefixs 
	if (configurated == null) {

	}
	for(String current : configurated) {
	    if (current == g.getId()) {
		
	    }
	}
    }
    public String getPrefix(Guild g) {
	// TODO Methode um das jeweilige gespeicherte Prefix auszulesen
	if (configurated == null) return "+";
	for(String current : configurated) {
	    if (current == g.getId()) {
		
	    }
	}
	return "+";
    }
    public boolean isloaded(Guild g) {
	for (GuildConfig current : loaded) {
	    if (current.getId() == g.getId()) return true;
	}
	return false;
    }
    public int load(Guild g) {
	if (isloaded(g)) return -1;
	try {
	    BufferedReader input = new BufferedReader(new FileReader("." + File.separator + "guilds.config"));
	    String readed;
	    while(input.ready()) {
		readed = input.readLine();
		if (readed == g.getId()) {
		    String config = input.readLine();
		    loaded.add(new GuildConfig(g.getId(), config.substring(0, 0), config.substring(2, config.length())));
		    input.close();
		    return loaded.indexOf(new GuildConfig(g.getId(), config.substring(0, 0), config.substring(2, config.length())));
		} else {
		    input.readLine();
		}
	    }
	    input.close();
	    return -1;
	} catch (IOException IOEx) {
	    IOEx.printStackTrace();
	    return -1;
	}
    }
    public boolean isLogActive(Guild g) {
	// TODO Methode, die zurückgibt ob geloggt werden soll
	return true;
    }
    public TextChannel getLogChannel(Guild g) {
	// TODO Methode die den Channel zum loggen zurückgibt initalisieren
	return g.getTextChannelCache().asList().get(0);
    }
    public void writeStandardConfig(Guild g) {
	
    }
    public Languages getLanguage(Guild g) {
	// TODO Methode zur Rückgabe der Guild-Sprache initalisieren
	return Languages.GERMAN;
    }
    // TODO Methode zum Speichern der Guild-Sprache erstellen
}
