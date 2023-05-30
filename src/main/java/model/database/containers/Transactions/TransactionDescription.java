package model.database.containers.Transactions;

import model.database.containers.DailyAssets.AssetDescription;
import model.database.containers.Description;

import java.util.Date;

public class TransactionDescription implements Comparable<TransactionDescription>,Description {
    AssetDescription assetDescription;
    Date recordInitialDate, recordFinalDate;

    public static final int NUMBER_OF_PARAMETERS = AssetDescription.NUMBER_OF_PARAMETERS + 2;
    @Override
    public int compareTo(TransactionDescription o) {
        return 0;
    }

    @Override
    public String describe() {
        return null;
    }

    @Override
    public String[] toArray() {
        return new String[0];
    }

    public enum TransactionType{

    }
}
