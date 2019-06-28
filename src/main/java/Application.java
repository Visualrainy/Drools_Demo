import com.drools.demo.rule.RuleExecutor;
import com.drools.demo.rule.RuleGenerator;
import com.drools.demo.rule.evaluator.BetweenEvaluatorDefinition;
import com.drools.demo.rule.model.TwCondition;
import com.drools.demo.rule.model.TwRule;
import com.drools.demo.rule.util.RuleHelper;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Application {
    public static void main(String[] args) throws ParseException {
        List<TwCondition> conditions = RuleHelper.generateConditions();
        TwRule twRule = RuleHelper.generateRule(conditions);

        RuleGenerator.generatorDrlContent(twRule);
        RuleExecutor.fireRule(RuleHelper.generateFacts());

//        testCustomBetweenOperation();
    }

    private static void testCustomBetweenOperation() throws ParseException {
        String drlContentString =
                "global java.util.Set passRuleSet;\n" +
                "import java.util.Map;\n" +
                "import java.util.List;\n" +
                "rule \"rule_demo\"\n" +
                "when\n" +
                "$data: Map($data.get(\"amount\") > 809 ,$data.get(\"bookDate\") before 1561688512795)\n" +
                "$list: List($data.get(\"bookDate\") between $list)\n" +
                "then\n" +
                "passRuleSet.add(\"rule_demo\");\n" +
                "end";

        KieServices ks = KieServices.Factory.get();
        KieSession kiesession = new KieHelper()
                .setKieModuleModel(ks.newKieModuleModel()
                        .setConfigurationProperty("drools.evaluator.between",
                                BetweenEvaluatorDefinition.class.getName()))
                .addContent(drlContentString, ResourceType.DRL)
                .build()
                .newKieSession();

        Set passRuleSet = new HashSet();
        kiesession.setGlobal("passRuleSet", passRuleSet);
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-25");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-30");
        List<Long> duration = Arrays.asList(startDate.getTime(), endDate.getTime());
        kiesession.insert(RuleHelper.generateFacts());
        kiesession.insert(duration);
        kiesession.fireAllRules();
        passRuleSet.forEach(item -> System.out.println("Pass Rule is::::::::: " + item));
    }

}
