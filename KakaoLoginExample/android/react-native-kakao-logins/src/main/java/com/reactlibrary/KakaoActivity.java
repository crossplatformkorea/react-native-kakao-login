package com.reactlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kakao.usermgmt.LoginButton;

public class KakaoActivity extends AppCompatActivity {

  private LoginButton btnKakaoLogin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_kakao);

    btnKakaoLogin = findViewById(R.id.btn_kakao_login);
    btnKakaoLogin.callOnClick();
  }
}
