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
package botcore.languages;

public enum Languages {
    GERMAN, ENGLISH;

    public static Languages getLangByShort(String shortName) {
	switch (shortName) {
	case "GER" : 
	    return GERMAN;
	case "ENG" :
	    return ENGLISH;
	default : 
	    return ENGLISH;
	}
    }
    
    public String getTextInLanguage(String name) {
    	try {
    	    switch (this.name()) {
    	    case "GERMAN": 
    		return GermanText.valueOf(name).getText();
    	    case "ENGLISH":
    		return EnglishText.valueOf(name).getText();
    	    default: 
    		return EnglishText.valueOf(name).getText() + "\n *We are sorry for not displaying this message in the language requested. We'll do our best to fix it!*";
    	    }
    	} catch (IllegalArgumentException illArgEx) {
    	    illArgEx.printStackTrace();
    	    try {
    		switch (this.name()) {
        	    default: 
            		return EnglishText.valueOf(name).getText() + "\n *We are sorry for not displaying this message in the language requested. We'll do our best to fix it!*";
        	    }
    	    } catch (IllegalArgumentException illArgEx2) {
    		illArgEx2.printStackTrace();
    		return "Something went really wrong in order to send you a feedback. We'll fix that as soon as possible.";
    	    }
    	}
    }
    public String getNameShort() {
	switch (this) {
	case GERMAN : 
	    return "GER";
	case ENGLISH :
	    return "ENG";
	default : 
	    return "ERR";
	}
    }
}
