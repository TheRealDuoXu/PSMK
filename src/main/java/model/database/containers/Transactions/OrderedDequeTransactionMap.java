package model.database.containers.Transactions;

import java.util.*;

public interface OrderedDequeTransactionMap extends SortedMap<TransactionPK, TransactionValues> {
    public TransactionMap.TransactionEntry removeFirst();

    public TransactionMap.TransactionEntry removeLast();

    public TransactionMap.TransactionEntry pollFirst();

    public TransactionMap.TransactionEntry pollLast();

    public TransactionMap.TransactionEntry peekFirst();

    public TransactionMap.TransactionEntry peekLast();

    public boolean add(TransactionMap.TransactionEntry transactionEntry);

    public TransactionMap.TransactionEntry poll();

    public TransactionMap.TransactionEntry peek();

    public boolean addAll(Collection<? extends TransactionMap.TransactionEntry> c);

    public TransactionMap.TransactionEntry pop();

    public boolean containsAll(Collection<? extends TransactionMap.TransactionEntry> transactionPoints);

    public boolean contains(TransactionMap.TransactionEntry transactionEntry);

    public Set<TransactionPK> getPrimaryKeySet();
    public Collection<TransactionValues> getValuesSet();
    public Set<Entry<TransactionPK, TransactionValues>> getEntrySet();
    public Iterator<TransactionPK> keyIterator();
    public Iterator<TransactionValues> valuesIterator();
    public Iterator<Entry<TransactionPK, TransactionValues>> entryIterator();

}