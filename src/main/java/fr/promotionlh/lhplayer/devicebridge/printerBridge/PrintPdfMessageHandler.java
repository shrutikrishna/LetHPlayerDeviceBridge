package fr.promotionlh.lhplayer.devicebridge.printerBridge;

import fr.promotionlh.lhplayer.devicebridge.event.LetHEventDispatcher;
import merapi.messages.IMessage;

/**
 * Handle PrintPdf Message
 * 
 * @author Sylvain Artois
 */
public class PrintPdfMessageHandler extends LetHEventDispatcher {

    public PrintPdfMessageHandler() {
        handleEventOfType = PrintPdfMessage.PRINT_PDF;
        addMessageType( handleEventOfType );
    }
    
    @Override
    public void handleMessage( IMessage message ) {
        fireEvent( message );
    }
}
