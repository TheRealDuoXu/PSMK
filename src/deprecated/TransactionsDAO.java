//package model.database.dao;
//
//import model.database.containers.DailyAssets.DailyAssetPK;
//import model.database.containers.PrimaryKey;
//import model.database.containers.Values;
//import model.database.containers.DailyAssets.BondHistoricalMap;
//import model.database.containers.DailyAssets.InvestmentHistoricalMap;
//import model.database.containers.DailyAssets.StockHistoricalMap;
//import model.database.containers.DailyAssets.AssetDescription;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.UUID;
//
///**
// * Connects tables Transaction and TransactionRemainder with java programme
// */
//public class TransactionsDAO extends DAO {
//    private final static int TRANSACTIONS_AMOUNT_COLUMN_INDEX = 3;
//    private static final int PRIMARY_KEY_DATA_LENGTH = 2;
//    private static final int FK_TICKER_DAILY_ASSETS_COLUMN_INDEX = 0;
//    private static final int FK_DATE_DAILY_ASSETS_COLUMN_INDEX = 1;
//
//    private static TransactionsDAO instance;
//
//    private TransactionsDAO() {
//        super();
//    }
//
//    static TransactionsDAO getInstance() {
//        synchronized (CSVFileDAO.class) {
//            if (instance == null) {
//                instance = new TransactionsDAO();
//            }
//        }
//        return instance;
//    }
//
//    public TransactionCollectionList getAllHistoryFromPorfolio(UUID portfolioUUID) {
//        SQLQueries sql = SQLQueries.SELECT_TRANSACTION_HISTORY_BY_PORTFOLIO_UUID;
//        TransactionCollectionList transactionCollectionList;
//        ResultSet resultSet = executeQuery(sql, portfolioUUID.toString());
//
//        try {
//            while (resultSet.next()) {
//                // Obtener los valores de las columnas del ResultSet y construir objetos TransactionPoint
//                // Agregar los TransactionPoint a la TransactionCollection
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeResultSet(resultSet);
//        }
//
//        return null;
//    }
//
//    public TransactionCollection<? extends InvestmentHistoricalMap> getAllHistoryFromPorfolio(UUID portfolioUUID, String ticker) {
//        SQLQueries sql = SQLQueries.SELECT_TRANSACTION_HISTORY_BY_PORTFOLIO_AND_TICKER;
//        try (ResultSet resultSet = executeQuery(sql, portfolioUUID.toString(), ticker)) {
//            return parseTransactionCollection(resultSet, getAssetDescription(ticker));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private TransactionCollection<? extends InvestmentHistoricalMap> parseTransactionCollection(ResultSet resultSet, AssetDescription assetDescription) throws SQLException {
//        switch (assetDescription.getAssetType()) {
//            case STOCK:
//                return getTransactionCollection(resultSet);
//            case BOND:
//                return parseBondTransactions(resultSet);
//            case INDEX:
//            case CURRENCY:
//            case CRYPTO:
//            default:
//        }
//
//        return null;
//    }
//
//    private TransactionCollection<StockHistoricalMap> getTransactionCollection(ResultSet resultSet) throws SQLException {
//        TransactionCollection<StockHistoricalMap> stockHistoricalDataTransactionCollection = new TransactionCollection<>();
//        StockHistoricalMap stock;
//        DailyAssetPK primaryKey;
//        Values<String> values;
//        float amount;
//
//        while (resultSet.next()) {
//            primaryKey = getDailyAssetPrimaryKey(resultSet.getString(FK_TICKER_DAILY_ASSETS_COLUMN_INDEX),
//                    resultSet.getString(FK_DATE_DAILY_ASSETS_COLUMN_INDEX));
//
//            values = getOneDailyAssetByPK(DailyAssetsDAO.getInstance(), primaryKey);
//
////          Always create a new map to hold the transaction's asset's point in time key value pair
//            stock = new StockHistoricalMap(primaryKey, values);
//            amount = resultSet.getFloat(TRANSACTIONS_AMOUNT_COLUMN_INDEX);
//
//            stockHistoricalDataTransactionCollection.add(new TransactionPoint<StockHistoricalMap>
//                    (stock, amount));
//        }
//
//        return stockHistoricalDataTransactionCollection;
//    }
//
//    private Values<String> getOneDailyAssetByPK(DailyAssetsDAO instance, DailyAssetPK primaryKey) {
//        return instance.getOneDailyAssetByPK(primaryKey);
//    }
//
//    private DailyAssetPK getDailyAssetPrimaryKey(String ticker, String date) {
//        return DailyAssetPK.getInstance(ticker, date);
//    }
//
//
//    private TransactionCollection<BondHistoricalMap> parseBondTransactions(ResultSet resultSet) {
//    }
//
//    private StockHistoricalMap buildSingleStockHistoricalData() {
//        LinkedHashMap<PrimaryKey, Values<String>> map;
//    }
//
//    /**
//     * Returns one transaction point from Transactions table given all the data except for amount
//     *
//     * @param portfolioUUID portfolio the transactions belongs to
//     * @param ticker        the asset ticket traded
//     * @param date          the date of the transaction
//     * @return an object that represents one transaction point
//     */
//    public TransactionPoint<? extends InvestmentHistoricalMap> getTransactionPoint(UUID portfolioUUID, String ticker, Date date) {
//        SQLQueries sql = SQLQueries.SELECT_TRANSACTION_POINT;
//        AssetDescription assetDescription = getAssetDescription(ticker);
//
//        InvestmentHistoricalMap assetWrapper;
//        float amount;
//        //todo not finished
//        try (ResultSet resultSet = executeQuery(sql, portfolioUUID.toString(), ticker, date.toString())) {
//            resultSet.next();
//            assetWrapper = createHistoricalDataObject(assetDescription);
//            amount = resultSet.getFloat(TRANSACTIONS_AMOUNT_COLUMN_INDEX);
//
//        } catch (SQLException e) {
//
//        }
//
////        InvestmentHistoricalData investmentHistoricalData = getInvestmentHistoricalData(assetDescription);
//
//
//        return transactionPoint;
//    }
//
//}
