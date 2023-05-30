package model.database;

import model.database.dao.CSVFileDAO;
import model.database.dao.DAO;
import model.database.dao.TransactionsDAO;

public class DAOFactory {
    private DAOFactory(){}

    public static DAO getDAOInstanceForTable(SQLTable table){
        switch (table){
            case ELEMENT:
                break;
            case PORTFOLIO:
                break;
            case SIMULATION:
                break;
            case TRANSACTION_REMAINDER:
                break;
            case TRANSACTIONS:
                return TransactionsDAO.getInstance();
            case DAILY_ASSETS:
                break;
            case USER:
                break;
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
