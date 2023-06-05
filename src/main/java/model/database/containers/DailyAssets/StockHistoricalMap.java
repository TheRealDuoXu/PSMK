package model.database.containers.DailyAssets;

import model.database.containers.PrimaryKey;
import model.database.containers.Values;

import java.util.LinkedHashMap;

public class StockHistoricalMap extends InvestmentHistoricalMap {
    public StockHistoricalMap(LinkedHashMap<PrimaryKey, Values<String>> map){
        super(map);
    }
    public StockHistoricalMap(DailyAssetPK primaryKey, DailyAssetValues values) {
        super(primaryKey, values);
    }
    public StockHistoricalMap(){
        super();
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * @param other the object to be compared. 
     * @return the order
     */
    @Override
    public int compareTo(InvestmentHistoricalMap other) {
        return 0;
    }

    @Override
    public boolean equals() {
        return false;
    }

    /**
     * @return AssetType enum object describing the asset type
     */
    @Override
    public AssetDescription.AssetType getType() {
        return AssetDescription.AssetType.STOCK;
    }

}
