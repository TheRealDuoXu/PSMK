package data.portfolio.historical;

import data.database.PrimaryKey;
import data.database.Values;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public abstract class InvestmentHistoricalData implements Comparable<InvestmentHistoricalData> {
    /**
     * Represents any kind of historical data been treated in this programme
     */
    public final static int ARRAY_POS_TICKER = 0;
    public final static int ARRAY_POS_STOCK_EXCHANGE = 1;
    public final static int ARRAY_POS_TYPE = 2;
    public final static int ARRAY_POS_DATE = 3;
    public final static int ARRAY_POS_OPEN = 4;
    public final static int ARRAY_POS_HIGH = 5;
    public final static int ARRAY_POS_LOW = 6;
    public final static int ARRAY_POS_CLOSE = 7;
    public final static int ARRAY_POS_VOL = 8;

    private final static int PRIMARY_KEY_POS_TICKER = 0;
    private final static int VALUES_POS_STOCK_EXCHANGE = 0;
    private final static int VALUES_POS_TYPE = 1;
    private final static int PRIMARY_KEY_POS_DATE = 1;
    private final static int VALUES_POS_OPEN = 2;
    private final static int VALUES_POS_HIGH = 3;
    private final static int VALUES_POS_LOW = 4;
    private final static int VALUES_POS_CLOSE = 5;
    private final static int VALUES_POS_VOL = 6;
    LinkedHashMap<PrimaryKey, Values<String>> map;
    String ticket, stockExchange;
    char type;

    protected InvestmentHistoricalData(LinkedHashMap<PrimaryKey, Values<String>> map) {
        this.map = map;
    }

    public abstract InvestmentHistoricalData getInstance(LinkedHashMap<PrimaryKey, Values<String>> map);

    @Override
    public abstract String toString();

    public void toCSVFile() {
        String[][] data = toArray();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(row[i]);
                    if (i < row.length - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.out.println("Error writing CSV file: " + e.getMessage());
        }
    }

    public String[][] toArray() {
        Set<Map.Entry<PrimaryKey, Values<String>>> entrySet = map.entrySet();
        Iterator<Map.Entry<PrimaryKey, Values<String>>> it = entrySet.iterator();
        Map.Entry<PrimaryKey, Values<String>> entry = it.next();


        int row_size = entrySet.size();
        int col_size = entry.getValue().getData().length + entry.getKey().getData().length;

        String[][] strTable = new String[row_size][col_size];

        int i = 0;
        PrimaryKey tmpKey;
        Values<String> tmpValues;
        while (it.hasNext()) {
            tmpKey = entry.getKey();
            tmpValues = entry.getValue();

            String[] strTableRow = strTable[i];

            populateStrTableRow(tmpKey, tmpValues, strTableRow);

            entry = it.next();
            i++;
        }

        return strTable;
    }

    private void populateStrTableRow(PrimaryKey tmpKey, Values<String> tmpValues, String[] nextRow) {
        nextRow[ARRAY_POS_TICKER] = tmpKey.getData()[PRIMARY_KEY_POS_TICKER]; // Ticket
        nextRow[ARRAY_POS_STOCK_EXCHANGE] = tmpValues.getData()[VALUES_POS_STOCK_EXCHANGE]; // Stock_exchange
        nextRow[ARRAY_POS_TYPE] = tmpValues.getData()[VALUES_POS_TYPE]; // Type
        nextRow[ARRAY_POS_DATE] = tmpKey.getData()[PRIMARY_KEY_POS_DATE]; // Date
        nextRow[ARRAY_POS_OPEN] = tmpValues.getData()[VALUES_POS_OPEN]; // Open
        nextRow[ARRAY_POS_HIGH] = tmpValues.getData()[VALUES_POS_HIGH]; // High
        nextRow[ARRAY_POS_LOW] = tmpValues.getData()[VALUES_POS_LOW]; // Low
        nextRow[ARRAY_POS_CLOSE] = tmpValues.getData()[VALUES_POS_CLOSE]; // Close
        nextRow[ARRAY_POS_VOL] = tmpValues.getData()[VALUES_POS_VOL]; // Vol
    }

    public String[] toFirstRowArray() {
        Set<Map.Entry<PrimaryKey, Values<String>>> entrySet = map.entrySet();
        Iterator<Map.Entry<PrimaryKey, Values<String>>> it = entrySet.iterator();
        Map.Entry<PrimaryKey, Values<String>> entry = it.next();

        PrimaryKey tmpKey = entry.getKey();
        Values<String> tmpValues = entry.getValue();

        String[] firstRow = new String[tmpKey.getData().length + tmpValues.getData().length];

        populateStrTableRow(tmpKey, tmpValues, firstRow);

        return firstRow;
    }

    @Override
    public abstract int compareTo(InvestmentHistoricalData other);

    public abstract boolean equals();

    public abstract char getType();

    public ArrayDeque<Integer> toClassMarks(Function<Integer, ArrayDeque<Integer>> mapper) {
        return null;
    }
}