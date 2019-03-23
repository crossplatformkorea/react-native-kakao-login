
package com.dooboolab.kakaologins;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.UiThreadUtil;
import com.kakao.auth.AccessTokenCallback;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNKakaoLoginsModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener{

  private static final String TAG = "RNKakaoLoginModule";
  private final ReactApplicationContext reactContext;
  public static SessionCallback callback;
  private static Callback loginCallback;

  private static class Item {
    final int textId;
    public final int icon;
    final int contentDescId;
    final AuthType authType;
    Item(final int textId, final Integer icon, final int contentDescId, final AuthType authType) {
      this.textId = textId;
      this.icon = icon;
      this.contentDescId = contentDescId;
      this.authType = authType;
    }
  }

  private List<AuthType> getAuthTypes() {
    final List<AuthType> availableAuthTypes = new ArrayList<>();
    if (Session.getCurrentSession().getAuthCodeManager().isTalkLoginAvailable()) {
      availableAuthTypes.add(AuthType.KAKAO_TALK);
    }
    if (Session.getCurrentSession().getAuthCodeManager().isStoryLoginAvailable()) {
      availableAuthTypes.add(AuthType.KAKAO_STORY);
    }
    availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);

    AuthType[] authTypes = KakaoSDK.getAdapter().getSessionConfig().getAuthTypes();
    if (authTypes == null || authTypes.length == 0 || (authTypes.length == 1 && authTypes[0] == AuthType.KAKAO_LOGIN_ALL)) {
      authTypes = AuthType.values();
    }
    availableAuthTypes.retainAll(Arrays.asList(authTypes));

    // 개발자가 설정한 것과 available 한 타입이 없다면 직접계정 입력이 뜨도록 한다.
    if(availableAuthTypes.size() == 0){
      availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);
    }

    return availableAuthTypes;
  }

  private Item[] createAuthItemArray(final List<AuthType> authTypes) {
    final List<Item> itemList = new ArrayList<Item>();
    if(authTypes.contains(AuthType.KAKAO_TALK)) {
      itemList.add(new Item(com.kakao.usermgmt.R.string.com_kakao_kakaotalk_account, com.kakao.usermgmt.R.drawable.talk, com.kakao.usermgmt.R.string.com_kakao_kakaotalk_account_tts, AuthType.KAKAO_TALK));
    }
    if(authTypes.contains(AuthType.KAKAO_STORY)) {
      itemList.add(new Item(com.kakao.usermgmt.R.string.com_kakao_kakaostory_account, com.kakao.usermgmt.R.drawable.story, com.kakao.usermgmt.R.string.com_kakao_kakaostory_account_tts, AuthType.KAKAO_STORY));
    }
    if(authTypes.contains(AuthType.KAKAO_ACCOUNT)){
      itemList.add(new Item(com.kakao.usermgmt.R.string.com_kakao_other_kakaoaccount, com.kakao.usermgmt.R.drawable.account, com.kakao.usermgmt.R.string.com_kakao_other_kakaoaccount_tts, AuthType.KAKAO_ACCOUNT));
    }

    return itemList.toArray(new Item[itemList.size()]);
  }

  @SuppressWarnings("deprecation")
  private ListAdapter createLoginAdapter(final Item[] authItems) {
        /*
          가능한 auth type들을 유저에게 보여주기 위한 준비.
         */
    return new ArrayAdapter<Item>(
        reactContext,
        android.R.layout.select_dialog_item,
        android.R.id.text1, authItems){
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
          LayoutInflater inflater = (LayoutInflater) getContext()
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          convertView = inflater.inflate(com.kakao.usermgmt.R.layout.layout_login_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(com.kakao.usermgmt.R.id.login_method_icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          imageView.setImageDrawable(reactContext.getResources().getDrawable(authItems[position].icon, getContext().getTheme()));
        } else {
          imageView.setImageDrawable(reactContext.getResources().getDrawable(authItems[position].icon));
        }
        TextView textView = (TextView) convertView.findViewById(com.kakao.usermgmt.R.id.login_method_text);
        textView.setText(authItems[position].textId);
        return convertView;
      }
    };
  }

  /**
   * 실제로 유저에게 보여질 dialog 객체를 생성한다.
   * @param authItems 가능한 AuthType들의 정보를 담고 있는 Item array
   * @param adapter Dialog의 list view에 쓰일 adapter
   * @return 로그인 방법들을 팝업으로 보여줄 dialog
   */
  private Dialog createLoginDialog(final Item[] authItems, final ListAdapter adapter) {
    final Dialog dialog = new Dialog(reactContext.getCurrentActivity(), com.kakao.usermgmt.R.style.LoginDialog);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(com.kakao.usermgmt.R.layout.layout_login_dialog);
    if (dialog.getWindow() != null) {
      dialog.getWindow().setGravity(Gravity.CENTER);
    }

//        TextView textView = (TextView) dialog.findViewById(R.id.login_title_text);
//        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/KakaoOTFRegular.otf");
//        if (customFont != null) {
//            textView.setTypeface(customFont);
//        }

    ListView listView = (ListView) dialog.findViewById(com.kakao.usermgmt.R.id.login_list_view);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final AuthType authType = authItems[position].authType;
        if (authType != null) {
          openSession(authType);
        }
        dialog.dismiss();
      }
    });

    Button closeButton = (Button) dialog.findViewById(com.kakao.usermgmt.R.id.login_close_button);
    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });
    return dialog;
  }

  public void openSession(final AuthType authType) {
    Log.d(TAG, "openSession: " + authType.toString());
    if (reactContext.getCurrentActivity() == null) {
      Log.d(TAG, "getCurrentActivity is null.");
    }
    Session.getCurrentSession().open(authType, reactContext.getCurrentActivity());
  }

  public RNKakaoLoginsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    if (KakaoSDK.getAdapter() == null) {
      KakaoSDK.init(new KakaoSDKAdapter(reactContext.getApplicationContext()));
    } else {
      Session.getCurrentSession().clearCallbacks();
    }
    reactContext.addActivityEventListener(this);
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
    final List<AuthType> authTypes = getAuthTypes();
    if (authTypes.size() == 1) {
      Session.getCurrentSession().open(authTypes.get(0), reactContext.getCurrentActivity());
    } else {
      final Item[] authItems = createAuthItemArray(authTypes);
      ListAdapter adapter = createLoginAdapter(authItems);
      final Dialog dialog = createLoginDialog(authItems, adapter);
      dialog.show();
    }
  }

  @ReactMethod
  private void logout(final Callback cb) {
    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
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
        cb.invoke(null, String.valueOf(result));
      }
      @Override
      public void onCompleteLogout() {
        Log.d(TAG, "Complete Logout!");
        cb.invoke(null, "Logged out");
      }
    });
  }

  @ReactMethod
  private void getProfile(final Callback cb) {
    Log.d(TAG, "getProfile");

    UserManagement.getInstance().me(new MeV2ResponseCallback() {
      @Override
      public void onSessionClosed(ErrorResult errorResult) {

      }

      @Override
      public void onSuccess(MeV2Response result) {
        try {
          JSONObject jsonObject = new JSONObject();

          jsonObject.put("id", String.valueOf(result.getId()));
          jsonObject.put("nickname", result.getNickname());
          UserAccount kakaoAccount = result.getKakaoAccount();
          if(kakaoAccount != null) {
            jsonObject.put("email", kakaoAccount.getEmail());
            jsonObject.put("display_id", kakaoAccount.getDisplayId());
            jsonObject.put("phone_number", kakaoAccount.getPhoneNumber());
            jsonObject.put("email_verified",  kakaoAccount.isEmailVerified().toString() == "TRUE");
            jsonObject.put("kakaotalk_user", kakaoAccount.isKakaoTalkUser().toString() == "TRUE");
          } else {
            jsonObject.put("email", null);
            jsonObject.put("display_id", null);
            jsonObject.put("phone_number", null);
            jsonObject.put("email_verified",  false);
            jsonObject.put("kakaotalk_user", false);
          }
          jsonObject.put("profile_image_path", result.getProfileImagePath());
          jsonObject.put("thumb_image_path", result.getThumbnailImagePath());
          jsonObject.put("has_signed_up", result.hasSignedUp().toString() == "TRUE");

          cb.invoke(null, jsonObject.toString());
        } catch (JSONException e) {
          cb.invoke(e.toString(), null);
        }
      }
    });

    /*
    UserManagement.getInstance().requestMe(new MeResponseCallback() {
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
    */
  }

  public static class SessionCallback implements ISessionCallback {
    @Override
    public void onSessionOpened() {
      Log.d(TAG, "Logged in!\ntoken: " + Session.getCurrentSession().getAccessToken());

      if (loginCallback != null) {
        JSONObject response = new JSONObject();
        String token = Session.getCurrentSession().getAccessToken();
        try {
          response.put("token",token);
          loginCallback.invoke(null,response.toString());
          loginCallback = null;
        } catch (JSONException e) {
          loginCallback.invoke(e.toString(), null);
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

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
      return;
    }
  }

  @Override
  public void onNewIntent(Intent intent) {

  }

  @Override
  public void onHostDestroy() {
    Session.getCurrentSession().removeCallback(this.callback);
  }

  @Override
  public void onHostPause() {

  }

  @Override
  public void onHostResume() {

  }
}
