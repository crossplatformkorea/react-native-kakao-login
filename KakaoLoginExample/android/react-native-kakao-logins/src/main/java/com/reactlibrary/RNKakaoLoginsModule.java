
package com.reactlibrary;

import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.UiThreadUtil;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class RNKakaoLoginsModule extends ReactContextBaseJavaModule {

  private static final String TAG = "RNKakaoLoginModule";
  private final ReactApplicationContext reactContext;
  public static SessionCallback callback;
  private static Callback loginCallback;

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
  private void login(final Callback cb) {
    loginCallback = cb;
    // btnKakaoLogin.callOnClick();
  }

  @ReactMethod
  private void logout(final Callback cb) {
    UserManagement.requestLogout(new LogoutResponseCallback() {
      @Override
      public void onSessionClosed(ErrorResult errorResult) {
        Log.w(TAG, "sessionClosed!!\n" + errorResult.toString());
        cb.invoke(errorResult.toString(), null);
      }
      @Override
      public void onNotSignedUp() {
        Log.w(TAG, "NotSignedUp!!");
      }
      @Override
      public void onSuccess(Long result) {
        Log.d(TAG, "Logout!");
        cb.invoke(null, true);
      }
      @Override
      public void onCompleteLogout() {
        Log.d(TAG, "Complete Logout!");
        cb.invoke(null, true);
      }
    });
  }

  @ReactMethod
  private void getProfile(final Callback cb) {
    Log.d(TAG, "getProfile");
    UserManagement.requestMe(new MeResponseCallback() {
      @Override
      public void onFailure(ErrorResult errorResult) {
        String message = "failed to get user info. msg=" + errorResult;
        Log.e(TAG, message);
        cb.invoke(message, null);
      }

      @Override
      public void onSessionClosed(ErrorResult errorResult) {
        Log.e(TAG, "sessionClosed");
      }

      @Override
      public void onSuccess(UserProfile userProfile) {
        try {
          JSONObject jsonObject = new JSONObject();
          jsonObject.put("nickname", userProfile.getNickname());
          jsonObject.put("email", userProfile.getEmail());
          jsonObject.put("emailVerified", userProfile.getEmailVerified());
          jsonObject.put("thumbImagePath", userProfile.getThumbnailImagePath());
          jsonObject.put("profileImagePath", userProfile.getProfileImagePath());
          jsonObject.put("uuid", userProfile.getUUID());
          jsonObject.put("serviceUserId", userProfile.getServiceUserId());
          jsonObject.put("remainingInviteCount", userProfile.getRemainingInviteCount());
          jsonObject.put("remainingGroupMsgCount", userProfile.getRemainingGroupMsgCount());
          jsonObject.put("properties", userProfile.getProperties());
          cb.invoke(null, jsonObject.toString());
        } catch (JSONException e) {
          cb.invoke(e.toString(), null);
        }
      }

      @Override
      public void onNotSignedUp() {
        cb.invoke("NotSignedUp", null);
      }
    });
  }

  public static class SessionCallback implements ISessionCallback {
    @Override
    public void onSessionOpened() {
      Log.d(TAG, "Logged in!\ntoken: " + Session.getCurrentSession().getTokenInfo());
      if (loginCallback != null) {
        try {
          JSONObject jsonObject = new JSONObject();
          jsonObject.put("token", Session.getCurrentSession().getTokenInfo());
          loginCallback.invoke(null, jsonObject.toString());
        } catch (JSONException e) {
          loginCallback.invoke(e.toString(), null);
        } finally {
          loginCallback = null;
        }
      }
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
      if(exception != null) {
        if (loginCallback != null) {
          loginCallback.invoke(null, exception.toString());
          loginCallback = null;
        }
        Log.e(TAG, "Logged in!\nSessionOpenFailed");
        Logger.e(exception);
      }
    }
  }
}