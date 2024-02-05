package cn.sunjinxin.savior.doc.configuration;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.doc.anno.Doc;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

/**
 * Parser
 *
 * @author issavior
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Deprecated
public class DocParser implements BeanPostProcessor, ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Doc doc = bean.getClass().getAnnotation(Doc.class);

        if (doc == null) {
            return bean;
        }

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Docket.class)
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .addConstructorArgValue(DocumentationType.SWAGGER_2)
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME)
                .getBeanDefinition();

        ConfigurableApplicationContext context = (ConfigurableApplicationContext) this.applicationContext;
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();

        factory.registerBeanDefinition(beanName + "SaviorDocket", beanDefinition);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (!(bean instanceof Docket)) {
            return bean;
        }

        String substring = beanName.substring(0, beanName.length() - "SaviorDocket".length());
        Object bean1 = SpringHelper.getBean(substring);
        String name = bean1.getClass().getName();
        RequestMapping annotation = bean1.getClass().getAnnotation(RequestMapping.class);
        String regex = Optional.ofNullable(annotation).map(r -> Arrays.stream(r.value()).findFirst().orElse(StringUtils.EMPTY)).orElse(StringUtils.EMPTY);

        ((Docket) bean).apiInfo(api())
                .groupName(name)
                .select()
                .paths(PathSelectors.regex("/" + regex + ".*"))
                .build();

        return bean;
    }

    private ApiInfo api() {
        DocProperties docProperties = SpringHelper.getBean(DocProperties.class);
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

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
