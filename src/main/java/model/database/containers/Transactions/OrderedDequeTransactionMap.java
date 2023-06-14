package model.database.containers.Transactions;

import java.util.*;

public abstract class OrderedDequeTransactionMap implements SortedMap<Date, TransactionValues>, Iterable<Map.Entry<Date, TransactionValues>> {
    abstract TransactionValues put(TransactionPK key, TransactionValues value);
    abstract TransactionValues put(TransactionPK.Ticker ticker, Date date, TransactionValues value);
    abstract void putAll(TransactionPK.Ticker ticker, Map<? extends Date, ? extends TransactionValues> map);

    abstract public TransactionMap.TransactionEntry removeFirst();

    abstract public TransactionMap.TransactionEntry removeLast();

    abstract  public TransactionMap.TransactionEntry pollFirst();

    abstract public TransactionMap.TransactionEntry pollLast();

    abstract public TransactionMap.TransactionEntry peekFirst();

    abstract public TransactionMap.TransactionEntry peekLast();

    abstract public TransactionMap.TransactionEntry poll();

    abstract public TransactionMap.TransactionEntry peek();

    abstract public TransactionMap.TransactionEntry pop();

    abstract  boolean addAll(TransactionPK.Ticker ticker, Collection<? extends TransactionMap.TransactionEntry> collection);

    abstract boolean add(TransactionPK.Ticker ticker, TransactionMap.TransactionEntry transactionEntry);

    abstract public boolean containsAll(Collection<? extends TransactionMap.TransactionEntry> transactionPoints);

    abstract public boolean contains(TransactionMap.TransactionEntry transactionEntry);

    abstract public Collection<TransactionValues> getValuesSet();
    abstract public Set<Entry<Date, TransactionValues>> getEntrySet();
    abstract public Iterator<Date> keyIterator();
    abstract public Iterator<TransactionValues> valuesIterator();
    abstract public Iterator<Entry<Date, TransactionValues>> entryIterator();
    abstract public void resetCursor();
    abstract public TransactionMap.TransactionEntry nextEntry();
    abstract public boolean hasNextEntry();
    @Override
    public final void putAll(Map<? extends Date, ? extends TransactionValues> m) {
        throw new UnsupportedOperationException();
    }
    @Override
    public final TransactionValues put(Date key, TransactionValues value) {
        throw new UnsupportedOperationException();
    }

}