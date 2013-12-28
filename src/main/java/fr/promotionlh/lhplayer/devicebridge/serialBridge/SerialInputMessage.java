package fr.promotionlh.lhplayer.devicebridge.serialBridge;

import merapi.messages.Message;

/**
 *
 * @author Sylvain Artois
 */
public class SerialInputMessage extends Message {
    
    public static final String SERIAL_INPUT = "SerialInput";
    
    private String _serialInput = "";
    private String _messageToWrite = "";
    
    public SerialInputMessage(){
        super();
    }

    public String getSerialInput() {
        return _serialInput;
    }

    public void setSerialInput(String serialInput) {
        this._serialInput = serialInput;
    }

    public String getMessageToWrite() {
        return _messageToWrite;
    }

    public void setMessageToWrite(String messageToWrite) {
        this._messageToWrite = messageToWrite;
    }
}
