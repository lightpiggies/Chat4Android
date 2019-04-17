package com.lightpiggies.anti.chat_for_android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.IdRes;

public class ToastUtil {

  private ToastUtil() {}

  public static void info(Context context, String text) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
  }

  @SuppressLint("ResourceType")
  public static void info(Context context, @IdRes int res) {
    Toast.makeText(context, res, Toast.LENGTH_LONG).show();
  }
}
