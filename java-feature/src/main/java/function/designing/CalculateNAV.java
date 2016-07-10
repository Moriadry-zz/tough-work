package function.designing;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */
public class CalculateNAV {

    private Function<String, BigDecimal> priceFinder;

    public CalculateNAV(final Function<String, BigDecimal> aPriceFinder) {
        priceFinder = aPriceFinder;
    }

    public BigDecimal computeStockWorth(final String ticker, final int shares) {
        return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
    }

    public static void main(String[] args) {
        Function<String, BigDecimal> func = input -> new BigDecimal("1000.1");

        CalculateNAV calculateNAV = new CalculateNAV(func);
        BigDecimal rtn = calculateNAV.computeStockWorth("GOOD", 10);
        System.out.println(rtn);

        System.out.println(new CalculateNAV(func).computeStockWorth("BABA", 12));

        System.out.println(new CalculateNAV(function.designing.YahooFinance::getPrice).computeStockWorth("GOOG", 1));
    }
}
