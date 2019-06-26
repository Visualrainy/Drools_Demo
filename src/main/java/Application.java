import com.drools.demo.domain.model.Person;
import com.drools.demo.rule.RuleGenerator;
import com.drools.demo.rule.model.CompareMethod;
import com.drools.demo.rule.model.TwCondition;
import com.drools.demo.rule.model.TwRule;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Application {
    public static void main(String[] args) throws ParseException {
        Person user = new Person();
        user.setLevel(1);
        user.setName("Name1");

        Map<String, Object> data = new HashMap<>();
        data.put("amount", 810);
        data.put("bookDate", new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-25"));
        data.put("person", user);

        TwCondition condition1 = new TwCondition("amount", CompareMethod.GRATER, Arrays.asList(800));
//        TwCondition condition2 = new TwCondition("bookDate", CompareMethod.GRATER, Arrays.asList("2019-06-25"));

        TwRule twRule = new TwRule("rule1");
        twRule.setConditions(Collections.singletonList(condition1));

        new RuleGenerator().generatorDrlContent(twRule);

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession kiesession = kieBase.newKieSession();
        Set passRuleSet = new HashSet();
        kiesession.setGlobal("passRuleSet", passRuleSet);

        kiesession.insert(data);
        kiesession.fireAllRules();
        passRuleSet.forEach(item -> System.out.println("Pass Rule is: " + item));
    }
}
