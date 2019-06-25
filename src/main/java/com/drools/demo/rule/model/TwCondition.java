package com.drools.demo.rule.model;

import java.util.ArrayList;

public class TwCondition {
    private CompareMethod compareMethod;
    private String left;
    private ArrayList right;

    public TwCondition(String left, CompareMethod compareMethod, ArrayList right) {
        this.left = left;
        this.compareMethod = compareMethod;
        this.right = right;
    }

    public CompareMethod getCompareMethod() {
        return compareMethod;
    }

    public void setCompareMethod(CompareMethod compareMethod) {
        this.compareMethod = compareMethod;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public ArrayList getRight() {
        return right;
    }

    public void setRight(ArrayList right) {
        this.right = right;
    }
}
