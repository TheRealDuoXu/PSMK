package data.portfolio;

import data.portfolio.transaction.AssetTransaction;

import java.util.ArrayList;

public class Portfolio {
    ArrayList<AssetTransaction> assetCollection;

    private final float irr=0;  //TODO


    public Portfolio(ArrayList<AssetTransaction> assetCollection){
        this.assetCollection = assetCollection;
    }
    public Portfolio(AssetTransaction ...assetArray){

    }

    public void refreshActiveAssets(){

    }



}
