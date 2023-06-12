package model.database.containers.Transactions;

import model.database.containers.DailyAssets.AssetDescription;
import model.database.containers.Description;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Describes a Transaction, does not contain all intermediary transactions, only the initial aka first trade
 * and the last trade known dates. To access all intermediary transactions, access {@link TransactionMap}
 */
public class TransactionDescription implements Comparable<TransactionDescription>, Description {
    AssetDescription assetDescription;
    private Date recordInitialDate, recordFinalDate;
    double totalVolume;
    double remainder;

    public TransactionDescription(AssetDescription assetDescription, Date recordInitialDate, Date recordFinalDate, double totalVolume, double remainder) {
        this.assetDescription = assetDescription;
        this.recordInitialDate = recordInitialDate;
        this.recordFinalDate = recordFinalDate;
        this.totalVolume = totalVolume;
        this.remainder = remainder;
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
                "amount: " + totalVolume;
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

    public double getTotalVolume() {
        return totalVolume;
    }

    public double getRemainder() {
        return remainder;
    }

    public void setAssetDescription(AssetDescription assetDescription) {
        this.assetDescription = assetDescription;
    }

    public void setRecordInitialDate(Date recordInitialDate) {
        this.recordInitialDate = recordInitialDate;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public void setRemainder(double remainder) {
        this.remainder = remainder;
    }

    public void setRecordFinalDate(Date recordFinalDate) {
        this.recordFinalDate = recordFinalDate;
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
