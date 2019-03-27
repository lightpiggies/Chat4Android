package com.lightpiggies.anti.chat_for_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lightpiggies.anti.chat_for_android.response.TestResponse;
import com.lightpiggies.anti.chat_for_android.retrofit.RequestBodyBuilder;
import com.lightpiggies.anti.chat_for_android.retrofit.ResponseFunction;
import com.lightpiggies.anti.chat_for_android.retrofit.RetrofitManager;
import com.lightpiggies.anti.chat_for_android.widget.SafeEditText;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends RxFragmentActivity {

  private Button mLoginButton;
  private SafeEditText mUserNameEditText;
  private SafeEditText mPasswordEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mLoginButton = findViewById(R.id.login_button);
    mUserNameEditText = findViewById(R.id.user_name_edit_text);
    mPasswordEditText = findViewById(R.id.password_edit_text);
    mLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        RetrofitManager.getApiRequest()
            .getRegistration(RequestBodyBuilder.newBuilder()
                .setData(mUserNameEditText.getText().toString(),
                    mPasswordEditText.getText().toString())
                .build())
            .map(new ResponseFunction<TestResponse>())
            .subscribeOn(Schedulers.io())
            .subscribe(new Consumer<TestResponse>() {
              @Override
              public void accept(TestResponse testResponse) throws Exception {
                final TestResponse testResponse1 = testResponse;
                runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    Toast.makeText(MainActivity.this, testResponse1.getBirthday(),
                        Toast.LENGTH_LONG);
                  }
                });
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(final Throwable throwable) throws Exception {
                runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    Toast.makeText(MainActivity.this, throwable.getMessage(),
                        Toast.LENGTH_LONG);
                  }
                });
              }
            });
      }
    });
  }
}
