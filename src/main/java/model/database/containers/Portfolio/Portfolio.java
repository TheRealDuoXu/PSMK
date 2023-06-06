package model.database.containers.Portfolio;

import model.database.containers.Transactions.TransactionDescription;
import model.database.containers.Transactions.TransactionMap;
import model.database.dao.PortfolioDAO;

import java.util.*;
import java.util.stream.Collectors;

public class Portfolio implements Map<TransactionDescription,TransactionMap>, Comparable<Portfolio> {
    private static final int FIRST_OCCURRENCE = 0;
    private PorfolioDescription porfolioDescription;
    private LinkedHashMap<TransactionDescription,TransactionMap> portfolio;

    public Portfolio(PorfolioDescription porfolioDescription, LinkedList<TransactionMap> transactionMapLinkedList) {
        this.porfolioDescription = porfolioDescription;
        this.portfolio = initPortfolioFromTransactionMapList(transactionMapLinkedList);
    }

    public Portfolio(LinkedList<TransactionMap> transactionMapLinkedList) {
        this.portfolio = initPortfolioFromTransactionMapList(transactionMapLinkedList);
        this.porfolioDescription = getPortfolioDescription(getUUIDFromTransactionMaps(transactionMapLinkedList));
    }

    public Portfolio(TransactionMap... transactionMaps) {
        this.portfolio = initPortfolioFromTransactionMapArray(transactionMaps);
        this.porfolioDescription = getPortfolioDescription(getUUIDFromTransactionMaps(transactionMaps));
    }

    public Portfolio(TransactionMap transactionMap) {
        this.portfolio = initPortfolioFromTransactionMap(transactionMap);
        this.porfolioDescription = getPortfolioDescription(getUUIDFromTransactionMaps(portfolio));
    }

    public Portfolio(String uuid) {
        this.portfolio = new LinkedHashMap<>();
        this.porfolioDescription = getPortfolioDescription(uuid);
    }

    public Portfolio(String uuid, TransactionMap transactionMap) {
        this.portfolio = initPortfolioFromTransactionMap(transactionMap);
        this.porfolioDescription = getPortfolioDescription(uuid);
    }

    public Portfolio(String uuid, TransactionMap ... transactionMaps) {
        this.portfolio = initPortfolioFromTransactionMapArray(transactionMaps);
        this.porfolioDescription = getPortfolioDescription(uuid);
    }
    public Portfolio(String uuid, LinkedList<TransactionMap> transactionMaps) {
        this.portfolio = initPortfolioFromTransactionMapList(transactionMaps);
        this.porfolioDescription = getPortfolioDescription(uuid);
    }
    private String getUUIDFromTransactionMaps(LinkedList<TransactionMap> transactionMaps) {
        return transactionMaps.get(FIRST_OCCURRENCE).keySet().iterator().next().getPortfolioUUID();
    }
    private String getUUIDFromTransactionMaps(TransactionMap[] transactionMaps) {
        return transactionMaps[FIRST_OCCURRENCE].keySet().iterator().next().getPortfolioUUID();
    }

    private String getUUIDFromTransactionMaps(TransactionMap transactionMaps) {
        return transactionMaps.keySet().iterator().next().getPortfolioUUID();
    }
    private PorfolioDescription getPortfolioDescription(String uuid) {
        PortfolioDAO dao = PortfolioDAO.getInstance();
        return dao.getPortfolioDescription(uuid);
    }
    private LinkedHashMap<TransactionDescription, TransactionMap> initPortfolioFromTransactionMapList(LinkedList<TransactionMap> transactionMapLinkedList) {
        LinkedHashMap<TransactionDescription, TransactionMap> tmpMap = new LinkedHashMap<>();
        LinkedList<TransactionDescription> transactionDescriptions = new LinkedList<>();
        Iterator<TransactionMap> mapIterator;
        Iterator<TransactionDescription> descriptionIterator;

        for (TransactionMap transactionMap :
                transactionMapLinkedList) {
            transactionDescriptions.add(transactionMap.getTransactionDescription());
        }

        mapIterator = transactionMapLinkedList.iterator();
        descriptionIterator = transactionDescriptions.iterator();

        while (mapIterator.hasNext()){
            //todo
        }

        return tmpMap;
    }

    private LinkedHashMap<TransactionDescription, TransactionMap> initPortfolioFromTransactionMap(TransactionMap transactionMap) {
        LinkedHashMap<TransactionDescription, TransactionMap> tmpMap = new LinkedHashMap<>();
        LinkedList<TransactionDescription> transactionDescriptions = new LinkedList<>();

        return tmpMap;
    }

    private LinkedHashMap<TransactionDescription, TransactionMap> initPortfolioFromTransactionMapArray(TransactionMap[] transactionMaps) {
        LinkedHashMap<TransactionDescription, TransactionMap> tmpMap = new LinkedHashMap<>();
        LinkedList<TransactionDescription> transactionDescriptions = new LinkedList<>();

        return tmpMap;
    }

    /**
     * Orders Portfolios based on amount invested
     * @param o the object to be compared.
     * @return negative if other Portfolio is bigger than current Porfolio amount, and viceversa
     */
    @Override
    public int compareTo(Portfolio o) {
        return (int) (this.porfolioDescription.getBudget() - o.porfolioDescription.getBudget());
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public TransactionMap get(Object key) {
        return null;
    }

    @Override
    public TransactionMap put(TransactionDescription key, TransactionMap value) {
        return null;
    }

    @Override
    public TransactionMap remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends TransactionDescription, ? extends TransactionMap> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<TransactionDescription> keySet() {
        return null;
    }

    @Override
    public Collection<TransactionMap> values() {
        return null;
    }

    @Override
    public Set<Entry<TransactionDescription, TransactionMap>> entrySet() {
        return null;
    }

    @Override
    public TransactionMap getOrDefault(Object key, TransactionMap defaultValue) {
        return Map.super.getOrDefault(key, defaultValue);
    }
}
