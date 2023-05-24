package data.results;

import java.util.ArrayDeque;

public class IRRResult extends NPVResult {

    public IRRResult(ArrayDeque<String> rawData) {
        super(rawData);
    }
    @Override
    public String getResultType() {
        return "IRR Standard precision";
    }
    @Override
    public String getFormat() {
        return "Author, type, solution, cash flow year 1, cash flow year 2, etc";
    }
}
