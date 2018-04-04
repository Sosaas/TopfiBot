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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import botcore.exceptions.DatabaseConnectionException;
import botcore.languages.Languages;

import java.sql.*;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.impl.GuildImpl;

public class GuildAdmin {
    
    private ArrayList<GuildConfig> loaded;
    private Connection databaseConn;
    private PreparedStatement selectStatById;
    private PreparedStatement updateCon;
    private PreparedStatement insertCon;

    public GuildAdmin() {
	try {
	    connect();
	    loaded = new ArrayList<GuildConfig>();
	} 
	catch (DatabaseConnectionException e) {
	    e.printStackTrace();
	} 
    }
    public void setPrefix(Guild g) {
	
    }
    public String getPrefix(Guild g) {
	GuildConfig con = getIfLoaded(g);
	if (con == null) {
	    try {
		con = loadAndGet(g);
	    } catch (SQLException e) {
		return "+";
	    }
	}
	return con.getPrefix();
    }
    public boolean isLoaded(Guild g) {
	for (GuildConfig current : loaded) {
	    if (current.getId() == g.getId()) {
		return true;
	    }
	}
	return false;
    }
    public GuildConfig getIfLoaded(Guild g) {
	for (int i = 0; i < loaded.size(); i += 1) {
	    if (loaded.get(i).getId().equals(g.getId())) {
		return loaded.get(i);
	    }
	}
	return null;
    }
    public void load(Guild g) throws SQLException {
	selectStatById.setString(1, g.getId());
	ResultSet rs = selectStatById.executeQuery();
	GuildConfig con = new GuildConfig().adjust(g);
	try {
	    con.setLanguage(Languages.getLangByShort(rs.getString(1)));
	    con.setPrefix(rs.getString(2));
	    con.setConfig(rs.getString(3));
	    loaded.add(con);
	} catch (SQLException noLineEx) {
	    loaded.add(con);
	}
    }
    public GuildConfig loadAndGet(Guild g) throws SQLException {
	selectStatById.setString(1, g.getId());
	ResultSet rs = selectStatById.executeQuery();
	GuildConfig con = new GuildConfig().adjust(g);
	try {
	    con.setLanguage(Languages.getLangByShort(rs.getString(1)));
	    con.setPrefix(rs.getString(2));
	    con.setConfig(rs.getString(3));
	    loaded.add(con);
	    return con;
	} catch (SQLException noLineEx) {
	    loaded.add(con);
	    return con;
	}
    }
    public boolean isLogActive(Guild g) {
	return true;
    }
    public TextChannel getLogChannel(Guild g) {
	// TODO Methode die den Channel zum loggen zurückgibt initalisieren
	return g.getTextChannelCache().asList().get(0);
    }
    public Languages getLanguage(Guild g) throws SQLException {
	GuildConfig con = getIfLoaded(g);
	if (con == null) {
	    try {
		con = loadAndGet(g);
	    } catch (SQLException e) {
		return Languages.ENGLISH;
	    }
	}
	return con.getLanguage();   
    }
    public void saveConfig(GuildConfig con) throws SQLException {
	selectStatById.setString(1, con.getId());
	ResultSet rs = selectStatById.executeQuery();
	boolean exists;
	try {
	    rs.getString(1);
	    exists = true;
	    System.out.println("try success");
	} catch (SQLException noLineEx) {
	    exists = false;
	    System.out.println("catch success");
	} finally {
	    rs.close();
	}
	if (exists) {
	    updateCon.setString(1, con.getLanguage().getNameShort());
	    updateCon.setString(2, con.getPrefix());
	    updateCon.setString(3, con.getConfig());
	    updateCon.executeUpdate();
	} else {
	    insertCon.setString(1, con.getId());
	    insertCon.setString(2, con.getLanguage().getNameShort());
	    insertCon.setString(3, con.getPrefix());
	    insertCon.setString(4, con.getConfig());
	    insertCon.executeUpdate();
	}
    }
    public void connect() throws DatabaseConnectionException {
	try {
	    Class.forName("org.hsqldb.jdbcDriver");
	    String url;
	    Path startPath = Paths.get(".", "start.config");
	    BufferedReader input = Files.newBufferedReader(startPath);
	    for (byte b = 0; b < 4; b += 1) {
		input.readLine();
	    }
	    url = input.readLine();
	    databaseConn = DriverManager.getConnection(url, "TBot", "");
	    if (!databaseConn.isValid(5)) {
		throw new DatabaseConnectionException();
	    }
	    selectStatById = databaseConn.prepareStatement("SELECT Id FROM General WHERE EXISTS (SELECT ID FROM General WHERE ID = ?)");
	    updateCon = databaseConn.prepareStatement("UPDATE General SET Language = ?, Prefix = ?, Prop = ? WHERE ID = ?");
	    insertCon = databaseConn.prepareStatement("INSERT INTO General VALUES (?, ?, ?, ?)");
	} 
	catch (ClassNotFoundException classEx) {
	    DatabaseConnectionException dce = new DatabaseConnectionException();
	    dce.initCause(classEx);
	    throw dce;
	} 
	catch (IOException ioEx) {
	    DatabaseConnectionException dce = new DatabaseConnectionException();
	    dce.initCause(ioEx);
	    throw dce;
	}
	catch (SQLException sqlEx) {
	    DatabaseConnectionException dce = new DatabaseConnectionException();
	    dce.initCause(sqlEx);
	    throw dce;
	}
    }
    public void shutdown() {
	try {
	    for (GuildConfig con : loaded) {
		saveConfig(con);
	    }
	    databaseConn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
}
