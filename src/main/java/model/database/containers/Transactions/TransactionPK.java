package model.database.containers.Transactions;

import model.database.containers.Inmutable;
import model.database.containers.Portfolio.PortfolioPK;
import model.database.containers.PrimaryKey;

import java.util.Date;

@Inmutable
public class TransactionPK extends PrimaryKey {
    private final Ticker ticker;
    private final Date date;
    private final PortfolioPK portfolioUUID;
    private static final int NUMBER_OF_FIELDS_IN_PRIMARY_KEY = 3;
    public static final int FIELD_TICKER_POS = 0;
    public static final int FIELD_DATE_POS = 1;
    public static final int FIELD_PORTFOLIO_UUID_POS = 2;


    public TransactionPK(Ticker ticker, Date date, PortfolioPK portfolioUUID) {
        this.ticker = ticker;
        this.date = date;
        this.portfolioUUID = portfolioUUID;
    }

    public String getTicker(){
        return ticker.getTicker();
    }

    public Date getDate(){
        return date;
    }
    public String getStrDate(){
        return date.toString();
    }

    public PortfolioPK getPortfolioUUID(){
        return portfolioUUID;
    }

    @Override
    public int compareTo(PrimaryKey o) {
        return 0;
    }

    @Override
    public int length() {
        return NUMBER_OF_FIELDS_IN_PRIMARY_KEY;
    }
}
