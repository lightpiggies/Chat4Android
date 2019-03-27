package com.lightpiggies.anti.chat_for_android.retrofit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class ResponseFunction<T> implements Function<Response<T>, T> {

  @Override
  public T apply(@NonNull Response<T> response) throws Exception {
    return response.body();
  }
}
