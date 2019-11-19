package nbp;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class DataDownloaded {
    private List<BigDecimal> buyingRates;
    private List<BigDecimal> sellingRates;

    public DataDownloaded(List<BigDecimal> buyingRates, List<BigDecimal> sellingRates) {
        this.buyingRates = buyingRates;
        this.sellingRates = sellingRates;
    }

    public BigDecimal averageBuyingRate() {
        return average(buyingRates);
    }

    public BigDecimal sellingRatesStandardDeviation() {
        return standardDeviation(sellingRates);
    }

    private BigDecimal average(List<BigDecimal> rates) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal rate : rates) {
            sum = sum.add(rate);
        }
        return sum.divide(new BigDecimal(rates.size()), 4, RoundingMode.HALF_UP);
    }

    private BigDecimal standardDeviation(List<BigDecimal> rates) {
        BigDecimal average = average(rates);
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal rate : rates) {
            sum = sum.add(rate.subtract(average).pow(2));
        }
        BigDecimal sumDivided = sum.divide(new BigDecimal(rates.size()), 4, BigDecimal.ROUND_HALF_UP);
        return sqrt(sumDivided).round(new MathContext(3, RoundingMode.HALF_UP));
    }

    private BigDecimal sqrt(BigDecimal value) {
        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
        return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
    }

}
