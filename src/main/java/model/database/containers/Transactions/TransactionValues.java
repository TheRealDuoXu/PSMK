package model.database.containers.Transactions;

import model.database.containers.Inmutable;
import model.database.containers.Values;

@Inmutable
public class TransactionValues extends Values<Float> {
    private static final int NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES = 1;
    private static final int FIELD_AMOUNT_TRADED_POS = 3;

    private TransactionValues(Float[] data) {
        super(data);
    }

    public static TransactionValues getInstance(Float... data) {
        if (data.length == NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES) {
            return new TransactionValues(data);
        }
        throw new IllegalArgumentException("Exceeds number of fields in schema, number of fields expected: " + NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES
                + " you introduced: " + data.length);
    }

    public float getAmountTraded(){
        return data[FIELD_AMOUNT_TRADED_POS];
    }
    public float getAbsAmountTraded(){
        return StrictMath.abs(data[FIELD_AMOUNT_TRADED_POS]);
    }

}

