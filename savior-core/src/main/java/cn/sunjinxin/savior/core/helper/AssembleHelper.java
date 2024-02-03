package cn.sunjinxin.savior.core.helper;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * assemble
 *
 * @author issavior
 */
public class AssembleHelper {

    /**
     * semble
     *
     * @param <R> /
     * @return /
     */
    public static <R, T> R semble(T t, Function<T, R> function) {
        return Optional.ofNullable(t).map(function).orElse(null);
    }

    /**
     * semble
     *
     * @param <R> /
     * @return /
     */
    public static <R, T> List<R> semble(List<T> t, Function<T, R> function) {
        return Optional.ofNullable(t).orElse(Lists.newArrayList()).stream().map(function).collect(Collectors.toList());
    }
}
