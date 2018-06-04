package com.dooboolab.kakaologins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class KakaoActivity extends AppCompatActivity implements View.OnClickListener{
  private String TAG = "KakaoActivity";
  private LoginButton btnKakaoLogin;
  private Button btnLogin;
  private Button btnLogout;
  private Button btnProfile;
  private SessionCallback callback;
  private UserManagement userManagement;

  /**
   * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
   *
   * @param savedInstanceState 기존 session 정보가 저장된 객체
   */
  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    try {
//      PackageInfo info = getPackageManager().getPackageInfo("com.dooboolab.test", PackageManager.GET_SIGNATURES);
//      for (Signature signature : info.signatures) {
//        MessageDigest md = MessageDigest.getInstance("SHA");
//        md.update(signature.toByteArray());
//        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//      }
//    } catch (PackageManager.NameNotFoundException e) {
//      e.printStackTrace();
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//    }

    btnKakaoLogin = findViewById(R.id.btn_kakao_login);
    btnLogin = findViewById(R.id.btn_login);
    btnLogout = findViewById(R.id.btn_logout);
    btnProfile = findViewById(R.id.btn_profile);

    btnLogin.setOnClickListener(this);
    btnLogout.setOnClickListener(this);
    btnProfile.setOnClickListener(this);

    callback = new SessionCallback();
    Session.getCurrentSession().addCallback(callback);
    Session.getCurrentSession().checkAndImplicitOpen();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_login:
        btnKakaoLogin.callOnClick();
        break;
      case R.id.btn_logout:
        Log.d(TAG, "btnLogout clicked");
        UserManagement.requestLogout(new LogoutResponseCallback() {
          @Override
          public void onSessionClosed(ErrorResult errorResult) {
            Log.d(TAG, "sessionClosed!!\n" + errorResult.toString());
          }
          @Override
          public void onNotSignedUp() {
            Log.d(TAG, "NotSignedUp!!");
          }
          @Override
          public void onSuccess(Long result) {
            Toast.makeText(MainActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
          }
          @Override
          public void onCompleteLogout() {
            Toast.makeText(MainActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
          }
        });
        break;
      case R.id.btn_profile:
        requestMe();
        break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
      Log.d(TAG, "requestCode: " + requestCode + ", resultCode: " + resultCode);
      return;
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Session.getCurrentSession().removeCallback(callback);
  }

  private class SessionCallback implements ISessionCallback {

    @Override
    public void onSessionOpened() {
      Toast.makeText(
          MainActivity.this,
          "Logged in!\ntoken: " + Session.getCurrentSession().getTokenInfo(),
          Toast.LENGTH_SHORT
      ).show();
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
      if(exception != null) {
        Logger.e(exception);
      }
    }
  }

  private void requestMe() {
    Log.d(TAG, "requestMe");
    UserManagement.requestMe(new MeResponseCallback() {
      @Override
      public void onFailure(ErrorResult errorResult) {
        String message = "failed to get user info. msg=" + errorResult;
        Log.e(TAG, message);
      }

      @Override
      public void onSessionClosed(ErrorResult errorResult) {
        Log.e(TAG, "seesionClosed");
      }

      @Override
      public void onSuccess(UserProfile userProfile) {
        Toast.makeText(MainActivity.this, "userProfile!\n" + userProfile.toString(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNotSignedUp() {
        Log.e(TAG, "NotSignedUp");
      }
    });
  }
}
