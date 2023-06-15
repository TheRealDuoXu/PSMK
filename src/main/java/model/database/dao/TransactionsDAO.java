package model.database.dao;

import model.database.SQLQuery;
import model.database.containers.LegalCompliance;
import model.database.containers.Portfolio.PortfolioPK;
import model.database.containers.Portfolio.TransactionPortfolio;
import model.database.containers.PrimaryKey;
import model.database.containers.Transactions.TransactionMap;
import model.database.containers.Transactions.TransactionPK;
import model.database.containers.Transactions.TransactionValues;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionsDAO extends DAO {
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

    public TransactionPortfolio getAllTransactionsOnPortfolio(PortfolioPK FK_UUID_Portfolio) {
        TransactionPortfolio transactionPortfolio = new TransactionPortfolio(FK_UUID_Portfolio);
        SQLQuery sqlQuery = SQLQuery.SELECT_TRANSACTION_MAPS_ON_PORTFOLIO;

        final int TICKER_POS = 0, DATE_POS = 1, PORTFOLIO_POS = 2, AMOUNT_POS = 3;

        try (ResultSet resultSet = executeQuery(sqlQuery, FK_UUID_Portfolio.getUUID())) {
            TransactionPK.Ticker tmpTicker;
            TransactionMap tmpMap;
            Date date;
            TransactionValues amount;

            resultSet.next();
            tmpTicker = TransactionPK.Ticker.getInstance(resultSet.getString(TICKER_POS));
            tmpMap = new TransactionMap(tmpTicker, PrimaryKey.parseDate(resultSet.getString(DATE_POS)), resultSet.getFloat(AMOUNT_POS));

            while (resultSet.next()) {
                tmpTicker = TransactionPK.Ticker.getInstance(resultSet.getString(TICKER_POS));

                if (!tmpMap.isCompatible(tmpTicker)) {
                    transactionPortfolio.put(tmpTicker, tmpMap);
                    tmpMap = new TransactionMap(tmpTicker);
                }

                date = PrimaryKey.parseDate(resultSet.getString(DATE_POS));
                amount = new TransactionValues(resultSet.getFloat(AMOUNT_POS));

                tmpMap.put(date, amount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionPortfolio;
    }

    public TransactionMap getAllTransactionsOnTicker(PortfolioPK portfolioPK, TransactionPK.Ticker ticker) {
        SQLQuery sqlQuery = SQLQuery.SELECT_TRANSACTION_MAPS_ON_PORTFOLIO_BY_TICKER;
        TransactionMap transactionMap = new TransactionMap(ticker);
        final int DATE_POS = 0, AMOUNT_POS = 1;
        try (ResultSet resultSet = executeQuery(sqlQuery, portfolioPK.getUUID(), ticker.getTicker())) {
            while (resultSet.next()) {
                transactionMap.put(ticker, TransactionPK.parseDate(resultSet.getString(DATE_POS)),
                        TransactionValues.getInstance(resultSet.getDouble(AMOUNT_POS)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionMap;
    }

    public TransactionMap.TransactionEntry getTransactionOnDate(PortfolioPK portfolioPK, TransactionPK.Ticker ticker, Date date) {
        SQLQuery sqlQuery = SQLQuery.SELECT_TRANSACTION_POINT;
        TransactionMap.TransactionEntry entry;
        final int AMOUNT_POS = 0;
        try (ResultSet resultSet = executeQuery(sqlQuery, portfolioPK.getUUID(), ticker.getTicker(), date.toString())) {
            resultSet.next();
            entry = new TransactionMap.TransactionEntry(date, new TransactionValues(resultSet.getDouble(AMOUNT_POS)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entry;
    }

    public TransactionMap.TransactionEntry getTransactionOnDate(PortfolioPK portfolioPK, TransactionPK.Ticker ticker, String strDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        SQLQuery sqlQuery = SQLQuery.SELECT_TRANSACTION_POINT;
        TransactionMap.TransactionEntry entry;
        final int AMOUNT_POS = 0;
        try (ResultSet resultSet = executeQuery(sqlQuery, portfolioPK.getUUID(), ticker.getTicker(), dateFormat.format(strDate))) {
            resultSet.next();
            entry = new TransactionMap.TransactionEntry(dateFormat.parse(strDate), new TransactionValues(resultSet.getDouble(AMOUNT_POS)));
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
        return entry;
    }

    public void createTransaction(TransactionPK transactionPK, PortfolioPK portfolioPK, double amount) {
        SQLQuery sqlQuery = SQLQuery.INSERT_TRANSACTION;

        try {
            ResultSet resultSet = executeQuery(sqlQuery, transactionPK.getTicker(), transactionPK.getStrDate(), portfolioPK.getUUID(), String.valueOf(amount));
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAllTransactions(PortfolioPK portfolioPK, TransactionMap transactionMap) {
        SQLQuery sqlQuery = SQLQuery.INSERT_TRANSACTION;
        TransactionMap.TransactionEntry entry;
        PrimaryKey.Ticker ticker = transactionMap.getTicker();

        while (transactionMap.hasNextEntry()) {
            entry = transactionMap.nextEntry();
            try {
                ResultSet resultSet = executeQuery(sqlQuery, ticker.toString(), entry.getKey().toString(),
                        portfolioPK.getUUID(), String.valueOf(entry.getValue().getAmountTraded()));
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @LegalCompliance("Retention policy requires storing transaction history")
    public void deleteTransaction(PortfolioPK portfolioPK, TransactionPK transactionPK){
        SQLQuery sqlQuery = SQLQuery.DELETE_TRANSACTION_ENTRY;

        try{
            ResultSet resultSet = executeQuery(sqlQuery, portfolioPK.getUUID(), transactionPK.getStrDate(), transactionPK.getTicker());
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmptyPortfolio(PortfolioPK portfolioPK) throws UnsupportedOperationException{
        SQLQuery sqlCheck = SQLQuery.CHECK_IF_PORTFOLIO_HAS_TRANSACTIONS;
        SQLQuery sqlDelete = SQLQuery.DELETE_PORTFOLIO;

        try {
            ResultSet checking = executeQuery(sqlCheck, portfolioPK.getUUID());
            if (checking.next()){
                checking.close();
                throw new UnsupportedOperationException("Cannot delete Portfolio, as FK will fail");
            }else {
                ResultSet delete = executeQuery(sqlDelete, portfolioPK.getUUID());
            }
        }catch (SQLException e){
            e.printStackTrace(); //Todo
        }
    }

    public void createPortfolio(){

    }

    /*
    todo CRUD on portfolio, U on transaction, CRUD on User, CRUD on TransactionRemainder, CRUD on DailyAssets.
     Create a new table in database called tracking or favorites
     */
}
