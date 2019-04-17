package com.lightpiggies.anti.chat_for_android.retrofit;

import java.util.List;

import com.google.common.collect.Lists;

public enum ApiRequestKey {

  REGISTER(2, new String[] {"username", "password"});

  private final int mKeyCount;
  private final List<String> mKeys;

  ApiRequestKey(int count, String[] keys) {
    mKeyCount = count;
    mKeys = Lists.newArrayList(keys);
  }

  public int getKeyCount() {
    return mKeyCount;
  }

  public List<String> getKeys() {
    return mKeys;
  }
}
