package com.lightpiggies.anti.chat_for_android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.lightpiggies.anti.chat_for_android.R;

public class SafeEditText extends EditText {
  private boolean mAllowAutoAdjustHintSize = true;
  private String mOriginalHintText = "";
  private boolean mNeedsResize = true;
  private AdjustingTextSizeFinder mAdjustingTextSizeFinder;

  public SafeEditText(Context context) {
    super(context);
    initAttr(context, null);
    initHintText();
  }

  public SafeEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    initAttr(context, attrs);
    initHintText();
  }

  public SafeEditText(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initAttr(context, attrs);
    initHintText();
  }

  private void initAttr(Context context, AttributeSet attrs) {
    final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SafeEditText);
    mAllowAutoAdjustHintSize = a.getBoolean(R.styleable.SafeEditText_autoHintSize, true);
    a.recycle();
  }

  private void initHintText() {
    if (getHint() != null) {
      mOriginalHintText = getHint().toString();
    }
  }

  public void setHintText(String text) {
    mOriginalHintText = text;
    resizeHintText(getWidth(), getHeight());
  }

  @Override
  public Editable getText() {
    Editable ret = super.getText();
    return ret == null ? new SpannableStringBuilder("") : ret;
  }

  @Override
  public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
    // 小米2 MIUI-JLB52.0 android 4.1.1 JRO03L 会出现NPE闪退
    // 三星N7100 在增加span text 之后，点击会出现NPE闪退
    try {
      return super.dispatchTouchEvent(event);
    } catch (Throwable e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (isAllowAutoAdjustHintSize() && !TextUtils.isEmpty(mOriginalHintText) && (changed || mNeedsResize)) {
      int widthLimit = (right - left) - getCompoundPaddingLeft() - getCompoundPaddingRight();
      int heightLimit = (bottom - top) - getCompoundPaddingBottom() - getCompoundPaddingTop();
      resizeHintText(widthLimit, heightLimit);
    }
    super.onLayout(changed, left, top, right, bottom);
  }

  public void setAllowAutoAdjustHintSize(boolean val) {
    mAllowAutoAdjustHintSize = val;
  }

  public boolean isAllowAutoAdjustHintSize() {
    return mAllowAutoAdjustHintSize;
  }

  public void resizeHintText(int width, int height) {
    if (mAdjustingTextSizeFinder == null) {
      mAdjustingTextSizeFinder = new AdjustingTextSizeFinder();
    }
    if (TextUtils.isEmpty(mOriginalHintText) || height <= 0 || width <= 0) {
      return;
    }
    float newTextSize = mAdjustingTextSizeFinder.findAdjustedTextSizeForWidth(getPaint(), width, mOriginalHintText);
    // 新建一个可以添加属性的文本对象
    SpannableString ss = new SpannableString(mOriginalHintText);
    AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) newTextSize, false);
    ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    mNeedsResize = false;
  }

}

