package com.dooboolab.kakaologins;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RNKakaoLoginsModule extends ReactContextBaseJavaModule {

    private static final String TAG = "RNKakaoLoginModule";
    private final ReactApplicationContext reactContext;

    private String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public RNKakaoLoginsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        String kakaoAppKey = reactContext.getResources().getString(
                reactContext.getResources().getIdentifier("kakao_app_key", "string", reactContext.getPackageName()));
        KakaoSdk.init(reactContext, kakaoAppKey);
    }

    @NonNull
    @Override
    public String getName() {
        return "RNKakaoLogins";
    }

    @ReactMethod
    private void login(Promise promise){
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(reactContext)) {
            UserApiClient.getInstance().loginWithKakaoTalk(reactContext.getCurrentActivity(), (token, error) -> {
                if (error != null) {
                    promise.reject("RNKakaoLogins", error.getMessage(), error);
                    return null;
                }

                WritableMap map = Arguments.createMap();
                map.putString("accessToken", token.getAccessToken());
                map.putString("refreshToken", token.getRefreshToken());
                map.putString("accessTokenExpiresAt", dateFormat(token.getAccessTokenExpiresAt()));
                map.putString("refreshTokenExpiresAt", dateFormat(token.getRefreshTokenExpiresAt()));

                WritableArray scopes = Arguments.createArray();

                if (token.getScopes() != null) {
                    for (String scope : token.getScopes()) {
                        scopes.pushString(scope);
                    }
                }

                map.putArray("scopes", scopes);

                promise.resolve(map);
                return null;
            });
        } else {
            UserApiClient.getInstance().loginWithKakaoAccount(reactContext.getCurrentActivity(), (token, error) -> {
                if (error != null) {
                    promise.reject("RNKakaoLogins", error.getMessage(), error);
                    return null;
                }

                WritableMap map = Arguments.createMap();
                map.putString("accessToken", token.getAccessToken());
                map.putString("refreshToken", token.getRefreshToken());
                map.putString("accessTokenExpiresAt", dateFormat(token.getAccessTokenExpiresAt()));
                map.putString("refreshTokenExpiresAt", dateFormat(token.getRefreshTokenExpiresAt()));

                WritableArray scopes = Arguments.createArray();

                if (token.getScopes() != null) {
                    for (String scope : token.getScopes()) {
                        scopes.pushString(scope);
                    }
                }

                map.putArray("scopes", scopes);

                promise.resolve(map);
                return null;
            });
        }
    }

    @ReactMethod
    private void logout(final Promise promise) {
        UserApiClient.getInstance().logout((error) -> {
            if (error != null) {
                promise.reject("RNKakaoLogins", error.getMessage(), error);
                return null;
            }
            promise.resolve("Successfully logged out");
            return null;
        });
    }

    @ReactMethod
    private void unlink(final Promise promise) {
        UserApiClient.getInstance().unlink((error) -> {
            if (error != null) {
                promise.reject("RNKakaoLogins", error.getMessage(), error);
                return null;
            }
            promise.resolve("Successfully unlinked");
            return null;
        });
    }

    @ReactMethod
    private void getAccessToken(final Promise promise) {
        UserApiClient.getInstance().accessTokenInfo((token, error) -> {
            if (error != null) {
                promise.reject("RNKakaoLogins", error.getMessage(), error);
                return null;
            }

            WritableMap map = Arguments.createMap();
            map.putString("accessToken", String.valueOf(token.getId()));
            map.putString("expiresIn", String.valueOf(token.getExpiresIn()));

            promise.resolve(map);
            return null;
        });
    }

    private boolean convertValue(Boolean val) {
        return val == null ? false : val;
    }

    @ReactMethod
    private void getProfile(final Promise promise) {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                promise.reject("RNKakaoLogins", error.getMessage(), error);
                return null;
            }

            WritableMap map = Arguments.createMap();
            map.putString("id", String.valueOf(user.getId()));

            Account kakaoUser = user.getKakaoAccount();
            map.putString("email", String.valueOf(kakaoUser.getEmail()));
            map.putString("nickname", String.valueOf(kakaoUser.getProfile().getNickname()));
            map.putString("profileImageUrl", String.valueOf(kakaoUser.getProfile().getProfileImageUrl()));
            map.putString("thumbnailImageUrl", String.valueOf(kakaoUser.getProfile().getThumbnailImageUrl()));
            map.putString("phoneNumber", String.valueOf(kakaoUser.getPhoneNumber()));
            map.putString("ageRange", String.valueOf(user.getKakaoAccount().getAgeRange()));
            map.putString("birthday", String.valueOf(kakaoUser.getBirthday()));
            map.putString("birthdayType", String.valueOf(kakaoUser.getBirthdayType()));
            map.putString("birthyear", String.valueOf(kakaoUser.getBirthyear()));
            map.putString("gender", String.valueOf(kakaoUser.getGender()));
            map.putBoolean("isEmailValid", convertValue(kakaoUser.isEmailValid()));
            map.putBoolean("isEmailVerified", convertValue(kakaoUser.isEmailVerified()));
            map.putBoolean("isKorean", convertValue(kakaoUser.isKorean()));
            map.putBoolean("ageRangeNeedsAgreement", convertValue(kakaoUser.getAgeRangeNeedsAgreement()));
            map.putBoolean("birthdayNeedsAgreement", convertValue(kakaoUser.getBirthdayNeedsAgreement()));
            map.putBoolean("birthyearNeedsAgreement", convertValue(kakaoUser.getBirthyearNeedsAgreement()));
            map.putBoolean("emailNeedsAgreement", convertValue(kakaoUser.getEmailNeedsAgreement()));
            map.putBoolean("genderNeedsAgreement", convertValue(kakaoUser.getGenderNeedsAgreement()));
            map.putBoolean("isKoreanNeedsAgreement", convertValue(kakaoUser.isKoreanNeedsAgreement()));
            map.putBoolean("phoneNumberNeedsAgreement", convertValue(kakaoUser.getPhoneNumberNeedsAgreement()));
            map.putBoolean("profileNeedsAgreement", convertValue(kakaoUser.getProfileNeedsAgreement()));

            promise.resolve(map);
            return null;
        });
    }
}
