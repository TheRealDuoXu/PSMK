package data.portfolio.transaction;

public class TransactionPoint<InvestmentHistoricalData extends data.portfolio.historical.InvestmentHistoricalData> {
    /**
     * Represents one transaction point from TransactionCollection. Accepts any kind of historical asset data.
     * It's called point because it's a 2d data. {@link data.portfolio.historical.InvestmentHistoricalData} and
     * tradedAmount
     */
    InvestmentHistoricalData tradedAsset;
    int tradedAmount;

    public TransactionPoint(InvestmentHistoricalData tradedAsset, int tradedAmount) {
        this.tradedAsset = tradedAsset;
        this.tradedAmount = tradedAmount;
    }
}
