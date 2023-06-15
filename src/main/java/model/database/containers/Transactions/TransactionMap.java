package model.database.containers.Transactions;

import model.database.containers.DailyAssets.AssetDescription;
import model.database.containers.Inmutable;
import model.database.containers.PrimaryKey;
import model.database.dao.DailyAssetsDAO;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TransactionMap extends OrderedDequeTransactionMap {
    private TreeMap<Date, TransactionValues> transactionRecord; // TreeMap will auto-index based on dates
    public final TransactionPK.Ticker ticker; // Useful for comparing with other tickers
    private TransactionDescription transactionDescription;
    private Iterator<Entry<Date, TransactionValues>> entryIterator;
    private boolean pendingDescriptionUpdate = false;

    public TransactionMap(TransactionPK.Ticker ticker, TreeMap<Date, TransactionValues> transactionRecord) {
        this.ticker = ticker;
        this.transactionRecord = transactionRecord;
        this.transactionDescription = initTransactionDescription(ticker);
    }

    public TransactionMap(TransactionPK.Ticker ticker, Date date, double amount) {
        this.ticker = ticker;
        this.transactionRecord = new TreeMap<Date, TransactionValues>();
        TransactionValues values = TransactionValues.getInstance(amount);

        this.transactionRecord.put(date, values);

        this.transactionDescription = initTransactionDescription(ticker);
    }

    public TransactionMap(TransactionPK.Ticker ticker) {
        this.transactionRecord = new TreeMap<>();
        this.ticker = ticker;
    }

    /**
     * Implies transaction description from transaction record's every entry.
     *
     * @return An instance of {@link TransactionDescription}
     */
    private TransactionDescription initTransactionDescription(TransactionPK.Ticker ticker) {
        AssetDescription assetDescription;
        Date recordInitialDate, recordFinalDate;
        double totalVolume, remainder;

        DailyAssetsDAO assetsDAO = DailyAssetsDAO.getInstance();

        assetDescription = assetsDAO.getAssetDescription(ticker.getTicker());
        recordInitialDate = transactionRecord.firstEntry().getKey();
        recordFinalDate = transactionRecord.lastEntry().getKey();

        totalVolume = transactionRecord.values().stream()
                .mapToDouble(TransactionValues::getAbsAmountTraded)
                .sum();
        remainder = transactionRecord.values().stream()
                .mapToDouble(TransactionValues::getAmountTraded)
                .sum();

        this.pendingDescriptionUpdate = false;
        return new TransactionDescription(assetDescription, recordInitialDate, recordFinalDate, totalVolume, remainder);
    }

    public void updateTransactionDescription() {
        Date recordInitialDate, recordFinalDate;
        double totalVolume, remainder;

        recordInitialDate = transactionRecord.firstEntry().getKey();
        recordFinalDate = transactionRecord.lastEntry().getKey();

        totalVolume = transactionRecord.values().stream()
                .mapToDouble(TransactionValues::getAbsAmountTraded)
                .sum();
        remainder = transactionRecord.values().stream()
                .mapToDouble(TransactionValues::getAmountTraded)
                .sum();

        this.transactionDescription.setRecordFinalDate(recordFinalDate);
        this.transactionDescription.setRecordInitialDate(recordInitialDate);
        this.transactionDescription.setTotalVolume(totalVolume);
        this.transactionDescription.setRemainder(remainder);
        this.pendingDescriptionUpdate = false;
    }

    private void verifyTransactionMapHoldsSameAsset(TransactionPK.Ticker otherTicker) throws IllegalArgumentException {
        if (isCompatible(otherTicker)) return;
        else throw new IllegalArgumentException();
    }

    public boolean isCompatible(TransactionPK.Ticker ticker){
        if (isFirstRecordEntry()) return true;
        else return this.ticker.equals(ticker);
    }

    private boolean isFirstRecordEntry() {
        return this.transactionRecord.isEmpty();
    }

    public TransactionDescription getTransactionDescription() {
        if (pendingDescriptionUpdate)
            updateTransactionDescription();

        return transactionDescription;
    }

    public TransactionPK.Ticker getTicker() {
        return ticker;
    }

    @Override
    public int size() {
        return transactionRecord.size();
    }

    @Override
    public boolean isEmpty() {
        return transactionRecord.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return transactionRecord.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return transactionRecord.containsValue(value);
    }

    @Override
    public synchronized TransactionValues get(Object key) {
        return transactionRecord.get(key);
    }


    @Override
    public synchronized TransactionValues remove(Object key) {
        TransactionValues values = transactionRecord.remove(key); // just fulfilling Map contract
        updateTransactionDescription();
        return values;
    }

    /**
     * As this is the default method to add entries to the map, it does not update its description. This allows for
     * faster encapsulation of data.
     * To "add" and update, use add method.
     */
    @Override
    public synchronized TransactionValues put(TransactionPK.Ticker ticker, Date date, TransactionValues value) throws IllegalArgumentException {
        verifyTransactionMapHoldsSameAsset(ticker);
        TransactionValues values = transactionRecord.put(date, value);

        this.pendingDescriptionUpdate = true;
        return values;
    }

    /**
     * As this is the default method to add entries to the map, it does not update its description. This allows for
     * faster encapsulation of data.
     * To "add" and update, use add method.
     */
    @Override
    public synchronized TransactionValues put(TransactionPK pk, TransactionValues value) throws IllegalArgumentException {
        verifyTransactionMapHoldsSameAsset(TransactionPK.Ticker.getInstance(pk.getTicker()));
        TransactionValues values = transactionRecord.put(pk.getDate(), value);

        this.pendingDescriptionUpdate = true;
        return values;
    }

    /**
     * WARNING! Does not perform integrity check on all data, just on the ticker you provided. Put will throw exception
     * if ticker does not match first item's ticker.
     * Put does not update transaction description.
     *
     * @param map mappings to be stored in this map
     */
    @Override
    public synchronized void putAll(TransactionPK.Ticker ticker, Map<? extends Date, ? extends TransactionValues> map) throws IllegalArgumentException {
        synchronized (this) {
            verifyTransactionMapHoldsSameAsset(ticker);
            transactionRecord.putAll(map);

            this.pendingDescriptionUpdate = true;
        }
    }

    @Override
    public synchronized void clear() {
        transactionRecord.clear();
        this.transactionDescription = null;
    }

    @Override
    public Comparator<? super Date> comparator() {
        return transactionRecord.comparator();
    }

    @Override
    public SortedMap<Date, TransactionValues> subMap(Date fromDate, Date toDate) {
        return transactionRecord.subMap(fromDate, toDate);
    }

    @Override
    public SortedMap<Date, TransactionValues> headMap(Date toDate) {
        return transactionRecord.headMap(toDate);
    }

    @Override
    public SortedMap<Date, TransactionValues> tailMap(Date fromDate) {
        return transactionRecord.tailMap(fromDate);
    }

    @Override
    public Date firstKey() {
        return transactionRecord.firstKey();
    }

    @Override
    public Date lastKey() {
        return transactionRecord.lastKey();
    }

    @Override
    public Set<Date> keySet() {
        return transactionRecord.keySet();
    }

    @Override
    public Collection<TransactionValues> values() {
        return transactionRecord.values();
    }

    @Override
    public Set<Entry<Date, TransactionValues>> entrySet() {
        return transactionRecord.entrySet();
    }


    public LinkedHashSet<TransactionEntry> transactionPointSet() {
        LinkedHashSet<TransactionEntry> transactionEntries = new LinkedHashSet<>();
        for (Entry<Date, TransactionValues> entry :
                transactionRecord.entrySet()) {
            transactionEntries.add(new TransactionEntry(entry.getKey(), entry.getValue()));
        }
        return transactionEntries;
    }

    @Override
    public synchronized TransactionEntry removeFirst() {
        return pollFirst();
    }

    @Override
    public synchronized TransactionEntry removeLast() {
        return pollLast();
    }

    @Override
    public synchronized TransactionEntry pollFirst() {
        if (this.transactionRecord.isEmpty()) return null;

        Iterator<Date> it = this.transactionRecord.keySet().iterator();
        TransactionEntry transactionEntry;
        Date firstDate = it.next();

        transactionEntry = new TransactionEntry(firstDate, this.transactionRecord.get(firstDate));
        this.transactionRecord.remove(firstDate);

        updateTransactionDescription();
        return transactionEntry;
    }

    @Override
    public synchronized TransactionEntry pollLast() {
        if (this.transactionRecord.isEmpty()) return null;

        Stream<Date> stream = this.transactionRecord.keySet().stream();
        TransactionEntry transactionEntry;
        Date lastDate = stream.reduce((first, second) -> second).orElseThrow();

        transactionEntry = new TransactionEntry(lastDate, this.transactionRecord.get(lastDate));
        this.transactionRecord.remove(lastDate);

        updateTransactionDescription();
        return transactionEntry;
    }

    @Override
    public TransactionEntry peekFirst() {
        if (this.transactionRecord.isEmpty()) return null;

        Iterator<Date> it = this.transactionRecord.keySet().iterator();
        TransactionEntry transactionEntry;
        Date firstDate = it.next();

        transactionEntry = new TransactionEntry(firstDate, this.transactionRecord.get(firstDate));

        return transactionEntry;
    }

    @Override
    public TransactionEntry peekLast() {
        if (this.transactionRecord.isEmpty()) return null;

        Stream<Date> stream = this.transactionRecord.keySet().stream();
        TransactionEntry transactionEntry;
        Date lastDate = stream.reduce((first, second) -> second).orElseThrow();

        transactionEntry = new TransactionEntry(lastDate, this.transactionRecord.get(lastDate));

        return transactionEntry;
    }

    @Override
    public synchronized TransactionEntry poll() {
        return pollFirst();
    }

    @Override
    public synchronized TransactionEntry pop() {
        if (this.transactionRecord.isEmpty()) throw new NullPointerException();
        return pollFirst();
    }

    @Override
    public TransactionEntry peek() {
        return peekFirst();
    }

    /**
     * WARNING! Performs only one inspection on ticker, it assumes all following entries are for the same ticker.
     * Version of putAll method that does not throw exception, but instead returns a boolean indicating if operation was
     * successful or not.
     *
     * @param collection collections of entries to be added
     * @return true if success, false if transactionPoint failed to match ticket
     */
    @Override
    public synchronized boolean addAll(TransactionPK.Ticker ticker, Collection<? extends TransactionEntry> collection) {
        try {
            verifyTransactionMapHoldsSameAsset(ticker);

            for (TransactionEntry transactionEntry :
                    collection) {
                this.transactionRecord.put(transactionEntry.date, transactionEntry.values);
            }
            updateTransactionDescription();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Version of put method that does not throw exception, but instead returns a boolean indicating if operation was
     * successful or not. Add always updates
     *
     * @param transactionEntry the entry to be added
     * @return true if success, false if transactionPoint failed to match ticket
     */
    @Override
    public synchronized boolean add(TransactionPK.Ticker ticker, TransactionEntry transactionEntry) {
        try {
            verifyTransactionMapHoldsSameAsset(ticker);
            this.transactionRecord.put(transactionEntry.date, transactionEntry.values);

            updateTransactionDescription();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<? extends TransactionEntry> transactionPoints) {
        boolean containsAll = true;

        for (TransactionEntry transactionEntry :
                transactionPoints) {
            if (!this.transactionRecord.containsKey(transactionEntry.date)) {
                containsAll = false;
                break;
            }
        }

        return containsAll;
    }

    @Override
    public boolean contains(TransactionEntry transactionEntry) {
        return this.transactionRecord.containsKey(transactionEntry.date);
    }

    @Override
    public Collection<TransactionValues> getValuesSet() {
        return this.transactionRecord.values();
    }

    @Override
    public Set<Entry<Date, TransactionValues>> getEntrySet() {
        return this.entrySet();
    }

    @Override
    public Iterator<Date> keyIterator() {
        return this.keySet().iterator();
    }

    @Override
    public Iterator<TransactionValues> valuesIterator() {
        return this.transactionRecord.values().iterator();
    }

    @Override
    public Iterator<Entry<Date, TransactionValues>> entryIterator() {
        return this.transactionRecord.entrySet().iterator();
    }
    @Override
    public Iterator<Entry<Date, TransactionValues>> iterator() {
        return this.transactionRecord.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Entry<Date, TransactionValues>> action) {
        transactionRecord.entrySet().forEach(action);
    }

    @Override
    public void resetCursor() {
        this.entryIterator = transactionRecord.entrySet().iterator();
    }

    @Override
    public TransactionEntry nextEntry() {
        if (entryIterator == null) entryIterator = transactionRecord.entrySet().iterator();

        if (!hasNextEntry()) throw new NullPointerException("No more entries on iterator");

        Entry<Date, TransactionValues> entry = this.entryIterator.next();
        return new TransactionEntry(entry.getKey(), entry.getValue(), this.transactionDescription);
    }

    @Override
    public boolean hasNextEntry() {
        return entryIterator.hasNext();
    }

    @Inmutable
    public static class TransactionEntry implements Entry<Date, TransactionValues> {
        private final Date date;
        private final TransactionValues values;
        private TransactionDescription transactionDescription;

        public TransactionEntry(Date date, TransactionValues values, TransactionDescription transactionDescription) {
            this.date = date;
            this.values = values;
            this.transactionDescription = transactionDescription;
        }

        public TransactionEntry(Date date, TransactionValues values) {
            this.date = date;
            this.values = values;
        }

        public TransactionDescription getTransactionDescription() {
            return transactionDescription;
        }

        @Override
        public Date getKey() {
            return date;
        }

        @Override
        public TransactionValues getValue() {
            return values;
        }

        @Override
        public final TransactionValues setValue(TransactionValues value) {
            throw new UnsupportedOperationException("A transaction entry is supposed @Inmutable");
        }

    }
}
