package model.database.containers;

public abstract class PrimaryKey{
    public String[] data;
    public PrimaryKey(String[] data){
        this.data = data;
    }

    public abstract String[] getData();
}
