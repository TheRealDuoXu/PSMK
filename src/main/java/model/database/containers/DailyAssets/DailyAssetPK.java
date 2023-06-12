package model.database.containers.DailyAssets;

import model.database.containers.Inmutable;
import model.database.containers.PrimaryKey;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Inmutable
public class DailyAssetPK extends PrimaryKey {
    // todo
    private static final int HISTORIC_YEARS_INTERVAL = 2023 - 1990;
    private static final int NUMBER_OF_FIELDS_IN_PRIMARY_KEY = 2;
    public static final int FIELD_TICKET_POS = 0;
    public static final int FIELD_DATE_POS = 1;
    private int currentComparisonMode;

    private DailyAssetPK(String[] data) {
        super(data);
    }

    //TODO make this method check for data integrity
    public static DailyAssetPK getInstance(String... data) {
        if (data.length == NUMBER_OF_FIELDS_IN_PRIMARY_KEY) {
            return new DailyAssetPK(data);
        }
        throw new IllegalArgumentException("Exceeds number of fields in schema, number of fields expected: " + NUMBER_OF_FIELDS_IN_PRIMARY_KEY
                + " you introduced: " + data.length);
    }

    public String getTicket() {
        return data[FIELD_TICKET_POS];
    }

    public String getStrDate() {
        return data[FIELD_DATE_POS];
    }

    public Date getDate() {
        return parseDate(data[FIELD_DATE_POS]);
    }

    @Override
    public String[] getData() {
        return new String[]{getTicket(), getStrDate()};
    }

    /**
     * Comparison will be performed on two primary keys of DailyAssets, ordering will occur based on Ticker and Date
     *
     * @param otherPrimaryKey the object to be compared.
     * @return comparison based on unicode values of two strings and {@link LocalDate}. The Strings comparison adds more
     * weight
     */
    @Override
    public int compareTo(PrimaryKey otherPrimaryKey) {
        checkNullSafety(otherPrimaryKey);         // following recommendations from Comparable's javadoc
        checkClassCastSafety(otherPrimaryKey);    // following recommendations from Comparable's javadoc

        DailyAssetPK other = (DailyAssetPK) otherPrimaryKey;
        String otherTicket = other.getTicket();

        // While Date stores millis as a long starting from 1970 Jan 1, long is signed and will not fail
        LocalDate thisDate = this.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate otherDate = other.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // We use 1000 because it's round and will provide buffer for leap years, any int bigger or equal as 366 will do
        return this.getTicket().compareTo(otherTicket) * HISTORIC_YEARS_INTERVAL * 1000
                + thisDate.until(otherDate).getDays();
    }

    private void checkClassCastSafety(Object other) throws ClassCastException {
        if (!(other instanceof DailyAssetPK)) {
            throw new ClassCastException("Class does not match, cannot compare");
        }
    }

    private void checkNullSafety(Object other) throws NullPointerException {
        if (other == null) {
            throw new NullPointerException("Cannot compare to null");
        }
    }
}
