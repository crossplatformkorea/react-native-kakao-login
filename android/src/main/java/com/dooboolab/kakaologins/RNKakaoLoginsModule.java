
package com.dooboolab.kakaologins;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
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

public class RNKakaoLoginsModule extends ReactContextBaseJavaModule {

  private static final String TAG = "RNKakaoLoginModule";
  private final ReactApplicationContext reactContext;
  public static SessionCallback callback;
  private LoginButton btnKakaoLogin;

  public RNKakaoLoginsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;

    callback = new SessionCallback();
    Session.getCurrentSession().addCallback(callback);
    Session.getCurrentSession().checkAndImplicitOpen();
  }

  @Override
  public String getName() {
    return "RNKakaoLogins";
  }

  @ReactMethod
  private void login() {
    btnKakaoLogin.callOnClick();
  }

  @ReactMethod
  private void logout() {
    UserManagement.requestLogout(new LogoutResponseCallback() {
      @Override
      public void onSessionClosed(ErrorResult errorResult) {
        Log.w(TAG, "sessionClosed!!\n" + errorResult.toString());
      }
      @Override
      public void onNotSignedUp() {
        Log.w(TAG, "NotSignedUp!!");
      }
      @Override
      public void onSuccess(Long result) {
        Log.d(TAG, "Logout!");
      }
      @Override
      public void onCompleteLogout() {
        Log.d(TAG, "Complete Logout!");
      }
    });
  }

  @ReactMethod
  private void getProfile() {
    Log.d(TAG, "getProfile");
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
        Toast.makeText(reactContext, "userProfile!\n" + userProfile.toString(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNotSignedUp() {
        Log.e(TAG, "NotSignedUp");
      }
    });
  }

  public static class SessionCallback implements ISessionCallback {
    @Override
    public void onSessionOpened() {
      Log.d(TAG, "Logged in!\ntoken: " + Session.getCurrentSession().getTokenInfo());
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
      if(exception != null) {
        Log.e(TAG, "Logged in!\nSessionOpenFailed");
        Logger.e(exception);
      }
    }
  }
}