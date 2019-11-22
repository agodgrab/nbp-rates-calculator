package nbp.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NbpHandler extends DefaultHandler {

    private String requestedCurrency;
    public List<BigDecimal> listOfBuyingRates;
    public List<BigDecimal> listOfSellingRates;
    private String tmpValue;
    private boolean bCurrency;
    private boolean bBuyingRate;
    private boolean bSellingRate;


    public NbpHandler(String currency) {
        this.requestedCurrency = currency;
        this.listOfBuyingRates = new ArrayList<>();
        this.listOfSellingRates = new ArrayList<>();
    }

    public List<BigDecimal> getListOfBuyingRates() {
        return Collections.unmodifiableList(listOfBuyingRates);
    }

    public List<BigDecimal> getListOfSellingRates() {
        return Collections.unmodifiableList(listOfSellingRates);
    }

    @Override
    public void startElement(String uri, String localName, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("pozycja")) {
            bCurrency = true;
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        if (element.equalsIgnoreCase("kurs_kupna") && bCurrency && bBuyingRate) {
            listOfBuyingRates.add(new BigDecimal(tmpValue.replace(",", ".")));
            bBuyingRate = false;
        }
        if (element.equalsIgnoreCase("kurs_sprzedazy") && bCurrency && bSellingRate) {
            listOfSellingRates.add(new BigDecimal(tmpValue.replace(",", ".")));
            bSellingRate = false;
        }
        if (element.equalsIgnoreCase("pozycja")) {
            bCurrency = false;
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        if (bCurrency) {
            tmpValue = new String(ac, i, j);
            if (tmpValue.equals(requestedCurrency)) {
                bBuyingRate = true;
                bSellingRate = true;
            }
        }
    }
}
