package model.database.dao;

import model.database.SQLQuery;
import model.database.containers.DailyAssets.*;
import model.database.containers.Values;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class DailyAssetsDAO extends DAO {
    private static final int DAILY_ASSETS_VALUE_DATA_LENGTH = 7;
    private static final int TICKER_POS = 0;
    private static final int STOCK_EXCHANGE_POS = 1;
    private static final int TYPE_POS = 2;
    private static final int DATE_POS = 3;
    private static final int OPEN_POS = 4;
    private static final int HIGH_POS = 5;
    private static final int LOW_POS = 6;
    private static final int CLOSE_POS = 7;
    private static final int VOL_POS = 8;
    private static DailyAssetsDAO instance;

    private DailyAssetsDAO() {
        super();
    }

    static DailyAssetsDAO getInstance() {
        synchronized (CSVFileDAO.class) {
            if (instance == null) {
                instance = new DailyAssetsDAO();
            }
        }
        return instance;
    }

    public Values<String> getOneDailyAssetByPK(DailyAssetPK primaryKey) {
        SQLQuery sql = SQLQuery.SELECT_DAILY_ASSETS_VALUES_BY_PKs;
        try (ResultSet resultSet = executeQuery(sql, primaryKey.getTicket(), primaryKey.getStrDate())) {
            String[] values = new String[DAILY_ASSETS_VALUE_DATA_LENGTH];
            for (int i = 0; i < DAILY_ASSETS_VALUE_DATA_LENGTH; i++) {
                values[i] = resultSet.getString(i);
            }
            return DailyAssetValues.getInstance(values);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AssetDescription getAssetDescription(String ticker) {
        SQLQuery sql = SQLQuery.SELECT_FIRST_ASSET_MATCH_TICKER;

        try (ResultSet resultSet = executeQuery(sql, ticker)) {
            resultSet.next(); //init cursor
            String[] assetDescriptionArray = new String[AssetDescription.NUMBER_OF_PARAMETERS];

            for (int i = 0; i < AssetDescription.NUMBER_OF_PARAMETERS; i++) {
                assetDescriptionArray[i] = resultSet.getString(i);
            }

            return new AssetDescription(assetDescriptionArray);

        } catch (SQLException e) {
            System.out.println("Error executing query, could not get description: " + e.toString());
            throw new RuntimeException(e);
        }
    }

    public StockHistoricalMap getStockHistoricalMap(String ticker) {
        StockHistoricalMap stockHistoricalMap = new StockHistoricalMap();
        return collectDailyAssets(stockHistoricalMap, ticker);
    }

    public BondHistoricalMap getBondHistoricalMap(String ticker) {
        BondHistoricalMap bondHistoricalMap = new BondHistoricalMap();
        return collectDailyAssets(bondHistoricalMap, ticker);
    }

    //todo
    public StockHistoricalMap getStockHistoricalMapBtwDates(String ticker, Date from, Date to){
        return null;
    }
    //todo
    public BondHistoricalMap getBondHistoricalMapBtwDates(String ticker, Date from, Date to){
        return null;
    }

    private <ContainerType extends InvestmentHistoricalMap> ContainerType collectDailyAssets(ContainerType container, String ticker) {
        SQLQuery sql = SQLQuery.SELECT_DAILY_ASSET_BY_TICKER;
        DailyAssetPK pk;
        DailyAssetValues values;

        try (ResultSet resultSet = executeQuery(sql, ticker)) {
            return fillContainer(container, resultSet);
        } catch (ClassCastException e) {
            System.out.println("Error executing query: " + e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Error executing query, could not get historical data: " + e.toString());
            throw new RuntimeException(e);
        }
    }
    private <ContainerType extends InvestmentHistoricalMap> ContainerType collectDailyAssetsBtwDates
            (ContainerType container, String ticker, Date from, Date to) {
        SQLQuery sql = SQLQuery.SELECT_DAILY_ASSET_BTW_DATES;
        DailyAssetPK pk;
        DailyAssetValues values;

        try (ResultSet resultSet = executeQuery(sql, ticker, from.toString(), to.toString())) {
            return fillContainer(container, resultSet);
        } catch (ClassCastException e) {
            System.out.println("Error executing query: " + e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Error executing query, could not get historical data: " + e);
            throw new RuntimeException(e);
        }
    }

    private <ContainerType extends InvestmentHistoricalMap> ContainerType fillContainer(ContainerType container, ResultSet resultSet) throws SQLException {
        DailyAssetPK pk;
        DailyAssetValues values;
        ensureDataIntegrity(resultSet.getString(TYPE_POS), container.getType());

        while (resultSet.next()) {
            pk = DailyAssetPK.getInstance(
                    resultSet.getString(TICKER_POS),
                    resultSet.getString(DATE_POS));

            values = DailyAssetValues.getInstance(
                    resultSet.getString(STOCK_EXCHANGE_POS),
                    resultSet.getString(TYPE_POS),
                    resultSet.getString(OPEN_POS),
                    resultSet.getString(HIGH_POS),
                    resultSet.getString(LOW_POS),
                    resultSet.getString(CLOSE_POS),
                    resultSet.getString(VOL_POS));

            container.put(pk, values);
        }
        return container;
    }
    private void ensureDataIntegrity(String assetType, AssetDescription.AssetType type) throws ClassCastException{
        if (assetType.charAt(0) != type.chr()){
            throw new ClassCastException("Asset type does not match: " + assetType + " vs " + type.name());
        }
    }

    public void insertDailyAssetFromCSV(){

    }

    public void insertDailyAssetFromDirectory(){

    }
}
