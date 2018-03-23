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

import botcore.languages.Languages;
import net.dv8tion.jda.core.entities.Guild;

public class GuildConfig {
    
    private String gId;
    private String prefix;
    private String gConfig;
    private Languages gLang;
    
    public GuildConfig() {
	gId = "";
	prefix = "+";
	gConfig = "0";
	gLang = Languages.ENGLISH;
    }
    public GuildConfig(Guild g, String newPrefix, String newConfig, Languages newLang) {
	gId = g.getId();
	prefix = newPrefix;
	gConfig = newConfig;
	gLang = newLang;
    }
    public GuildConfig(String newId, String newPrefix, String newConfig, Languages newLang) {
	gId = newId;
	prefix = newPrefix;
	gConfig = newConfig;
	gLang = newLang;
    }
    public String getConfig() {
	return gConfig;
    }
    public void setConfig(String newConfig) {
	gConfig = newConfig;
    }
    public String getPrefix() {
	return prefix;
    }
    public void setPrefix(String pre) {
	prefix = pre;
    }
    public String getId() {
	return gId;
    }
    public Languages getLanguage() {
	return gLang;
    }
    public void setLanguage(Languages newLang) {
	gLang = newLang;
    }
    public GuildConfig adjust(Guild g) {
	if (g.isMember(g.getJDA().getSelfUser())) {
	    gId = g.getId();
	}
	prefix = "+";
	gConfig = "0";
	gLang = Languages.ENGLISH;
	return this;
    }

}
