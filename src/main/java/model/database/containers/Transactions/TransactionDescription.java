package model.database.containers.Transactions;

import model.database.containers.DailyAssets.AssetDescription;
import model.database.containers.Description;
import model.database.containers.Inmutable;

import java.util.Date;

/**
 * Describes a Transaction, does not contain all intermediary transactions, only the initial aka first trade
 * and the last trade known. To access all intermediary transactions, access {@link TransactionMap}
 * <p>
 * This is an inmutable object, the user shall not edit as all fields are final
 */
@Inmutable
public class TransactionDescription implements Comparable<TransactionDescription>, Description {
    public static final int NUMBER_OF_PARAMETERS = AssetDescription.NUMBER_OF_PARAMETERS + 2;
    final AssetDescription assetDescription;
    final Date recordInitialDate, recordFinalDate;
    final float amount;

    public TransactionDescription(AssetDescription assetDescription, Date recordInitialDate, Date recordFinalDate, float amount) {
        this.assetDescription = assetDescription;
        this.recordInitialDate = recordInitialDate;
        this.recordFinalDate = recordFinalDate;
        this.amount = amount;
    }

    /**
     * Allows ordering by date
     *
     * @param other the object to be compared.
     * @return negative represents that other is bigger (further away) than this, and viceversa
     */
    @Override
    public int compareTo(TransactionDescription other) {
        if (other.recordFinalDate != null) {
            return this.recordFinalDate.compareTo(other.recordFinalDate);
        }
        throw new NullPointerException("The comparison object does not have a final date" +
                ". therefor it's not closed, and cannot be compared to");
    }

    @Override
    public String describe() {
        return  "asset traded: " + assetDescription.describe() +
                "started: " + recordInitialDate.toString() + "\n " +
                "finished: " + recordFinalDate.toString() +
                "amount: " + amount;
    }

    @Override
    public String shortDescribe() {
        return null;
    }

    @Override
    public String[] toArrayDescription() {
        //todo
        return new String[0];
    }

    public AssetDescription getAssetDescription() {
        return assetDescription;
    }

    public Date getRecordInitialDate() {
        return recordInitialDate;
    }

    public Date getRecordFinalDate() {
        return recordFinalDate;
    }

    public float getAmount() {
        return amount;
    }

    public enum TransactionType {
        LENDING("Lending"), BORROWING("Borrowing"),
        SHORT("Leveraged short"), LONG("Leveraged long");

        private String description;

        TransactionType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
