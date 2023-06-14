package model.database.containers.DailyAssets;

public class StockHistoricalMap extends InvestmentHistoricalMap {
    public StockHistoricalMap(DailyAssetPK pk, DailyAssetValues values) {
        super();
        map.put(pk.getDate(), values);
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
