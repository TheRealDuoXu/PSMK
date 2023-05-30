package model.database.dao;

import model.database.SQLQuery;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class DAO implements AutoCloseable{
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/PSMK";
    protected static final String DB_USER = "root";
    protected static final String DB_PASSWORD = "root";
    protected static Connection con;

    protected DAO() {
        con = getConnection();
    }

    private static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return con;
        } catch (SQLException e) {
            System.out.println("Connection error: " + e);
            //TODO
        }
        return null;
    }

    protected ResultSet executeQuery(SQLQuery query, String... values) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            query.parseValuesIntoSql(values);
            preparedStatement = con.prepareStatement(query.toString());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }

        return resultSet;
    }
    protected ResultSet executeQuery(SQLQuery query, SimpleDateFormat dateFormat, String[] values, String[] dates) {
        //todo
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            query.parseValuesIntoSql(values);
            preparedStatement = con.prepareStatement(query.toString());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }

        return resultSet;
    }

    protected static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception{
        con.close();
    }

}
