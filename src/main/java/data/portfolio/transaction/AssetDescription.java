package data.portfolio.transaction;

public class AssetDescription implements Comparable<AssetDescription>{
    String ticker;
    String stockExchange;
    String strType;
    char chrType;

    public AssetDescription(String ticker, String stockExchange, char chrType) {
        this.ticker = ticker;
        this.stockExchange = stockExchange;
        this.chrType = chrType;
        this.strType = parseAssetTypeToString(chrType);
    }

    private String parseAssetTypeToString(char chrType) {
        switch (chrType) {
            case 'S':
                return "Stock";
            case 'B':
                return "Bond";
            case 'I':
                return "Index";
            case 'C':
                return "Currency";
            case 'Y':
                return "Crypto";
            default:
                throw new IllegalArgumentException("No such description matching " + chrType);
        }
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public char getChrType() {
        return chrType;
    }

    public void setChrType(char chrType) {
        this.chrType = chrType;
    }

    @Override
    public int compareTo(AssetDescription other) {
        return this.ticker.compareToIgnoreCase(other.ticker);
    }
}
