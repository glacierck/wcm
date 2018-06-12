package com.kongrongqi.shopmall.http;

/**
 * 创建日期：2017/5/16 0016 on 16:17
 * 描述:URLConstans 地址
 * 作者:penny
 */
public interface URLConstans {

      /*test*/
    String test = "user/getAccountNumberInfo";

    /**
     * 校验手机号是否存在
     */
    String MOBILE_EXISTED = "/v1/base/accounts/mobileExisted";

    /**
     * 短信验证
     */
    String SMS = "/v1/base/sms/sendReg";

    /**
     * 注册
     */
    String REGISTER = "/v1/base/accounts/register";

    /**
     * 忘记密码
     */
    String FORGET_PWD = "/v1/base/accounts/forgetPwd";

    /**
     * 登录
     */
    String LOGIN = "/v1/base/accounts/login";

    /**
     * 更新用户账号表状态
     */
    String UPDATE_USER_DEVICE_WECHT = "/user/updateUserDeviceWecht";


    /**
     * 获取单个设备信息
     */
    String GET_SINGLE_USER_DEVICE_INFO = "/user/getSingleUserDeviceInfo";


    /**
     * 重新执行服务
     */
    String RE_USERSERVICE = "/user/reUserservice";


    /**
     * 获取用户信息
     */
    String GET_USER_DETAILS = "/v1/base/accounts/";

    /**
     * 忘记密码获取验证码
     */
    String FORGET_PWD_CODE = "/v1/base/sms/sendFpwd";

    /**
     * 修改密码
     */
    String UPDATE_PWD = "/v1/base/accounts/forgetPwd";

    /**
     * 退出应用
     */
    String LOGOUT = "/v1/base/accounts/logout";

    /**
     * 用户设备号机信息初始化信息
     */
    String GET_USER_DEVICE_WECHATNO_INFO = "/user/getUserDeviceWechatNoInfo";

    /**
     * 用户设备各个号机对应的信息
     */
    String GET_ACCOUNT_EACH_NUMBER_INFO = "/user/getUserDeviceInfo";

    /**
     * 获取用户账户绑定列表
     */
    String GET_ACCOUNT_BINDING_LIST = "/user/getAccountBindingList";

    /**
     * 用户的微信账户详情
     */
    String GET_WECHAT_DETAIL = "/user/getWechatDetail";


    /**
     * 获取灌粉或者加群服务列表
     */
    String GET_ADD_FANORGROUP_LIST = "/user/getAddFanOrGroupList";

    /**
     * 添加用户设备的IMEI码
     */
    String ADD_USER_DEVICE = "/user/addUserDevice";

    /**
     * 替换用户设备的IMEI码
     */
    String UPDATE_DEVICE = "/user/updateUserDevice";

    /**
     * 替换用户设备的IMEI码 我
     */
    String UPDATE_USER_DEVICEBYOLDIMEI = "/me/updateUserDeviceByOldImei";

    /**
     * 用户设备账号添加微信账号
     */
    String ADD_ACCOUNT = "/user/addAccount";

    /**
     * 我要换号
     */
    String REPLACE_USER_ACCOUNT = "/user/replaceUserAccount.do";

    /**
     * 恢复原号
     */
    String RECOVER_USER_ACCOUNT = "/user/recoverUserAccount.do";

    /**
     * 重新审核
     */
    String RE_AUDIT_ACCOUNT = "/user/reAuditAccount";

    /**
     * 获取未使用服务列表  已进程 未使用为准
     */
    String GET_UNUSED_SERVICE = "/process/getUserServicesStat.do";


    /**
     * 获取未使用服务列表  设备 暂不用
     */
    String GETUNUSEDSERVICE = "/user/getUnusedService";


    /**
     * 得到上传需灌粉数据URL【购买精准邀友-购买】
     */
    String GET_UPLOAD_URL = "/uploadExcel/getUploadUrl.do";

    /**
     * 使用 （使用服务-使用）,让服务跑起来
     */
    String USE_SERVICE = "/user/useService";


    /**
     * 检测是否有相同的服务和相同类型的服务
     */
    String CHECK_SAME_SERVICE = "/user/checkSameService";


    /**
     * 查询招呼语模板列表
     */
    String QUERY_CALL_TEMPLATE = "/user/queryCallTemplate";



    /**
     * 用户的账户详情【账号列表（首页）-查看详情】
     */
    String GET_USER_WECHAT_DETAIL = "/user/getUserWechatDetail";


    /**
     * 输入参数: wechatNo 账号 dodayFinish 状态：0 今日未完成，1 今日已完成， 2 登出
     */
    String UPDATE_WECHAT_TODAY_FINISH = "/user/updateWechatTodayFinish";

    /**
     * 重启/暂停 服务
     */
    String RESTART_SERVICE = "/user/restartService";

    /**
     * 重启/暂停 当日服务
     */
    String RESTART_TODAY_SERVICE = "/user/restartTodayService";


    /**
     * 我要用号，提示明天使用时间
     */
    String GET_SERVER_START = "/user/getServerStart";

    /**
     * 设备帐号已满，请购买新服务
     */
    String GET_NEWDEVICE_SERVICEINFO = "/user/getNewDeviceServiceInfo";

    /**
     * 设备帐号已满，请购买新服务
     */
    String GET_ONE_NEW_DEVICE_SERVICE_INFO = "/user/getOneNewDeviceServiceInfo";

    /**
     * 获取号槽信息
     */
    String GET_NUMBER_GROOVE_INFO = "/user/getNumberGrooveInfo";


    /**
     * 获得服务列表（系统默认提供的服务列表）
     */
    String GET_SERVICE_LIST = "/service/getServiceList.do";

    /**
     * 更新用户信息
     */
    String UPDATE_USERINFO = "/me/updateMeUserInfo.do";

    /**
     * 交易记录
     */
    String GET_USER_ORDER_LIST = "/me/getUserOrderList.do";

    /**
     * 获取我的会话列表
     */
    String GET_USER_SESSION = "/me/getUserSession.do";

    /**
     * 我的账户信息
     */
    String GET_ME_USER_INFO = "/me/getMeUserInfo.do";

    /**
     * 获取服务商品列表
     */
//    String GET_SERVICE_COMMODITY_LIST = "/v1/base/serves/getServiceList";
    String GET_SERVICE_COMMODITY_LIST = "/serves/getServiceList";
    /**
     * status 服务状态 0 未使用
     */
    String TASK_SERVICE_LIST = "/process/getUserServicesStat.do";

    /**
     * 获取绑定微信账号列表
     */
    String BIND_WECHAT_ACCOUNT = "/process/getUserWechatList.do";

    /**
     * 未使用入群服务
     */
    String UNUSE_IN_GROUNP = "/process/getUnuseGroupInfoList.do";

    /**
     * 获取灌粉服务B列表
     */
    String FANS_B_LIST = "/process/getUserServiceRecordList.do";

    /**
     * 进行中和已完成列表   1 进行中 2 已完成
     */
    String SCHEDULE_LIST = "/process/getInProgressOrFinishServicesList.do";

    /**
     * 查询微信群
     */
//    String WECHAT_GROUNP = "/v1/base/groups/findGroupWorthIsmarketInfo";
    String WECHAT_GROUNP = "/groups/findGroupWorthIsmarketInfo";

    /**
     * pay 获取支付类型的URL
     */
    String PAY_TYPE_URL = "/pay/getPayGateway";

    /**
     * 获取订单详情
     */
    String ORDER_INFO = "/pay/getOrderNoInfo";

    /**
     * 获取签名
     */
    String ORDER_SIGN = "/pay/getPayInfoByOrderNo";

    /**
     * 查询开票列表   isBill 是否开票 1 是 0 否，
     */
    String QUERY_IVOICE_LIST = "/me/queryOrderBill.do";

    /**
     * 获取地址列表
     */
    String ADDRESS_LIST = "/user/getConsigneeAddress";

    /**
     * 删除地址
     */
    String DELETE_CONSIGNEE_ADDRESS = "/user/deleteConsigneeAddress";

    /**
     * 设置默认
     */
    String DETDEFAULT_CONSIGNEE_ADDRESS = "/user/detDefaultConsigneeAddress";


    /***
     * 编辑地址
     */
    String EDIT_ADDRESS = "/user/addConsigneeAddress";

    /**
     * banner and splash pic
     */
    String GET_PIC_URL = "/getPicUrl.do";

    /**
     * 开票
     */
    String ADD_ORDER_BILL = "/me/addOrderBill.do";

    /**
     * 文件上传
     */
    String IS_UPLOAD_EXCEL = "/uploadExcel/queryUserSrc.do";

    /**
     * 版本更新
     */
    String UPDATE_APP = "/user/getVersionUpgradeInfo";

    /**
     * 跟新为一读
     */
    String updateUserSession = "/me/updateUserSession.do";

    String help = "/help.html";

    String about = "/about.html";

    String agreement = "/agreement.html";

    String GET_SERVICE_DETAIL_HTML = "/service/getServiceDetailHtml";

    /**
     * 获得粉丝类型 加油服务C
     */
//    String FIND_ALL_FANS = "/v1/base/admin/fans/findAllFans";
    String FIND_ALL_FANS = "/admin/fans/findAllFans";

    /**
     * 折扣列表
     */
//    String SALE_LIST = "/v1/base/promotion/findFrontPromotionByType";
    String SALE_LIST = "/promotion/findFrontPromotionByType";

    /**
     * 发烧友论坛
     */
    String fans_url = "http://sns.yysp.me/forum.php?forumlist=1&mobile=2";

    /**
     * 发烧友论坛
     */
    String getDisShopConfig = "/getDisShopConfig.do";


    /**
     * 获取行业信息
     */
    String queryPageForUserDict = "/me/queryPageForUserDict.do";


    /**
     * 定向加友【购买 -生成服务】
     */
    String createServiceForOrienteering = "/service/createServiceForOrienteering.do";


    /**
     * 获取设备的请求（设备）_for_wechatLogin
     */
    String getDeviceRequest = "/getDeviceRequest.do";
}
