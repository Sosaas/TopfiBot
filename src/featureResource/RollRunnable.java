package featureResource;

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
