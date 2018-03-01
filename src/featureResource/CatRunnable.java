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
package featureResource;

import java.io.File;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CatRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;

    public CatRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	double rand = Math.random();
	    if (rand < 1.0/6.0) {
		messageEvent.getChannel().sendFile(new File("." + File.separator + "Medien" + File.separator + "Wikipedia-lolcat.jpg")).complete();
		return;
	    }
	    if (rand < 2.0/6.0) {
		messageEvent.getChannel().sendFile(new File("." + File.separator + "Medien" + File.separator + "th.jpg")).complete();
		return;
	    }
	    if (rand < 3.0/6.0) {
		messageEvent.getChannel().sendFile(new File("." + File.separator + "Medien" + File.separator + "Yet_another_lolcat.jpg")).complete();
		return;
	    }
	    if (rand < 4.0/6.0) {
		messageEvent.getChannel().sendFile(new File("." + File.separator + "Medien" + File.separator + "hqdefault.jpg")).complete();
		return;
	    }
	    if (rand < 5.0/6.0) {
		messageEvent.getChannel().sendFile(new File("." + File.separator + "Medien" + File.separator + "Cat_crying_(Lolcat).jpg")).complete();
		return;
	    }
	    if (rand < 1.0) {
		messageEvent.getChannel().sendFile(new File("." + File.separator + "Medien" + File.separator + "Lolcat.jpg")).complete();
	    }

    }

}
