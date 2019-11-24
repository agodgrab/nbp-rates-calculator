package nbp;

import nbp.xmlparser.NbpSaxParser;

import java.util.List;

public class Results {

    public static void calculateResults(List<String> listOfXmlFiles, NbpSaxParser nbpSaxParser) {

        nbpSaxParser.parseDocument(listOfXmlFiles);
        Calculator calculator = new Calculator(nbpSaxParser.getDataBuyingRates(), nbpSaxParser.getDataSellingRates());

        System.out.println("Average buying rate: " + calculator.averageBuyingRate());
        System.out.println("Standard deviation of selling rates: " + calculator.sellingRatesStandardDeviation());
    }
}
