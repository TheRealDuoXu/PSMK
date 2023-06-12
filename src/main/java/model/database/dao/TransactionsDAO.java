package model.database.dao;

import model.database.containers.Portfolio.Portfolio;
import model.database.containers.Transactions.TransactionMap;

import java.util.Date;

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

    public Portfolio getAllTransactionsOnPortfolio(String FK_UUID_Portfolio){
        return null;
    }
    public TransactionMap getAllTransactionsOnTicker(String FK_Ticker_DailyAssets){return null;}
    public TransactionMap.TransactionEntry getTransactionOnDate(Date date){return null;}
    public TransactionMap.TransactionEntry getTransactionOnDate(String date, String format){return null;}
}
