package model.database;

public enum SQLQuery {
    SELECT_FIRST_ASSET_MATCH_TICKER("select Ticker, Stock_exchange, Type from " + SQLTable.DAILY_ASSETS + " " +
            "where Ticker = '?' limit 1", 1),
    SELECT_DAILY_ASSET_BY_TICKER("select * from DailyAssets where Ticker ='?'", 1),
    SELECT_DAILY_ASSET_BTW_DATES("select * from DailyAssets where Ticker = '?' and Date between '?' and '?'", 3),
    SELECT_TRANSACTION_REMAINDER_BY_PORTFOLIO_UUID("select * from " + SQLTable.TRANSACTION_REMAINDER + " " +
            "where FK_UUID_Portfolio = '?'", 1),
    SELECT_TRANSACTION_MAPS_ON_PORTFOLIO("select FK_Ticker_DailyAssets, FK_Date_DailyAssets, amount from " + SQLTable.TRANSACTIONS +
            " where FK_UUID_Portfolio = '?'", 1),
    SELECT_TRANSACTION_HISTORY_BY_PORTFOLIO_AND_TICKER("select * from " + SQLTable.TRANSACTIONS + " " +
            "where FK_UUID_Portfolio = '?' and FK_Ticker_DailyAssets = '?'", 2),
    SELECT_TRANSACTION_POINT("select * from " + SQLTable.TRANSACTIONS + " " +
            "where FK_UUID_Portfolio = '?' and FK_Ticker_DailyAssets = '?' and FK_Date_DailyAssets = '?'", 3),
    SELECT_USER_BY_LOGIN("select * from " + SQLTable.USER + " where login = '?'", 1),
    SELECT_PORTFOLIO_BY_USER_UUID("select * from " + SQLTable.PORTFOLIO + " " +
            "where FK_UUID_user = '?'", 1),
    SELECT_DAILY_ASSETS_VALUES_BY_PKs("select Stock_exchange, Type, Open, High, Low, Close, Vol " +
            "from DailyAssets where Ticker='?' and Date='?'", 2),
    SELECT_PORTFOLIO_DESCRIPTION_BY_UUID("select * from Portfolio where UUID = '?'", 1),


    INSERT_INTO_USER("insert into " + SQLTable.USER + "(UUID, login, password) values ('?', '?', '?')", 3),
    INSERT_DAILY_ASSET("insert into DailyAssets(Ticker, Stock_exchange, Type, Date, Open, High, Low, Close, Vol) " +
            "values ('?','?','?','?','?','?','?','?','?')", 9);

    final String rawSQL;
    private String parsedSQL;
    final int requiredValues;

    SQLQuery(String rawSQL, int requiredValues) {
        this.rawSQL = rawSQL;
        this.requiredValues = requiredValues;
    }

    private void checkArgumentFulfilsNumberRequiredValues(int arrayLength) throws IllegalArgumentException {
        if (this.requiredValues != arrayLength) {
            throw new IllegalArgumentException("Value length " + arrayLength + " does not match required values: " + requiredValues);
        }
    }

    public synchronized void parseValuesIntoSql(String... values) throws IllegalArgumentException {
        checkArgumentFulfilsNumberRequiredValues(values.length);

        StringBuilder parsedSql = new StringBuilder(rawSQL);
        for (String value : values) {
            int index = parsedSql.indexOf("?");
            if (index != -1) {
                parsedSql.replace(index, index + 1, value);
            }
        }
        this.parsedSQL = parsedSql.toString();
    }

    @Override
    public synchronized String toString() {
        if (this.parsedSQL != null) {
            return this.parsedSQL;
        }
        throw new NullPointerException("Parsed SQL is not initialized");
    }
}
