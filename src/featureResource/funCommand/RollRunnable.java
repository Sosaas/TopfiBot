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
package featureResource.funCommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RollRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;

    public RollRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	double ran = Math.random();
	    if (ran < 5.0/30.0) {
		messageEvent.getChannel().sendMessage("1").complete();
		return;
	    }
	    if (ran < 10.0/30.0) {
		messageEvent.getChannel().sendMessage("2").complete();
		return;
	    }
	    if (ran < 15.0/30.0) {
		messageEvent.getChannel().sendMessage("3").complete();
		return;
	    }
	    if (ran < 20.0/30.0) {
		messageEvent.getChannel().sendMessage("4").complete();
		return;
	    }
	    if (ran < 25.0/30.0) {
		messageEvent.getChannel().sendMessage("5").complete();
		return;
	    }
	    if (ran < 30.0/30.0) {
		messageEvent.getChannel().sendMessage("6").complete();
		return;
	    }
    }

}
