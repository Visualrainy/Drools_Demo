package com.drools.demo.rule.model;

public enum CompareMethod {
    EQUAL("=="),
    GRATER(">"),
    SMALLER("<"),
    BEFORE("before"),
    AFTER("after");

    private String symbol;

    CompareMethod(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
