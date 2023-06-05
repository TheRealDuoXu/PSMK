package model.database.containers;

public abstract class Values<T> {
    public T[] data;

    public Values(T[] data){
        this.data = data;
    }
    public T[] getData() {
        return data;
    }
    public void setData(T[] data) {
        this.data = data;
    }
}
