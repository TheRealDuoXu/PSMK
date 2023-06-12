package model.database.containers.Transactions;

import model.database.containers.DailyAssets.DailyAssetPK;
import model.database.containers.Inmutable;
import model.database.containers.PrimaryKey;

import java.util.Date;
@Inmutable
public class TransactionPK extends PrimaryKey {
    private static final int NUMBER_OF_FIELDS_IN_PRIMARY_KEY = 3;
    public static final int FIELD_TICKER_POS = 0;
    public static final int FIELD_DATE_POS = 1;
    public static final int FIELD_PORTFOLIO_UUID_POS = 2;

    private TransactionPK(String[] data) {
        super(data);
    }

    //TODO make this method check for data integrity
    public static TransactionPK getInstance(String ...data){
        if (data.length == NUMBER_OF_FIELDS_IN_PRIMARY_KEY) {
            return new TransactionPK(data);
        }
        throw new IllegalArgumentException("Exceeds number of fields in schema, number of fields expected: " + NUMBER_OF_FIELDS_IN_PRIMARY_KEY
                + " you introduced: " + data.length);
    }
    @Override
    public String[] getData() {
        return data;
    }
    public String getTicker(){
        return data[FIELD_TICKER_POS];
    }

    public Date getDate(){
        return parseDate(data[FIELD_DATE_POS]);
    }
    public String getStrDate(){
        return data[FIELD_DATE_POS];
    }

    public String getPortfolioUUID(){
        return data[FIELD_PORTFOLIO_UUID_POS];
    }

    @Override
    public int compareTo(PrimaryKey o) {
        return 0;
    }
}
