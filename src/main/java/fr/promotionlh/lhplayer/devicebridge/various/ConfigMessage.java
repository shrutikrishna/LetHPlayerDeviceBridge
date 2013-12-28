package fr.promotionlh.lhplayer.devicebridge.various;

import merapi.messages.Message;

/**
 *
 * @author Sylvain Artois
 */
public class ConfigMessage extends Message {
    
    public static final String CONFIG = "config"; 
    
    private String _printer = "";
    private String[] _ports;
    
    public ConfigMessage() {
        super();
    }

    public String getPrinter() {
        return _printer;
    }

    public void setPrinter(String _printer) {
        this._printer = _printer;
    }

    public String[] getPorts() {
        return _ports;
    }

    public void setPorts(String[] _ports) {
        this._ports = _ports;
    }
    
    
}
