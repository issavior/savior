package cn.sunjinxin.savior.ext.container;

import cn.hutool.core.lang.Assert;
import cn.sunjinxin.savior.ext.anno.Ability;
import cn.sunjinxin.savior.ext.anno.ExtensionPoint;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ext init
 *
 * @author issavior
 */
@Slf4j
public class ExtContainer implements BeanPostProcessor {

    private static final Map<String, Map<Class<? extends IExt>, Map<String, List<IExt>>>> EXTENSION_MAP = Maps.newHashMap();

    /**
     * list
     *
     * @param request /
     * @param clazz   /
     * @return /
     */
    @SuppressWarnings("all")
    public static <E>List<E> getBeans(ExtRo request, Class<E> clazz) {
        return (List<E>)EXTENSION_MAP.get(String.format("%s_%s", request.getIndustry(), request.getBusiness()))
                .get(clazz)
                .get(String.format("%s_%s", request.getBizCode(), request.getScenario()));
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, @Nonnull String beanName) throws BeansException {
        ExtensionPoint[] annotations = bean.getClass().getAnnotationsByType(ExtensionPoint.class);
        if (annotations.length == 0) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }

        ExtensionPoint extensionPoint = annotations[0];
        Class<? extends IExt> superclass = checkAbs(bean);
        Ability ability = superclass.getAnnotation(Ability.class);
        Assert.isTrue(ObjectUtils.isNotEmpty(ability), () -> new RuntimeException("Need to add @ Ability annotation"));

        EXTENSION_MAP.computeIfAbsent(String.format("%s_%s", ability.industry(), ability.business()), v -> Maps.newHashMap())
                .computeIfAbsent(superclass, v -> Maps.newHashMap())
                .computeIfAbsent(String.format("%s_%s", extensionPoint.bizCode(), extensionPoint.scenario()), v -> Lists.newArrayList())
                .add((IExt) bean);

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private Class<? extends IExt> checkAbs(Object bean) {
        @SuppressWarnings("all")
        Class<? extends IExt> superclass = (Class<? extends IExt>) bean.getClass().getSuperclass();

        if (superclass == null) {
            throw new RuntimeException("An abstract class that needs to inherit the annotation @ Ability and implement the IExt interface");

        }

        if (Arrays.stream(superclass.getInterfaces()).allMatch(r -> r != IExt.class)) {
            beanSpecs(bean.getClass());
        }

        return superclass;
    }

    private static void beanSpecs(Class<?> aClass) {
        throw new RuntimeException("According to the usage guidelines of savior-ext, your encoding has encountered an error as follows: bean usage exception " + aClass);
    }

}
