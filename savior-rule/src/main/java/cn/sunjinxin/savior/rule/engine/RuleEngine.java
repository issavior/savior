package cn.sunjinxin.savior.rule.engine;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

public class RuleEngine {

    public void compile(String ruleGroup, String ruleId, String expression) {
        AviatorEvaluator.getInstance().validate(expression);
        AviatorEvaluator.getInstance().compile(ruleGroup + "_" + ruleId, expression, true);
    }

    public Expression getExpression(String ruleGroup, String ruleId) {
        return AviatorEvaluator.getInstance().getCachedExpression(ruleGroup + "_" + ruleId);
    }
}
