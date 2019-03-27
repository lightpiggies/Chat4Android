package com.lightpiggies.anti.chat_for_android.retrofit;

import java.io.IOException;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

  private static final String CHAT_BASE_URL = "http://114.115.163.70";

  private RetrofitManager() {}

  public static RetrofitManager getInstance() {
    return RetrofitManagerHolder.sInstance;
  }

  private final static class RetrofitManagerHolder {
    private static final RetrofitManager sInstance = new RetrofitManager();
  }

  public static final int API_STATUS_SC_SERVER_THROTTLING = 13;

  private static Retrofit buildRetrofit() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(CHAT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
          @Override
          public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8").build();
            return chain.proceed(request);
          }
        }).build())
        .build();
    return retrofit;
  }

  public static ApiService getApiRequest() {
    return buildRetrofit().create(ApiService.class);
  }

}
