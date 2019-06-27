package com.drools.demo.rule;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RuleExecutor {
    public static void fireRule(Map<String, Object> facts) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession kiesession = kieBase.newKieSession();

        Set passRuleSet = new HashSet();
        kiesession.setGlobal("passRuleSet", passRuleSet);
        kiesession.insert(facts);
        kiesession.fireAllRules();
        passRuleSet.forEach(item -> System.out.println("Pass Rule is::::::::: " + item));
    }
}
