package model.database.containers.Transactions;

import model.database.containers.DailyAssets.InvestmentHistoricalMap;

public class TransactionRemainder<InvestmentHistoricalData extends InvestmentHistoricalMap> {
    /**
     * Represents entity from table ActiveInvestment which works as a cache for Portfolio, computing the resulting
     * remainder from all the stock transactions to avoid consulting the big historical data
     */
    InvestmentHistoricalData asset;
    float remainder;

    public TransactionRemainder(InvestmentHistoricalData asset, float remainder) {
        this.asset = asset;
        this.remainder = remainder;
    }
}
