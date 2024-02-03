package cn.sunjinxin.savior.ext.container;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author issavior
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ExtRo {
    String bizCode;
    @Builder.Default()
    String scenario = "default";
    @Builder.Default()
    String industry = "default";
    @Builder.Default()
    String business = "default";

}
