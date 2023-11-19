package cn.sunjinxin.savior.core.configuration;

import cn.sunjinxin.savior.core.anno.External;
import cn.sunjinxin.savior.core.anno.Savior;
import cn.sunjinxin.savior.core.constant.SaviorContent;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.reflections.Reflections;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import javax.annotation.Nonnull;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;

/**
 * SaviorAutoconfigurationImportSelector
 *
 * @author issavior
 */
public class SaviorAutoconfigurationImportSelector implements ImportSelector {

    /**
     * Injecting beans that implement the specified interface
     *
     * @param metadata /
     * @return /
     */
    @Override
    @Nonnull
    public String[] selectImports(@Nonnull AnnotationMetadata metadata) {
        return Optional.ofNullable(metadata.getAnnotationAttributes(Savior.class.getName()))
                .filter(map -> (boolean) map.get(SaviorContent.ENABLE))
                .map(map -> Optional.of(Lists.newArrayList((String[]) map.get(SaviorContent.VALUE)))
                        .filter(CollectionUtils::isNotEmpty)
                        .orElse(Lists.newArrayList(ClassUtils.getPackageName(metadata.getClassName())))
                        .stream()
                        .map(Reflections::new)
                        .flatMap(reflections ->
                                new Reflections(SaviorContent.ROOT_PATH)
                                        .getTypesAnnotatedWith(External.class, true)
                                        .stream()
                                        .flatMap(s ->
                                                reflections.getSubTypesOf(s)
                                                        .stream()
                                                        .filter(t -> !t.isInterface())
                                                        .filter(i -> !Modifier.isAbstract(i.getModifiers()))
                                                        .map(Class::getName)))
                        .toArray(String[]::new))
                .orElse(new String[0]);
    }

    /**
     * this#getExclusionFilter invoke when this#selectImports is not empty.
     * <p>iterate this#selectImports,exclude true of predicate</p>
     *
     * @return /
     */
    @Override
    public Predicate<String> getExclusionFilter() {
        return s -> false;
    }
}
