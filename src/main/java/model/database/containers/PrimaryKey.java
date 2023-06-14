package model.database.containers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class PrimaryKey implements Comparable<PrimaryKey>{
    public static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }
    public final static class Ticker implements Comparable<Ticker>{
        private String ticker;

        public Ticker(String ticker) {
            this.ticker = ticker;
        }

        public Ticker() {
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
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
