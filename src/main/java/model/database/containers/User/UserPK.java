package model.database.containers.User;

import model.database.containers.Inmutable;
import model.database.containers.PrimaryKey;

@Inmutable
public class UserPK extends PrimaryKey {
    public static final int UUID_LENGTH = 36;
    private final String uuid;

    private UserPK(String uuid) {
        this.uuid = uuid;
    }

    public static UserPK getInstance(String uuid) {
        if (uuid.length() == UUID_LENGTH){
            return new UserPK(uuid);
        }else {
            throw new IllegalArgumentException("UUID length does not match 36 characters length");
        }
    }
    public String getUuid() {
        return uuid;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public int compareTo(PrimaryKey o) {
        return 0;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
