package com.lswd.youpin.weixin.core;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lswd.youpin.common.http.Http;
import com.lswd.youpin.common.json.Jsons;
import com.lswd.youpin.common.util.Fields;
import com.lswd.youpin.weixin.exception.WechatException;
import com.lswd.youpin.weixin.loader.AccessTokenLoader;
import com.lswd.youpin.weixin.loader.DefaultAccessTokenLoader;
import com.lswd.youpin.weixin.loader.DefaultTicketLoader;
import com.lswd.youpin.weixin.loader.TicketLoader;
import com.lswd.youpin.weixin.model.base.AccessToken;
import com.lswd.youpin.weixin.model.js.Ticket;
import com.lswd.youpin.weixin.model.js.TicketType;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 微信核心组件库
 *liruilong
 * Date: 5/11/15
 * @since 1.0.0
 */
public final class Wechat {

    /**
     * 微信APP ID
     */
    private String appId;

    /**
     * 微信APP 密钥
     */
    private String appSecret;

    /**
     * 微信APP (令牌)Token
     */
    String appToken;

    /**
     * 消息加密Key
     */
    String msgKey;

    /**
     * AccessToken加载器
     */
    AccessTokenLoader tokenLoader = DEFAULT_ACCESS_TOKEN_LOADER;

    /**
     * Ticket加载器
     */
    TicketLoader ticketLoader = DEFAULT_TICKET_LOADER;

    /**
     * 异步执行器
     */
    ExecutorService executor = DEFAULT_EXECUTOR;

    /**
     * 微信错误码变量
     */
    private final String ERROR_CODE = "errcode";

    private static final String BASES = "Bases";

    private static final String USERS = "Users";

    private static final String MENUS = "Menus";

    private static final String CUSTOMER_SERVICES = "CustomerServices";

    private static final String MESSAGES = "Messages";

    private static final String QRCODES = "QrCodes";

    private static final String MATERIALS = "Materials";

    private static final String DATAS = "Datas";

    private static final String JSSDKS = "JsSdks";

    private static final AccessTokenLoader DEFAULT_ACCESS_TOKEN_LOADER = new DefaultAccessTokenLoader();

    private static final DefaultTicketLoader DEFAULT_TICKET_LOADER = new DefaultTicketLoader();

    private static final JavaType MAP_STRING_OBJ_TYPE = Jsons.DEFAULT.createCollectionType(Map.class, String.class, Object.class);

    private static final ExecutorService DEFAULT_EXECUTOR = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() + 1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("wechat");
                    return t;
                }
            });

    private LoadingCache<String, Component> components =
            CacheBuilder.newBuilder().build(new CacheLoader<String, Component>() {
                @Override
                public Component load(String classFullName) throws Exception {
                    Class clazz = Class.forName(classFullName);
                    Object comp = clazz.newInstance();
                    injectWechat(clazz, comp);
                    return (Component)comp;
                }
            });

    Wechat(String appId, String appSecret){
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAppToken() {
        return appToken;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public Bases base(){
        return (Bases)components.getUnchecked(BASES);
    }

    public CustomerServices cs(){
        return (CustomerServices)components.getUnchecked(CUSTOMER_SERVICES);
    }

    public Menus menu(){
        return (Menus)components.getUnchecked(MENUS);
    }

    public Users user(){
        return (Users)components.getUnchecked(USERS);
    }

    public Messages msg(){
        return (Messages)components.getUnchecked(MESSAGES);
    }

    public QrCodes qr(){
        return (QrCodes)components.getUnchecked(QRCODES);
    }

    public Materials material(){
        return (Materials)components.getUnchecked(MATERIALS);
    }

    public Datas data(){
        return (Datas)components.getUnchecked(DATAS);
    }

    public JsSdks js(){
        return (JsSdks)components.getUnchecked(JSSDKS);
    }

    private void injectWechat(Class clazz, Object comp) throws NoSuchFieldException {
        Field wechat = clazz.getSuperclass().getDeclaredField("wechat");
        Fields.put(comp, wechat, this);
    }

    /**
     * 注册组件
     * @param component 组件对象
     * @param <T> 范型
     */
    public <T extends Component> void register(T component){
        try {
            injectWechat(component.getClass(), component);
        } catch (NoSuchFieldException e) {
            throw new WechatException(e);
        }
    }

    /**
     * 关闭异步执行器(不再支持异步执行)
     */
    public void destroy(){
        if (executor.isShutdown()){
            executor.shutdown();
        }
    }

    String loadAccessToken(){
        String accessToken = tokenLoader.get();
        if (Strings.isNullOrEmpty(accessToken)){
            AccessToken token = base().accessToken();
            tokenLoader.refresh(token);
            accessToken = token.getAccessToken();
        }
        return accessToken;
    }

    String loadTicket(TicketType type){
        String ticket = ticketLoader.get(type);
        if (Strings.isNullOrEmpty(ticket)){
            Ticket t = js().getTicket(type);
            ticketLoader.refresh(t);
            ticket = t.getTicket();
        }
        return ticket;
    }

    Map<String, Object> doPost(String url, Map<String, Object> params) {
        String body = null;
        if (params != null && !params.isEmpty()){
            body = Jsons.DEFAULT.toJson(params);
        }
        return doPost(url, body);
    }

    Map<String, Object> doPost(String url, String body) {
        Http http = Http.post(url);
        if (!Strings.isNullOrEmpty(body)){
            http.body(body);
        }
        Map<String, Object> resp = http.request(MAP_STRING_OBJ_TYPE);
        Integer errcode = (Integer)resp.get(ERROR_CODE);
        if (errcode != null && errcode != 0){
            throw new WechatException(resp);
        }
        return resp;
    }

    Map<String, Object> doGet(String url) {
        return doGet(url, null);
    }

    Map<String, Object> doGet(String url, Map<String, Object> params) {
        Http http = Http.get(url);
        if (params != null && params.size() > 0){
            http.body(Jsons.DEFAULT.toJson(params));
        }
        Map<String, Object> resp = http.request(MAP_STRING_OBJ_TYPE);
        Integer errcode = (Integer)resp.get(ERROR_CODE);
        if (errcode != null && errcode != 0){
            throw new WechatException(resp);
        }
        return resp;
    }

    Map<String, Object> doUpload(String url, String fieldName, String fileName, InputStream input, Map<String, String> params){
        String json = Http.upload(url, fieldName, fileName, input, params);
        Map<String, Object> resp = Jsons.DEFAULT.fromJson(json, MAP_STRING_OBJ_TYPE);
        Integer errcode = (Integer)resp.get(ERROR_CODE);
        if (errcode != null && errcode != 0){
            throw new WechatException(resp);
        }
        return resp;
    }

    <T> void doAsync(final AsyncFunction<T> f){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    T res = f.execute();
                    f.cb.onSuccess(res);
                } catch (Exception e){
                    f.cb.onFailure(e);
                }
            }
        });
    }
}
