package model.database.dao;

import model.database.SQLQuery;
import model.database.containers.Portfolio.Portfolio;
import model.database.containers.Portfolio.PortfolioPK;
import model.database.containers.Transactions.TransactionMap;
import model.database.containers.Transactions.TransactionPK;
import model.database.containers.Transactions.TransactionValues;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeMap;

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

    public Portfolio getAllTransactionsOnPortfolio(PortfolioPK FK_UUID_Portfolio){
        Portfolio portfolio = new Portfolio(FK_UUID_Portfolio);
        TreeMap<TransactionPK, TransactionValues> transactionValuesTreeMap;
        SQLQuery sqlQuery = SQLQuery.SELECT_TRANSACTION_MAPS_ON_PORTFOLIO;

        try(ResultSet resultSet = executeQuery(sqlQuery, FK_UUID_Portfolio.getUUID())) {
            while (resultSet.next()){
                // iterate and create one map per FK_Ticker_DailyAssets
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public TransactionMap getAllTransactionsOnTicker(String FK_Ticker_DailyAssets){return null;}
    public TransactionMap.TransactionEntry getTransactionOnDate(Date date){return null;}
    public TransactionMap.TransactionEntry getTransactionOnDate(String date, String format){return null;}
}
