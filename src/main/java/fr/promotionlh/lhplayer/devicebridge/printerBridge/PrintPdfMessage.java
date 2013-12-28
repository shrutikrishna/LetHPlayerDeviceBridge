package fr.promotionlh.lhplayer.devicebridge.printerBridge;

import merapi.messages.Message;

/**
 *
 * @author Sylvain Artois
 */
public class PrintPdfMessage extends Message {
    
    public static final String PRINT_PDF = "PrintPDF";

    private String _pdfFilePath = "";
    private Boolean _addDate = false;
    private Boolean _addBarcod = false;
    private String _barcod = "";
    private String _dateFormat = "";
    private String _datePrefix = "";
    private String _barcoPrefix = "";
    
    public PrintPdfMessage() {
        super();
    }

    public String getPdfFilePath() {
        return _pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this._pdfFilePath = pdfFilePath;
    }

    public Boolean getAddDate() {
        return _addDate;
    }

    public void setAddDate(Boolean addDate) {
        this._addDate = addDate;
    }

    public Boolean getAddBarcod() {
        return _addBarcod;
    }

    public void setAddBarcod(Boolean addBarcod) {
        this._addBarcod = addBarcod;
    }

    public String getBarcod() {
        return _barcod;
    }

    public void setBarcod(String barcod) {
        this._barcod = barcod;
    }

    public String getDateFormat() {
        return _dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this._dateFormat = dateFormat;
    }

    public String getDatePrefix() {
        return _datePrefix;
    }

    public void setDatePrefix(String datePrefix) {
        this._datePrefix = datePrefix;
    }

    public String getBarcoPrefix() {
        return _barcoPrefix;
    }

    public void setBarcoPrefix(String barcoPrefix) {
        this._barcoPrefix = barcoPrefix;
    }
    
    
}
