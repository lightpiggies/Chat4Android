package com.lightpiggies.anti.chat_for_android.retrofit;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyBuilder {

  private List<String> mKeys = Lists.newArrayList();
  private List<Object> mValues = Lists.newArrayList();

  private RequestBodyBuilder() {}

  public static RequestBodyBuilder newBuilder() {
    return new RequestBodyBuilder();
  }

  public RequestBodyBuilder setData(String key, Object data) {
    mKeys.add(key);
    mValues.add(data);
    return this;
  }

  public RequestBody build() {
    JsonObject requestData = new JsonObject();
    Gson gson = new Gson();
    for (int i = 0; i < mKeys.size(); i++) {
      requestData.add(mKeys.get(i), gson.toJsonTree(mValues.get(i)));
    }
    return RequestBody.create(MediaType.parse("application/json"), requestData.toString());
  }

}
