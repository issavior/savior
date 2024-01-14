package cn.sunjinxin.savior.rule;

import cn.hutool.extra.expression.engine.aviator.AviatorEngine;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunjinxin
 * @since 2023/12/7 19:33
 */
public class RuleRun {

    public static void main(String[] args) {

        AviatorEvaluator.validate("x > 1");

        Expression expression = AviatorEvaluator.compile("key1", "x > 1", true);
        Expression key1 = AviatorEvaluator.getCachedExpression("key1");

        HashMap<@Nullable String, @Nullable Object> map = Maps.newHashMap();
        map.put("x", 1);

        Object execute = expression.execute(map);
        System.out.println(execute);

    }
}
