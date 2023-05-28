package data.portfolio.transaction;

@Deprecated
public class AssetTransaction<InvestmentHistoricalData extends data.portfolio.historical.InvestmentHistoricalData> {
    /**
     * {@link AssetTransaction} is inmutable object that represents entry and exit point for an asset transaction
     */
    private final InvestmentHistoricalData buyPoint;
    private InvestmentHistoricalData sellPoint;
    private AssetDescription assetDescription;

    AssetTransaction(InvestmentHistoricalData buyPoint) {
        this.buyPoint = buyPoint;
        this.sellPoint = null;
    }

    AssetTransaction(InvestmentHistoricalData buyPoint, InvestmentHistoricalData sellPoint) throws IllegalArgumentException {
        if (sellPointMatchBuyPoint(buyPoint, sellPoint)) {
            this.buyPoint = buyPoint;
            this.sellPoint = sellPoint;
        } else {
            throw new IllegalArgumentException("Buy point must match sell point data");
        }
    }

    AssetTransaction(InvestmentHistoricalData buyPoint, InvestmentHistoricalData sellPoint, boolean exposeAssetDescription)
            throws IllegalArgumentException {
        this(buyPoint, sellPoint);
        this.assetDescription = initAssetDescription(buyPoint);
    }

    private AssetDescription initAssetDescription(InvestmentHistoricalData transactionPoint) {
        String[] buyPointFirstRow = transactionPoint.toFirstRowArray();
        return new AssetDescription(buyPointFirstRow[InvestmentHistoricalData.ARRAY_POS_TICKER],
                buyPointFirstRow[InvestmentHistoricalData.ARRAY_POS_STOCK_EXCHANGE],
                buyPointFirstRow[InvestmentHistoricalData.ARRAY_POS_TYPE].charAt(0));
    }

    public AssetDescription getAssetDescription(InvestmentHistoricalData transactionPoint) {
        if (assetDescription == null) {
            assetDescription = initAssetDescription(transactionPoint);
        }
        return assetDescription;
    }

    /**
     * @return true if it does have a sell point, false if it doesn't have a sell point
     */
    public boolean hasSellPoint() {
        return sellPoint != null;
    }

    public InvestmentHistoricalData getBuyPoint() {
        return buyPoint;
    }

    public InvestmentHistoricalData getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(InvestmentHistoricalData sellPoint) throws IllegalAccessException {
        if (!hasSellPoint() && sellPointMatchBuyPoint(buyPoint, sellPoint)) {
            this.sellPoint = sellPoint;
        }
        throw new IllegalAccessException("Object is inmutable, sell point already set");
    }

    private boolean sellPointMatchBuyPoint(InvestmentHistoricalData buyPoint, InvestmentHistoricalData sellPoint)
            throws IllegalArgumentException {

        AssetDescription sellPointDescription = getAssetDescription(sellPoint);
        AssetDescription buyPointDescription = getAssetDescription(buyPoint);

        if (sellPointDescription == buyPointDescription) {
            return true;
        }
        throw new IllegalArgumentException("Buy point ticker " + buyPointDescription.ticker +
                " does not match sell point ticker " + sellPointDescription.ticker);
    }

}


