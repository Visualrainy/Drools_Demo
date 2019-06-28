package com.drools.demo.rule.util;

import com.drools.demo.domain.model.Person;
import com.drools.demo.rule.model.CompareMethod;
import com.drools.demo.rule.model.TwCondition;
import com.drools.demo.rule.model.TwRule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RuleHelper {
    public static Map<String, Object> generateFacts() throws ParseException {
        Person user = new Person();
        user.setLevel(1);
        user.setName("Name1");

        Map<String, Object> facts = new HashMap<>();
        facts.put("amount", 810);
        facts.put("bookDate", new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-26"));
        facts.put("person", user);
        return facts;
    }

    public static TwRule generateRule(List<TwCondition> conditions) {
        TwRule twRule = new TwRule("rule_demo");
        twRule.setConditions(conditions);
        return twRule;
    }

    public static List<TwCondition> generateConditions() throws ParseException {
        TwCondition condition1 = new TwCondition("amount", CompareMethod.GRATER,
                Collections.singletonList(800));
        TwCondition condition2 = new TwCondition("bookDate", CompareMethod.BEFORE,
                Collections.singletonList(new Date().getTime()));

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-25");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-30");

        TwCondition condition3 = new TwCondition("bookDate", CompareMethod.BETWEEN,
                Arrays.asList(startDate.getTime(), endDate.getTime()));
        return Arrays.asList(condition1, condition2, condition3);
    }
}
