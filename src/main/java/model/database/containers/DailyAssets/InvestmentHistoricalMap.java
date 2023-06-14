package model.database.containers.DailyAssets;
import model.database.containers.Values;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public abstract class InvestmentHistoricalMap implements Comparable<InvestmentHistoricalMap>, Map<Date, Values<String>> {
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
    private static final short COL_DATE_LENGTH = 1;
    LinkedHashMap<java.util.Date, Values<String>> map;
    AssetDescription assetDescription;

    public InvestmentHistoricalMap(LinkedHashMap<java.util.Date, Values<String>> map) {
        this.map = map;
    }
    public InvestmentHistoricalMap(){
        this.map = new LinkedHashMap<>();
    }

    @Override
    public abstract String toString();

    @Deprecated
    public void toCSVFile() {
        //todo use fileDAO instead
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
        Set<Map.Entry<Date, Values<String>>> entrySet = map.entrySet();
        Iterator<Map.Entry<Date, Values<String>>> it = entrySet.iterator();
        Map.Entry<Date, Values<String>> entry = it.next();


        int row_size = entrySet.size();
        int col_size = entry.getValue().getData().length + COL_DATE_LENGTH;

        String[][] strTable = new String[row_size][col_size];

        int i = 0;
        Date tmpKey;
        Values<String> tmpValues;
        while (it.hasNext()) {
            tmpKey = entry.getKey();
            tmpValues = entry.getValue();

            String[] strTableRow = strTable[i];

            populateStrTableRow(new DailyAssetPK(assetDescription.getTicker(), tmpKey), tmpValues, strTableRow);

            entry = it.next();
            i++;
        }

        return strTable;
    }

    private void populateStrTableRow(DailyAssetPK tmpKey, Values<String> tmpValues, String[] nextRow) {
        nextRow[ARRAY_POS_TICKER] = tmpKey.getStrTicket(); // Ticket
        nextRow[ARRAY_POS_STOCK_EXCHANGE] = tmpValues.getData()[VALUES_POS_STOCK_EXCHANGE]; // Stock_exchange
        nextRow[ARRAY_POS_TYPE] = tmpValues.getData()[VALUES_POS_TYPE]; // Type
        nextRow[ARRAY_POS_DATE] = tmpKey.getStrDate(); // Date
        nextRow[ARRAY_POS_OPEN] = tmpValues.getData()[VALUES_POS_OPEN]; // Open
        nextRow[ARRAY_POS_HIGH] = tmpValues.getData()[VALUES_POS_HIGH]; // High
        nextRow[ARRAY_POS_LOW] = tmpValues.getData()[VALUES_POS_LOW]; // Low
        nextRow[ARRAY_POS_CLOSE] = tmpValues.getData()[VALUES_POS_CLOSE]; // Close
        nextRow[ARRAY_POS_VOL] = tmpValues.getData()[VALUES_POS_VOL]; // Vol
    }

    public String[] toFirstRowArray() {
        Set<Map.Entry<Date, Values<String>>> entrySet = map.entrySet();
        Iterator<Map.Entry<Date, Values<String>>> it = entrySet.iterator();
        Map.Entry<Date, Values<String>> entry = it.next();

        DailyAssetPK tmpKey = new DailyAssetPK(assetDescription.getTicker(), entry.getKey());
        Values<String> tmpValues = entry.getValue();

        String[] firstRow = new String[DailyAssetPK.NUMBER_OF_FIELDS_IN_PK + tmpValues.getData().length];

        populateStrTableRow(tmpKey, tmpValues, firstRow);

        return firstRow;
    }

    @Override
    public abstract int compareTo(InvestmentHistoricalMap other);

    public abstract boolean equals();

    public abstract AssetDescription.AssetType getType();

    public ArrayDeque<Integer> toClassMarks(Function<Integer, ArrayDeque<Integer>> mapper) {
        return null;
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Values<String> get(Object key) {
        return map.get(key);
    }

    @Override
    public Values<String> put(Date key, Values<String> value) {
        return map.put(key,value);
    }

    @Override
    public Values<String> remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Date, ? extends Values<String>> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Date> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Values<String>> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Date, Values<String>>> entrySet() {
        return map.entrySet();
    }
}
