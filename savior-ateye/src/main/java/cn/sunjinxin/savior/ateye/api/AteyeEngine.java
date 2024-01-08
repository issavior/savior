package cn.sunjinxin.savior.ateye.api;

import cn.sunjinxin.savior.ateye.anno.AteyeInvoker;
import cn.sunjinxin.savior.ateye.anno.AteyeService;
import cn.sunjinxin.savior.ateye.config.AteyeProperties;
import cn.sunjinxin.savior.ateye.db.AteyeManager;
import cn.sunjinxin.savior.ateye.db.AteyeView;
import cn.sunjinxin.savior.core.helper.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author sunjinxin
 * @since 2024/1/8 18:02
 */
@Slf4j
public class AteyeEngine implements SmartLifecycle {
    private volatile boolean running = false;

    /**
     * true：让Lifecycle类所在的上下文在调用`refresh`时,能够自己自动进行回调
     * false：表明组件打算通过显式的start()调用来启动，类似于普通的Lifecycle实现。
     */
    @Override
    public boolean isAutoStartup() {
        return true;
    }

    /**
     * 很多框架中，把真正逻辑写在stop()方法内。比如quartz和Redis的spring支持包
     */
    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public void start() {
        running = true;
        initDB();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * 阶段值。越小：start()方法越靠前，stop()方法越靠后
     */
    @Override
    public int getPhase() {
        return 0;
    }

    private void initDB() {
        List<String> allBeanName = SpringHelper.getAllBeanName();
        for (String beanName : allBeanName) {
            Object bean = SpringHelper.getBean(beanName);
            String className = bean.getClass().getName();
            AteyeService ateyeService = bean.getClass().getAnnotation(AteyeService.class);
            if (ateyeService == null || !ateyeService.enable()) {
                continue;
            }
            String ateyeServiceDesc = ateyeService.desc();

            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                AteyeInvoker ateyeInvoker = method.getAnnotation(AteyeInvoker.class);
                if (ateyeInvoker == null || !ateyeInvoker.enable()) {
                    continue;
                }
                String ateyeInvokerDesc = ateyeInvoker.desc();
                String methodName = method.getName();
                String prefix = SpringHelper.getBean(AteyeProperties.class).getPathPrefix();
                String path = prefix + "." + className + "." + methodName;
                log.info("my path = {}", path);
                AteyeMappingApi service = SpringHelper.getBean(AteyeMappingApi.class);
                service.registerMapping(path, RequestMethod.GET.name(), bean, method);
                AteyeManager.DB.query().add(AteyeView.builder()
                        .ateyeServiceName(ateyeServiceDesc)
                        .ateyeInvokeName(method.getDeclaringClass().getSimpleName())
                        .ateyeInvokeDesc(ateyeInvokerDesc)
                        .path(path)
                        .build());
            }
        }
    }
}
