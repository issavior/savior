package cn.sunjinxin.savior.core.common;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * lazy
 *
 * @author issavior
 */
public class Lazy<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;

    private T value;

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    @Override
    public T get() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }

    public <S> Lazy<S> map(Function<? super T, ? extends S> function) {
        return Lazy.of(() -> function.apply(get()));
    }

    public <S> Lazy<S> flatMap(Function<? super T, Lazy<? extends S>> function) {
        return Lazy.of(() -> function.apply(get()).get());
    }
}
