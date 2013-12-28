package fr.promotionlh.lhplayer.devicebridge.printerBridge;

/**
 *
 * @author Sylvain Artois
 */
public class PrintStringMessage {
    
    public static final String PRINT_STRING = "PrintString";
    
    private String _string2print = "";
    
    public PrintStringMessage(){
        super();
    }

    public String getString2print() {
        return _string2print;
    }

    public void setString2print(String string2print) {
        this._string2print = string2print;
    }
}
