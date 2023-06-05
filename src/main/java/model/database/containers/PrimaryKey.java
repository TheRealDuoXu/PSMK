package model.database.containers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class PrimaryKey{
    protected String[] data;
    public PrimaryKey(String[] data){
        this.data = data;
    }
    protected Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }
    public abstract String[] getData();
}
