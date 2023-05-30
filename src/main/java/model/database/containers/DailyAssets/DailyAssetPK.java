package model.database.containers.DailyAssets;

import model.database.containers.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyAssetPK extends PrimaryKey implements Comparable<DailyAssetPK> {
    private static final int NUMBER_OF_FIELDS_IN_PRIMARY_KEY = 2;
    public static final int COMPARISON_MODE_ORDER_BY_DATE = 0;
    public static final int COMPARISON_MODE_ORDER_BY_TICKET = 1;
    private int currentComparisonMode;

    private DailyAssetPK(String[] data) {
        super(data);
        currentComparisonMode = COMPARISON_MODE_ORDER_BY_DATE;
    }
    public static DailyAssetPK getInstance(String ...data) {
        if (data.length == NUMBER_OF_FIELDS_IN_PRIMARY_KEY) {
            return new DailyAssetPK(data);
        }
        throw new IllegalArgumentException("Exceeds number of fields in schema, number of fields expected: " + NUMBER_OF_FIELDS_IN_PRIMARY_KEY
                + " you introduced: " + data.length);
    }

    public String getTicket() {
        return data[0];
    }

    public String getStrDate() {
        return data[1];
    }
    public Date getDate(){
        return parseDate(data[1]);
    }

    public void setCurrentComparisonMode(int newComparisonMode) {
        if (newComparisonMode == COMPARISON_MODE_ORDER_BY_TICKET || newComparisonMode == COMPARISON_MODE_ORDER_BY_DATE) {
            currentComparisonMode = newComparisonMode;
        } else {
            throw new IllegalArgumentException("Must be one of the defined options in DailyAssetPrimaryKey");
        }
    }

    @Override
    public String[] getData() {
        return new String[]{getTicket(), getStrDate()};
    }

    @Override
    public int compareTo(DailyAssetPK other) {
        checkNullSafety(other);         // following recommendations from Comparable's javadoc
        checkClassCastSafety(other);    // following recommendations from Comparable's javadoc

        switch (currentComparisonMode) {
            case COMPARISON_MODE_ORDER_BY_DATE:
                return compareDateTo(other);
            case COMPARISON_MODE_ORDER_BY_TICKET:
                return compareTicketTo(other);
            default:
                throw new RuntimeException("Current comparison mode invalid");
        }
    }

    private int compareDateTo(DailyAssetPK other) {
        Date otherDate = parseDate(other.getStrDate());
        Date thisDate = parseDate(this.getStrDate());

        return thisDate.compareTo(otherDate);
    }

    private int compareTicketTo(DailyAssetPK other) {
        String otherTicket = other.getTicket();
        String thisTicket = this.getTicket();

        return thisTicket.compareTo(otherTicket);
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
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
