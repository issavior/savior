package cn.sunjinxin.savior.ext.container;

/**
 * ext
 *
 * @author issavior
 */
public class ExtAbility {

    /**
     * builder
     *
     * @param clazz ability
     * @param <A>   /
     * @return ExtExecutor
     */
    public static <A extends IExt> ExtExecutor<A> as(Class<A> clazz) {
        return new ExtExecutor<>(clazz);
    }
}
