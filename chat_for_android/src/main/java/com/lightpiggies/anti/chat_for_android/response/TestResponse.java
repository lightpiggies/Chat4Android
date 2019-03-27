package com.lightpiggies.anti.chat_for_android.response;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TestResponse implements Serializable {

  private static final long serialVersionUID = 1583246051864874248L;

  @SerializedName("data")
  public TextData mData = new TextData();

  public String getName() {
    return mData.mNickName;
  }

  public String getBirthday() {
    return mData.mBirthday;
  }

  public static class TextData implements Serializable {

    private static final long serialVersionUID = 8858671930910815694L;

    @SerializedName("nickname")
    public String mNickName;

    @SerializedName("birthday")
    public String mBirthday;
  }
}
