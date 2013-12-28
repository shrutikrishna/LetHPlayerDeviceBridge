package fr.promotionlh.lhplayer.devicebridge.event;


import java.util.EventObject;
import merapi.messages.IMessage;


/**
 * @author Sylvain Artois
 */
public class LetHEvent extends EventObject {
    
    private String _eventType;
    private IMessage _originalMessage;
    
    public LetHEvent( Object source ) {
        super(source);
    }

    public String getEventType() {
        return _eventType;
    }

    public void setEventType(String eventType) {
        this._eventType = eventType;
    }

    public IMessage getOriginalMessage() {
        return _originalMessage;
    }

    public void setOriginalMessage( IMessage originalMessage) {
        this._originalMessage = originalMessage;
    }
    
}
