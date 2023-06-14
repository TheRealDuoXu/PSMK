package model.database.containers.Portfolio;

import model.database.containers.Transactions.TransactionMap;
import model.database.containers.Transactions.TransactionPK;
import model.database.dao.PortfolioDAO;

import java.util.*;

public class Portfolio implements Map<TransactionPK.Ticker, TransactionMap>, Comparable<Portfolio>{
    private static final int FIRST_OCCURRENCE = 0;
    private PorfolioDescription porfolioDescription;
    private LinkedHashMap<TransactionPK.Ticker, TransactionMap> portfolio;
    private Iterator<Entry<TransactionPK.Ticker, TransactionMap>> entryIterator;

    public Portfolio(PorfolioDescription porfolioDescription, LinkedList<TransactionMap> transactionMapLinkedList) {
        this.porfolioDescription = porfolioDescription;
        this.portfolio = initPortfolioFromTransactionMapList(transactionMapLinkedList);
    }

    public Portfolio(PortfolioPK pk, LinkedList<TransactionMap> transactionMapLinkedList) {
        this.portfolio = initPortfolioFromTransactionMapList(transactionMapLinkedList);
        this.porfolioDescription = getPortfolioDescription(pk);
    }

    public Portfolio(PortfolioPK pk, TransactionMap... transactionMaps) {
        this.portfolio = initPortfolioFromTransactionMapArray(transactionMaps);
        this.porfolioDescription = getPortfolioDescription(pk);
    }

    public Portfolio(PortfolioPK pk, TransactionMap transactionMap) {
        this.portfolio = initPortfolioFromTransactionMap(transactionMap);
        this.porfolioDescription = getPortfolioDescription(pk);
    }

    public Portfolio(PortfolioPK pk) {
        this.portfolio = new LinkedHashMap<>();
        this.porfolioDescription = getPortfolioDescription(pk);
    }

    private PorfolioDescription getPortfolioDescription(PortfolioPK pk) {
        PortfolioDAO dao = PortfolioDAO.getInstance();
        return dao.getPortfolioDescription(pk.getUUID());
    }

    private LinkedHashMap<TransactionPK.Ticker, TransactionMap> initPortfolioFromTransactionMapList(LinkedList<TransactionMap> transactionMapLinkedList) {
        LinkedHashMap<TransactionPK.Ticker, TransactionMap> tmpMap = new LinkedHashMap<>();

        for (TransactionMap transactionMap :
                transactionMapLinkedList) {
            tmpMap.put(transactionMap.ticker, transactionMap);
        }

        return tmpMap;
    }

    private LinkedHashMap<TransactionPK.Ticker, TransactionMap> initPortfolioFromTransactionMap(TransactionMap transactionMap) {
        LinkedHashMap<TransactionPK.Ticker, TransactionMap> tmpMap = new LinkedHashMap<>();
        tmpMap.put(transactionMap.ticker, transactionMap);

        return tmpMap;
    }

    private LinkedHashMap<TransactionPK.Ticker, TransactionMap> initPortfolioFromTransactionMapArray(TransactionMap[] transactionMaps) {
        LinkedHashMap<TransactionPK.Ticker, TransactionMap> tmpMap = new LinkedHashMap<>();

        for (TransactionMap transactionMap :
                transactionMaps) {
            tmpMap.put(transactionMap.ticker, transactionMap);
        }

        return tmpMap;
    }

    /**
     * Orders Portfolios based on amount invested
     *
     * @param o the object to be compared.
     * @return negative if other Portfolio is bigger than current Porfolio amount, and viceversa
     */
    @Override
    public int compareTo(Portfolio o) {
        return (int) (this.porfolioDescription.getBudget() - o.porfolioDescription.getBudget());
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
    public TransactionMap get(Object key) {
        return portfolio.get(key);
    }

    @Override
    public TransactionMap put(TransactionPK.Ticker key, TransactionMap value) {
        return portfolio.put(key, value);
    }

    @Override
    public TransactionMap remove(Object key) {
        return portfolio.remove(key);
    }

    @Override
    public void putAll(Map<? extends TransactionPK.Ticker, ? extends TransactionMap> m) {
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
    public Collection<TransactionMap> values() {
        return portfolio.values();
    }

    @Override
    public Set<Entry<TransactionPK.Ticker, TransactionMap>> entrySet() {
        return portfolio.entrySet();
    }

    @Override
    public TransactionMap getOrDefault(Object key, TransactionMap defaultValue) {
        return Map.super.getOrDefault(key, defaultValue);
    }

    public void resetCursor() {
        this.entryIterator = this.portfolio.entrySet().iterator();
    }

    public Entry<TransactionPK.Ticker, TransactionMap> nextEntry() {
        if (entryIterator == null) entryIterator = portfolio.entrySet().iterator();

        if (!hasNextEntry()) throw new NullPointerException("No more entries on iterator");

        return entryIterator.next();
    }
    public TransactionPK.Ticker nextTicker(){
        if (entryIterator == null) entryIterator = portfolio.entrySet().iterator();

        if (!hasNextEntry()) throw new NullPointerException("No more entries on iterator");

        return entryIterator.next().getKey();
    }

    public TransactionMap nextTransactionMap(){
        if (entryIterator == null) entryIterator = portfolio.entrySet().iterator();

        if (!hasNextEntry()) throw new NullPointerException("No more entries on iterator");

        return entryIterator.next().getValue();
    }
    public boolean hasNextEntry() {
        return entryIterator.hasNext();
    }
}
