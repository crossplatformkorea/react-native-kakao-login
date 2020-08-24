package com.dooboolab.kakaologins;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.AccessTokenCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.AgeRange;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RNKakaoLoginsModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener{

    private static final String TAG = "RNKakaoLoginModule";
    private final ReactApplicationContext reactContext;
    public static SessionCallback callback;

    private static Promise loginPromise;
    private static boolean hasInit; // becomes true after KakaoSDK.init() is called

    private static void loginResolver(WritableMap result){
        if(loginPromise != null){
            loginPromise.resolve(result);
            loginPromise = null;
        }
    }

    private static void loginRejecter(KakaoException exception){
        if(loginPromise != null){
            String ErrorCode = getLoginErrorCode(exception);
            loginPromise.reject(ErrorCode, exception.getMessage(), exception);
            loginPromise = null;
        }
    }

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

    private List<AuthType> getAuthTypes(final List<Integer> authTypeIntegerList) {
        final List<AuthType> availableAuthTypes = new ArrayList<>();
        if (Session.getCurrentSession().getAuthCodeManager().isTalkLoginAvailable()) {
            availableAuthTypes.add(AuthType.KAKAO_TALK);
        }
        if (Session.getCurrentSession().getAuthCodeManager().isStoryLoginAvailable()) {
            availableAuthTypes.add(AuthType.KAKAO_STORY);
        }
        availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);

        // 옵션으로 입력받은 인증타입만 남김 (리스트가 비어있다면 수행하지 않음)
        final List<AuthType> authTypeListReceived = new ArrayList<>();
        for (Integer authTypeInteger : authTypeIntegerList) {
            AuthType authType = convertIntegerToAuthType(authTypeInteger);
            authTypeListReceived.add(authType);
        }
        if (authTypeListReceived.size() > 0) {
            availableAuthTypes.retainAll(authTypeListReceived);
        }

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

    private void handleOptionalBooleanWithMap(WritableMap map, String key, OptionalBoolean bool){
        switch(bool){
            case NONE:
                map.putNull(key);
                break;
            default:
                map.putBoolean(key, bool.getBoolean());
                break;
        }
    }

    private String handleOptionalEnumGender(Gender gender){
        if (gender == null) {
            return null;
        }

        switch(gender){
            case FEMALE:
                return "FEMALE";
            case MALE:
                return "MALE";
            default:
                return null;
        }
    }

    private String handleOptionalEnumAgeRange(AgeRange ageRange){
        if (ageRange == null) {
            return null;
        }
        return ageRange.getValue();
    }

    private HashMap<String, String> getNullableAccountInfo(UserAccount kakaoAccount) {
        HashMap<String, String> profileMap = new HashMap();

        if(kakaoAccount != null){
            Profile kakaoProfile = kakaoAccount.getProfile();
            if(kakaoProfile != null) {
                profileMap.put("profile_image_url", kakaoProfile.getProfileImageUrl());
                profileMap.put("thumb_image_url", kakaoProfile.getThumbnailImageUrl());
                profileMap.put("nickname", kakaoProfile.getNickname());
            }

            Gender gender = kakaoAccount.getGender();
            AgeRange ageRange = kakaoAccount.getAgeRange();
            profileMap.put("gender", handleOptionalEnumGender(gender));
            profileMap.put("email", kakaoAccount.getEmail());
            profileMap.put("birthyear", kakaoAccount.getBirthyear());
            profileMap.put("birthday", kakaoAccount.getBirthday());
            profileMap.put("display_id", kakaoAccount.getDisplayId());
            profileMap.put("phone_number", kakaoAccount.getPhoneNumber());
            profileMap.put("age_range", handleOptionalEnumAgeRange(ageRange));
        }

        return profileMap;
    }

    private AuthType convertIntegerToAuthType(Integer type){
        switch (type) {
            case 2:
            return AuthType.KAKAO_TALK;
            case 4:
            return AuthType.KAKAO_STORY;
            case 8:
            return AuthType.KAKAO_ACCOUNT;
            default:
            return null;
        }
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
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    private Dialog createLoginDialog(final Item[] authItems, final ListAdapter adapter, final Promise promise) {
        final Dialog dialog = new Dialog(reactContext.getCurrentActivity(), com.kakao.usermgmt.R.style.LoginDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(com.kakao.usermgmt.R.layout.layout_login_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setGravity(Gravity.CENTER);
        }

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener () {
            @Override
            public void onCancel(DialogInterface dialog) {
                promise.reject("E_CANCELLED_OPERATION", "로그인을 취소하였습니다. 다시 시도해주세요.");
                dialog.dismiss();
            }
        });

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
                promise.reject("E_CANCELLED_OPERATION", "로그인을 취소하였습니다. 다시 시도해주세요.");
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public void openSession(final AuthType authType) {
        if (reactContext.getCurrentActivity() == null) {
            Log.d(TAG, "getCurrentActivity is null.");
        }
        Session.getCurrentSession().open(authType, reactContext.getCurrentActivity());
    }

    public RNKakaoLoginsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    private void initKakaoSDK() {
        if (hasInit) {
            return;
        }
        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(new KakaoSDKAdapter(reactContext.getApplicationContext()));
        } else {
            Session.getCurrentSession().clearCallbacks();
        }
        reactContext.addActivityEventListener(this);
        reactContext.addLifecycleEventListener(this);
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
        hasInit = true;
    }

    @Override
    public String getName() {
        return "RNKakaoLogins";
    }

    @ReactMethod
    private void login(Promise promise) {
        List<Integer> array = new ArrayList<Integer>();

        this.internalLogin(array, promise);
    }

    @ReactMethod
    private void login(ReadableArray authTypes, Promise promise){
        List<Integer> authList = new ArrayList<>();
        int size = authTypes.size();
        for(int i = 0; i < size; i++) {
            authList.add(authTypes.getInt(i));
        }

        this.internalLogin(authList, promise);
    }

    private void internalLogin(final List<Integer> authTypes, Promise promise) {
        initKakaoSDK();
        loginPromise = promise;

        if (getAuthTypes(authTypes).size() == 1) {
            Session.getCurrentSession().open(getAuthTypes(authTypes).get(0), reactContext.getCurrentActivity());
        } else {
            final Item[] authItems = createAuthItemArray(getAuthTypes(authTypes));
            ListAdapter adapter = createLoginAdapter(authItems);
            final Dialog dialog = createLoginDialog(authItems, adapter, promise);
            dialog.show();
        }
    }

    @ReactMethod
    private void logout(final Promise promise) {
        initKakaoSDK();
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult error) {
                Log.e(TAG, "Logout::Error\n" + error);
                promise.reject(String.valueOf(error.getErrorCode()), error.getErrorMessage(), error.getException());
            }

            @Override
            public void onCompleteLogout() {
                promise.resolve("Logged out");
            }
        });
    }

    @ReactMethod
    private void getProfile(final Promise promise) {
        initKakaoSDK();
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult error) {
                Log.e(TAG, "getProfile::Error\n" + error);
                promise.reject(String.valueOf(error.getErrorCode()), error.getErrorMessage(), error.getException());
            }

            @Override
            public void onFailure(ErrorResult error) {
                Log.e(TAG, "getProfile::Error\n" + error);
                promise.reject(String.valueOf(error.getErrorCode()), error.getErrorMessage(), error.getException());
            }

            @Override
            public void onSuccess(MeV2Response me) {
                try {
                    WritableMap profile = Arguments.createMap();

                    profile.putString("id", String.valueOf(me.getId()));
                    handleOptionalBooleanWithMap(profile, "has_signed_up", me.hasSignedUp());

                    if(me.getKakaoAccount() != null) {
                        handleOptionalBooleanWithMap(profile, "is_email_verified", me.getKakaoAccount().isEmailVerified());
                        handleOptionalBooleanWithMap(profile, "is_kakaotalk_user", me.getKakaoAccount().isKakaoTalkUser());
                    } else {
                        profile.putNull("is_email_verified");
                        profile.putNull("is_kakaotalk_user");
                    }

                    HashMap<String,String> nullableAccount = getNullableAccountInfo(me.getKakaoAccount());
                    profile.putString("profile_image_url", nullableAccount.get("profile_image_url"));
                    profile.putString("thumb_image_url", nullableAccount.get("thumb_image_url"));
                    profile.putString("nickname", nullableAccount.get("nickname"));
                    profile.putString("gender", nullableAccount.get("gender"));
                    profile.putString("email", nullableAccount.get("email"));
                    profile.putString("birthyear", nullableAccount.get("birthyear"));
                    profile.putString("birthday", nullableAccount.get("birthday"));
                    profile.putString("display_id", nullableAccount.get("display_id"));
                    profile.putString("phone_number", nullableAccount.get("phone_number"));
                    profile.putString("age_range", nullableAccount.get("age_range"));
                    promise.resolve(profile);
                } catch (Exception e) {
                    promise.reject("E_UNKOWN", e.getMessage(), e);
                    Log.e(TAG, "getProfile::Error\n" + e);
                }
            }
        });
    }

    @ReactMethod
    private void unlink(final Promise promise) {
        initKakaoSDK();
        UserManagement.getInstance()
                .requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult error) {
                        promise.reject(String.valueOf(error.getErrorCode()), error.getErrorMessage(), error.getException());
                    }

                    @Override
                    public void onFailure(ErrorResult error) {
                        promise.reject(String.valueOf(error.getErrorCode()), error.getErrorMessage(), error.getException());

                    }
                    @Override
                    public void onSuccess(Long result) {
                        promise.resolve("Unlinked");
                    }
                });
    }

    @ReactMethod
    private void updateScopes(final ReadableArray scopes, final Promise promise) {
        initKakaoSDK();
        List<String> targetScopes = new ArrayList<String>();
        for (Object scopeObj : scopes.toArrayList()) {
            String scope = scopeObj.toString();
            targetScopes.add(scope);
        }

        Session.getCurrentSession().updateScopes(reactContext.getCurrentActivity(), targetScopes, new AccessTokenCallback() {
            @Override
            public void onAccessTokenReceived(AccessToken accessToken) {
                WritableMap result = Arguments.createMap();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                result.putString("accessToken", Session.getCurrentSession().getTokenInfo().getAccessToken());
                result.putString("refreshToken", Session.getCurrentSession().getTokenInfo().getRefreshToken());
                result.putString("accessTokenExpiresAt", transFormat.format(Session.getCurrentSession().getTokenInfo().accessTokenExpiresAt()));
                result.putString("refreshTokenExpiresAt", transFormat.format(Session.getCurrentSession().getTokenInfo().refreshTokenExpiresAt()));

                promise.resolve(result);
            }

            @Override
            public void onAccessTokenFailure(ErrorResult errorResult) {
                promise.reject(String.valueOf(errorResult.getErrorCode()), errorResult.getErrorMessage(), errorResult.getException());
            }
        });
    }

    @ReactMethod
    private void getTokens(final Promise promise) {
        initKakaoSDK();
        AuthService.getInstance()
                .requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        promise.reject(String.valueOf(errorResult.getErrorCode()), errorResult.getErrorMessage(), errorResult.getException());
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        promise.reject(String.valueOf(errorResult.getErrorCode()), errorResult.getErrorMessage(), errorResult.getException());
                    }

                    @Override
                    public void onSuccess(AccessTokenInfoResponse result) {
                        WritableMap tokenInfo = Arguments.createMap();
                        tokenInfo.putString("accessToken", Session.getCurrentSession().getTokenInfo().getAccessToken());
                        tokenInfo.putString("refreshToken", Session.getCurrentSession().getTokenInfo().getRefreshToken());
                        promise.resolve(tokenInfo);
                    }
                });
    }


    public static class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            WritableMap result = Arguments.createMap();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            result.putString("accessToken", Session.getCurrentSession().getTokenInfo().getAccessToken());
            result.putString("refreshToken", Session.getCurrentSession().getTokenInfo().getRefreshToken());
            result.putString("accessTokenExpiresAt", transFormat.format(Session.getCurrentSession().getTokenInfo().accessTokenExpiresAt()));
            result.putString("refreshTokenExpiresAt", transFormat.format(Session.getCurrentSession().getTokenInfo().refreshTokenExpiresAt()));

            loginResolver(result);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Log.e(TAG, "Login::SessionOpenFailed\n" + exception);
                loginRejecter(exception);
            }
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (!hasInit) {
            return;
        }
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            return;
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onHostDestroy() {
        if (!hasInit) {
            return;
        }
        Session.getCurrentSession().removeCallback(this.callback);
    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostResume() {
        if (!hasInit) {
            return;
        }
        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(new KakaoSDKAdapter(reactContext.getApplicationContext()));
            reactContext.addActivityEventListener(this);
            callback = new SessionCallback();
        }
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    public static String getLoginErrorCode(KakaoException exception) {
        switch(exception.getErrorType()){
            case UNSPECIFIED_ERROR:
                return "E_UNKNOWN";
            case CANCELED_OPERATION:
                return "E_CANCELLED_OPERATION";
            case ILLEGAL_ARGUMENT:
                return "E_ILLEGAL_ARGUMENT";
            case ILLEGAL_STATE:
                return "E_ILLEGAL_STATE";
            case MISS_CONFIGURATION:
                return "E_MISS_CONFIGURATION";
            case AUTHORIZATION_FAILED:
                return "E_AUTHORIZATION_FAILED";
            case JSON_PARSING_ERROR:
                return "E_JSON_PARSING_ERROR";
            case URI_LENGTH_EXCEEDED:
                return "E_URI_LENGTH_EXCEEDED";
            case KAKAOTALK_NOT_INSTALLED:
                return "E_KAKAOTALK_NOT_INSTALLED";
            default:
                return "E_UNKNOWN";

        }
    }
}
