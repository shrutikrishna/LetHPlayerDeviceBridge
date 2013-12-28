package fr.promotionlh.lhplayer.devicebridge.serialBridge;

import merapi.handlers.MessageHandler;
import merapi.messages.IMessage;

/**
 * Handle SerialInputMessage
 * 
 * @author Sylvain Artois
 */
public class SerialInputMessageHandler extends MessageHandler {
    
    public SerialInputMessageHandler() {
        super( SerialInputMessage.SERIAL_INPUT );
    }

    @Override
    public void handleMessage(IMessage message) {
        
    }
}
