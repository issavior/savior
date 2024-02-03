//package cn.sunjinxin.savior.toc.service;
//
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.util.XxlJobRemotingUtil;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class XxlJobUtil {
//    public static final int SUCCESS_CODE = 200;
//    private static String adminAddresses;
//    private static String appname;
//    private static Integer groupId;
//    private static String accessToken;
//
//    @Value("${xxl.job.admin.addresses}")
//    public void setAdminAddresses(String adminAddresses) {
//        XxlJobUtil.adminAddresses = adminAddresses;
//    }
//
//    @Value("${xxl.job.accessToken}")
//    public void setAccessToken(String accessToken) {
//        XxlJobUtil.accessToken = accessToken;
//    }
//
//    @Value("${xxl.job.executor.appname}")
//    public void setAppname(String appname) {
//        XxlJobUtil.appname = appname;
//    }
//
//    @PostConstruct
//    public void init() throws XllJobRemoteException {
//        initGroupId();
//    }
//
//    private static final String ADD_URL = "/ext/jobinfo/add";
//    private static final String UPDATE_URL = "/ext/jobinfo/update";
//    private static final String REMOVE_URL = "/ext/jobinfo/remove";
//    private static final String PAUSE_URL = "/ext/jobinfo/stop";
//    private static final String START_URL = "/ext/jobinfo/start";
//    private static final String ADD_AND_START_URL = "/ext/jobinfo/add-and-start";
//    private static final String GET_GROUP_ID = "/ext/jobgroup/get-group-id";
//
//
//    /**
//     * 添加任务
//     * @param jobInfo 任务信息
//     * @return {@link String} 任务id
//     * @author sunjinxin
//     * @date 2024/01/14 17:37
//     **/
//    public static String add(XxlJobInfo jobInfo) throws XllJobRemoteException {
//        jobInfo.setJobGroup(groupId);
//        return doPost(adminAddresses + ADD_URL, jobInfo, String.class);
//    }
//
//    /**
//     * 初始化获取执行器id
//     * @author sunjinxin
//     * @date 2024/01/14 17:37
//     **/
//    public static void initGroupId() throws XllJobRemoteException {
//        // 查询对应groupId:
//        Map<String, Object> param = new HashMap<>(4);
//        param.put("appname", appname);
//        Integer groupId = doPost(adminAddresses + GET_GROUP_ID, param, Integer.class);
//        if (groupId == null) {
//            throw new XllJobRemoteException(String.format("【xxl-job】接口调用未获取到分组id，appname：%s", appname));
//        }
//        XxlJobUtil.groupId = groupId;
//    }
//
//    /**
//     * 修改执行时间
//     * @param id 任务id
//     * @param cron cron表达式
//     * @return {@link String}
//     * @author sunjinxin
//     * @date 2024/01/14 17:38
//     **/
//    public static String update(int id, String cron) throws XllJobRemoteException {
//        Map<String, Object> param = new HashMap<>(4);
//        param.put("id", id);
//        param.put("jobCron", cron);
//        return doPost(adminAddresses + UPDATE_URL, param, String.class);
//    }
//
//    /**
//     * 删除任务
//     * @param id 任务id
//     * @return {@link String}
//     * @author sunjinxin
//     * @date 2024/01/14 17:39
//     **/
//    public static String remove(int id) throws XllJobRemoteException {
//        Map<String, Object> param = new HashMap<>(4);
//        param.put("id", id);
//        return doPost(adminAddresses + REMOVE_URL, param, String.class);
//    }
//
//    /**
//     * 暂停任务
//     * @param id 任务id
//     * @return {@link String}
//     * @author sunjinxin
//     * @date 2024/01/14 17:40
//     **/
//    public static String pause(int id) throws XllJobRemoteException {
//        Map<String, Object> param = new HashMap<>(4);
//        param.put("id", id);
//        return doPost(adminAddresses + PAUSE_URL, param, String.class);
//    }
//
//    /**
//     * 开始任务
//     * @param id 任务id
//     * @return {@link String}
//     * @author sunjinxin
//     * @date 2024/01/14 17:40
//     **/
//    public static String start(int id) throws XllJobRemoteException {
//        Map<String, Object> param = new HashMap<>();
//        param.put("id", id);
//        return doPost(adminAddresses + START_URL, param, String.class);
//    }
//
//    /**
//     * 添加并启动
//     * @param jobInfo 任务信息
//     * @return {@link String}
//     * @author sunjinxin
//     * @date 2024/01/14 20:10
//     **/
//    public static String addAndStart(XxlJobInfo jobInfo) throws XllJobRemoteException {
//        jobInfo.setJobGroup(groupId);
//        return doPost(adminAddresses + ADD_AND_START_URL, jobInfo, String.class);
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T> T doPost(String url, Object json, Class<T> clazz) throws XllJobRemoteException {
//        ReturnT<T> returnT = XxlJobRemotingUtil.postBody(url, accessToken, 3, json, clazz);
//        if (returnT.getCode() != SUCCESS_CODE) {
//            throw new XllJobRemoteException(String.format("【xxl-job】接口调用失败，错误码：%s，原因：%s",
//                    returnT.getCode(), returnT.getMsg()));
//        }
//        return returnT.getContent();
//    }
//
//}
