package model.database.dao;

import model.database.SQLQuery;
import model.database.containers.Portfolio.PorfolioDescription;
import model.database.containers.Portfolio.PortfolioPK;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public PorfolioDescription getPortfolioDescription(PortfolioPK pk) {
        SQLQuery sql = SQLQuery.SELECT_PORTFOLIO_DESCRIPTION_BY_UUID;

        try(ResultSet resultSet = executeQuery(sql, pk.getUUID())) {
            resultSet.next();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
