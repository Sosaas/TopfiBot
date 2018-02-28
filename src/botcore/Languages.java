package botcore;

public enum Languages {
    GERMAN, ENGLISH;

    public String getTextInLanguage(String name) throws IllegalArgumentException {
    	switch (this.name()) {
    	case "GERMAN": 
    	    return GermanText.valueOf(name).getText();
    	case "ENGLISH":
    	    return EnglishText.valueOf(name).getText();
    	}
	throw new IllegalArgumentException();
    	
    }
    
}
