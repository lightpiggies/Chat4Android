package com.lightpiggies.anti.chat_for_android.retrofit;

import com.lightpiggies.anti.chat_for_android.response.TestResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

  @POST("/auth/register")
  Observable<Response<TestResponse>> getRegistration(@Body RequestBody requestBody);

}
