package model.database;

import model.database.dao.*;

public class DAOFactory {
    private DAOFactory(){}

    public static DAO getDAOInstanceForTable(SQLTable table){
        switch (table){
            case ELEMENT:
                break;
            case PORTFOLIO:
                return PortfolioDAO.getInstance();
            case SIMULATION:
                break;
            case TRANSACTION_REMAINDER:
                break;
            case TRANSACTIONS:
                return TransactionsDAO.getInstance();
            case DAILY_ASSETS:
                return DailyAssetsDAO.getInstance();
            case USER:
                return UserDAO.getInstance();
            case USER_PREFERENCES:
                break;
            default:
                throw new IllegalArgumentException("No such DAO exists for table " + table.getTableName());
        }
        return null;
    }

    public static CSVFileDAO getFileDAO(){
        return CSVFileDAO.getInstance();
    }
    public static CSVFileDAO getFileDAO(String filePath){
        return CSVFileDAO.getInstance(filePath);
    }
}
