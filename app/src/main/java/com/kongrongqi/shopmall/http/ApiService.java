package com.kongrongqi.shopmall.http;

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
import com.kongrongqi.shopmall.modules.login.request.EditAddressRequest;
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

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
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

public interface ApiService {



    /**
     * 短信验证
     *
     * @param mobile 手机
     * @return
     */
    @POST  @FormUrlEncoded
    Observable<BaseResponse<Boolean>> test(@Url String url,@Field("mobile") String mobile);

    /**
     * 短信验证
     *
     * @param mobile 手机
     * @return
     */
//    @POST(API.XHS + URLConstans.MOBILE_EXISTED)
    @POST @FormUrlEncoded
    Observable<BaseResponse<Boolean>> mobileExisted(@Url String url,@Field("mobile") String mobile);


    /**
     * 短信验证
     *
     * @param mobile 手机
     * @return
     */
//    @POST(API.SMS + URLConstans.SMS)
    @POST @FormUrlEncoded
    Observable<BaseResponse<SMSModel>> sendMsg(@Url String url,@Field("mobile") String mobile);


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
    @POST @FormUrlEncoded
    Observable<BaseResponse<RegisterModel>> registerUser(@Url String url,@Field("mobile") String mobile,
                                                         @Field("smsCode") String smsCode,
                                                         @Field("realName") String realName,
                                                         @Field("password") String password,
                                                         @Field("bizsysCode") String bizsysCode);


    /**
     * 登录
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS + URLConstans.LOGIN)
    @POST @FormUrlEncoded
    Observable<BaseResponse<LoginModel>> login(@Url String url,@Field("mobile") String mobile,
                                               @Field("password") String password);


    /**
     * 用户设备号机信息初始化信息
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_DEVICE_WECHATNO_INFO)
    @POST
    Observable<BaseResponse<Device_Wechat>> getAccountNumberInfo(@Url String url,@Body RequestBody userid);


    /**
     * 获取用户账户绑定列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_ACCOUNT_BINDING_LIST)
    @POST
    Observable<BaseResponse<List<AccountBind>>> getAccountBindingList(@Url String url,@Body RequestBody id);

    /**
     * 交易记录
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_ORDER_LIST)
    @POST
    Observable<BaseResponse<List<BuyRecord>>> getUserOrderList(@Url String url,@Body RequestBody id);


    /**
     * 获取我的会话列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_SESSION)
    @POST
    Observable<BaseResponse<List<UserSession>>> getUserSession(@Url String url,@Body RequestBody id);


    /**
     * 获取我的会话列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_ME_USER_INFO)
    @POST @FormUrlEncoded
    Observable<BaseResponse<Me>> getMeUserInfo(@Url String url,@Field("userId") String userId);


    /**
     * 添加用户设备的IMEI码
     */
//    @POST(API.XHS_1 + URLConstans.ADD_USER_DEVICE)
    @POST
    Observable<BaseResponse<String>> addUserDevice(@Url String url,@Body RequestBody id);


    /**
     * 替换用户设备的IMEI码
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_DEVICE)
    @POST
    Observable<BaseResponse<String>> updateDevice(@Url String url,@Body RequestBody id);


    /**
     * 替换用户设备的IMEI码
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_USER_DEVICEBYOLDIMEI)
    @POST
    Observable<BaseResponse<String>> updateUserDeviceByOldImei(@Url String url,@Body RequestBody id);

    /**
     * 用户设备账号添加微信账号
     */
//    @POST(API.XHS_1 + URLConstans.ADD_ACCOUNT)
    @POST
    Observable<BaseResponse<String>> addAccount(@Url String url,@Body RequestBody id);

    /**
     * 我要换号
     */
//    @POST(API.XHS_1 + URLConstans.REPLACE_USER_ACCOUNT)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> replaceUserAccount(@Url String url,@Field("userDeviceWechatId")String userDeviceWechatId,
                                                @Field("userId")String userId,
                                                @Field("oldWechatNo")String oldWechatNo,
                                                @Field("wechatNo")String wechatNo,
                                                @Field("wechatPwd")String wechatPwd);


    /**
     * 我要换号
     */
//    @POST(API.XHS_1 + URLConstans.RECOVER_USER_ACCOUNT)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> recoverUserAccount(@Url String url,@Field("userDeviceWechatId")String userDeviceWechatId);


    /**
     * 重新审核
     */
//    @POST(API.XHS_1 + URLConstans.RE_AUDIT_ACCOUNT)
    @POST
    Observable<BaseResponse<String>> reAuditAccount(@Url String url,@Body RequestBody id);


    /**
     * 用户的微信账户详情
     */
//    @POST(API.XHS_1 + URLConstans.GET_WECHAT_DETAIL)
    @POST
    Observable<BaseResponse<List<AccountInfo>>> getWechatDetail(@Url String url,@Body RequestBody requestBody);


    /**
     * 获取未使用服务列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_UNUSED_SERVICE)
    @POST
    Observable<BaseResponse<List<UserService>>> getUnusedService(@Url String url,@Body RequestBody requestBody);


    /**
     * 使用 （使用服务-使用）,让服务跑起来
     */
//    @POST(API.XHS_1 + URLConstans.USE_SERVICE)
    @POST
    Observable<BaseResponse<String>> useService(@Url String url,@Body RequestBody requestBody);


    /**
     * 更新账号今日是否完成状态
     * 输入参数: wechatNo 账号 dodayFinish 状态：0 今日未完成，1 今日已完成， 2 登出
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_WECHAT_TODAY_FINISH)
    @POST @FormUrlEncoded
    Observable<BaseResponse<ServiceDatails>> updateWechatTodayFinish(@Url String url,@Field("wechatNo") String wechatNo,@Field("todayFinish") int todayFinish,@Field("token") String token);


    /**
     * 检测是否有相同的服务和相同类型的服务
     */
//    @POST(API.XHS_1 + URLConstans.CHECK_SAME_SERVICE)
    @POST @FormUrlEncoded
    Observable<BaseResponse<BindWechatAccountModel>> checkSameService(@Url String url,@Field("deviceWechatId") String deviceWechatId,@Field("type") int type);



    /**
     * 查询招呼语模板列表
     */
//    @POST(API.XHS_1 + URLConstans.QUERY_CALL_TEMPLATE)
    @POST @FormUrlEncoded
    Observable<BaseResponse<List<CallModel>>> queryCallTemplate(@Url String url,@Field("userId") String userId);



    /**
     * 重启/暂停  服务  isRestart 是否重启  1 重启   2 暂停
     */
//    @POST(API.XHS_1 + URLConstans.RESTART_SERVICE)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> restartService(@Url String url,@Field("userServiceRecordId") String userServiceRecordId,@Field("isRestart") int isRestart);


    /**
     * 重启/暂停 当日服务
     */
//    @POST(API.XHS_1 + URLConstans.RESTART_TODAY_SERVICE)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> restartTodayService(@Url String url,@Field("userServiceRecordId") String userServiceRecordId,@Field("isRestart") int isRestart);

    /**
     * 我要用号，提示明天使用时间
     */
//    @POST(API.XHS_1 + URLConstans.GET_SERVER_START)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> getServerStart(@Url String url,@Field("userId") String userId);


    /**
     * 用户的账户详情【账号列表（首页）-查看详情】
     */
//    @POST(API.XHS_1 + URLConstans.GET_USER_WECHAT_DETAIL)
    @POST @FormUrlEncoded
    Observable<BaseResponse<ServiceDatails>> getUserWechatDetail(@Url String url,@Field("deviceWechatId") String deviceWechatId);



    /**
     * 设备帐号已满，请购买新服务
     */
//    @POST(API.XHS_1 + URLConstans.GET_NEWDEVICE_SERVICEINFO)
    @POST
    Observable<BaseResponse<UserService>> getNewDeviceServiceInfo(@Url String url,@Body RequestBody requestBody);


    /**
     * 设备帐号已满，请购买新服务
     */
//    @POST(API.XHS_1 + URLConstans.GET_ONE_NEW_DEVICE_SERVICE_INFO)
    @POST
    Observable<BaseResponse<UserService>> getOneNewDeviceServiceInfo(@Url String url,@Body RequestBody requestBody);

    /**
     * 设备帐号已满，请购买新服务
     */
//    @POST(URLConstans.GET_SERVICE_LIST)
//    @POST(API.FS + URLConstans.GET_SERVICE_COMMODITY_LIST)
    @POST
    Observable<BaseResponse<List<UserService>>> getServiceList(@Url String url,@Body RequestBody requestBody);


    /**
     * 获取灌粉或者加群服务列表
     */
//    @POST(API.XHS_1 + URLConstans.GET_ADD_FANORGROUP_LIST)
    @POST
    Observable<BaseResponse<List<FanOrGroup>>> getAddFanOrGroupList(@Url String url,@Body RequestBody requestBody);


    /**
     * 用户设备号机信息初始化信息
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_ACCOUNT_EACH_NUMBER_INFO)
    @POST
    Observable<BaseResponse<List<DeviceWechat>>> getAccountEachNumberInfo(@Url String url,@Body RequestBody id);

    /**
     * 获取号槽信息
     *
     * @param mobile   手机
     * @param password 密码
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_NUMBER_GROOVE_INFO)
    @POST
    Observable<BaseResponse<GrooveInfo>> getNumberGrooveInfo(@Url String url,@Body RequestBody id);


    /**
     * 强制审核通过
     * @param isForce
     * @param wechatNo
     * @param state
     * @param failureReasons
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_USER_DEVICE_WECHT)
    @POST  @FormUrlEncoded
    Observable<BaseResponse<String>> updateUserDeviceWecht(@Url String url,@Field("isForce") int  isForce,@Field("wechatNo") String wechatNo,@Field("state") int state,@Field("failureReasons") String failureReasons,@Field("token") String token);


    /**
     * 获取单个设备信息
     * @param wechatNo
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_SINGLE_USER_DEVICE_INFO)
    @POST @FormUrlEncoded
    Observable<BaseResponse<Device>> getSingleUserDeviceInfo(@Url String url,@Field("userDeviceId") String userDeviceId);


    /**
     * 账号异常  再次启动服务
     * @param wechatNo
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.RE_USERSERVICE)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> reUserservice(@Url String url,@Field("wechatNo") String wechatNo,@Field("userId") String userId,@Field("token") String token);



    /**
     * 获取用户详细信息
     *
     * @param userId 用户id
     * @param token  token
     * @return
     */
//    @POST(API.XHS + URLConstans.GET_USER_DETAILS)
    @POST @FormUrlEncoded
    Observable<BaseResponse<UserModel>> userDetails(@Url String url,@Field("token") String token);

    /**
     * 忘记密码发送短信
     *
     * @param mobile
     * @return
     */
//    @POST(API.SMS + URLConstans.FORGET_PWD_CODE)
    @POST @FormUrlEncoded
    Observable<BaseResponse<SMSModel>> forgetPwdMsg(@Url String url,@Field("mobile") String mobile);


    /**
     * 更改密码
     *
     * @param mobile
     * @param smsCode
     * @param newPwd
     * @return
     */
//    @POST(API.XHS + URLConstans.UPDATE_PWD)
    @POST @FormUrlEncoded
    Observable<BaseResponse> updatePwd(@Url String url,@Field("mobile") String mobile,
                                       @Field("smsCode") String smsCode,
                                       @Field("newPwd") String newPwd);

    /**
     * 退出登录
     *
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS + URLConstans.LOGOUT)
    @POST  @FormUrlEncoded
    Observable<BaseResponse> logout(@Url String url,@Field("token") String token);

    /**
     * 更新用户
     *
     * @param userId
     * @param token
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_USERINFO)
    @POST
    Observable<BaseResponse<String>> updateUserInfo(@Url String url,@Body RequestUser user);


    /**
     * 获取服务商品列表
     *
     * @param user
     * @return
     */
//    @POST(API.FS + URLConstans.GET_SERVICE_COMMODITY_LIST)
    @POST
    Observable<BaseResponse<List<ServiceBean>>> serviceList(@Url String url);


    /**
     * 获取未服务列表
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.TASK_SERVICE_LIST)
    @POST
    Observable<BaseResponse<List<NotUseModel>>> notUseServiceList(@Url String url,@Body RequestEmpty requestEmpty);


//    /**
//     * 获取绑定微信账号列表
//     *
//     * @param requestEmpty 请求体
//     * @return
//     */
//    @POST(API.XHS_1 + URLConstans.BIND_WECHAT_ACCOUNT)
//    Observable<BaseResponse<List<BindWechatAccountModel>>> bindWechatAccount(@Body RequestEmpty requestEmpty);

    //TODO
    /**
     * 获取绑定微信账号列表
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.BIND_WECHAT_ACCOUNT)
    @POST @FormUrlEncoded
    Observable<BaseResponse<List<BindWechatAccountModel>>> bindWechatAccount(@Url String url,@Field("userId") String userId,@Field("serviceType") int serviceType,@Field("token") String token);

    /**
     * 未使用入群服务
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UNUSE_IN_GROUNP)
    @POST
    Observable<BaseResponse<List<UnUseGrounpServiceModel>>> unUseGrounpService(@Url String url,@Body RequestUser requestEmpty);

    /**
     * 获取灌粉B
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.FANS_B_LIST)
    @POST
    Observable<BaseResponse<List<FansBListModel>>> fansBList(@Url String url,@Body RequestBody requestBody);

    /**
     * 已完成和进行中的列表
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.SCHEDULE_LIST)
    @POST
    Observable<BaseResponse<List<WorkingModel>>> scheduleList(@Url String url,@Body RequestEmpty requestEmpty);


    /**
     * 获取微信群
     *
     * @param requestEmpty
     * @return
     */
//    @POST(API.FS_1 + URLConstans.WECHAT_GROUNP)
    @POST
    Observable<BaseResponse<List<WechatGrounpModel>>> wechatGrounp(@Url String url,@Body RequestGroup requestEmpty);

    /**
     * 获取payURL 类型
     *
     * @return
     */
//    @POST(API.PAY + URLConstans.PAY_TYPE_URL)
    @POST
    Observable<BaseResponse<List<PayTypeUrlModel>>> payTypeUrl(@Url String url);


    /**
     * 获取订单信息
     *
     * @param request
     * @return
     */
//    @POST(API.PAY + URLConstans.ORDER_INFO)
    @POST
    Observable<BaseResponse<OrderInfoMode>> orderInfo(@Url String url,@Body OrderNumRequest request);


    /**
     * 获取签名
     *
     * @param request
     * @return
     */
//    @POST(API.PAY + URLConstans.ORDER_SIGN)
    @POST
    Observable<BaseResponse<ALiPayResponse>> orderSign(@Url String url,@Body OrderInfoRequest request);

    /***
     * 查询开票列表   isBill 是否开票 1 是 0 否，
     *
     * @param request
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.QUERY_IVOICE_LIST)
    @POST
    Observable<BaseResponse<List<IvoiceModel>>> queryIvoiceList(@Url String url,@Body RequestBody requestBody);


    /***
     * 开票
     *
     * @param request
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.ADD_ORDER_BILL)
    @POST
    Observable<BaseResponse<String>> addOrderBill(@Url String url,@Body RequestBody requestBody);

    /**
     * 获取地址列表
     *
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.DELETE_CONSIGNEE_ADDRESS)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> deleteConsigneeAddress(@Url String url,@Field("userReceiveAddressId") String userReceiveAddressId);


    /**
     * 设置默认地址
     *
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.DETDEFAULT_CONSIGNEE_ADDRESS)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> detDefaultConsigneeAddress(@Url String url,@Field("userId") String userId ,@Field("userReceiveAddressId") String userReceiveAddressId);


    /**
     * 获取地址列表
     *
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.ADDRESS_LIST)
    @POST @FormUrlEncoded
    Observable<BaseResponse<List<AddressModel>>> addressList(@Url String url,@Field("userId") String userId);

    /***
     * 编辑地址
     *
     * @param userId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.EDIT_ADDRESS)
    @POST
    Observable<BaseResponse> editAddress(@Url String url,@Body AddressModel request);



    /**
     * 获取splash图片和banner地址
     *
     * @param queryType
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_PIC_URL)
    @POST @FormUrlEncoded
    Observable<BaseResponse<List<String>>> splash(@Url String url,@Field("queryType") int queryType);

    /**
     * 获得dis服务商城配置
     * @param url
     * @param queryType
     * @return
     * /getDisShopConfig.do
     */
    @POST @FormUrlEncoded
    Observable<BaseResponse<DisShopConfig>> getDisShopConfig(@Url String url,@Field("queryType") int queryType);


    /**
     * 获取splash图片和banner地址
     *
     * @param queryType
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.GET_PIC_URL)
    @POST  @FormUrlEncoded
    Observable<BaseResponse<List<Banner>>> banner(@Url String url,@Field("queryType") int queryType);


    /**
     * 是否上傳文件
     *
     * @param type
     * @param userId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.IS_UPLOAD_EXCEL)
    @POST @FormUrlEncoded
    Observable<BaseResponse<UpLoadExcelModel>> isUploadExcel(@Url String url,@Field("type") int type,
                                                             @Field("userId") String userId);


    /**
     * 更新版本
     *
     * @param versionNo
     * @param platformId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.UPDATE_APP)
    @POST  @FormUrlEncoded
    Observable<BaseResponse<UpdateAppModel>> updateApp(@Url String url,@Field("versionNo") String versionNo,
                                                       @Field("platformId") String platformId);


    /**
     * 更新消息为已读
     *
     * @param userId
     * @param userSessionId
     * @return
     */
//    @POST(API.XHS_1 + URLConstans.updateUserSession)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> updateUserSession(@Url String url,@Field("userId") String userId);

    /**
     * 得到上传需灌粉数据URL【购买精准邀友-购买】
     */
//    @POST(API.XHS_1 + URLConstans.GET_UPLOAD_URL)
    @POST @FormUrlEncoded
    Observable<BaseResponse<String>> getUploadUrl(@Url String url,@Field("userId") String userId);

    /**
     * 得到上传需灌粉数据URL【购买精准邀友-购买】
     */
//    @GET(API.XHS_1 + URLConstans.GET_SERVICE_DETAIL_HTML)
    @GET
    Observable<BaseResponse<String>> getServiceDetailHtml(@Url String url,@Query("type") int type);


    /***
     * 查询开票列表   isBill 是否开票 1 是 0 否，
     *
     * @param request
     * @return
     */
//    @POST(API.FS+URLConstans.FIND_ALL_FANS)
    @POST
    Observable<BaseResponse<List<FansModel>>> findAllFans(@Url String url);


    /**
     * 显示折扣列表
     */
//    @POST(API.FS+URLConstans.SALE_LIST)
    @POST
    Observable<BaseResponse<List<SaleModel>>> saleList(@Url String url,@Body SaleListRequest pRequest);

    /**
     * 选择行业
     */
//    @POST(API.FS+URLConstans.SALE_LIST)
    @POST @FormUrlEncoded
    Observable<BaseResponse<List<IndustryModel>>> queryPageForUserDict(@Url String url, @Field("dictName") String dictName);


    /**
     * 定向加友【购买 -生成服务】
     */
    @POST @FormUrlEncoded
    Observable<BaseResponse<ServiceBean>> createServiceForOrienteering(@Url String url, @Field("type") int type,@Field("userId") String userId,@Field("daysEachPart") int daysEachPart,@Field("parts") int parts);



    /**
     * 定向加友【购买 -生成服务】
     */
    @POST @FormUrlEncoded
    Observable<BaseResponse<WxLoginSMSModel>> getDeviceRequest(@Url String url, @Field("userId") String userId);

}
