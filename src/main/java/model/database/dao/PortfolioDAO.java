package model.database.dao;

import model.database.SQLQuery;
import model.database.containers.Portfolio.PorfolioDescription;

public class PortfolioDAO extends DAO {
    private static PortfolioDAO instance;

    private PortfolioDAO() {
        super();
    }

    public static PortfolioDAO getInstance() {
        synchronized (PortfolioDAO.class) {
            if (instance == null) {
                instance = new PortfolioDAO();
            }
        }
        return instance;
    }

    public PorfolioDescription getPortfolioDescription(String uuid) {
        SQLQuery sql = SQLQuery.SELECT_PORTFOLIO_DESCRIPTION_BY_UUID;
        return null;
    }
}
