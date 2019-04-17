package com.lightpiggies.anti.chat_for_android.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.lightpiggies.anti.chat_for_android.R;
import com.lightpiggies.anti.chat_for_android.response.TestResponse;
import com.lightpiggies.anti.chat_for_android.retrofit.ApiRequestKey;
import com.lightpiggies.anti.chat_for_android.retrofit.RequestBodyBuilder;
import com.lightpiggies.anti.chat_for_android.retrofit.ResponseFunction;
import com.lightpiggies.anti.chat_for_android.retrofit.RetrofitManager;
import com.lightpiggies.anti.chat_for_android.widget.SafeEditText;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends RxFragmentActivity {

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
    mLoginButton.setOnClickListener(view -> {
      RetrofitManager.getApiRequest()
          .getRegistration(RequestBodyBuilder.newBuilder()
              .setData(ApiRequestKey.REGISTER, mUserNameEditText.getText().toString(),
                  mPasswordEditText.getText().toString())
              .build())
          .map(new ResponseFunction<>())
          .subscribeOn(Schedulers.io())
          .subscribe(testResponse -> {
            final TestResponse testResponse1 = testResponse;
            runOnUiThread(() -> Toast
                .makeText(LoginActivity.this, testResponse1.getBirthday(), Toast.LENGTH_LONG)
                .show());
          }, throwable -> runOnUiThread(
              () -> Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_LONG)
                  .show()));
    });
  }
}
