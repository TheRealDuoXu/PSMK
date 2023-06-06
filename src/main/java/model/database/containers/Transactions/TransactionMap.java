package model.database.containers.Transactions;

import java.util.*;

public class TransactionMap implements Map<TransactionPK, TransactionValues> {

    private LinkedHashMap<TransactionPK, TransactionValues> transactionRecord;
    public TransactionMap(LinkedHashMap<TransactionPK, TransactionValues> transactionRecord) {
        this.transactionRecord = transactionRecord;
    }
    public TransactionMap(TransactionPK pk, Float ...data){
        this.transactionRecord = new LinkedHashMap<>();
        TransactionValues values = TransactionValues.getInstance(data);

        this.transactionRecord.put(pk, values);
    }

    //TODO
    public TransactionDescription getTransactionDescription(){
        return null;
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
    public TransactionValues get(Object key) {
        return transactionRecord.get(key);
    }

    @Override
    public TransactionValues put(TransactionPK key, TransactionValues value) {
        return transactionRecord.put(key, value);
    }

    @Override
    public TransactionValues remove(Object key) {
        return transactionRecord.remove(key);
    }

    @Override
    public void putAll(Map<? extends TransactionPK, ? extends TransactionValues> m) {
        transactionRecord.putAll(m);
    }

    @Override
    public void clear() {
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
        for (Entry<TransactionPK, TransactionValues> entry:
        transactionRecord.entrySet()){
            transactionPoints.add(new TransactionPoint(entry.getKey(), entry.getValue()));
        }
        return transactionPoints;
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
