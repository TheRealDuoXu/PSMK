package model.database.containers.User;

public class User {
    private UserPK userPK;
    private String login;
    private String password;
    private boolean isHashed = false;

    private UserDescription userDescription;
    private User(UserPK userPK, String login, String password) {
        this.userPK = userPK;
        this.login = login;
        this.password = password;
        this.userDescription = getUserDescription();
    }

    private User(UserPK userPK, String hashedLogin, String hashedPassword, boolean isHashed) {
        this.userPK = userPK;
        this.login = hashedLogin;
        this.password = hashedPassword;
        this.isHashed = isHashed;
        this.userDescription = getUserDescription();
    }

    /**
     * Gets a User object hashing login and password. Intended for first time creation.
     *
     * @param userPK   user UUID, char 36
     * @param login    varchar 64, fits for sha-256 output
     * @param password varchar 64, fits for sha-256 output
     */
    public static User getHashedInstance(UserPK userPK, String login, String password) {
        String hashedPassword = PersonalDataCipher.sha256HexHash(password);
        String hashedLogin = PersonalDataCipher.sha256HexHash(login);

        return new User(userPK, hashedLogin, hashedPassword, true);
    }

    /**
     * Gets a User object not hashing login and password. Intended for representing database entity
     *
     * @param userPK   user UUID, char 36
     * @param login    varchar 64, fits for sha-256 output
     * @param password varchar 64, fits for sha-256 output
     */
    public static User getInstance(UserPK userPK, String login, String password, boolean isHashed) {
        // todo data integrity check, isHashed should be true
        return new User(userPK, login, password, isHashed);
    }

    private UserDescription getUserDescription() {
        return null;
    }

    public UserPK getUserPK() {
        return userPK;
    }

    public void setUserPK(UserPK userPK) {
        this.userPK = userPK;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserDescription(UserDescription userDescription) {
        this.userDescription = userDescription;
    }

    public boolean isHashed() {
        return isHashed;
    }

    public void hashCredentials(){
        this.login = PersonalDataCipher.sha256HexHash(this.login);
        this.password = PersonalDataCipher.sha256HexHash(this.password);
        this.isHashed = true;
    }

}
