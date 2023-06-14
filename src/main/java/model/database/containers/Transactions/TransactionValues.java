package model.database.containers.Transactions;

import model.database.containers.Inmutable;
import model.database.containers.Values;

@Inmutable
public class TransactionValues extends Values<Double> {
    private static final int NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES = 1;
    private static final int FIELD_AMOUNT_TRADED_POS = 3;

    private TransactionValues(Double[] data) {
        super(data);
    }

    public static TransactionValues getInstance(Double... data) {
        if (data.length == NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES) {
            return new TransactionValues(data);
        }
        throw new IllegalArgumentException("Exceeds number of fields in schema, number of fields expected: " + NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES
                + " you introduced: " + data.length);
    }

    public double getAmountTraded(){
        return data[FIELD_AMOUNT_TRADED_POS];
    }
    public double getAbsAmountTraded(){
        return StrictMath.abs(data[FIELD_AMOUNT_TRADED_POS]);
    }

}

