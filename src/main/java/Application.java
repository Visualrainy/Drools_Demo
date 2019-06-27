import com.drools.demo.domain.model.Person;
import com.drools.demo.rule.RuleGenerator;
import com.drools.demo.rule.model.CompareMethod;
import com.drools.demo.rule.model.TwCondition;
import com.drools.demo.rule.model.TwRule;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Application {
    public static void main(String[] args) throws ParseException {
        Map<String, Object> facts = generateFacts();

        TwCondition condition1 = new TwCondition("amount", CompareMethod.GRATER,
                Collections.singletonList(800));
        TwCondition condition2 = new TwCondition("bookDate", CompareMethod.BEFORE,
                Collections.singletonList(new Date().getTime()));

        TwRule twRule = new TwRule("rule_demo");
        twRule.setConditions(Arrays.asList(condition1, condition2));

//        new RuleGenerator().generatorDrlContent(twRule);

//        KieServices kieServices = KieServices.Factory.get();
//        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
//        KieBase kieBase = kieContainer.getKieBase();
//        KieSession kiesession = kieBase.newKieSession();

        StatelessKieSession kiesession = KieServices.Factory.get().getKieClasspathContainer().newStatelessKieSession();

        Set passRuleSet = new HashSet();
        kiesession.setGlobal("passRuleSet", passRuleSet);
        kiesession.execute(facts);
//        kiesession.insert(facts);
//        kiesession.fireAllRules();
        passRuleSet.forEach(item -> System.out.println("Pass Rule is: " + item));
    }

    private static Map<String, Object> generateFacts() throws ParseException {
        Person user = new Person();
        user.setLevel(1);
        user.setName("Name1");

        Map<String, Object> facts = new HashMap<>();
        facts.put("amount", 810);
        facts.put("bookDate", new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-26"));
        facts.put("person", user);
        return facts;
    }
}
