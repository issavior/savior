package cn.sunjinxin.savior.toc.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.RegistryConfig;
import com.xxl.job.core.util.DateUtil;
import com.xxl.job.core.util.IpUtil;
import com.xxl.job.core.util.NetUtil;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.net.HttpCookie;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xxl动态添加任务
 *
 */
@Component
@Slf4j
//@RefreshScope
public class EventXxlJobUtil {

    @Value("${xxl.job.admin.login-username:admin}")
    private String loginUsername;

    @Value("${xxl.job.admin.login-pwd:123456}")
    private String loginPwd;

    @Value("${xxl.job.executor.ip:}")
    private String ip;


    @Value("${xxl.job.admin.addresses:''}")
    private String adminAddresses;

    @Value("${xxl.job.executor.port:-1}")
    private int port;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.accessToken:}")
    private String accessToken;


    private final static String TITLE = "身份中心资源分发";

    /**
     * 增加执行任务
     *
     * @param eventId      事件id
     * @param executorDate 执行日期
     */
    public void addExecutorTask(Long eventId, Long tenantId, Date executorDate) {
        Assert.notNull(eventId, "eventId must be not null");
        String desc = DateUtil.format(executorDate, DatePattern.CHINESE_DATE_TIME_PATTERN + "执行资源分发任务(" + eventId + ")");
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(16);
        //执行器主键ID,获取执行器主键
        paramMap.put("jobGroup", getJobGroupId());
        paramMap.put("jobDesc", desc);
        paramMap.put("executorRouteStrategy", "FIRST");
        String cron = getCron(executorDate);
        paramMap.put("cronGen_display", cron);
        //任务执行CRON表达式
        paramMap.put("jobCron", cron);
        //任务模式，可选值参考 com.xxl.job.core.glue.GlueTypeEnum
        paramMap.put("glueType", "BEAN");
        //执行器，任务Handler名称
        paramMap.put("executorHandler", "eventManagementJobHandler");
        //任务阻塞策略，可选值参考 com.xxl.job.core.enums.ExecutorBlockStrategyEnum
        paramMap.put("executorBlockStrategy", "SERIAL_EXECUTION");
        //任务超时时间，单位秒，大于零时生效
        paramMap.put("executorTimeout", 5);
        //失败重试次数
        paramMap.put("executorFailRetryCount", 2);
        //负责人
        paramMap.put("author", "admin");
        //GLUE备注
        paramMap.put("glueRemark", desc);
        //调度状态：0-停止，1-运行
        paramMap.put("triggerStatus", 1);
        HashMap<String, Object> executorParam = Maps.newHashMap();
        executorParam.put("eventId", eventId);
        executorParam.put("tenantId", tenantId);
        //执行器，任务参数
        paramMap.put("executorParam", JSON.toJSONString(executorParam));
        log.info("增加xxl执行任务,请求参数:{}", paramMap);
        HttpResponse response = HttpRequest.post(adminAddresses + "/jobinfo/add").form(paramMap).cookie(getCookie()).execute();
        if (response.isOk()) {
            JSONObject jsonObject = JSON.parseObject(response.body());
            log.info("增加xxl执行任务成功,返回信息:{}", jsonObject);
            return;
        }
        log.error("调用xxl增加执行任务失败:{}", JSON.parseObject(response.body()));
        throw new RuntimeException("");

    }

    /**
     * 获取执行id
     *
     * @return int
     */
    private int getJobGroupId() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appname", appName);
        paramMap.put("start", "0");
        paramMap.put("length", "100");
        log.info("获取xxl执行id,请求参数:{}", paramMap);
        //通过appname 查询appname对应的id
        HttpResponse response = HttpRequest.post(adminAddresses + "/jobgroup/pageList").form(paramMap).cookie(getCookie()).execute();
        if (response.isOk()) {
//            XxlSearchDto xxlSearchDto = JSON.parseObject(response.body(), XxlSearchDto.class);
//            log.info("获取xxl执行器成功,返回信息:{}", xxlSearchDto);
//            List<XxlSearchDto.DataEntity> list = xxlSearchDto.getData();
//            if (CollectionUtils.isEmpty(list)) {
//                //新增成功没有返回id
//                createJobGroup();
//                //在调一次列表查询 获取组id
//                return getJobGroupId();
//            }
//            XxlSearchDto.DataEntity dataEntity = list.get(0);
//            return dataEntity.getId();
        }
        log.error("调用xxl获取执行器失败:{}", JSON.parseObject(response.body()));
        throw new RuntimeException("");
    }

    /**
     * 创建执行器
     */
    private void createJobGroup() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appname", appName);
        paramMap.put("title", TITLE + profiles);
        //注册方式  0自动  1 为手动注册
        paramMap.put("addressType", "0");
        log.info("调用xxl增加执行器,请求参数：{}", paramMap);
        HttpResponse response = HttpRequest.post(adminAddresses + "/jobgroup/save").form(paramMap).cookie(getCookie()).execute();
        if (response.isOk()) {
            log.info("调用xxl增加执行器,成功返回信息:{}", JSON.parseObject(response.body()));
            //注册执行器
            registry();
            return;
        }
        throw new RuntimeException("");
    }

    /**
     * 注册执行器
     * 不能设置超时
     * AdminBiz adminBiz = new AdminBizClient(adminAddresses, accessToken)
     * ReturnT<String> returnT = adminBiz.registry(registryParam);
     */
    private void registry() {
        //copy xxl-job 源码
        port = port > 0 ? port : NetUtil.findAvailablePort(9999);
        ip = (ip != null && ip.trim().length() > 0) ? ip : IpUtil.getIp();
        String ipPort = IpUtil.getIpPort(ip, port);
        String address = "http://{ip_port}/".replace("{ip_port}", ipPort);
        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appName, address);
        ReturnT<String> returnT = XxlJobRemotingUtil.postBody(adminAddresses + "api/registry", accessToken, 6, registryParam, String.class);
        log.info("注册执行器返回结果：{}", returnT);

    }


    /***
     * 生成 日期对应的  cron表达式
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return String
     */
    private String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return DateUtil.format(date, dateFormat);
    }


    /**
     * 获取cookie
     *
     * @return String
     */
    private String getCookie() {
        String path = adminAddresses + "/login";
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("userName", loginUsername);
        hashMap.put("password", loginPwd);
        log.info("获取xxl cookie,请求参数：{}", hashMap);
        HttpResponse response = HttpRequest.post(path).form(hashMap).execute();
        boolean ok = response.isOk();
        if (ok) {
            List<HttpCookie> cookies = response.getCookies();
            log.info("获取xxl cookie成功,返回信息:{}", cookies);
            StringBuilder sb = new StringBuilder();
            for (HttpCookie cookie : cookies) {
                sb.append(cookie.toString());
            }
            return sb.toString();
        }
        log.error("调用xxl获取cookie失败:{}", JSON.parseObject(response.body()));
        throw new RuntimeException("");

    }
}


