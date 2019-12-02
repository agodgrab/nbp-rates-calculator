package nbp.processor;

import nbp.xmlparser.NbpSaxParser;

public class Runner {

    public void run() {

        XmlCollector dir = new XmlCollector(Parameters.INSTANCE.getStart(), Parameters.INSTANCE.getEnd());
        NbpSaxParser nbpSaxParser = new NbpSaxParser(Parameters.INSTANCE.getCurrency());

        nbpSaxParser.parseDocuments(dir.fetchXmlFilesList());

        Calculator calculator = new Calculator(nbpSaxParser.getDataBuyingRates(), nbpSaxParser.getDataSellingRates());
        calculator.printResult();
    }


}
