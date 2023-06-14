package model.database.containers.Portfolio;

import model.database.containers.Inmutable;
import model.database.containers.PrimaryKey;

@Inmutable
public class PortfolioPK extends PrimaryKey {
    private static final int NUMBER_OF_FIELDS_IN_PRIMARY_KEY = 1;
    private static final int UUID_LENGTH = 36;
    private final String uuid;
    private PortfolioPK(String uuid){
        this.uuid = uuid;
    }

    public static PortfolioPK getInstance(String uuid) {
        if (uuid.length() == UUID_LENGTH) return new PortfolioPK(uuid);
        else throw new IllegalArgumentException("uuid length does not match");
    }

    public String getUUID(){
        return uuid;
    }

    @Override
    public int compareTo(PrimaryKey o) {
        return 0;
    }

    @Override
    public int length() {
        return NUMBER_OF_FIELDS_IN_PRIMARY_KEY;
    }
}
