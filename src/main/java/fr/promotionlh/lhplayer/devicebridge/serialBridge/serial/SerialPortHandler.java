package fr.promotionlh.lhplayer.devicebridge.serialBridge.serial;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import gnu.io.CommPort;
import gnu.io.NoSuchPortException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * La classe chargée de l'écoute du / des ports séries
 * 
 * @author sartois
 * @see http://rxtx.qbang.org/wiki/index.php/Main_Page
 */
public class SerialPortHandler extends Thread implements SerialPortEventListener
{
    
    private final static int BPS_2_LISTEN = 9600;
    private final static Logger _logger = Logger.getLogger( "fr.promotionlh.lhplayer.devicebridge.serialBridge.serial.SerialPortHandler" );

    private CommPortIdentifier _comPortIdentifier;
    private CommPort _commPort;
    private BufferedReader _serialBuffer;
    private SerialPort _serialPort;
    private String _portName;
        
    private boolean running;

    /**
     * Initialise l'écoute du port
     * 
     * @param portName
     * @param windowRef
     * @see <a href="http://rxtx.qbang.org/wiki/index.php/Examples">RXTX Wiki, codes examples</a>
     */
    public SerialPortHandler( String threadName, String portName ){

        super(threadName);
        _portName = portName;
    }
        
    /**
     *
     * @param portName
     */
     public void init( String portName ){

         try {
             _comPortIdentifier = CommPortIdentifier.getPortIdentifier(portName);

             if( ! _comPortIdentifier.isCurrentlyOwned() ) {
                 _commPort = _comPortIdentifier.open( this.getClass().getName(), 500 );
                 _serialBuffer = new BufferedReader( new InputStreamReader( _commPort.getInputStream()));
             }
             else{
                 throw new PortInUseException();
             }
         }
         catch( NoSuchPortException e ){
             _logger.severe(e.getMessage());
         }
         catch( PortInUseException e ){
             _logger.severe(e.getMessage());
         }
         catch( IOException e ){
             _logger.severe(e.getMessage());
         }

         _serialPort = (SerialPort) _commPort;

         try{
             _serialPort.enableReceiveTimeout(1000);
             _serialPort.enableReceiveThreshold(0);
         }
         catch ( UnsupportedCommOperationException e ){
             _logger.severe(e.getMessage());
         }

         try {
             _serialPort.addEventListener(this);
         }
         catch (TooManyListenersException e){
             _logger.severe(e.getMessage());
         }

         _serialPort.notifyOnDataAvailable(true);

         try {
             _serialPort.setSerialPortParams( BPS_2_LISTEN, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE );
         }
         catch (UnsupportedCommOperationException e) {
             _logger.severe(e.getMessage());
         }

         _logger.log( Level.INFO, "COM Port {0} open, waiting for data", portName);
     }
        
    /**
     * Boucle infinie, arrétée lorsque running passe à false
     */
    @Override
    public void run(){

        running = true;

        while( running ){
            try{
                Thread.sleep(1000);
            }
            catch( InterruptedException e ){
                _logger.severe(e.getMessage());
            }
        }

        close();
    }
	
    public void stopThread(){
        running = false;
    }
	
    public void close(){
        try{
            _serialBuffer.close();
            _commPort.close();
        }
        catch (IOException e){
            _logger.severe(e.getMessage());
        }
    }

    /**
     * Evènement déclenché lorsqu'un flux arrive dans le port série
     * On lit la chaine charactère par charactère pour éviter les problèmes
     * 
     * Appelle la méthode sendResponse de TanukisSocketServer (si l'action existe dans triggerInfo)
     * 
     * @see TanukisSocketServer#sendResponse(String)
     */
    @Override
    public void  serialEvent( SerialPortEvent event ){

        switch( event.getEventType() ){

            case SerialPortEvent.BI :
            case SerialPortEvent.OE :
            case SerialPortEvent.FE :
            case SerialPortEvent.PE :
            case SerialPortEvent.CD :
            case SerialPortEvent.CTS :
            case SerialPortEvent.DSR :
            case SerialPortEvent.RI :
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
                //do nothing
                break;

            case SerialPortEvent.DATA_AVAILABLE :
                String serialString = "";
                break;
        }
    }
	
    public String getPortTypeName ( int portType ) {
        switch ( portType ){
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }

}