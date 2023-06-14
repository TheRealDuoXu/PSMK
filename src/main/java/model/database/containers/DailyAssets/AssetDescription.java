package model.database.containers.DailyAssets;

import model.database.containers.Description;
import model.database.containers.Inmutable;
import model.database.containers.PrimaryKey;

/**
 * Describes an asset, does not contain time data as it is contained in the maps as PK
 * <p>
 * This is an inmutable object, it's fields are all final, and shall not be modified by
 * the user
 */
@Inmutable
public class AssetDescription implements Comparable<AssetDescription>, Description {
    public static final int NUMBER_OF_PARAMETERS = 3;
    final PrimaryKey.Ticker ticker;
    final String stockExchange;
    final String strType;
    final char chrType;
    final AssetType assetType;

    public AssetDescription(PrimaryKey.Ticker ticker, String stockExchange, char chrType) {
        this.ticker = ticker;
        this.stockExchange = stockExchange;
        this.chrType = chrType;
        this.strType = parseAssetTypeToString(chrType);
        this.assetType = parseAssetType(chrType);
    }

    public AssetDescription(String[] orderedStrings) {
        this.ticker = PrimaryKey.Ticker.getInstance(orderedStrings[0]);
        this.stockExchange = orderedStrings[1];
        this.chrType = orderedStrings[2].toUpperCase().charAt(0);
        this.strType = parseAssetTypeToString(chrType);
        this.assetType = parseAssetType(chrType);
    }

    @Override
    public String describe() {
        return "ticker: " + ticker + "\n" +
                "stock exchange: " + stockExchange + "\n" +
                "asset type: " + strType + "\n";
    }

    @Override
    public String shortDescribe() {
        return "ticker: " + ticker;
    }

    @Override
    public String[] toArrayDescription() {
        return new String[0];
    }

    public enum AssetType {
        STOCK("Stock", 'S'), BOND("Bond", 'B'), INDEX("Index", 'I'),
        CURRENCY("Currency", 'C'), CRYPTO("Crypto", 'Y');
        final String name;
        final char chr;

        AssetType(String name, char chr) {
            this.name = name;
            this.chr = chr;
        }

        public char chr() {
            return chr;
        }
    }

    private AssetType parseAssetType(char chrType) {
        switch (chrType) {
            case 'S':
                return AssetType.STOCK;
            case 'B':
                return AssetType.BOND;
            case 'I':
                return AssetType.INDEX;
            case 'C':
                return AssetType.CURRENCY;
            case 'Y':
                return AssetType.CRYPTO;
            default:
                throw new IllegalArgumentException("No such description matching " + chrType);
        }
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

    public PrimaryKey.Ticker getTicker() {
        return ticker;
    }
    public String getStrTicker() {
        return ticker.getTicker();
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public String getStrType() {
        return strType;
    }

    public char getChrType() {
        return chrType;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    @Override
    public int compareTo(AssetDescription other) {
        return this.ticker.toString().compareToIgnoreCase(other.ticker.toString());
    }
}
