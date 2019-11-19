package nbp.xmlparser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class NbpSaxParser {

    private NbpHandler handler;

    public NbpSaxParser(String currency) {
        this.handler = new NbpHandler(currency);
    }

    public void parseDocument(String xmlFileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlFileName, handler);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    public List<BigDecimal> getDataBuyingRates() {
        return handler.getListOfBuyingRates();
    }

    public List<BigDecimal> getDataSellingRates() {
        return handler.getListOfSellingRates();
    }
}
