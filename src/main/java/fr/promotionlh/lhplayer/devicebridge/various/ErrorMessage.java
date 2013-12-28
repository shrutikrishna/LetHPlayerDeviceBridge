package fr.promotionlh.lhplayer.devicebridge.various;

import merapi.messages.Message;

/**
 *
 * @author Sylvain Artois
 */
public class ErrorMessage extends Message {
    
    /**
     * Message type
     */
    public static final String ERROR = "error"; 

    private String _error = "";

    public ErrorMessage() {
        super();
    }
    
    public ErrorMessage( String error ) {
        super();
        _error=error;
    }
    
    public String getError() {
        return _error;
    }

    public void setError( String error ) {
        this._error = error;
    }
}
