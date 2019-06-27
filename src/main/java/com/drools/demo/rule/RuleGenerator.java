package com.drools.demo.rule;

import com.drools.demo.rule.model.TwRule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;

import java.util.Set;

public class RuleGenerator {
    public void generatorDrlContent(TwRule twRule) {
        StringBuilder sb = new StringBuilder();
        sb.append("package rules.point;\n")
                .append("global ")
                .append(Set.class.getName())
                .append(" passRuleSet;\n")
                .append("import java.util.Map;\n")
                .append("rule \"")
                .append(twRule.getName())
                .append("\"\n")
                .append("when $data: Map(");


        twRule.getConditions().forEach(condition -> {
            sb.append("$data.get(\"")
                    .append(condition.getLeft())
                    .append("\") ")
                    .append(condition.getCompareMethod().getSymbol())
                    .append(" ");

            if (condition.getRight().get(0) instanceof Number) {
                sb.append(condition.getRight().get(0))
                        .append(" ");
            } else {
                sb.append("\"").append(condition.getRight().get(0)).append("\" ");
            }
            if (twRule.getConditions().lastIndexOf(condition) != twRule.getConditions().size() - 1) {
                sb.append(",");
            }
        });
        sb.append(");\n");
        sb.append("then \n")
                .append("passRuleSet.add(")
                .append("\"" + twRule.getName() + "\"")
                .append(");\n")
                .append("end");

        writeDrlMemoryFile(sb.toString());
    }

    private void writeDrlMemoryFile(String rule) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/point/rules.drl", rule);
        KieBuilder kb = kieServices.newKieBuilder(kfs).buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
    }
}
