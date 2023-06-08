package model.database.containers.Transactions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface ITransactionDequeMap extends Map<TransactionPK, TransactionValues> {
    public TransactionMap.TransactionPoint removeFirst();

    public TransactionMap.TransactionPoint removeLast();

    public TransactionMap.TransactionPoint pollFirst();

    public TransactionMap.TransactionPoint pollLast();

    public TransactionMap.TransactionPoint peekFirst();

    public TransactionMap.TransactionPoint peekLast();

    public boolean add(TransactionMap.TransactionPoint transactionPoint);

    public TransactionMap.TransactionPoint poll();

    public TransactionMap.TransactionPoint peek();

    public boolean addAll(Collection<? extends TransactionMap.TransactionPoint> c);

    public void push(TransactionMap.TransactionPoint transactionPoint);

    public TransactionMap.TransactionPoint pop();

    public boolean containsAll(Collection<? extends TransactionMap.TransactionPoint> transactionPoints);

    public boolean contains(TransactionMap.TransactionPoint transactionPoint);

    public Set<TransactionPK> getPrimaryKeySet();
    public Collection<TransactionValues> getValuesSet();
    public Set<Entry<TransactionPK, TransactionValues>> getEntrySet();
    public Iterator<TransactionPK> keyIterator();
    public Iterator<TransactionValues> valuesIterator();
    public Iterator<Entry<TransactionPK, TransactionValues>> entryIterator();
}