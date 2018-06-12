package com.kongrongqi.shopmall.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.kongrongqi.shopmall.utils.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;


public class JsonRequestBodyConverter<T>
        implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;

    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */

    public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {

        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public RequestBody convert(T value)
            throws IOException {
        //		//加密
        //		APIBodyData data = new APIBodyData();
        //		if (BuildConfig.DEBUG) {
        //			KLog.a("request中传递的json数据：\n" + value.toString());
        //		}
        //		data.setData(Authcode3.authcodeEncode(
        //				value.toString(),
        //				HttpConstant.EN_KEY,
        //				60
        //											 ));
        //		String postBody = gson.toJson(data); //对象转化成json
        //		if (BuildConfig.DEBUG) {
        //			KLog.a("转化后的数据：\n" + postBody);
        //		}
        //		return RequestBody.create(
        //				MEDIA_TYPE,
        //				postBody
        //								 );
        Logger.d("httpRequest:", "request中传递的json数据：\n" + gson.toJson(value));
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(
                buffer.outputStream(),
                UTF_8
        );
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(
                jsonWriter,
                value
        );
        jsonWriter.close();
        return RequestBody.create(
                MEDIA_TYPE,
                buffer.readByteString()
        );
    }

}