package botcore;

import java.time.Instant;

public class ActionTimestamp {
    
    private Instant timestamp;
    private String action;

    public ActionTimestamp() {
	timestamp = Instant.EPOCH;
	action = "";
    }
    public ActionTimestamp(Instant inst, String act) {
	timestamp = inst;
	action = act;
    }
    public void setInstant(Instant inst) {
	timestamp = inst;
    }
    public void setAction(String act) {
	action = act;
    }
    public Runnable getAction() {
	switch (action) {
	
	default : return null;
	}
    }
    public Instant getInstant() {
	return timestamp;
    }
    public boolean isOver() {
	if (timestamp.compareTo(Instant.now()) >= 0) {
	    return true;
	} else {
	    return false;
	}
    }

}
