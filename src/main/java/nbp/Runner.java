package nbp;

import nbp.xmlparser.NbpSaxParser;

import java.util.List;
import java.util.Scanner;

public class Runner {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj jedną z walut: USD, EUR, CHF, GBP");
        String currency = scanner.nextLine();
        System.out.println("Podaj datę początkową w formacie rrrrMMdd");
        String start = scanner.nextLine();
        System.out.println("Podaj datę końcową w formacie rrrrMMdd");
        String end = scanner.nextLine();

        Dir dir = new Dir(start, end);
        NbpSaxParser nbpSaxParser = new NbpSaxParser(currency);

        dir.download();
        calculateResults(dir.select(), nbpSaxParser);

    }

    private void calculateResults(List<String> listOfXmlFiles, NbpSaxParser nbpSaxParser) {
        for (String xmlName : listOfXmlFiles) {
            nbpSaxParser.parseDocument(xmlName);
        }
        DataDownloaded dataDownloaded = new DataDownloaded(nbpSaxParser.getDataBuyingRates(), nbpSaxParser.getDataSellingRates());
        System.out.println("Average buying rate: " + dataDownloaded.averageBuyingRate());
        System.out.println("Standard deviation of selling rates: " + dataDownloaded.sellingRatesStandardDeviation());
    }
}
