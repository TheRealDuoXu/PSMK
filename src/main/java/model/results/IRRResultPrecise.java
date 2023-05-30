package model.results;

import java.util.ArrayDeque;

public class IRRResultPrecise extends IRRResult{
    public IRRResultPrecise(ArrayDeque<String> rawData) {
        super(rawData);
    }

    @Override
    public String getResultType() {
        return "IRR High Precision";
    }
}
