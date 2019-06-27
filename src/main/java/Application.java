import com.drools.demo.rule.RuleExecutor;
import com.drools.demo.rule.RuleGenerator;
import com.drools.demo.rule.model.TwCondition;
import com.drools.demo.rule.model.TwRule;
import com.drools.demo.rule.util.RuleHelper;

import java.text.ParseException;
import java.util.*;

public class Application {
    public static void main(String[] args) throws ParseException {
        List<TwCondition> conditions = RuleHelper.generateConditions();
        TwRule twRule = RuleHelper.generateRule(conditions);

        RuleGenerator.generatorDrlContent(twRule);
        RuleExecutor.fireRule(RuleHelper.generateFacts());
    }

}
