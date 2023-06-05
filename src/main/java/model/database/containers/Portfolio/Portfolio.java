package model.database.containers.Portfolio;

import model.database.containers.Transactions.TransactionMap;
import model.database.dao.PortfolioDAO;

import java.util.*;
import java.util.stream.Collectors;

public class Portfolio implements Collection<TransactionMap>, Comparable<Portfolio> {
    private static final int FIRST_OCURRENCE = 0;
    private PorfolioDescription porfolioDescription;
    private LinkedList<TransactionMap> transactionMaps;

    public Portfolio(PorfolioDescription porfolioDescription, LinkedList<TransactionMap> transactionMaps) {
        this.porfolioDescription = porfolioDescription;
        this.transactionMaps = transactionMaps;
    }

    public Portfolio(LinkedList<TransactionMap> transactionMaps) {
        this.transactionMaps = transactionMaps;
        this.porfolioDescription = getPortfolioDescription(getUUIDFromTransactionMaps(transactionMaps));
    }
    public Portfolio(TransactionMap... transactionMaps) {
        this.transactionMaps = new LinkedList<>();
        this.porfolioDescription = getPortfolioDescription(getUUIDFromTransactionMaps(transactionMaps));

        this.transactionMaps.addAll(Arrays.stream(transactionMaps)
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    public Portfolio(TransactionMap transactionMap) {
        this.transactionMaps = new LinkedList<>();
        this.porfolioDescription = getPortfolioDescription(getUUIDFromTransactionMaps(transactionMaps));

        this.transactionMaps.add(transactionMap);
    }

    public Portfolio(String uuid) {
        this.transactionMaps = new LinkedList<>();
        this.porfolioDescription = getPortfolioDescription(uuid);
    }
    public Portfolio(String uuid, TransactionMap transactionMap) {
        this.transactionMaps = new LinkedList<>();
        this.porfolioDescription = getPortfolioDescription(uuid);

        this.transactionMaps.add(transactionMap);
    }
    public Portfolio(String uuid, TransactionMap ...transactionMaps) {
        this.transactionMaps = new LinkedList<>();
        this.porfolioDescription = getPortfolioDescription(uuid);

        this.transactionMaps.addAll(Arrays.stream(transactionMaps)
                .collect(Collectors.toCollection(ArrayList::new)));
    }
    public Portfolio(String uuid, LinkedList<TransactionMap> transactionMaps) {
        this.transactionMaps = new LinkedList<>();
        this.porfolioDescription = getPortfolioDescription(uuid);

        this.transactionMaps.addAll(transactionMaps);
    }

    private String getUUIDFromTransactionMaps(LinkedList<TransactionMap> transactionMaps) {
        return transactionMaps.get(FIRST_OCURRENCE).keySet().iterator().next().getPortfolioUUID();
    }
    private String getUUIDFromTransactionMaps(TransactionMap[] transactionMaps) {
        return transactionMaps[FIRST_OCURRENCE].keySet().iterator().next().getPortfolioUUID();
    }
    private String getUUIDFromTransactionMaps(TransactionMap transactionMaps) {
        return transactionMaps.keySet().iterator().next().getPortfolioUUID();
    }

    private PorfolioDescription getPortfolioDescription(String uuid) {
        PortfolioDAO dao = PortfolioDAO.getInstance();
        return dao.getPortfolioDescription(uuid);
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
        return transactionMaps.size();
    }

    @Override
    public boolean isEmpty() {
        return transactionMaps.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return transactionMaps.contains(o);
    }

    @Override
    public Iterator<TransactionMap> iterator() {
        return transactionMaps.iterator();
    }

    @Override
    public Object[] toArray() {
        return transactionMaps.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return transactionMaps.toArray(a);
    }

    @Override
    public boolean add(TransactionMap transactionMap) {
        return transactionMaps.add(transactionMap);
    }

    @Override
    public boolean remove(Object o) {
        return transactionMaps.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return transactionMaps.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends TransactionMap> c) {
        return transactionMaps.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return transactionMaps.remove(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return transactionMaps.retainAll(c);
    }

    @Override
    public void clear() {
        transactionMaps.clear();
    }
}
