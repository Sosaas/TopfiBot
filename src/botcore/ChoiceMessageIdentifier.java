package botcore;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

public class ChoiceMessageIdentifier<T> {
    
    private Message choiceMessage;
    private T relatedObject;
    private User requester;
    private Lock relatedLock;
    private Condition relatedCondition;
    private boolean choosen = false;
    
    public ChoiceMessageIdentifier(Message msg, User request, Lock toLock, Condition toCondition, T toRelated) {
	choiceMessage = msg;
	requester = request;
	relatedLock = toLock;
	relatedCondition = toCondition;
	relatedObject = toRelated;
    }
    public Message getChMessage() {
	return choiceMessage;
    }
    public T getRealtedObject() {
	return relatedObject;
    }
    public User getRequester() {
	return requester;
    }
    public Lock getRelatedLock() {
	return relatedLock;
    }
    public Condition getRelatedCondition() {
	return relatedCondition;
    }
    public boolean isChoosen() {
	return choosen;
    }
    public void choose() {
	try {
	    relatedLock.lock();
	    choosen = true;
	    relatedCondition.signalAll();
	} finally {
	    relatedLock.unlock();
	}
    }
}
