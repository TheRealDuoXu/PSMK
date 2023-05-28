package data.portfolio.historical;

import data.database.PrimaryKey;
import data.database.Values;

import java.util.LinkedHashMap;

public class StockHistoricalData extends InvestmentHistoricalData{
    /**
     * Represents the {@link InvestmentHistoricalData} of type Bond. Bond has different data than stock
     * @param map
     */
    private StockHistoricalData(LinkedHashMap<PrimaryKey, Values<String>> map) {
        super(map);
    }

    @Override
    public InvestmentHistoricalData getInstance(LinkedHashMap<PrimaryKey, Values<String>> map) {
        return new StockHistoricalData(map);
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * @param other the object to be compared. 
     * @return
     */
    @Override
    public int compareTo(InvestmentHistoricalData other) {
        return 0;
    }

    @Override
    public boolean equals() {
        return false;
    }

    /**
     * @return 
     */
    @Override
    public char getType() {
        return 0;
    }
}
