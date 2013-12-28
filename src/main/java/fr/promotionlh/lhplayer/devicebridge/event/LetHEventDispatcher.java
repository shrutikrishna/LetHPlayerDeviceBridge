package fr.promotionlh.lhplayer.devicebridge.event;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import merapi.handlers.MessageHandler;
import merapi.messages.IMessage;


/**
 * @author Sylvain Artois
 */
public abstract class LetHEventDispatcher extends MessageHandler {
    
    protected List _listeners = new ArrayList();
    
    protected String handleEventOfType;
    
    synchronized protected void fireEvent( IMessage c ) {
        
        LetHEvent event = new LetHEvent(this);
        event.setEventType( handleEventOfType );
        event.setOriginalMessage(c);
        
        Iterator i = _listeners.iterator();
        
        while( i.hasNext() ) {
            ((LetHEventListener) i.next()).handleLetHEvent(event);
        }
    }
    
    public synchronized void addEventListener( LetHEventListener  listener ) {
        _listeners.add(listener);
    }

    public synchronized void removeEventListener( LetHEventListener listener) {
        _listeners.remove(listener);
    }
    
    
}
