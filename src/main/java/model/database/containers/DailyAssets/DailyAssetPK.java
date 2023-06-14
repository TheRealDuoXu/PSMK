package model.database.containers.DailyAssets;

import model.database.containers.Inmutable;
import model.database.containers.PrimaryKey;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Inmutable
public class DailyAssetPK extends PrimaryKey {
    public static final int NUMBER_OF_FIELDS_IN_PK = 2;
    private final Ticker ticker;
    private final Date date;
    private static final int HISTORIC_YEARS_INTERVAL = 2023 - 1990;
    public static final int FIELD_TICKET_POS = 0;
    public static final int FIELD_DATE_POS = 1;
    private int currentComparisonMode;

    public DailyAssetPK(Ticker ticker, Date date) {
        this.ticker = ticker;
        this.date = date;
    }

    public DailyAssetPK(Ticker ticker, Date date, int currentComparisonMode) {
        this.ticker = ticker;
        this.date = date;
        this.currentComparisonMode = currentComparisonMode;
    }

    public DailyAssetPK(String ticker, String date) {
        this.ticker = Ticker.getInstance(ticker);
        this.date = parseDate(date);
    }

    public Ticker getTicket() {
        return ticker;
    }
    public String getStrTicket() {
        return ticker.getTicker();
    }

    public String getStrDate() {
        return date.toString();
    }

    public Date getDate() {
        return date;
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
        String otherTicket = other.getStrTicket();

        // While Date stores millis as a long starting from 1970 Jan 1, long is signed and will not fail
        LocalDate thisDate = this.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate otherDate = other.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // We use 1000 because it's round and will provide buffer for leap years, any int bigger or equal as 366 will do
        return this.getStrTicket().compareTo(otherTicket) * HISTORIC_YEARS_INTERVAL * 1000
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

    public void setCurrentComparisonMode(int currentComparisonMode) {
        this.currentComparisonMode = currentComparisonMode;
    }

    @Override
    public int length() {
        return NUMBER_OF_FIELDS_IN_PK;
    }
}
