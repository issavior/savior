package cn.sunjinxin.savior.ext.container;

import cn.sunjinxin.savior.core.anno.External;

/**
 * @author issavior
 */
@External
public interface IExt{

    /**
     * enable
     *
     * @return /
     */
    default boolean enable() {
        return true;
    }

    /**
     * order
     *
     * @return /
     */
    default int order() {
        return 0;
    }

    /**
     * Set ID and filter according to rules
     *
     * @return /
     */
    default String pointId() {
        return null;
    }

}
