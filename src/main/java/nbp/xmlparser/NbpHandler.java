package nbp.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NbpHandler extends DefaultHandler {

    private String requestedCurrency;
    public List<BigDecimal> listOfBuyingRates;
    public List<BigDecimal> listOfSellingRates;
    private String tmpValue;
    private boolean bcurrency;
    private boolean bBuyingRate;
    private boolean bSellingRate;

    public NbpHandler(String currency) {
        this.requestedCurrency = currency;
        this.listOfBuyingRates = new ArrayList<>();
        this.listOfSellingRates = new ArrayList<>();
    }

    public List<BigDecimal> getListOfBuyingRates() {
        return new ArrayList<>(listOfBuyingRates);
    }

    public List<BigDecimal> getListOfSellingRates() {
        return new ArrayList<>(listOfSellingRates);
    }

    @Override
    public void startElement(String uri, String localName, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("pozycja")) {
            bcurrency = true;
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        if (element.equalsIgnoreCase("kurs_kupna") && bcurrency && bBuyingRate) {
            listOfBuyingRates.add(new BigDecimal(tmpValue.replace(",", ".")));
            bBuyingRate = false;
        }
        if (element.equalsIgnoreCase("kurs_sprzedazy") && bcurrency && bSellingRate) {
            listOfSellingRates.add(new BigDecimal(tmpValue.replace(",", ".")));
            bSellingRate = false;
        }
        if (element.equalsIgnoreCase("pozycja")) {
            bcurrency = false;
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        if (bcurrency) {
            tmpValue = new String(ac, i, j);
            if (tmpValue.equals(requestedCurrency)) {
                bBuyingRate = true;
                bSellingRate = true;
            }
        }
    }
}
