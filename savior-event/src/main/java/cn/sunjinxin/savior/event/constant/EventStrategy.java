package cn.sunjinxin.savior.event.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * EventStrategy
 *
 * @author issavior
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EventStrategy {

    DEFAULT,
    SPRING,
    DISRUPTOR


}
