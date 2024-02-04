package cn.sunjinxin.savior.doc.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
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
    String title;
    String description;
    String version;
    String license;
    String licenseUrl;
    ContactProperties contact;


}
