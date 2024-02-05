package cn.sunjinxin.savior.doc.configuration;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

/**
 * EventAutoConfiguration
 *
 * @author issavior
 */
@Configuration
@EnableSwagger2
public class DocAutoConfiguration {

    @Bean
    public DocProperties docProperties() {
        return new DocProperties();
    }

    @Bean
    public Docket docket(DocProperties docProperties) {
        return Optional.ofNullable(docProperties).filter(DocProperties::getEnable).map(doc ->
                        new Docket(DocumentationType.SWAGGER_2)
                                .groupName(doc.getGroupName())
                                .apiInfo(api(doc))
                                .select()
                                .paths(PathSelectors.regex("/" + doc.getRegex() + ".*"))
                                .build())
                .orElse(null);
    }

    private ApiInfo api(DocProperties docProperties) {
        return new ApiInfoBuilder()
                .title(docProperties.getTitle())
                .description(docProperties.getDescription())
                .version(docProperties.getVersion())
                .license(docProperties.getLicense())
                .licenseUrl(docProperties.getLicenseUrl())
                .contact(new Contact(docProperties.getContact().getName(),
                        docProperties.getContact().getUrl(),
                        docProperties.getContact().getEmail()))
                .build();
    }

}
