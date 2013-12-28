package fr.promotionlh.lhplayer.devicebridge.various;


import fr.promotionlh.lhplayer.devicebridge.event.LetHEventDispatcher;
import merapi.messages.IMessage;

/**
 * Handle ConfigMessage
 *
 * @author Sylvain Artois
 */
public class ConfigMessageHandler extends LetHEventDispatcher {

    public ConfigMessageHandler() {
        handleEventOfType = ConfigMessage.CONFIG;
        addMessageType( handleEventOfType );
    }
    
    @Override
    public void handleMessage( IMessage message ) {
        fireEvent( message );
    }
}
