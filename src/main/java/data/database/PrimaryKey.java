package data.database;

public abstract class PrimaryKey{
    String[] data;
    public PrimaryKey(String[] data){
        this.data = data;
    }

    public abstract String[] getData();
}
