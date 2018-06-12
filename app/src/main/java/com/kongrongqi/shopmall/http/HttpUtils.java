package com.kongrongqi.shopmall.http;

import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.AccountBean;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 创建日期：2017/5/16 0016 on 16:18
 * 作者:penny Administrator
 */
public class HttpUtils {

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

    public static ApiService getApiService() {
        return ClientHolder.retrofit.create(ApiService.class);
    }

    /**
     * 设置头
     *
     * @return
     */
    public static OkHttpClient genericClient() {

        String token = getToken();
        Logger.d("httpToken:", token);
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

}
