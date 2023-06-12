package model.database.containers.Transactions;

import model.database.containers.DailyAssets.AssetDescription;
import model.database.containers.Inmutable;
import model.database.dao.DailyAssetsDAO;

import java.util.*;
import java.util.stream.Stream;

public class TransactionMap implements OrderedDequeTransactionMap {

    private final TreeMap<TransactionPK, TransactionValues> transactionRecord;
    private TransactionDescription transactionDescription;

    public TransactionMap(TreeMap<TransactionPK, TransactionValues> transactionRecord) {
        this.transactionRecord = transactionRecord;
        this.transactionDescription = initTransactionDescription();
    }

    public TransactionMap(TransactionPK pk, Float... data) {
        this.transactionRecord = new TreeMap<TransactionPK, TransactionValues>();
        TransactionValues values = TransactionValues.getInstance(data);

        this.transactionRecord.put(pk, values);

        this.transactionDescription = initTransactionDescription();
    }

    /**
     * Implies transaction description from transaction record's every entry.
     *
     * @return An instance of {@link TransactionDescription}
     */
    private TransactionDescription initTransactionDescription() {
        AssetDescription assetDescription;
        Date recordInitialDate, recordFinalDate;
        double totalVolume, remainder;

        DailyAssetsDAO assetsDAO = DailyAssetsDAO.getInstance();

        Entry<TransactionPK, TransactionValues> inferingEntry = this.transactionRecord.firstEntry();
        String transactionTicker = inferingEntry.getKey().getTicker();

        assetDescription = assetsDAO.getAssetDescription(transactionTicker);
        recordInitialDate = transactionRecord.firstEntry().getKey().getDate();
        recordFinalDate = transactionRecord.lastEntry().getKey().getDate();

        totalVolume = transactionRecord.values().stream()
                .mapToDouble(TransactionValues::getAbsAmountTraded)
                .sum();
        remainder = transactionRecord.values().stream()
                .mapToDouble(TransactionValues::getAmountTraded)
                .sum();

        return new TransactionDescription(assetDescription, recordInitialDate, recordFinalDate, totalVolume, remainder);
    }
    private void updateTransactionDescription() {
        Date recordInitialDate, recordFinalDate;
        double totalVolume, remainder;

        Entry<TransactionPK, TransactionValues> inferingEntry = this.transactionRecord.firstEntry();

        recordInitialDate = transactionRecord.firstEntry().getKey().getDate();
        recordFinalDate = transactionRecord.lastEntry().getKey().getDate();

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
    }

    private void verifyTransactionMapHoldsSameAsset(TransactionPK primaryKey) throws IllegalArgumentException {
        if (isFirstRecordEntry()) return;

        if (!transactionRecord.firstEntry().getKey().getTicker().equals(primaryKey.getTicker()))
            throw new IllegalArgumentException();
    }

    private boolean isFirstRecordEntry() {
        return this.transactionRecord.isEmpty();
    }

    public TransactionDescription getTransactionDescription() {
        return transactionDescription;
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

    @Override
    public synchronized TransactionValues put(TransactionPK key, TransactionValues value) {
        TransactionValues values = transactionRecord.put(key, value); // just fulfilling contract

        verifyTransactionMapHoldsSameAsset(key);
        updateTransactionDescription();
        return values;
    }

    /**
     * Performs data integrity check on all entries before adding. One exception cases all to abort.
     *
     * @param map mappings to be stored in this map
     */
    @Override
    public void putAll(Map<? extends TransactionPK, ? extends TransactionValues> map) {
        synchronized (this) {
            for (Entry<? extends TransactionPK, ? extends TransactionValues> entry :
                    map.entrySet()) {
                verifyTransactionMapHoldsSameAsset(entry.getKey());
            }
            transactionRecord.putAll(map);
            updateTransactionDescription();
        }
    }

    @Override
    public synchronized void clear() {
        transactionRecord.clear();
        this.transactionDescription = null;
    }

    @Override
    public Comparator<? super TransactionPK> comparator() {
        return transactionRecord.comparator();
    }

    @Override
    public SortedMap<TransactionPK, TransactionValues> subMap(TransactionPK fromKey, TransactionPK toKey) {
        return transactionRecord.subMap(fromKey, toKey);
    }

    @Override
    public SortedMap<TransactionPK, TransactionValues> headMap(TransactionPK toKey) {
        return transactionRecord.headMap(toKey);
    }

    @Override
    public SortedMap<TransactionPK, TransactionValues> tailMap(TransactionPK fromKey) {
        return transactionRecord.tailMap(fromKey);
    }

    @Override
    public TransactionPK firstKey() {
        return transactionRecord.firstKey();
    }

    @Override
    public TransactionPK lastKey() {
        return transactionRecord.lastKey();
    }

    @Override
    public Set<TransactionPK> keySet() {
        return transactionRecord.keySet();
    }

    @Override
    public Collection<TransactionValues> values() {
        return transactionRecord.values();
    }

    @Override
    public Set<Entry<TransactionPK, TransactionValues>> entrySet() {
        return transactionRecord.entrySet();
    }

    public LinkedHashSet<TransactionEntry> transactionPointSet() {
        LinkedHashSet<TransactionEntry> transactionEntries = new LinkedHashSet<>();
        for (Entry<TransactionPK, TransactionValues> entry :
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

        Iterator<TransactionPK> it = this.transactionRecord.keySet().iterator();
        TransactionEntry transactionEntry;
        TransactionPK firstPK = it.next();

        transactionEntry = new TransactionEntry(firstPK, this.transactionRecord.get(firstPK));
        this.transactionRecord.remove(firstPK);

        updateTransactionDescription();
        return transactionEntry;
    }

    @Override
    public synchronized TransactionEntry pollLast() {
        if (this.transactionRecord.isEmpty()) return null;

        Stream<TransactionPK> stream = this.transactionRecord.keySet().stream();
        TransactionEntry transactionEntry;
        TransactionPK lastPK = stream.reduce((first, second) -> second).orElseThrow();

        transactionEntry = new TransactionEntry(lastPK, this.transactionRecord.get(lastPK));
        this.transactionRecord.remove(lastPK);

        updateTransactionDescription();
        return transactionEntry;
    }

    @Override
    public TransactionEntry peekFirst() {
        if (this.transactionRecord.isEmpty()) return null;

        Iterator<TransactionPK> it = this.transactionRecord.keySet().iterator();
        TransactionEntry transactionEntry;
        TransactionPK firstPK = it.next();

        transactionEntry = new TransactionEntry(firstPK, this.transactionRecord.get(firstPK));

        return transactionEntry;
    }

    @Override
    public TransactionEntry peekLast() {
        if (this.transactionRecord.isEmpty()) return null;

        Stream<TransactionPK> stream = this.transactionRecord.keySet().stream();
        TransactionEntry transactionEntry;
        TransactionPK lastPK = stream.reduce((first, second) -> second).orElseThrow();

        transactionEntry = new TransactionEntry(lastPK, this.transactionRecord.get(lastPK));

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
     * Version of putAll method that does not throw exception, but instead returns a boolean indicating if operation was
     * successful or not. Performs block inspection before adding data, if one fails to match Ticker, so does the rest.
     *
     * @param collection collections of entries to be added
     * @return true if success, false if transactionPoint failed to match ticket
     */
    @Override
    public synchronized boolean addAll(Collection<? extends TransactionEntry> collection) {
        try {
            for (TransactionEntry transactionEntry :
                    collection) {
                verifyTransactionMapHoldsSameAsset(transactionEntry.getKey());
            }

            for (TransactionEntry transactionEntry :
                    collection) {
                this.transactionRecord.put(transactionEntry.key, transactionEntry.values);
            }
            updateTransactionDescription();
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    /**
     * Version of put method that does not throw exception, but instead returns a boolean indicating if operation was
     * successful or not.
     *
     * @param transactionEntry the entry to be added
     * @return true if success, false if transactionPoint failed to match ticket
     */
    @Override
    public synchronized boolean add(TransactionEntry transactionEntry) {
        try {
            verifyTransactionMapHoldsSameAsset(transactionEntry.getKey());
            this.transactionRecord.put(transactionEntry.key, transactionEntry.values);

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
            if (!this.transactionRecord.containsKey(transactionEntry.key)) {
                containsAll = false;
                break;
            }
        }

        return containsAll;
    }

    @Override
    public boolean contains(TransactionEntry transactionEntry) {
        return this.transactionRecord.containsKey(transactionEntry.key);
    }

    @Override
    public Set<TransactionPK> getPrimaryKeySet() {
        return this.transactionRecord.keySet();
    }

    @Override
    public Collection<TransactionValues> getValuesSet() {
        return this.transactionRecord.values();
    }

    @Override
    public Set<Entry<TransactionPK, TransactionValues>> getEntrySet() {
        return this.entrySet();
    }

    @Override
    public Iterator<TransactionPK> keyIterator() {
        return this.keySet().iterator();
    }

    @Override
    public Iterator<TransactionValues> valuesIterator() {
        return this.transactionRecord.values().iterator();
    }

    @Override
    public Iterator<Entry<TransactionPK, TransactionValues>> entryIterator() {
        return this.transactionRecord.entrySet().iterator();
    }

    @Inmutable
    public static class TransactionEntry implements Entry<TransactionPK, TransactionValues> {
        private final TransactionPK key;
        private final TransactionValues values;
        private TransactionDescription transactionDescription;

        public TransactionEntry(TransactionPK key, TransactionValues values, TransactionDescription transactionDescription) {
            this.key = key;
            this.values = values;
            this.transactionDescription = transactionDescription;
        }

        public TransactionEntry(TransactionPK key, TransactionValues values) {
            this.key = key;
            this.values = values;
        }

        public TransactionDescription getTransactionDescription() {
            return transactionDescription;
        }

        @Override
        public TransactionPK getKey() {
            return key;
        }

        @Override
        public TransactionValues getValue() {
            return values;
        }

        @Override
        public TransactionValues setValue(TransactionValues value) {
            throw new UnsupportedOperationException("A transaction entry is supposed @Inmutable");
        }

    }
}
