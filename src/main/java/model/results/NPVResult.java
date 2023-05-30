package model.results;

import java.util.ArrayDeque;

public class NPVResult implements Result<ArrayDeque<String>>{
    private final ArrayDeque<String> rawData;

    public NPVResult(ArrayDeque<String> rawData) {
        this.rawData = rawData;
    }

    @Override
    public ArrayDeque<String> getRawData() {
        return rawData;
    }

    @Override
    public ArrayDeque<String> getCashFlows() {
        ArrayDeque<String> tmp = rawData.clone();

        tmp.pop();
        tmp.pop();
        tmp.pop();

        return tmp;
    }

    @Override
    public String getResultType() {
        return "Net Present Value";
    }

    @Override
    public String getFormat() {
        return "Author, type, solution, discount, cash flow year 1, cash flow year 2, etc";
    }

    /**
     * Implementado de tal forma que permita comparar soluciones y clasificarlos
     * @param arrayDequeResult the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Result<ArrayDeque<String>> arrayDequeResult) {
        if (rawData.equals(arrayDequeResult)){
            return 0;
        } else if (Double.parseDouble((String) rawData.toArray()[2]) > Double.parseDouble((String) arrayDequeResult.getRawData().toArray()[2])) {
            return 1;
        }else {
            return -1;
        }
    }
}
