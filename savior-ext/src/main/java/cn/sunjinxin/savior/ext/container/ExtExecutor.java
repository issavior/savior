package cn.sunjinxin.savior.ext.container;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author issavior
 */
public class ExtExecutor {

    private final Class<IExt> clazz;

    private ExtExecutor(Class<IExt> clazz) {
        this.clazz = clazz;
    }

    public static ExtExecutor abilityClass(Class<IExt> clazz) {
        return new ExtExecutor(clazz);
    }

    /**
     * Execute the extension point with the sorting result being the first one
     *
     * @param target   /
     * @param callBack /
     * @param <T>      /
     * @param <R>      /
     * @return /
     */
    public <T extends ExtParam, R> R execFirst(T target, Function<IExt, R> callBack) {
        return Optional.ofNullable(ExtContainer.getBeans(target, clazz))
                .orElse(Lists.newArrayList())
                .stream()
                .filter(IExt::enable)
                .max(Comparator.comparingInt(IExt::order))
                .map(callBack)
                .orElse(null);
    }

    /**
     * Execute all extension points
     *
     * @param target   /
     * @param consumer /
     * @param <T>      /
     */
    public <T extends ExtParam> void execAll(T target, Consumer<IExt> consumer) {
        Optional.ofNullable(ExtContainer.getBeans(target, clazz))
                .orElse(Lists.newArrayList())
                .stream()
                .filter(IExt::enable)
                .forEach(consumer);
    }

    /**
     * Execute all eligible extension points
     *
     * @param target    /
     * @param consumer  /
     * @param predicate /
     * @param <T>       /
     */
    public <T extends ExtParam> void execFilter(T target, Consumer<IExt> consumer, Predicate<IExt> predicate) {
        Optional.ofNullable(ExtContainer.getBeans(target, clazz))
                .orElse(Lists.newArrayList())
                .stream()
                .filter(IExt::enable)
                .filter(predicate)
                .forEach(consumer);
    }
}
