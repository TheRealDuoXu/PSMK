package model.database.dao;

import model.database.containers.Portfolio.Portfolio;
import model.database.containers.Transactions.TransactionMap;

public class TransactionsDAO extends DAO{
    private static TransactionsDAO instance;

    private TransactionsDAO() {
        super();
    }

    public static TransactionsDAO getInstance() {
        synchronized (TransactionsDAO.class) {
            if (instance == null) {
                instance = new TransactionsDAO();
            }
        }
        return instance;
    }

    public Portfolio getTransactions(String FK_UUID_Portfolio){
        return null;
    }
}
