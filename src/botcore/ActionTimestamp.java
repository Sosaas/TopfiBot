/*Copyright 2018 Jonas Wischnewski

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
