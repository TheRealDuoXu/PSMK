package model.database.containers.DailyAssets;

import model.database.containers.Inmutable;
import model.database.containers.Values;

@Inmutable
public class DailyAssetValues extends Values<String> {
    private static final int NUMBER_OF_FIELDS_IN_DAILY_ASSETS = 7;

    private DailyAssetValues(String[] data) {
        super(data);
    }

    public static DailyAssetValues getInstance(String ...data){
        if (data.length == NUMBER_OF_FIELDS_IN_DAILY_ASSETS) {
            return new DailyAssetValues(data);
        }
        throw new IllegalArgumentException("Exceeds number of fields in schema, number of fields expected: " + NUMBER_OF_FIELDS_IN_DAILY_ASSETS
                + " you introduced: " + data.length);
    }
}
