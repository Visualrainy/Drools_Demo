package com.drools.demo.rule.model;

import java.util.List;

public class TwRule {
    private String name;
    private List<TwCondition> conditions;
    private TwAction twAction;

    public TwRule(String name) {
        this.name = name;
    }

    public List<TwCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<TwCondition> conditions) {
        this.conditions = conditions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
