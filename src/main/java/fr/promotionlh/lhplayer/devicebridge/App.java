package fr.promotionlh.lhplayer.devicebridge;

import fr.promotionlh.lhplayer.devicebridge.event.LetHEvent;
import fr.promotionlh.lhplayer.devicebridge.event.LetHEventListener;
import fr.promotionlh.lhplayer.devicebridge.printerBridge.PrintPdfMessage;
import fr.promotionlh.lhplayer.devicebridge.printerBridge.PrintPdfMessageHandler;
import fr.promotionlh.lhplayer.devicebridge.printerBridge.printer.PdfPrinter;
import fr.promotionlh.lhplayer.devicebridge.various.ConfigMessage;
import fr.promotionlh.lhplayer.devicebridge.various.ConfigMessageHandler;
import fr.promotionlh.lhplayer.devicebridge.various.ErrorMessage;
import java.io.File;
import merapi.Bridge;

/**
 * Main entry point for LetHPlayerDeviceBridge
 */
public class App implements LetHEventListener
{
    public static void main( String[] args ) {
        
        App app = new App();
        
        Bridge.open();
        
        ConfigMessageHandler configHandler = new ConfigMessageHandler();
        configHandler.addEventListener(app);
        
        PrintPdfMessageHandler printPdfHandler = new PrintPdfMessageHandler();
        printPdfHandler.addEventListener(app);
    }
    
    
    private String _printerName = "";
    private String[] _ports2listen;
    private PdfPrinter _pdfPrinter;
    
    public App(){
        
    }
    
    public void createPrinter( String printerName ) {
        
        _pdfPrinter = new PdfPrinter();
        _pdfPrinter.initPrinter(printerName);
    }

    public void handleLetHEvent( LetHEvent e ) {
        
        String eventType = e.getEventType();
        
        if( eventType.equals( ConfigMessage.CONFIG ) ) {
            
            ConfigMessage cm = (ConfigMessage) e.getOriginalMessage();
            _ports2listen = cm.getPorts();
            _printerName = cm.getPrinter();
            
            createPrinter(_printerName);
            
            return;
        }
        
        if( eventType.equals( PrintPdfMessage.PRINT_PDF ) ) {
            
            if( _printerName.isEmpty() ) {
                throw new RuntimeException( "App.handleLetHEvent, can't print cause printerName is empty" );
            }
            
            if( _pdfPrinter == null ) {
                throw new RuntimeException( "App.handleLetHEvent, can't print cause pdfPrinter is null" );
            }
            
            PrintPdfMessage pdfMessage = (PrintPdfMessage) e.getOriginalMessage();
            _pdfPrinter.loadPDF(new File(pdfMessage.getPdfFilePath()));
            boolean performPrint = _pdfPrinter.performPrint();
            
            if( !performPrint ){
                
                ErrorMessage m = new ErrorMessage( "Oops, printing of "+pdfMessage.getPdfFilePath()+"seems to fail" );
                m.send();
            }
        }
    }
}
