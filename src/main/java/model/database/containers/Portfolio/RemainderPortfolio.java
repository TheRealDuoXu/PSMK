package model.database.containers.Portfolio;

import model.database.containers.Transactions.TransactionMap;
import model.database.containers.Transactions.TransactionPK;

import java.util.*;

public class RemainderPortfolio implements Map<TransactionPK.Ticker, Double>, Comparable<RemainderPortfolio> {
    private final LinkedHashMap<TransactionPK.Ticker, Double> portfolio;
    private Iterator<Entry<TransactionPK.Ticker, Double>> entryIterator;

    public RemainderPortfolio(LinkedList<TransactionMap> transactionMapLinkedList) {
        this.portfolio = initPortfolioFromTransactionMapList(transactionMapLinkedList);
    }

    public RemainderPortfolio(TransactionMap... transactionMaps) {
        this.portfolio = initPortfolioFromTransactionMapArray(transactionMaps);
    }

    public RemainderPortfolio(TransactionMap transactionMap) {
        this.portfolio = initPortfolioFromTransactionMap(transactionMap);
    }


    private LinkedHashMap<TransactionPK.Ticker, Double> initPortfolioFromTransactionMapList(LinkedList<TransactionMap> transactionMapLinkedList) {
        LinkedHashMap<TransactionPK.Ticker, Double> tmpMap = new LinkedHashMap<>();

        for (TransactionMap transactionMap :
                transactionMapLinkedList) {
            tmpMap.put(transactionMap.ticker, transactionMap.getTransactionDescription().getRemainder());
        }

        return tmpMap;
    }

    private LinkedHashMap<TransactionPK.Ticker, Double> initPortfolioFromTransactionMap(TransactionMap transactionMap) {
        LinkedHashMap<TransactionPK.Ticker, Double> tmpMap = new LinkedHashMap<>();
        tmpMap.put(transactionMap.ticker, transactionMap.getTransactionDescription().getRemainder());

        return tmpMap;
    }

    private LinkedHashMap<TransactionPK.Ticker, Double> initPortfolioFromTransactionMapArray(TransactionMap[] transactionMaps) {
        LinkedHashMap<TransactionPK.Ticker, Double> tmpMap = new LinkedHashMap<>();

        for (TransactionMap transactionMap :
                transactionMaps) {
            tmpMap.put(transactionMap.ticker, transactionMap.getTransactionDescription().getRemainder());
        }

        return tmpMap;
    }

    public void resetCursor() {
        this.entryIterator = portfolio.entrySet().iterator();
    }

    public TransactionPK.Ticker nextTicker() {
        if (this.entryIterator == null) resetCursor();
        if (this.entryIterator.hasNext()) return entryIterator.next().getKey();

        throw new NullPointerException("Iterator is empty");
    }
    public double nextRemainder(){
        if (this.entryIterator == null) resetCursor();
        if (this.entryIterator.hasNext()) return entryIterator.next().getValue();

        throw new NullPointerException("Iterator is empty");
    }
    public Entry<TransactionPK.Ticker, Double> nextEntry(){
        if (this.entryIterator == null) resetCursor();
        if (this.entryIterator.hasNext()) return entryIterator.next();

        throw new NullPointerException("Iterator is empty");
    }
    public boolean hasNextEntry() {
        if (this.entryIterator == null) resetCursor();
        return this.entryIterator.hasNext();
    }

    @Override
    public int compareTo(RemainderPortfolio o) {
        return (int) (this.portfolio.values().stream().mapToDouble(aDouble -> aDouble).sum() -
                        o.values().stream().mapToDouble(value -> value).sum());
    }

    @Override
    public int size() {
        return portfolio.size();
    }

    @Override
    public boolean isEmpty() {
        return portfolio.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return portfolio.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return portfolio.containsValue(value);
    }

    @Override
    public Double get(Object key) {
        return portfolio.get(key);
    }

    @Override
    public Double put(TransactionPK.Ticker key, Double value) {
        return portfolio.put(key, value);
    }

    @Override
    public Double remove(Object key) {
        return portfolio.remove(key);
    }

    @Override
    public void putAll(Map<? extends TransactionPK.Ticker, ? extends Double> m) {
        portfolio.putAll(m);
    }

    @Override
    public void clear() {
        portfolio.clear();
    }

    @Override
    public Set<TransactionPK.Ticker> keySet() {
        return portfolio.keySet();
    }

    @Override
    public Collection<Double> values() {
        return portfolio.values();
    }

    @Override
    public Set<Entry<TransactionPK.Ticker, Double>> entrySet() {
        return portfolio.entrySet();
    }
}
