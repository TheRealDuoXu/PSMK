package data.portfolio.historical;

import data.database.PrimaryKey;
import data.database.Values;

import java.util.LinkedHashMap;

public class StockHistoricalData extends InvestmentHistoricalData{
    private StockHistoricalData(LinkedHashMap<PrimaryKey, Values<String>> map) {
        super(map);
    }

    @Override
    public InvestmentHistoricalData getInstance(LinkedHashMap<PrimaryKey, Values<String>> map) {
        return new StockHistoricalData(map);
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
     * 
     */
    @Override
    public void updateDDBBOnThisObjectData() {

    }

    /**
     * @return 
     */
    @Override
    public char getType() {
        return 0;
    }
}
