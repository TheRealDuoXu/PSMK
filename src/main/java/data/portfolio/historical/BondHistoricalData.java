package data.portfolio.historical;

import data.database.PrimaryKey;
import data.database.Values;

import java.util.LinkedHashMap;

public class BondHistoricalData extends InvestmentHistoricalData{
    private BondHistoricalData(LinkedHashMap<PrimaryKey, Values<String>> map) {
        super(map);
    }

    @Override
    public InvestmentHistoricalData getInstance(LinkedHashMap<PrimaryKey, Values<String>> map) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int compareTo(InvestmentHistoricalData other) {
        return 0;
    }

    @Override
    public boolean equals() {
        return false;
    }

    @Override
    public char getType() {
        return 0;
    }
}
