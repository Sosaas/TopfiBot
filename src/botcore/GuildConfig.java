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
