package cn.sunjinxin.savior.doc.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DocProperties
 *
 * @author issavior
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "savior.doc")
public class DocProperties {
    Boolean enable = true;

    String groupName = "savior";
    String regex = StringUtils.EMPTY;

    String title = "savior";
    String description = "savior doc framework is very nice.";
    String version = "1.0.0";
    String license = "savior official documents";
    String licenseUrl = "http://savior.sunjinxin.cn";
    ContactProperties contact = new ContactProperties("savior", "http://savior.sunjinxin.cn", "issavior@163.com");


}
