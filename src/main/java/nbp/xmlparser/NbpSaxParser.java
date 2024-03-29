package nbp.xmlparser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.math.BigDecimal;
import java.util.List;

public class NbpSaxParser {

    private NbpHandler handler;

    public NbpSaxParser(String currency) {
        this.handler = new NbpHandler(currency);
    }

    public void parseDocuments(List<String> xmlFileNameList) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            for (String xmlFileName : xmlFileNameList) {
                parser.parse(xmlFileName, handler);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<BigDecimal> getDataBuyingRates() {
        return handler.getListOfBuyingRates();
    }

    public List<BigDecimal> getDataSellingRates() {
        return handler.getListOfSellingRates();
    }
}
