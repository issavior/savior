//package cn.sunjinxin.savior.toc.service;
//
//import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//@Configuration
//@ConditionalOnProperty(prefix = "xxl.job", name = "enabled", havingValue = "true")
//public class XxlJobConfig {
//
//    @Value("${xxl.job.admin.addresses}")
//    private String adminAddresses;
//
//    @Value("${xxl.job.executor.appname}")
//    private String appName;
//
//    @Value("${xxl.job.executor.ip:}")
//    private String ip;
//
//    @Value("${xxl.job.executor.port:-1}")
//    private int port;
//
//    @Value("${xxl.job.accessToken:}")
//    private String accessToken;
//
//    @Value("${xxl.job.executor.logpath}")
//    private String logPath;
//
//    @Value("${xxl.job.executor.logretentiondays}")
//    private int logRetentionDays;
//
//    /**
//     * 注册xxlJob执行器
//     *
//     * @return
//     */
//    @Bean
//    public XxlJobSpringExecutor xxlJobExecutor() {
//        log.info(">>>>>>>>>>> xxl-job config init.");
//        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
//        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
//        //执行器 appname
//        xxlJobSpringExecutor.setAppname(appName);
//        //执行器 注册ip 为空 或null 会自动获取
//        xxlJobSpringExecutor.setIp(ip);
//        //执行器端口 为空 或null  会自动设置未使用的端口
//        xxlJobSpringExecutor.setPort(port);
//        xxlJobSpringExecutor.setAccessToken(accessToken);
//        xxlJobSpringExecutor.setLogPath(logPath);
//        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
//        return xxlJobSpringExecutor;
//    }
//
//}
//
