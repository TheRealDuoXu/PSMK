package model.database.containers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class PrimaryKey implements Comparable<PrimaryKey> {
    public static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }

    public abstract int length();

    public final static class Ticker implements Comparable<Ticker> {
        private final static int MAX_TICKER_VARCHAR_LENGTH = 10;
        private String ticker;

        private Ticker(String ticker) {
            this.ticker = ticker;
        }

        public static Ticker getInstance(String ticker) {
            if (ticker.length() <= MAX_TICKER_VARCHAR_LENGTH) return new Ticker(ticker);
            else throw new IllegalArgumentException("Ticker exceeds max length");
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            if (ticker.length() <= MAX_TICKER_VARCHAR_LENGTH) this.ticker = ticker;
            else throw new IllegalArgumentException("Ticker exceeds max length");
        }

        @Override
        public String toString() {
            return ticker;
        }

        @Override
        public int compareTo(Ticker o) {
            return this.ticker.compareTo(o.getTicker());
        }

        public boolean equals(Ticker o) {
            return this.ticker.equals(o.ticker);
        }
    }
}
