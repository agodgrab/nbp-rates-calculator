package nbp;

import nbp.xmlparser.NbpSaxParser;

import java.math.BigDecimal;
import java.util.List;

public class Results {

    public static void calculateResults(List<BigDecimal> dataBuyingRates, List<BigDecimal> dataSellingRates) {

        Calculator calculator = new Calculator(dataBuyingRates, dataSellingRates);

        System.out.println("Average buying rate: " + calculator.averageBuyingRate());
        System.out.println("Standard deviation of selling rates: " + calculator.sellingRatesStandardDeviation());
    }
}
