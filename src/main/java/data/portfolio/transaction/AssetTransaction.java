package data.portfolio.transaction;

public class AssetTransaction<InvestmentHistoricalData extends data.portfolio.historical.InvestmentHistoricalData>{
    /**
     * {@link AssetTransaction} is inmutable object that represents entry and exit point for an asset transaction
     */
    private InvestmentHistoricalData buyPoint;
    private InvestmentHistoricalData sellPoint;
    private AssetDescription assetDescription;

    AssetTransaction(InvestmentHistoricalData buyPoint){
        this.buyPoint = buyPoint;
        this.sellPoint = null;
    }
    AssetTransaction(InvestmentHistoricalData buyPoint, InvestmentHistoricalData sellPoint){
        this.buyPoint = buyPoint;
        this.sellPoint = sellPoint;
    }
    AssetTransaction(InvestmentHistoricalData buyPoint, InvestmentHistoricalData sellPoint, boolean exposeAssetDescription){
        this(buyPoint, sellPoint);
        this.assetDescription = getAssetDescription();
    }

    private AssetDescription getAssetDescription() {
        String[] buyPointFirstRow = buyPoint.toArray()[0];
        return new AssetDescription(buyPointFirstRow[InvestmentHistoricalData.ARRAY_POS_TICKER],
                buyPointFirstRow[InvestmentHistoricalData.ARRAY_POS_STOCK_EXCHANGE],
                buyPointFirstRow[InvestmentHistoricalData.ARRAY_POS_TYPE].charAt(0));
    }

    /**
     * @return true if it does have a sell point, false if it doesn't have a sell point
     */
    public boolean hasSellPoint(){
        return sellPoint != null;
    }

    public InvestmentHistoricalData getBuyPoint() {
        return buyPoint;
    }

    public InvestmentHistoricalData getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(InvestmentHistoricalData sellPoint) throws IllegalAccessException {
        if (hasSellPoint() && sellPointMatchBuyPoint(sellPoint)){
            this.sellPoint = sellPoint;
        }
        throw new IllegalAccessException("Object is inmutable, sell point already set");
    }

    private boolean sellPointMatchBuyPoint(InvestmentHistoricalData sellPoint) throws IllegalArgumentException{

        return true; // TODO
    }



}


