package com.aaronraffdev.squote;

public class Stock {
    private String companyName;
    private String latestPrice;
    private String primaryExchange;
    private String sector;
    private String symbol;

    public String getCompanyName() {
        return companyName;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public String getSector() {
        return sector;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLatestPrice() {
        return latestPrice;
    }
}
