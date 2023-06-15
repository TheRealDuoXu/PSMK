package model.database.dao;

import model.database.SQLQuery;
import model.database.containers.User.PersonalDataCipher;
import model.database.containers.User.User;
import model.database.containers.User.UserPK;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DAO {
    private static UserDAO instance;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        synchronized (TransactionsDAO.class) {
            if (instance == null) {
                instance = new UserDAO();
            }
        }
        return instance;
    }

    public User attemptLogin(String plainLogin, String plainPassword) throws AuthenticationException, NullPointerException {
        SQLQuery sqlLoginPwd = SQLQuery.SELECT_USER_BY_LOGIN_AND_PASSWORD;

        String hashedLogin = PersonalDataCipher.sha256HexHash(plainLogin);
        String hashedPassword = PersonalDataCipher.sha256HexHash(plainPassword);

        if (userExists(hashedLogin)) {
            try (ResultSet resultSet = executeQuery(sqlLoginPwd, hashedLogin, hashedPassword)) {
                if (resultSet.next()) {
                    return User.getInstance(UserPK.getInstance(resultSet.getString(0)), hashedLogin, hashedPassword, true);
                }else {
                    throw new AuthenticationException();
                }

            } catch (SQLException e) {
                // todo
                throw new RuntimeException(e);
            }
        }else {
            throw new NullPointerException("User does not exist");
        }
    }

    private boolean userExists(String hashedLogin) {
        SQLQuery sqlLogin = SQLQuery.CHECK_IF_USER_EXISTS;
        try (ResultSet resultSet = executeQuery(sqlLogin, hashedLogin)) {
            return resultSet.next();
        } catch (SQLException e) {
            // Error consulting database
            throw new RuntimeException(e);
        }
    }

    /**
     * AuthenticationException indicates that password failed to match
     */
    public static class AuthenticationException extends IllegalAccessException {
    }
}
