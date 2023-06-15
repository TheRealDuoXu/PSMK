package model.database.containers.User;

import model.database.containers.Description;

public class UserDescription implements Description {
    public static final int MAX_NAME_FIELD_LENGTH = 15;
    public static final int MAX_SURNAME_FIELD_LENGTH = 15;
    public static final int MAX_EMAIL_FIELD_LENGTH = 25;
    public static final int MAX_PLAIN_PASSWORD_FIELD_LENGTH = 32; // Hashed, not encrypted
    public static final int MAX_ENCRYPTED_NAME_LENGTH = 32;
    public static final int MAX_ENCRYPTED_SURNAME_LENGTH = 32;
    public static final int MAX_ENCRYPTED_EMAIL_LENGTH = 42;
    private String name, surname, email;
    private boolean isEncrypted = false;

    private UserDescription(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    private UserDescription(String name, String surname, String email, boolean isEncrypted) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isEncrypted = isEncrypted;
    }

    public static UserDescription getEncryptedInstance(String name, String surname, String email, String password) {
        String encryptedName, encryptedSurname, encryptedEmail;
        if (name.length() <= MAX_NAME_FIELD_LENGTH && surname.length() <= MAX_SURNAME_FIELD_LENGTH
                && email.length() <= MAX_EMAIL_FIELD_LENGTH && password.length() <= MAX_PLAIN_PASSWORD_FIELD_LENGTH) {
            encryptedName = PersonalDataCipher.encrypt(name, password);
            encryptedSurname = PersonalDataCipher.encrypt(surname, password);
            encryptedEmail = PersonalDataCipher.encrypt(email, password);

            return new UserDescription(encryptedName, encryptedSurname, encryptedEmail, true);
        } else {
            throw new IllegalArgumentException("One field is exceeding max allowed length");
        }
    }

    public static UserDescription getInstance(String name, String surname, String email) {
        if (name.length() <= MAX_ENCRYPTED_NAME_LENGTH && surname.length() <= MAX_ENCRYPTED_SURNAME_LENGTH
                && email.length() <= MAX_ENCRYPTED_EMAIL_LENGTH) {
            return new UserDescription(name, surname, email);
        } else {
            throw new IllegalArgumentException("One field is exceeding max allowed length");
        }
    }

    public String getName() throws IllegalAccessException {
        if (!isEncrypted)
            return name;
        else throw new IllegalAccessException("Encrypted field must be accessed by password");
    }

    public String getSurname() throws IllegalAccessException {
        if (!isEncrypted)
            return surname;
        else throw new IllegalAccessException("Encrypted field must be accessed by password");
    }

    public String getEmail() throws IllegalAccessException {
        if (!isEncrypted)
            return email;
        else throw new IllegalAccessException("Encrypted field must be accessed by password");
    }

    public String getName(String password) {
        if (password.length() <= MAX_PLAIN_PASSWORD_FIELD_LENGTH)
            return PersonalDataCipher.decrypt(name, password);
        else
            throw new IllegalArgumentException("Password too long");
    }

    public String getSurname(String password) {
        if (password.length() <= MAX_PLAIN_PASSWORD_FIELD_LENGTH)
            return PersonalDataCipher.decrypt(surname, password);
        else
            throw new IllegalArgumentException("Password too long");
    }

    public String getEmail(String password) {
        if (password.length() <= MAX_PLAIN_PASSWORD_FIELD_LENGTH)
            return PersonalDataCipher.decrypt(email, password);
        else
            throw new IllegalArgumentException("Password too long");
    }

    @Override
    public String describe() {
        return null;
    }

    @Override
    public String shortDescribe() {
        return null;
    }

    @Override
    public String[] toArrayDescription() {
        return new String[0];
    }
}
