package fr.promotionlh.lhplayer.devicebridge.printerBridge.printer;


import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;



/**
 * La classe qui implémente la logique applicative liée à l'impression
 * 
 * @author sartois
 */
public class PdfPrinter
{
    /**
     * Utile pour tester si une imprimante fonctionne. Il suffit de lancer cette classe dans Eclipse
     * Inutilisé dans le programme DeviceManager
     * 
     * Montre aussi le cheminement pour imprimer 1 pdf
     * 
     * <ul>
     * <li>Instancier un objet PrinterManager2</li>
     * <li>Appeler initPrinter</li>
     * <li>Génerer le pdf via addContent</li>
     * <li>Charger le pdf en mémoire</li>
     * <li>Lancer l'impression</li>
     * </ul>
     * 
     * Afin de controler l'impression, cette classe contient une classe interne qui implente l'interface Printable et réécrit la méthode print
     * 
     * Pour découvrir l'API d'impression en Java, le mieux est encore de lire mon tuto sur mon blog
     * 
     * @param args
     * @see Printable
     * @see <a href="http://www.sylvainartois.fr.nf/fr/JAVA/printing-in-java-with-DocFlavor">DocFlavor : imprimer avec Java</a>
     */
    public static void main( String[] args ) {
        
        //String pathToNewFile = PDFAdder.addContent( "C:/Documents and Settings/sartois/Bureau/WorldCupTicket_voucher.pdf", true, false, "", "yyyy-MM-dd HH:mm:ss", "The : ", ""  );

        PdfPrinter p = new PdfPrinter();
        
        try {
            p.initPrinter( "CITIZEN CT-S651" );
        }
        catch( Exception e ) {
            p.getLogger().severe( e.toString() );
        }
        
        p.loadPDF( "C:\\workspace_flash_builder\\LHTerminal\\resources\\terminalAssets\\test_printer_ticket.pdf" );
        p.performPrint();
    }
        
    final private Logger _logger;
    
    private PrintService[] _printServices;
    private PDFFile _pdfFile;
    private PDFPage _pdfPage;
    private int _printerKey = -1;
	
    /**
     * Constructeur vide
     */
    public PdfPrinter( Logger logger ) {
        this._logger = logger;
    }

    /**
     * Constructeur vide
     */
    public PdfPrinter() {
        this._logger = Logger.getLogger( "fr.promotionlh.lhplayer.devicebridge.printerBridge.printer.PdfPrinter" );
    }

    /**
     * @return the _logger
     */
    public Logger getLogger() {
        return _logger;
    }

    /**
     * Stocke l'index correspondant à l'imprimante choisie
     * @param printerKey
     */
    public void setPrinterKey( int printerKey ) {
        _printerKey = printerKey;
    }

    /**
     * Return system print services
     * @return 
     */
    public PrintService[] getPrintServices(){
        _printServices = PrinterJob.lookupPrintServices();
        return _printServices;
    }

    /**
     * Loop through system printer, and get the wanted printer key
     * 
     * @param printerName
     */
    public void initPrinter( String printerName ) {

        PrintService[] printServices = getPrintServices();
        int printerIndex = -1;

        for( int k = 0 ; k < printServices.length ; k++ ) {
            if( printServices[k].getName().equals( printerName ) ) {
                _logger.log( Level.INFO, "Found printer {0}", printerName);
                printerIndex = k;
                break;
            }
        }

        if( printerIndex == -1 ) {
            throw new RuntimeException( "Can't find needed printer : " + printerName );
        }
        else{
            setPrinterKey( printerIndex );
        }
    }
	
    /**
     * Charge un pdf en mémoire et le lit via l'API de Sun Pdf-renderer
     * @see <a href="http://java.net/projects/pdf-renderer/">Pdf-renderer home page</a>
     * @see <a href="http://www.javaworld.com/javaworld/jw-06-2008/jw-06-opensourcejava-pdf-renderer.html?page=1">Tuto sur Java World à propos de PDFRenderer</a>
     * 
     * @param pathToPdf
     * @see com.sun.pdfview.PDFFile
     */
    public void loadPDF( String pathToPdf ) {
        try {
            RandomAccessFile raf = new RandomAccessFile( pathToPdf, "r" );
            FileChannel fc = raf.getChannel();
            ByteBuffer buf = fc.map( FileChannel.MapMode.READ_ONLY, 0, fc.size() );

            _pdfFile = new PDFFile( buf );
            _pdfPage = _pdfFile.getPage( 1 );
        }
        catch( FileNotFoundException e ){
            _logger.log( Level.SEVERE, "", e );
        }
        catch (IOException e) {
            _logger.log( Level.SEVERE, "", e );
        }
    }
    
    public void loadPDF( File pdf ) {
        
        if( ! pdf.exists() ) {
            throw new RuntimeException( "PdfPrinter.loadPDF, can't load pdf cause file doesn't exist" );
        }
        
        loadPDF( pdf.getPath() );
    }
    
    /**
     * Méthode qui lance l'impression
     */
    public boolean performPrint() {
		
	DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
	DocPrintJob job = _printServices[ _printerKey ].createPrintJob();
		
	_logger.log( Level.INFO, "Will now print on {0}", _printServices[ _printerKey ].getName());
		
	//Noter le 1er argument, CustomPrinter (voir ci-dessous)
	Doc doc = new SimpleDoc( new CustomPrinter(), flavor, null );
		
	PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
        attrs.add( new Copies(1) );
        attrs.add( OrientationRequested.PORTRAIT );
        attrs.add( new MediaPrintableArea( (float)0.0, (float)15.0, (float)80.0 , (float)3245.0, MediaPrintableArea.MM ));

        try {
            job.print( doc, attrs );
            return true;
        }
        catch ( PrintException e ) {
            _logger.severe(e.toString());
            return false;
        }
    }
	
    /**
     * Implentation de l'interface Printable
     * @see Printable
     */
    class CustomPrinter implements Printable {
        
        @Override
        public int print( Graphics g, PageFormat pf, int pageIndex ) throws PrinterException {
            
            if ( pageIndex == 0) {

                if( _pdfPage == null ) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D)g;

                Paper p = pf.getPaper();
                p.setImageableArea( (double)0, (double)29, (double)228, (double)9245 );

                pf.setPaper(p);

                g2d.translate( pf.getImageableX(), pf.getImageableY() );
                g2d.setColor(Color.black);

                AffineTransform at = g2d.getTransform ();
                Dimension dim = _pdfPage.getUnstretchedSize( (int) pf.getImageableWidth(), (int) pf.getImageableHeight(), _pdfPage.getBBox() );
                Rectangle bounds = new Rectangle( (int) pf.getImageableX(),(int) pf.getImageableY (), dim.width, dim.height );
                PDFRenderer rend = new PDFRenderer( _pdfPage, g2d, bounds, null, null );

                try {
                    _pdfPage.waitForFinish();
                    rend.run();
                }
                catch ( InterruptedException e ) {
                    _logger.severe(e.toString());
                }

                g2d.setTransform(at);

                return Printable.PAGE_EXISTS;
            }

            return Printable.NO_SUCH_PAGE;
        }
    }
}