package com.lightpiggies.anti.chat_for_android.widget;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * 通过此类自动寻找最佳适配字号
 */
public class AdjustingTextSizeFinder {

  // Temporary upper bounds on the starting text size
  private float mMaxTextSize = -1;

  // Lower bounds for text size
  private float mMinTextSize = 10;
  private float mSpacingMult = 1;
  private float mSpacingAdd = 0;

  public AdjustingTextSizeFinder maxTextSize(float maxTextSize) {
    mMaxTextSize = maxTextSize;
    return this;
  }

  public AdjustingTextSizeFinder minTextSize(float minTextSize) {
    mMinTextSize = minTextSize;
    return this;
  }

  public AdjustingTextSizeFinder spacingMult(float spacingMult) {
    mSpacingMult = spacingMult;
    return this;
  }

  public AdjustingTextSizeFinder spacingAdd(float spacingAdd) {
    mSpacingAdd = spacingAdd;
    return this;
  }

  public float getMinTextSize() {
    return mMinTextSize;
  }

  public float getMaxTextSize() {
    return mMaxTextSize;
  }

  /**
   * 获取的size为px值
   */
  public float findAdjustedTextSizeForWidth(TextPaint piTextPaint, int width, CharSequence text) {
    if (width <= 0) {
      return piTextPaint.getTextSize();
    }
    TextPaint textPaint = new TextPaint(piTextPaint);
    float targetTextSize = mMaxTextSize > 0 ? mMaxTextSize : textPaint.getTextSize();

    float textWidth = getTextWidth(text, textPaint, targetTextSize);
    while (textWidth > width && targetTextSize > mMinTextSize) {
      targetTextSize = Math.max(targetTextSize - 1, mMinTextSize);
      textWidth = getTextWidth(text, textPaint, targetTextSize);
    }
    return targetTextSize;
  }

  public float getTextWidth(CharSequence source, TextPaint paint, float textSize) {
    paint.setTextSize(textSize);
    return Layout.getDesiredWidth(source, paint);
  }

  public float findAdjustedTextSizeForHeight(TextPaint piTextPaint, int width, int height, CharSequence text) {
    if (width <= 0) {
      return piTextPaint.getTextSize();
    }
    TextPaint textPaint = new TextPaint(piTextPaint);

    float targetTextSize = mMaxTextSize > 0 ? mMaxTextSize : textPaint.getTextSize();

    int textHeight = getTextHeight(text, textPaint, width, targetTextSize);
    while (textHeight > height && targetTextSize > mMinTextSize) {
      targetTextSize = Math.max(targetTextSize - 1, mMinTextSize);
      textHeight = getTextHeight(text, textPaint, width, targetTextSize);
    }
    return targetTextSize;
  }

  private int getTextHeight(CharSequence source, TextPaint paint, int width, float textSize) {
    paint.setTextSize(textSize);
    // 高度计算有些情况下并不准确，待完善
    StaticLayout layout = new StaticLayout(source, paint, width, Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd,
        true);
    return layout.getHeight();
  }
}
