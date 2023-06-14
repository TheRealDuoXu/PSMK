package model.database.containers.Transactions;

import model.database.containers.Inmutable;
import model.database.containers.Values;

@Inmutable
public class TransactionValues extends Values {
    private static final int NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES = 1;
    private final double amount;

    public TransactionValues(double amount) {
        this.amount = amount;
    }

    public static TransactionValues getInstance(double amount) {
        return new TransactionValues(amount);
    }

    public double getAmountTraded(){
        return amount;
    }
    public double getAbsAmountTraded(){
        return StrictMath.abs(amount);
    }

    @Override
    public int length() {
        return NUMBER_OF_FIELDS_IN_TRANSACTION_VALUES;
    }
}

