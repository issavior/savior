package cn.sunjinxin.savior.core.helper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;

import javax.annotation.Nonnull;

/**
 * spring helper
 * <p>BeanFactoryPostProcessor & PriorityOrdered can priority loading</p>
 *
 * @author issavior
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpringHelper implements BeanFactoryPostProcessor, ApplicationContextAware, PriorityOrdered {

    static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void postProcessBeanFactory(@Nonnull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // ignore
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        SpringHelper.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
