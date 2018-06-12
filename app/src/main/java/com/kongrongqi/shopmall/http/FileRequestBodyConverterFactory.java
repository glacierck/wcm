package com.kongrongqi.shopmall.http;


import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class FileRequestBodyConverterFactory
		extends Converter.Factory {

	@Override
	public Converter<File, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

		return new FileRequestBodyConverter();
	}
}
