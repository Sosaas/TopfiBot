package featureResource;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PruneRunnable implements Runnable {
    
    private MessageReceivedEvent messageEvent;

    public PruneRunnable(MessageReceivedEvent forEvent) {
	messageEvent = forEvent;
    }

    @Override
    public void run() {
	if (messageEvent.getMessage().getContentRaw().matches("")) {
	    
	}

    }

}
