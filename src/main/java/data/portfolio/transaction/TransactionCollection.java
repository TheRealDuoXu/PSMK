package data.portfolio.transaction;

import data.portfolio.historical.InvestmentHistoricalData;
import data.portfolio.transaction.TransactionPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TransactionCollection implements Collection<TransactionPoint<? extends InvestmentHistoricalData>> {
    /**
     * TransactionCollection represents a collection of TransactionPoints, exposes more information, than just the
     * sum of all points, such as the resulting irr (if called to compute such), information regarding where the data
     * has been selected from, such as the time periods if it was not constructed from a global select
     */
    ArrayList<TransactionPoint<? extends InvestmentHistoricalData>> transactionsCollection;

    private final float irr = 0;  //TODO

    public TransactionCollection(ArrayList<TransactionPoint<? extends InvestmentHistoricalData>> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
    }

    public TransactionCollection(TransactionPoint<? extends InvestmentHistoricalData>... transactionsArray) {

    }

    public void refreshActiveAssets() {

    }


    @Override
    public int size() {
        return transactionsCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return transactionsCollection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return transactionsCollection.contains(o);
    }

    @Override
    public Iterator<TransactionPoint<? extends InvestmentHistoricalData>> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(TransactionPoint<? extends InvestmentHistoricalData> transactionPoint) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends TransactionPoint<? extends InvestmentHistoricalData>> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
