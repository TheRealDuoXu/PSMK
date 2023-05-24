package data.results;

public interface Result<T> extends Comparable<Result<T>>{
    T getRawData();
    T getCashFlows();
    String getResultType();
    String getFormat();

}
