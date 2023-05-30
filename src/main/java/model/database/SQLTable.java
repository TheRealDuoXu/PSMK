package model.database;

public enum SQLTable {
    DAILY_ASSETS("DailyAssets", 0), ELEMENT("Element", 1), PORTFOLIO("Portfolio", 2),
    SIMULATION("Simulation", 3), TRANSACTION_REMAINDER("TransactionRemainder", 4),
    TRANSACTIONS("Transactions", 5), USER("User", 6), USER_PREFERENCES("UserPreferences", 7);
    private final int id;
    private final String sqlName;

    private SQLTable(String sqlName, int id) {
        this.sqlName = sqlName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTableName() {
        return sqlName;
    }

    @Override
    public String toString() {
        return sqlName;
    }
}
