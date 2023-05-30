package model.database.containers.Transactions;

import model.database.containers.DailyAssets.DailyAssetPK;
import model.database.containers.PrimaryKey;

public class TransactionPK extends PrimaryKey {
    private static final int NUMBER_OF_FIELDS_IN_PRIMARY_KEY = 2;
    private TransactionPK(String[] data) {
        super(data);
    }

    public static TransactionPK getInstance(String ...data){
        if (data.length == NUMBER_OF_FIELDS_IN_PRIMARY_KEY) {
            return new TransactionPK(data);
        }
        throw new IllegalArgumentException("Exceeds number of fields in schema, number of fields expected: " + NUMBER_OF_FIELDS_IN_PRIMARY_KEY
                + " you introduced: " + data.length);
    }
    @Override
    public String[] getData() {
        return new String[0];
    }
}
