package data.portfolio.transaction;

import data.portfolio.historical.InvestmentHistoricalData;

public class TransactionRemainder<InvestmentHistoricalData extends data.portfolio.historical.InvestmentHistoricalData> {
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
