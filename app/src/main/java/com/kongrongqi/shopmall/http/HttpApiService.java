package com.kongrongqi.shopmall.http;

import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.bean.AccountInfo;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.Device_Wechat;
import com.kongrongqi.shopmall.modules.account.bean.FanOrGroup;
import com.kongrongqi.shopmall.modules.account.bean.GrooveInfo;
import com.kongrongqi.shopmall.modules.account.bean.Me;
import com.kongrongqi.shopmall.modules.account.bean.ServiceDatails;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.AccountBean;
import com.kongrongqi.shopmall.modules.login.request.OrderInfoRequest;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.login.request.RequestEmpty;
import com.kongrongqi.shopmall.modules.login.request.RequestGroup;
import com.kongrongqi.shopmall.modules.login.request.RequestUser;
import com.kongrongqi.shopmall.modules.login.request.SaleListRequest;
import com.kongrongqi.shopmall.modules.me.bean.Banner;
import com.kongrongqi.shopmall.modules.me.bean.BuyRecord;
import com.kongrongqi.shopmall.modules.me.bean.UserSession;
import com.kongrongqi.shopmall.modules.model.ALiPayResponse;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.model.BindWechatAccountModel;
import com.kongrongqi.shopmall.modules.model.CallModel;
import com.kongrongqi.shopmall.modules.model.FansBListModel;
import com.kongrongqi.shopmall.modules.model.FansModel;
import com.kongrongqi.shopmall.modules.model.IndustryModel;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.modules.model.LoginModel;
import com.kongrongqi.shopmall.modules.model.NotUseModel;
import com.kongrongqi.shopmall.modules.model.OrderInfoMode;
import com.kongrongqi.shopmall.modules.model.PayTypeUrlModel;
import com.kongrongqi.shopmall.modules.model.RegisterModel;
import com.kongrongqi.shopmall.modules.model.SMSModel;
import com.kongrongqi.shopmall.modules.model.SaleModel;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UnUseGrounpServiceModel;
import com.kongrongqi.shopmall.modules.model.UpLoadExcelModel;
import com.kongrongqi.shopmall.modules.model.UpdateAppModel;
import com.kongrongqi.shopmall.modules.model.UserModel;
import com.kongrongqi.shopmall.modules.model.WechatGrounpModel;
import com.kongrongqi.shopmall.modules.model.WorkingModel;
import com.kongrongqi.shopmall.modules.model.WxLoginSMSModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by penny on 2017/5/16 0016.
 */

public class HttpApiService {


    public static final HttpApiService httpApiService = new HttpApiService();

    private HttpApiService() {
    }

    public static HttpApiService instance() {
        return httpApiService;
    }

    private static class ClientHolder {
        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                //封装网络请求的运行线程
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //自定义gson转换器  将通用类型在解析封装处理异常情况,直接返回数据
                .addConverterFactory(JsonConverterFactory.create())
                .client(genericClient())
                .build();
    }

    /**
     * 设置头
     *
     * @return
     */
    public static OkHttpClient genericClient() {

//        HttpLoggingInterceptor.Level body = DeBug.debug? HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE;
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder();
                        builder.addHeader("token", getToken());
                        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
                        Request build = builder.build();
                        return chain.proceed(build);
                    }
                })
                //谷歌浏览器调试网络请求
//                .addNetworkInterceptor(new StethoInterceptor())
                //日志打印
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return httpClient;
    }

    public static String getToken() {
        String token = null;
        try {
            AccountBean accountBean = LoginInfoManager.getInstance().getmAccountBean();
            if (accountBean != null) {
                token = accountBean.getToken();
                return token;
            } else {
                token = SPUtils.getString(App.getInstance().getContext(), Constans.TOKEN, "");
                return token;
            }
        } finally {
            return SPUtils.getString(App.getInstance().getContext(), Constans.TOKEN, "");
        }
    }

    /**
     * 短信验证
     *
     * @param mobile 手机
     * @return
     */

    public Observable<BaseResponse<Boolean>> test(String mobile) {
        return ClientHolder.retrofit.create(ApiService.class).test(ApiBean.instance().XHS + URLConstans.MOBILE_EXISTED, mobile);
    }

    /**
     * 短信验证
     *
     * @param mobile 手机
     * @return
     */
//    @POST(API.XHS + URLConstans.MOBILE_EXISTED)
//    @FormUrlEncoded
    public Observable<BaseResponse<Boolean>> mobileExisted(@Field("mobile") String mobile) {
        return ClientHolder.retrofit.create(ApiService.class).mobileExisted(ApiBean.instance().XHS + URLConstans.MOBILE_EXISTED, mobile);
    }


    /**
     * 短信验证
     *
     * @param mobile 手机
     * @return
     */
//    @POST(API.SMS + URLConstans.SMS)
//    @FormUrlEncoded
    public Observable<BaseResponse<SMSModel>> sendMsg(@Field("mobile") String mobile) {
        return ClientHolder.retrofit.create(ApiService.class).sendMsg(ApiBean.instance().SMS + URLConstans.SMS, mobile);
    }


    /**
     * 注册
     *
     * @param mobile     手机
     * @param smsCode    短信验证码
     * @param realName   昵称
     * @param password   mim
     * @param bizsysCode code
     * @return
     */
//    @POST(API.XHS + URLConstans.REGISTER)
//    @FormUrlEncoded
    public Observable<BaseResponse<RegisterModel>> registerUser(@Field("mobile") String mobile,
                                                                @Field("smsCode") String smsCode,
                                                                @Field("realName") String realName,
                                                                @Field("password") String password,
                                                                @Field("bizsysCode") String bizsysCode) {
        return ClientHolder.retrofit.create(ApiService.class).registerUser(ApiBean.instance().XHS + URLConstans.REGISTER, mobile, smsCode, realName, password, bizsysCode);
    }


    /**
     * 登录
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS + URLConstans.LOGIN)
    public Observable<BaseResponse<LoginModel>> login(@Field("mobile") String mobile,
                                                      @Field("password") String password) {
        return ClientHolder.retrofit.create(ApiService.class).login(ApiBean.instance().XHS + URLConstans.LOGIN, mobile, password);
    }


    /**
     * 用户设备号机信息初始化信息
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_DEVICE_WECHATNO_INFO)
    public Observable<BaseResponse<Device_Wechat>> getAccountNumberInfo(@Body RequestBody userid) {
        return ClientHolder.retrofit.create(ApiService.class).getAccountNumberInfo(ApiBean.instance().XHS_1 + URLConstans.GET_USER_DEVICE_WECHATNO_INFO, userid);
    }


    /**
     * 获取用户账户绑定列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_ACCOUNT_BINDING_LIST)
    public Observable<BaseResponse<List<AccountBind>>> getAccountBindingList(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).getAccountBindingList(ApiBean.instance().XHS_1 + URLConstans.GET_ACCOUNT_BINDING_LIST, id);
    }

    /**
     * 交易记录
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_ORDER_LIST)
    public Observable<BaseResponse<List<BuyRecord>>> getUserOrderList(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).getUserOrderList(ApiBean.instance().XHS_1 + URLConstans.GET_USER_ORDER_LIST, id);
    }


    /**
     * 获取我的会话列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_SESSION)
    public Observable<BaseResponse<List<UserSession>>> getUserSession(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).getUserSession(ApiBean.instance().XHS_1 + URLConstans.GET_USER_SESSION, id);
    }


    /**
     * 获取我的会话列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_ME_USER_INFO)
    public Observable<BaseResponse<Me>> getMeUserInfo(@Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).getMeUserInfo(ApiBean.instance().XHS_1 + URLConstans.GET_ME_USER_INFO, userId);
    }


    /**
     * 添加用户设备的IMEI码
     */
//    @POST(API.XHS_1 + URLConstans.ADD_USER_DEVICE)
    public Observable<BaseResponse<String>> addUserDevice(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).addUserDevice(ApiBean.instance().XHS_1 + URLConstans.ADD_USER_DEVICE, id);
    }


    /**
     * 替换用户设备的IMEI码
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_DEVICE)
    public Observable<BaseResponse<String>> updateDevice(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).updateDevice(ApiBean.instance().XHS_1 + URLConstans.UPDATE_DEVICE, id);
    }


    /**
     * 替换用户设备的IMEI码
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_USER_DEVICEBYOLDIMEI)
    public Observable<BaseResponse<String>> updateUserDeviceByOldImei(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).updateUserDeviceByOldImei(ApiBean.instance().XHS_1 + URLConstans.UPDATE_USER_DEVICEBYOLDIMEI, id);
    }

    /**
     * 用户设备账号添加微信账号
     */
//    @POST(API.XHS_1 + URLConstans.ADD_ACCOUNT)
    public Observable<BaseResponse<String>> addAccount(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).addAccount(ApiBean.instance().XHS_1 + URLConstans.ADD_ACCOUNT, id);
    }

    /**
     * 我要换号
     */
//    @POST(API.XHS_1 + URLConstans.REPLACE_USER_ACCOUNT)
    public Observable<BaseResponse<String>> replaceUserAccount(@Field("userDeviceWechatId") String userDeviceWechatId,
                                                               @Field("userId") String userId,
                                                               @Field("oldWechatNo") String oldWechatNo,
                                                               @Field("wechatNo") String wechatNo,
                                                               @Field("wechatPwd") String wechatPwd) {
        return ClientHolder.retrofit.create(ApiService.class).replaceUserAccount(ApiBean.instance().XHS_1 + URLConstans.REPLACE_USER_ACCOUNT, userDeviceWechatId, userId, oldWechatNo, wechatNo, wechatPwd);
    }


    /**
     * 我要换号
     */
//    @POST(API.XHS_1 + URLConstans.RECOVER_USER_ACCOUNT)
    public Observable<BaseResponse<String>> recoverUserAccount(@Field("userDeviceWechatId") String userDeviceWechatId) {
        return ClientHolder.retrofit.create(ApiService.class).recoverUserAccount(ApiBean.instance().XHS_1 + URLConstans.RECOVER_USER_ACCOUNT, userDeviceWechatId);
    }


    /**
     * 重新审核
     */
//    @POST(API.XHS_1 + URLConstans.RE_AUDIT_ACCOUNT)
    public Observable<BaseResponse<String>> reAuditAccount(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).reAuditAccount(ApiBean.instance().XHS_1 + URLConstans.RE_AUDIT_ACCOUNT, id);
    }


    /**
     * 用户的微信账户详情
     */
//    @POST(API.XHS_1 + URLConstans.GET_WECHAT_DETAIL)
    public Observable<BaseResponse<List<AccountInfo>>> getWechatDetail(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).getWechatDetail(ApiBean.instance().XHS_1 + URLConstans.GET_WECHAT_DETAIL, requestBody);
    }


    /**
     * 获取未使用服务列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_UNUSED_SERVICE)
    public Observable<BaseResponse<List<UserService>>> getUnusedService(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).getUnusedService(ApiBean.instance().XHS_1 + URLConstans.GET_UNUSED_SERVICE, requestBody);
    }

    /**
     * 使用 （使用服务-使用）,让服务跑起来
     */
//    @POST(API.XHS_1 + URLConstans.USE_SERVICE)
    public Observable<BaseResponse<String>> useService(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).useService(ApiBean.instance().XHS_1 + URLConstans.USE_SERVICE, requestBody);
    }


    /**
     * 更新账号今日是否完成状态
     * 输入参数: wechatNo 账号 dodayFinish 状态：0 今日未完成，1 今日已完成， 2 登出
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_WECHAT_TODAY_FINISH)
    public Observable<BaseResponse<ServiceDatails>> updateWechatTodayFinish(@Field("wechatNo") String wechatNo, @Field("todayFinish") int todayFinish, @Field("token") String token) {
        return ClientHolder.retrofit.create(ApiService.class).updateWechatTodayFinish(ApiBean.instance().XHS_1 + URLConstans.UPDATE_WECHAT_TODAY_FINISH, wechatNo, todayFinish, token);
    }


    /**
     * 检测是否有相同的服务和相同类型的服务
     */
//    @POST(API.XHS_1 + URLConstans.CHECK_SAME_SERVICE)
    public Observable<BaseResponse<BindWechatAccountModel>> checkSameService(@Field("deviceWechatId") String deviceWechatId, @Field("type") int type) {
        return ClientHolder.retrofit.create(ApiService.class).checkSameService(ApiBean.instance().XHS_1 + URLConstans.CHECK_SAME_SERVICE, deviceWechatId, type);
    }


    /**
     * 查询招呼语模板列表
     */
//    @POST(API.XHS_1 + URLConstans.QUERY_CALL_TEMPLATE)
    public Observable<BaseResponse<List<CallModel>>> queryCallTemplate(@Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).queryCallTemplate(ApiBean.instance().XHS_1 + URLConstans.QUERY_CALL_TEMPLATE, userId);
    }


    /**
     * 重启/暂停  服务  isRestart 是否重启  1 重启   2 暂停
     */
//    @POST(API.XHS_1 + URLConstans.RESTART_SERVICE)
    public Observable<BaseResponse<String>> restartService(@Field("userServiceRecordId") String userServiceRecordId, @Field("isRestart") int isRestart) {
        return ClientHolder.retrofit.create(ApiService.class).restartService(ApiBean.instance().XHS_1 + URLConstans.RESTART_SERVICE, userServiceRecordId, isRestart);
    }


    /**
     * 重启/暂停 当日服务
     */
//    @POST(API.XHS_1 + URLConstans.RESTART_TODAY_SERVICE)
    public Observable<BaseResponse<String>> restartTodayService(@Field("userServiceRecordId") String userServiceRecordId, @Field("isRestart") int isRestart) {
        return ClientHolder.retrofit.create(ApiService.class).restartTodayService(ApiBean.instance().XHS_1 + URLConstans.RESTART_TODAY_SERVICE, userServiceRecordId, isRestart);
    }

    /**
     * 我要用号，提示明天使用时间
     */
//    @POST(API.XHS_1 + URLConstans.GET_SERVER_START)
    public Observable<BaseResponse<String>> getServerStart(@Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).getServerStart(ApiBean.instance().XHS_1 + URLConstans.GET_SERVER_START, userId);
    }


    /**
     * 用户的账户详情【账号列表（首页）-查看详情】
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_WECHAT_DETAIL)
//    @FormUrlEncoded
    public Observable<BaseResponse<ServiceDatails>> getUserWechatDetail(@Field("deviceWechatId") String deviceWechatId) {
        return ClientHolder.retrofit.create(ApiService.class).getUserWechatDetail(ApiBean.instance().XHS_1 + URLConstans.GET_USER_WECHAT_DETAIL, deviceWechatId);
    }


    /**
     * 设备帐号已满，请购买新服务
     */
//    @POST(API.XHS_1 + URLConstans.GET_NEWDEVICE_SERVICEINFO)
    public Observable<BaseResponse<UserService>> getNewDeviceServiceInfo(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).getNewDeviceServiceInfo(ApiBean.instance().XHS_1 + URLConstans.GET_NEWDEVICE_SERVICEINFO, requestBody);
    }


    /**
     * 设备帐号已满，请购买新服务
     */
//    @POST(API.XHS_1 + URLConstans.GET_ONE_NEW_DEVICE_SERVICE_INFO)
    public Observable<BaseResponse<UserService>> getOneNewDeviceServiceInfo(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).getOneNewDeviceServiceInfo(ApiBean.instance().XHS_1 + URLConstans.GET_ONE_NEW_DEVICE_SERVICE_INFO, requestBody);
    }

    /**
     * 设备帐号已满，请购买新服务
     */
//    @POST(API.FS + URLConstans.GET_SERVICE_COMMODITY_LIST)
    public Observable<BaseResponse<List<UserService>>> getServiceList(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).getServiceList(ApiBean.instance().FS + URLConstans.GET_SERVICE_COMMODITY_LIST, requestBody);
    }


    /**
     * 获取灌粉或者加群服务列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_ADD_FANORGROUP_LIST)
    public Observable<BaseResponse<List<FanOrGroup>>> getAddFanOrGroupList(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).getAddFanOrGroupList(ApiBean.instance().XHS_1 + URLConstans.GET_ADD_FANORGROUP_LIST, requestBody);
    }


    /**
     * 用户设备号机信息初始化信息
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_ACCOUNT_EACH_NUMBER_INFO)
    public Observable<BaseResponse<List<DeviceWechat>>> getAccountEachNumberInfo(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).getAccountEachNumberInfo(ApiBean.instance().XHS_1 + URLConstans.GET_ACCOUNT_EACH_NUMBER_INFO, id);
    }

    /**
     * 获取号槽信息
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_NUMBER_GROOVE_INFO)
    public Observable<BaseResponse<GrooveInfo>> getNumberGrooveInfo(@Body RequestBody id) {
        return ClientHolder.retrofit.create(ApiService.class).getNumberGrooveInfo(ApiBean.instance().XHS_1 + URLConstans.GET_NUMBER_GROOVE_INFO, id);
    }


    /**
     * 强制审核通过
     *
     * @param isForce
     * @param wechatNo
     * @param state
     * @param failureReasons
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_USER_DEVICE_WECHT)
    public Observable<BaseResponse<String>> updateUserDeviceWecht(@Field("isForce") int isForce, @Field("wechatNo") String wechatNo, @Field("state") int state, @Field("failureReasons") String failureReasons, @Field("token") String token) {
        return ClientHolder.retrofit.create(ApiService.class).updateUserDeviceWecht(ApiBean.instance().XHS_1 + URLConstans.GET_USER_SESSION,
                isForce,
                wechatNo,
                state,
                failureReasons,
                token
        );
    }


    /**
     * 获取单个设备信息
     *
     * @param wechatNo
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_SINGLE_USER_DEVICE_INFO)
    public Observable<BaseResponse<Device>> getSingleUserDeviceInfo(@Field("userDeviceId") String userDeviceId) {
        return ClientHolder.retrofit.create(ApiService.class).getSingleUserDeviceInfo(ApiBean.instance().XHS_1 + URLConstans.GET_SINGLE_USER_DEVICE_INFO, userDeviceId);
    }


    /**
     * 账号异常  再次启动服务
     *
     * @param wechatNo
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.RE_USERSERVICE)
    public Observable<BaseResponse<String>> reUserservice(@Field("wechatNo") String wechatNo, @Field("userId") String userId, @Field("token") String token) {
        return ClientHolder.retrofit.create(ApiService.class).reUserservice(ApiBean.instance().XHS_1 + URLConstans.RE_USERSERVICE, wechatNo, userId, token
        );
    }


    /**
     * 获取用户详细信息
     *
     * @param userId 用户id
     * @param token  token
     * @return
     */
//    @POST(API.XHS + URLConstans.GET_USER_DETAILS)
    public Observable<BaseResponse<UserModel>> userDetails(@Field("token") String token) {
        return ClientHolder.retrofit.create(ApiService.class).userDetails(ApiBean.instance().XHS + URLConstans.GET_USER_DETAILS, token);
    }

    /**
     * 忘记密码发送短信
     *
     * @param mobile
     * @return
     */
//    @POST(API.SMS + URLConstans.FORGET_PWD_CODE)
    public Observable<BaseResponse<SMSModel>> forgetPwdMsg(@Field("mobile") String mobile) {
        return ClientHolder.retrofit.create(ApiService.class).forgetPwdMsg(ApiBean.instance().SMS + URLConstans.FORGET_PWD_CODE, mobile);
    }


    /**
     * 更改密码
     *
     * @param mobile
     * @param smsCode
     * @param newPwd
     * @return
     */
//    @POST(API.XHS + URLConstans.UPDATE_PWD)
    public Observable<BaseResponse> updatePwd(@Field("mobile") String mobile,
                                              @Field("smsCode") String smsCode,
                                              @Field("newPwd") String newPwd) {
        return ClientHolder.retrofit.create(ApiService.class).updatePwd(ApiBean.instance().XHS + URLConstans.UPDATE_PWD, mobile, smsCode, newPwd);
    }

    /**
     * 退出登录
     *
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS + URLConstans.LOGOUT)
    public Observable<BaseResponse> logout(@Field("token") String token) {
        return ClientHolder.retrofit.create(ApiService.class).logout(ApiBean.instance().XHS + URLConstans.LOGOUT, token);
    }

    /**
     * 更新用户
     *
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_USERINFO)
    public Observable<BaseResponse<String>> updateUserInfo(@Body RequestUser user) {
        return ClientHolder.retrofit.create(ApiService.class).updateUserInfo(ApiBean.instance().XHS_1 + URLConstans.UPDATE_USERINFO, user);
    }


    /**
     * 获取服务商品列表
     *
     * @param user
     * @return
     */
//    @POST(API.FS + URLConstans.GET_SERVICE_COMMODITY_LIST)
    public Observable<BaseResponse<List<ServiceBean>>> serviceList() {
        return ClientHolder.retrofit.create(ApiService.class).serviceList(ApiBean.instance().FS + URLConstans.GET_SERVICE_COMMODITY_LIST);
    }


    /**
     * 获取未服务列表
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.TASK_SERVICE_LIST)
    public Observable<BaseResponse<List<NotUseModel>>> notUseServiceList(@Body RequestEmpty requestEmpty) {
        return ClientHolder.retrofit.create(ApiService.class).notUseServiceList(ApiBean.instance().XHS_1 + URLConstans.TASK_SERVICE_LIST, requestEmpty);
    }

    /**
     * 获取绑定微信账号列表
     *
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.BIND_WECHAT_ACCOUNT)
    public Observable<BaseResponse<List<BindWechatAccountModel>>> bindWechatAccount(@Field("userId") String userId, @Field("serviceType") int serviceType, @Field("token") String token) {
        return ClientHolder.retrofit.create(ApiService.class).bindWechatAccount(ApiBean.instance().XHS_1 + URLConstans.BIND_WECHAT_ACCOUNT, userId, serviceType, token);
    }

    /**
     * 未使用入群服务
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UNUSE_IN_GROUNP)
    public Observable<BaseResponse<List<UnUseGrounpServiceModel>>> unUseGrounpService(@Body RequestUser requestEmpty) {
        return ClientHolder.retrofit.create(ApiService.class).unUseGrounpService(ApiBean.instance().XHS_1 + URLConstans.UNUSE_IN_GROUNP, requestEmpty);
    }

    /**
     * 获取灌粉B
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.FANS_B_LIST)
    public Observable<BaseResponse<List<FansBListModel>>> fansBList(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).fansBList(ApiBean.instance().XHS_1 + URLConstans.FANS_B_LIST, requestBody);
    }

    /**
     * 已完成和进行中的列表
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.SCHEDULE_LIST)
    public Observable<BaseResponse<List<WorkingModel>>> scheduleList(@Body RequestEmpty requestEmpty) {
        return ClientHolder.retrofit.create(ApiService.class).scheduleList(ApiBean.instance().XHS_1 + URLConstans.SCHEDULE_LIST, requestEmpty);
    }


    /**
     * 获取微信群
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.FS_1 + URLConstans.WECHAT_GROUNP)
    public Observable<BaseResponse<List<WechatGrounpModel>>> wechatGrounp(@Body RequestGroup requestEmpty) {
        return ClientHolder.retrofit.create(ApiService.class).wechatGrounp(ApiBean.instance().FS_1 + URLConstans.WECHAT_GROUNP, requestEmpty);
    }

    /**
     * 获取payURL 类型
     *
     * @return
     */
//    @POST(API.PAY + URLConstans.PAY_TYPE_URL)
    public Observable<BaseResponse<List<PayTypeUrlModel>>> payTypeUrl() {
        return ClientHolder.retrofit.create(ApiService.class).payTypeUrl(ApiBean.instance().PAY + URLConstans.PAY_TYPE_URL);
    }


    /**
     * 获取订单信息
     *
     * @param request
     * @return
     */
//    @POST(API.PAY + URLConstans.ORDER_INFO)
    public Observable<BaseResponse<OrderInfoMode>> orderInfo(@Body OrderNumRequest request) {
        return ClientHolder.retrofit.create(ApiService.class).orderInfo(ApiBean.instance().PAY + URLConstans.ORDER_INFO, request);
    }


    /**
     * 获取签名
     *
     * @param request
     * @return
     */
//    @POST(API.PAY + URLConstans.ORDER_SIGN)
    public Observable<BaseResponse<ALiPayResponse>> orderSign(@Body OrderInfoRequest request) {
        return ClientHolder.retrofit.create(ApiService.class).orderSign(ApiBean.instance().PAY + URLConstans.ORDER_SIGN, request);
    }

    /***
     * 查询开票列表   isBill 是否开票 1 是 0 否，
     *
     * @param request
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.QUERY_IVOICE_LIST)
    public Observable<BaseResponse<List<IvoiceModel>>> queryIvoiceList(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).queryIvoiceList(ApiBean.instance().XHS_1 + URLConstans.QUERY_IVOICE_LIST, requestBody);
    }


    /***
     * 开票
     *
     * @param request
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.ADD_ORDER_BILL)
    public Observable<BaseResponse<String>> addOrderBill(@Body RequestBody requestBody) {
        return ClientHolder.retrofit.create(ApiService.class).addOrderBill(ApiBean.instance().XHS_1 + URLConstans.ADD_ORDER_BILL, requestBody);
    }

    /**
     * 获取地址列表
     *
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.DELETE_CONSIGNEE_ADDRESS)
    public Observable<BaseResponse<String>> deleteConsigneeAddress(@Field("userReceiveAddressId") String userReceiveAddressId) {
        return ClientHolder.retrofit.create(ApiService.class).deleteConsigneeAddress(ApiBean.instance().XHS_1 + URLConstans.DELETE_CONSIGNEE_ADDRESS, userReceiveAddressId);
    }


    /**
     * 设置默认地址
     *
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.DETDEFAULT_CONSIGNEE_ADDRESS)
    public Observable<BaseResponse<String>> detDefaultConsigneeAddress(@Field("userId") String userId, @Field("userReceiveAddressId") String userReceiveAddressId) {
        return ClientHolder.retrofit.create(ApiService.class).detDefaultConsigneeAddress(ApiBean.instance().XHS_1 + URLConstans.DETDEFAULT_CONSIGNEE_ADDRESS, userId, userReceiveAddressId);
    }

    /**
     * 获取地址列表
     *
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.ADDRESS_LIST)
    public Observable<BaseResponse<List<AddressModel>>> addressList(@Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).addressList(ApiBean.instance().XHS_1 + URLConstans.ADDRESS_LIST, userId);
    }

    /***
     * 编辑地址
     *
     * @param userId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.EDIT_ADDRESS)
    public Observable<BaseResponse> editAddress(@Body AddressModel request) {
        return ClientHolder.retrofit.create(ApiService.class).editAddress(ApiBean.instance().XHS_1 + URLConstans.EDIT_ADDRESS, request);
    }


    /**
     * 获取splash图片和banner地址
     *
     * @param queryType
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_PIC_URL)
    public Observable<BaseResponse<List<String>>> splash(@Field("queryType") int queryType) {
        return ClientHolder.retrofit.create(ApiService.class).splash(API.yysp + URLConstans.GET_PIC_URL, queryType);
    }


    /**
     * 获取splash图片和banner地址
     *
     * @param queryType
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_PIC_URL)
    public Observable<BaseResponse<List<Banner>>> banner(@Field("queryType") int queryType) {
        return ClientHolder.retrofit.create(ApiService.class).banner(ApiBean.instance().XHS_1 + URLConstans.GET_PIC_URL, queryType);
    }


    /**
     * 是否上傳文件
     *
     * @param type
     * @param userId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.IS_UPLOAD_EXCEL)
    public Observable<BaseResponse<UpLoadExcelModel>> isUploadExcel(@Field("type") int type,
                                                                    @Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).isUploadExcel(ApiBean.instance().XHS_1 + URLConstans.IS_UPLOAD_EXCEL, type, userId);
    }


    /**
     * 更新版本
     *
     * @param versionNo
     * @param platformId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_APP)
    public Observable<BaseResponse<UpdateAppModel>> updateApp(@Field("versionNo") String versionNo,
                                                              @Field("platformId") String platformId) {
        return ClientHolder.retrofit.create(ApiService.class).updateApp(ApiBean.instance().XHS_1 + URLConstans.UPDATE_APP, versionNo, platformId);
    }


    /**
     * 更新消息为已读
     *
     * @param userId
     * @param userSessionId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.updateUserSession)
    public Observable<BaseResponse<String>> updateUserSession(@Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).updateUserSession(ApiBean.instance().XHS_1 + URLConstans.updateUserSession, userId);
    }

    /**
     * 得到上传需灌粉数据URL【购买精准邀友-购买】
     */
//    @POST(API.XHS_1 + URLConstans.GET_UPLOAD_URL)
    public Observable<BaseResponse<String>> getUploadUrl(@Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).getUploadUrl(ApiBean.instance().XHS_1 + URLConstans.GET_UPLOAD_URL, userId);
    }

    /**
     * 得到上传需灌粉数据URL【购买精准邀友-购买】
     */
//    @GET(API.XHS_1 + URLConstans.GET_SERVICE_DETAIL_HTML)
    public Observable<BaseResponse<String>> getServiceDetailHtml(@Query("type") int type) {
        return ClientHolder.retrofit.create(ApiService.class).getServiceDetailHtml(ApiBean.instance().XHS_1 + URLConstans.GET_SERVICE_DETAIL_HTML, type);
    }


    /***
     * 查询开票列表   isBill 是否开票 1 是 0 否，
     *
     * @param request
     * @return
     */
//    @POST(API.FS+URLConstans.FIND_ALL_FANS)
    public Observable<BaseResponse<List<FansModel>>> findAllFans() {
        return ClientHolder.retrofit.create(ApiService.class).findAllFans(ApiBean.instance().FS + URLConstans.FIND_ALL_FANS);
    }


    /**
     * 显示折扣列表
     */
//    @POST(API.FS+URLConstans.SALE_LIST)
    public Observable<BaseResponse<List<SaleModel>>> saleList(@Body SaleListRequest pRequest) {
        return ClientHolder.retrofit.create(ApiService.class).saleList(ApiBean.instance().FS + URLConstans.SALE_LIST, pRequest);
    }

    /**
     * 获得dis服务商城配置
     * @param queryType
     * @return /getDisShopConfig.do
     */
    public Observable<BaseResponse<DisShopConfig>> getDisShopConfig(@Field("queryType") int queryType) {
        return ClientHolder.retrofit.create(ApiService.class).getDisShopConfig(API.yysp + URLConstans.getDisShopConfig, queryType);
    }



    /**
     * 获得dis服务商城配置
     * @param queryType
     * @return /getDisShopConfig.do
     */
    public Observable<BaseResponse<List<IndustryModel>>> queryPageForUserDict(@Field("dictName") String dictName) {
        return ClientHolder.retrofit.create(ApiService.class).queryPageForUserDict(ApiBean.instance().XHS_1 + URLConstans.queryPageForUserDict, dictName);
    }


    /**
     * 定向加友【购买 -生成服务】
     */
    public Observable<BaseResponse<ServiceBean>> createServiceForOrienteering(@Field("type") int type,@Field("userId") String userId,@Field("daysEachPart") int daysEachPart,@Field("parts") int parts) {
        return ClientHolder.retrofit.create(ApiService.class).createServiceForOrienteering(ApiBean.instance().XHS_1 + URLConstans.createServiceForOrienteering, type,userId,daysEachPart,parts);
    }

    /**
     * 获取设备的请求（设备）_for_wechatLogin
     */
    public Observable<BaseResponse<WxLoginSMSModel>> getDeviceRequest(@Field("userId") String userId) {
        return ClientHolder.retrofit.create(ApiService.class).getDeviceRequest(ApiBean.instance().XHS_1 + URLConstans.getDeviceRequest, userId);
    }

}
