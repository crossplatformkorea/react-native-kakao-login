package com.dooboolab.kakaologins

import android.util.Log
import com.facebook.react.bridge.*
import com.kakao.sdk.common.KakaoSdk.init
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.model.ServiceTerms
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RNKakaoLoginsModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private fun dateFormat(date: Date?): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(date)
    }

    override fun getName(): String {
        return "RNKakaoLogins"
    }

    @ReactMethod
    private fun login(promise: Promise) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(reactContext)) {
            reactContext.currentActivity?.let {
                UserApiClient.instance.loginWithKakaoTalk(it) { token, error: Throwable? ->
                    if (error != null) {
                        if (error is AuthError && error.statusCode == 302) {
                            this.loginWithKakaoAccount(promise)
                            return@loginWithKakaoTalk
                        }
                        promise.reject("RNKakaoLogins", error.message, error)
                        return@loginWithKakaoTalk
                    }

                    if (token != null) {
                        val (accessToken, accessTokenExpiresAt, refreshToken, refreshTokenExpiresAt, idToken, scopes) = token
                        val map = Arguments.createMap()
                        map.putString("accessToken", accessToken)
                        map.putString("refreshToken", refreshToken)
                        map.putString("idToken", idToken)
                        map.putString("accessTokenExpiresAt", dateFormat(accessTokenExpiresAt))
                        map.putString("refreshTokenExpiresAt", dateFormat(refreshTokenExpiresAt))
                        val scopeArray = Arguments.createArray()
                        if (scopes != null) {
                            for (scope in scopes) {
                                scopeArray.pushString(scope)
                            }
                        }
                        map.putArray("scopes", scopeArray)
                        promise.resolve(map)
                        return@loginWithKakaoTalk
                    }

                    promise.reject("RNKakaoLogins", "Token is null")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(reactContext) { token, error: Throwable? ->
                if (error != null) {
                    promise.reject("RNKakaoLogins", error.message, error)
                    return@loginWithKakaoAccount
                }

                if (token != null) {
                    val (accessToken, accessTokenExpiresAt, refreshToken, refreshTokenExpiresAt, idToken, scopes) = token
                    val map = Arguments.createMap()
                    map.putString("accessToken", accessToken)
                    map.putString("refreshToken", refreshToken)
                    map.putString("idToken", idToken)
                    map.putString("accessTokenExpiresAt", dateFormat(accessTokenExpiresAt))
                    map.putString("refreshTokenExpiresAt", dateFormat(refreshTokenExpiresAt))
                    val scopeArray = Arguments.createArray()
                    if (scopes != null) {
                        for (scope in scopes) {
                            scopeArray.pushString(scope)
                        }
                    }
                    map.putArray("scopes", scopeArray)
                    promise.resolve(map)
                    return@loginWithKakaoAccount
                }

                promise.reject("RNKakaoLogins", "Token is null")
            }
        }
    }

    @ReactMethod
    private fun loginWithKakaoAccount(promise: Promise) {
        UserApiClient.instance.loginWithKakaoAccount(reactContext) { token, error: Throwable? ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@loginWithKakaoAccount
            }

            if (token == null) {
                promise.reject("RNKakaoLogins", "Token is null")
                return@loginWithKakaoAccount
            }

            if (token != null) {
                val (accessToken, accessTokenExpiresAt, refreshToken, refreshTokenExpiresAt, idToken, scopes) = token
                val map = Arguments.createMap()
                map.putString("accessToken", accessToken)
                map.putString("refreshToken", refreshToken)
                map.putString("idToken", idToken)
                map.putString("accessTokenExpiresAt", dateFormat(accessTokenExpiresAt))
                map.putString("refreshTokenExpiresAt", dateFormat(refreshTokenExpiresAt))
                val scopeArray = Arguments.createArray()
                if (scopes != null) {
                    for (scope in scopes) {
                        scopeArray.pushString(scope)
                    }
                }
                map.putArray("scopes", scopeArray)
                promise.resolve(map)
                return@loginWithKakaoAccount
            }
        }
    }

    @ReactMethod
    private fun logout(promise: Promise) {
        UserApiClient.instance.logout { error: Throwable? ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@logout
            }
            promise.resolve("Successfully logged out")
            null
        }
    }

    @ReactMethod
    private fun unlink(promise: Promise) {
        UserApiClient.instance.unlink { error: Throwable? ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@unlink
            }
            promise.resolve("Successfully unlinked")
            null
        }
    }

    @ReactMethod
    private fun getServiceTerms(promise: Promise) {
        // 동의한 약관 확인하기
        UserApiClient.instance.serviceTerms { userServiceTerms, error ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@serviceTerms
            }
            else if (userServiceTerms != null) {
                val map = Arguments.createMap()
                val list: List<ServiceTerms>? = userServiceTerms.allowedServiceTerms
                val jsonObject = JSONObject()
                val allowedServiceTerms: ArrayList<String> = ArrayList()
                if (list != null) {
                    for (item in list!!) {
                        Log.d("allowedServiceTerms", item.tag);
                        Log.d("allowedServiceTerms", item.agreedAt.toString());
                        jsonObject.put("tag", item.tag);
                        jsonObject.put("agreedAt", item.agreedAt.toString());
                        allowedServiceTerms.add(jsonObject.toString())
                    }
                    Log.d("allowedServiceTerms", allowedServiceTerms.toString());
                }else {
                    promise.reject("RNKakaoLogins", "serviceTerms is null")
                }
                map.putString("user_id", userServiceTerms.userId.toString());
                map.putString("allowedServiceTerms", allowedServiceTerms.toString());
                promise.resolve(map)
                return@serviceTerms
            }
            promise.reject("RNKakaoLogins", "serviceTerms is null")
        }
    }

    @ReactMethod
    private fun getAccessToken(promise: Promise) {
        val accessToken = TokenManagerProvider.instance.manager.getToken()?.accessToken

         UserApiClient.instance.accessTokenInfo { token, error: Throwable? ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@accessTokenInfo
            }

            if (token != null && accessToken != null) {
                val (expiresIn) = token
                val map = Arguments.createMap()
                map.putString("accessToken", accessToken.toString())
                map.putString("expiresIn", expiresIn.toString())
                promise.resolve(map)
                return@accessTokenInfo
            }

            promise.reject("RNKakaoLogins", "Token is null")
         }
    }

    private fun convertValue(`val`: Boolean?): Boolean {
        return `val` ?: false
    }

    @ReactMethod
    private fun getProfile(promise: Promise) {
        UserApiClient.instance.me { user: User?, error: Throwable? ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@me
            }

            if (user != null) {
                val map = Arguments.createMap()
                map.putString("id", user.id.toString())
                val kakaoUser = user.kakaoAccount
                if (kakaoUser != null) {
                    map.putString("name", kakaoUser.name.toString())
                    map.putString("email", kakaoUser!!.email.toString())
                    map.putString("nickname", kakaoUser.profile?.nickname)
                    map.putString("profileImageUrl", kakaoUser.profile?.profileImageUrl)
                    map.putString("thumbnailImageUrl", kakaoUser.profile?.thumbnailImageUrl)

                    map.putString("phoneNumber", kakaoUser.phoneNumber.toString())
                    map.putString("ageRange", kakaoUser!!.ageRange.toString())
                    map.putString("birthday", kakaoUser.birthday.toString())
                    map.putString("birthdayType", kakaoUser.birthdayType.toString())
                    map.putString("birthyear", kakaoUser.birthyear.toString())
                    map.putString("gender", kakaoUser.gender.toString())
                    map.putBoolean("isEmailValid", convertValue(kakaoUser.isEmailValid))
                    map.putBoolean("isEmailVerified", convertValue(kakaoUser.isEmailVerified))
                    map.putBoolean("isKorean", convertValue(kakaoUser.isKorean))
                    map.putBoolean("ageRangeNeedsAgreement", convertValue(kakaoUser.ageRangeNeedsAgreement))
                    map.putBoolean("birthdayNeedsAgreement", convertValue(kakaoUser.birthdayNeedsAgreement))
                    map.putBoolean("birthyearNeedsAgreement", convertValue(kakaoUser.birthyearNeedsAgreement))
                    map.putBoolean("emailNeedsAgreement", convertValue(kakaoUser.emailNeedsAgreement))
                    map.putBoolean("genderNeedsAgreement", convertValue(kakaoUser.genderNeedsAgreement))
                    map.putBoolean("isKoreanNeedsAgreement", convertValue(kakaoUser.isKoreanNeedsAgreement))
                    map.putBoolean("phoneNumberNeedsAgreement", convertValue(kakaoUser.phoneNumberNeedsAgreement))
                    map.putBoolean("profileNeedsAgreement", convertValue(kakaoUser.profileNeedsAgreement))
                }
                promise.resolve(map)
                return@me
            }

            promise.reject("RNKakaoLogins", "User is null")
        }
    }

    companion object {
        private const val TAG = "RNKakaoLoginModule"
    }

    init {
        val kakaoAppKey = reactContext.resources.getString(
                reactContext.resources.getIdentifier("kakao_app_key", "string", reactContext.packageName))
        val kakaoCustomSchemeId = reactContext.resources.getIdentifier(
            "kakao_custom_scheme", "string", reactContext.packageName
        )
        val kakaoCustomScheme = if (kakaoCustomSchemeId == 0) null else reactContext.getString(kakaoCustomSchemeId)
        init(
            context = reactContext, 
            appKey = kakaoAppKey,
            customScheme = kakaoCustomScheme
        )
    }
}
