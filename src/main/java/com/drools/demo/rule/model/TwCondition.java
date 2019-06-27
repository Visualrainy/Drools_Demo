package com.drools.demo.rule.model;

import java.util.List;

public class TwCondition {
    private CompareMethod compareMethod;
    private String left;
    private List right;

    public TwCondition(String left, CompareMethod compareMethod, List right) {
        this.left = left;
        this.compareMethod = compareMethod;
        this.right = right;
    }

    public CompareMethod getCompareMethod() {
        return compareMethod;
    }

    public String getLeft() {
        return left;
    }

    public List getRight() {
        return right;
    }

}
