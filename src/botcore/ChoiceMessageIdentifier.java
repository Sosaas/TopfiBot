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
