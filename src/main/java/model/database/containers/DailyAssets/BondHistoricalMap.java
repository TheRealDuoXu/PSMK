package model.database.containers.DailyAssets;

import model.database.containers.Values;

public class BondHistoricalMap extends InvestmentHistoricalMap {

    public BondHistoricalMap(DailyAssetPK key, DailyAssetValues value) {
        super();
        map.put(key.getDate(), value);
    }

    public BondHistoricalMap(){
        super();
    }
    @Override
    public String toString() {
        return null;
    }

    @Override
    public int compareTo(InvestmentHistoricalMap other) {
        return 0;
    }

    @Override
    public boolean equals() {
        return false;
    }

    @Override
    public AssetDescription.AssetType getType() {
        return AssetDescription.AssetType.BOND;
    }
}
