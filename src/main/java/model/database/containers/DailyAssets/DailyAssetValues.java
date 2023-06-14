package model.database.containers.DailyAssets;

import model.database.containers.Inmutable;
import model.database.containers.Values;

@Inmutable
public class DailyAssetValues extends Values {
    private static final int NUMBER_OF_FIELDS_IN_DAILY_ASSETS = 7;
    private static final int MAX_STOCK_EXCHANGE_VARCHAR_LENGTH = 6;
    private final String stockExchange;
    private final char type;
    private final double open, high, low, close, vol;

    public DailyAssetValues(String stockExchange, char type, double open, double high, double low, double close, double vol) {
        this.stockExchange = stockExchange;
        this.type = type;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.vol = vol;
    }

    public static DailyAssetValues getInstance(String stockExchange, char type, double open, double high, double low, double close, double vol) {
        if (stockExchange.length() <= MAX_STOCK_EXCHANGE_VARCHAR_LENGTH) {
            return new DailyAssetValues(stockExchange, type, open, high, low, close, vol);
        }
        throw new IllegalArgumentException("Stock exchange name too long!");
    }

    @Override
    public int length() {
        return NUMBER_OF_FIELDS_IN_DAILY_ASSETS;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public char getType() {
        return type;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVol() {
        return vol;
    }
}
