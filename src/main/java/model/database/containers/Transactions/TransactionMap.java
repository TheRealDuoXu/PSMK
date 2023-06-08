package model.database.containers.Transactions;

import model.database.containers.DailyAssets.AssetDescription;
import model.database.containers.PrimaryKey;

import java.util.*;
import java.util.stream.Stream;

public class TransactionMap implements ITransactionDequeMap {

    private LinkedHashMap<TransactionPK, TransactionValues> transactionRecord;
    private TransactionDescription transactionDescription;

    public TransactionMap(LinkedHashMap<TransactionPK, TransactionValues> transactionRecord) {
        this.transactionRecord = transactionRecord;

        this.transactionDescription = implyTransactionDescription();
    }

    public TransactionMap(TransactionPK pk, Float... data) {
        this.transactionRecord = new LinkedHashMap<>();
        TransactionValues values = TransactionValues.getInstance(data);

        this.transactionRecord.put(pk, values);

        this.transactionDescription = implyTransactionDescription();
    }

    public TransactionDescription getTransactionDescription() {
        return transactionDescription;
    }

    private TransactionDescription implyTransactionDescription() {
        AssetDescription assetDescription;
        Date recordInitialDate, recordFinalDate;
        float totalVolume, remainder;


    }

    private void verifyTransactionMapHoldsSameAsset(PrimaryKey primaryKey) throws IllegalArgumentException {
        if (this.transactionRecord.isEmpty()) {
            return;
        }

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
    public synchronized TransactionValues put(TransactionPK key, TransactionValues value) {
        return transactionRecord.put(key, value);
    }

    @Override
    public synchronized TransactionValues remove(Object key) {
        return transactionRecord.remove(key);
    }

    @Override
    public void putAll(Map<? extends TransactionPK, ? extends TransactionValues> m) {
        synchronized (this) {
            transactionRecord.putAll(m);
        }
    }

    @Override
    public synchronized void clear() {
        transactionRecord.clear();
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

    public LinkedHashSet<TransactionPoint> transactionPointSet() {
        LinkedHashSet<TransactionPoint> transactionPoints = new LinkedHashSet<>();
        for (Entry<TransactionPK, TransactionValues> entry :
                transactionRecord.entrySet()) {
            transactionPoints.add(new TransactionPoint(entry.getKey(), entry.getValue()));
        }
        return transactionPoints;
    }

    @Override
    public synchronized TransactionPoint removeFirst() {
        return pollFirst();
    }

    @Override
    public synchronized TransactionPoint removeLast() {
        return pollLast();
    }

    @Override
    public synchronized TransactionPoint pollFirst() {
        if (this.transactionRecord.isEmpty()) return null;

        Iterator<TransactionPK> it = this.transactionRecord.keySet().iterator();
        TransactionPoint transactionPoint;
        TransactionPK firstPK = it.next();

        transactionPoint = new TransactionPoint(firstPK, this.transactionRecord.get(firstPK));
        this.transactionRecord.remove(firstPK);

        return transactionPoint;
    }

    @Override
    public synchronized TransactionPoint pollLast() {
        if (this.transactionRecord.isEmpty()) return null;

        Stream<TransactionPK> stream = this.transactionRecord.keySet().stream();
        TransactionPoint transactionPoint;
        TransactionPK lastPK = stream.reduce((first, second) -> second).orElseThrow();

        transactionPoint = new TransactionPoint(lastPK, this.transactionRecord.get(lastPK));
        this.transactionRecord.remove(lastPK);

        return transactionPoint;
    }

    @Override
    public TransactionPoint peekFirst() {
        if (this.transactionRecord.isEmpty()) return null;

        Iterator<TransactionPK> it = this.transactionRecord.keySet().iterator();
        TransactionPoint transactionPoint;
        TransactionPK firstPK = it.next();

        transactionPoint = new TransactionPoint(firstPK, this.transactionRecord.get(firstPK));

        return transactionPoint;
    }

    @Override
    public TransactionPoint peekLast() {
        if (this.transactionRecord.isEmpty()) return null;

        Stream<TransactionPK> stream = this.transactionRecord.keySet().stream();
        TransactionPoint transactionPoint;
        TransactionPK lastPK = stream.reduce((first, second) -> second).orElseThrow();

        transactionPoint = new TransactionPoint(lastPK, this.transactionRecord.get(lastPK));

        return transactionPoint;
    }

    @Override
    public synchronized TransactionPoint poll() {
        return pollFirst();
    }

    @Override
    public synchronized TransactionPoint pop() {
        if (this.transactionRecord.isEmpty()) throw new NullPointerException();

        return pollFirst();
    }

    @Override
    public TransactionPoint peek() {
        return peekFirst();
    }

    @Override
    public synchronized boolean addAll(Collection<? extends TransactionPoint> collection) {
        for (TransactionPoint transactionPoint :
                collection) {
            this.transactionRecord.put(transactionPoint.key, transactionPoint.values);
        }
        return true;
    }

    @Override
    public synchronized boolean add(TransactionPoint transactionPoint) {
        this.transactionRecord.put(transactionPoint.key, transactionPoint.values);
        return true;
    }

    /**
     * Push a transaction point to the first element on the stack. That is index 0
     * <br>
     * Do not recommend using this method as it's much slower than offer or add
     *
     * @param transactionPoint the point to be inserted
     */
    @Override
    public synchronized void push(TransactionPoint transactionPoint) {
        LinkedHashMap<TransactionPK, TransactionValues> tmp = new LinkedHashMap<>();

        tmp.put(transactionPoint.key, transactionPoint.values);
        tmp.putAll(this.transactionRecord);

        this.transactionRecord = tmp;
    }

    @Override
    public boolean containsAll(Collection<? extends TransactionPoint> transactionPoints) {
        boolean containsAll = true;

        for (TransactionPoint transactionPoint :
                transactionPoints) {
            if (!this.transactionRecord.containsKey(transactionPoint.key)) containsAll = false;
        }
        
        return containsAll;
    }

    @Override
    public boolean contains(TransactionPoint transactionPoint) {
        return this.transactionRecord.containsKey(transactionPoint.key);
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

    public static class TransactionPoint implements Entry<TransactionPK, TransactionValues> {
        private final TransactionPK key;
        private TransactionValues values;
        private TransactionDescription transactionDescription;

        public TransactionPoint(TransactionPK key, TransactionValues values, TransactionDescription transactionDescription) {
            this.key = key;
            this.values = values;
            this.transactionDescription = transactionDescription;
        }

        public TransactionPoint(TransactionPK key, TransactionValues values) {
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
        public TransactionValues setValue(TransactionValues values) {
            this.values = values;
            return values;
        }

    }
}
