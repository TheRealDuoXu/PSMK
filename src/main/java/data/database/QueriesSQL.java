package data.database;

enum QueriesSQL {
    SELECT_TRANSACTION_REMAINDER_BY_PORTFOLIO_UUID("select * from TransactionRemainder " +
            "where FK_UUID_Portfolio = '?'", 1),
    SELECT_TRANSACTION_HISTORY_BY_PORTFOLIO_UUID("select * from Transactions " +
            "where FK_UUID_Portfolio = '?'", 1),
    SELECT_TRANSACTION_HISTORY_BY_PORTFOLIO_AND_TICKER("select * from Transactions " +
            "where FK_UUID_Portfolio = '?' and FK_Ticker_DailyAssets = '?'", 2),
    SELECT_TRANSACTION_POINT("select * from Transactions " +
            "where FK_UUID_Portfolio = '?' and FK_Ticker_DailyAssets = '?' and FK_Date_DailyAssets = '?'", 3),
    SELECT_USER_BY_LOGIN("select * from User where login = '?'", 1),
    SELECT_PORTFOLIO_BY_USER_UUID("select * from Portfolio " +
            "where FK_UUID_user = '?'", 1),

    INSERT_INTO_USER("insert into User(UUID, login, password) values ('?', '?', '?')", 3);

    String sql;
    final int requiredValues;

    QueriesSQL(String sql, int requiredValues) {
        this.sql = sql;
        this.requiredValues = requiredValues;
    }

    private void checkArgumentFulfilsNumberRequiredValues(int arrayLength) throws IllegalArgumentException {
        if (this.requiredValues != arrayLength) {
            throw new IllegalArgumentException("Value length " + arrayLength + " does not match required values: " + requiredValues);
        }
    }

    public void parseValuesIntoSql(String... values) throws IllegalArgumentException{
        checkArgumentFulfilsNumberRequiredValues(values.length);

        StringBuilder parsedSql = new StringBuilder(sql);
        for (String value : values) {
            int index = parsedSql.indexOf("?");
            if (index != -1) {
                parsedSql.replace(index, index + 1, value);
            }
        }
        this.sql = parsedSql.toString();
    }

    @Override
    public String toString(){
        return sql;
    }
}
