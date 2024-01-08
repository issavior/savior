package cn.sunjinxin.savior.ateye.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunjinxin
 * @since 2024/1/8 16:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AteyeView {
    private String ateyeServiceName;
    private String ateyeInvokeName;
    private String ateyeInvokeDesc;
    private String path;
}
