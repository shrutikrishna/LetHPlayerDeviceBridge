package fr.promotionlh.lhplayer.devicebridge.various;

import merapi.handlers.MessageHandler;
import merapi.messages.IMessage;

/**
 * Handle ErrorMessage
 * 
 * @author Sylvain Artois
 */
public class ErrorMessageHandler extends MessageHandler {
    
    public ErrorMessageHandler(){
        super( ErrorMessage.ERROR );
    }

    @Override
    public void handleMessage(IMessage message) {
        super.handleMessage(message);
    }
}
