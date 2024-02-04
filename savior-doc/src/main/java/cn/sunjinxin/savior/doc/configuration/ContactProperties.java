package cn.sunjinxin.savior.doc.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * ContactProperties
 *
 * @author issavior
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactProperties {
    String name;
    String url;
    String email;
}
