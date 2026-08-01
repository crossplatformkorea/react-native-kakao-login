package com.dooboolab.kakaologins

import com.facebook.react.bridge.*
import com.kakao.sdk.common.KakaoSdk.init
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import com.kakao.sdk.user.model.UserShippingAddresses
import com.kakao.sdk.auth.TokenManagerProvider
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
    private fun login(nonce: String?, promise: Promise) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(reactContext)) {
            val activity = reactContext.currentActivity

            if (activity == null) {
                // The KakaoTalk login intent needs an Activity to launch from.
                // Bailing out silently here used to leave the JS promise pending
                // forever, which surfaces as "login() never resolves and never
                // throws". Reject instead so the caller can react.
                promise.reject(
                    "RNKakaoLogins",
                    "Current activity is null. Call login() while the app is in the foreground."
                )
                return
            }

            activity.let {
                // `nonce` has to be forwarded here. Dropping it produced an
                // idToken with no `nonce` claim whenever KakaoTalk was installed,
                // so server-side OIDC verification failed on exactly the devices
                // where it normally succeeds — while iOS, which routes a
                // nonce-bearing login to the Kakao Account flow, kept working.
                UserApiClient.instance.loginWithKakaoTalk(it, nonce = nonce) { token, error: Throwable? ->
                    if (error != null) {
                        if (error is AuthError && error.statusCode == 302) {
                            this.loginWithKakaoAccount(null, promise)
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
            // If nonce is provided, use loginWithKakaoAccount (supports OpenID Connect)
            if (!nonce.isNullOrEmpty()) {
                this.loginWithKakaoAccount(nonce, promise)
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
    }

    @ReactMethod
    private fun loginWithKakaoAccount(nonce: String?, promise: Promise) {
        // Use loginWithKakaoAccount for OpenID Connect support (nonce)
        if (!nonce.isNullOrEmpty()) {
            UserApiClient.instance.loginWithKakaoAccount(
                reactContext,
                null, // prompts: List<Prompt>?
                null, // loginHint: String?
                nonce, // nonce: String?
                null, // channelPublicIds: List<String>?
                null  // serviceTerms: List<String>?
            ) { token, error: Throwable? ->
                if (error != null) {
                    promise.reject("RNKakaoLogins", error.message, error)
                    return@loginWithKakaoAccount
                }

                if (token == null) {
                    promise.reject("RNKakaoLogins", "Token is null")
                    return@loginWithKakaoAccount
                }

                val (accessToken, accessTokenExpiresAt, refreshToken, refreshTokenExpiresAt, idToken, tokenScopes) = token
                val map = Arguments.createMap()
                map.putString("accessToken", accessToken)
                map.putString("refreshToken", refreshToken)
                map.putString("idToken", idToken)
                map.putString("accessTokenExpiresAt", dateFormat(accessTokenExpiresAt))
                map.putString("refreshTokenExpiresAt", dateFormat(refreshTokenExpiresAt))
                val scopeArray = Arguments.createArray()
                if (tokenScopes != null) {
                    for (scope in tokenScopes) {
                        scopeArray.pushString(scope)
                    }
                }
                map.putArray("scopes", scopeArray)
                promise.resolve(map)
                return@loginWithKakaoAccount
            }
            return
        }
        
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

    @ReactMethod
    private fun shippingAddresses(promise: Promise) {
        UserApiClient.instance.shippingAddresses { shippingAddresses: UserShippingAddresses?, error: Throwable? ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@shippingAddresses
            }

            if (shippingAddresses != null) {
                val map = Arguments.createMap()
                map.putString("userId", shippingAddresses.userId.toString())
                map.putBoolean("needsAgreement", convertValue(shippingAddresses.needsAgreement))

                val array = Arguments.createArray()
                shippingAddresses.shippingAddresses
                    ?.map { shippingAddress ->
                        Arguments.createMap().apply {
                            putString("id", shippingAddress.id.toString())
                            putString("name", shippingAddress.name.toString())
                            putBoolean("isDefault", convertValue(shippingAddress.isDefault))
                            putString("updatedAt", dateFormat(shippingAddress.updatedAt))
                            putString("type", shippingAddress.type.toString())
                            putString("baseAddress", shippingAddress.baseAddress.toString())
                            putString("detailAddress", shippingAddress.detailAddress.toString())
                            putString("receiverName", shippingAddress.receiverName.toString())
                            putString(
                                "receiverPhoneNumber1",
                                shippingAddress.receiverPhoneNumber1.toString()
                            )
                            putString(
                                "receiverPhoneNumber2",
                                shippingAddress.receiverPhoneNumber2.toString()
                            )
                            putString("zoneNumber", shippingAddress.zoneNumber.toString())
                            putString("zipCode", shippingAddress.zipCode.toString())
                        }
                    }
                    ?.forEach(array::pushMap)
                map.putArray("shippingAddresses", array)

                promise.resolve(map)
                return@shippingAddresses
            }

            promise.reject("RNKakaoLogins", "User is null")
        }
    }

    @ReactMethod
    private fun serviceTerms(promise: Promise) {
        UserApiClient.instance.serviceTerms { userServiceTerms, error ->
            if (error != null) {
                promise.reject("RNKakaoLogins", error.message, error)
                return@serviceTerms
            }

            val result = Arguments.createMap()

            userServiceTerms?.id?.let { userId ->
                result.putDouble("userId", userId.toDouble())
            }

            val serviceTerms = Arguments.createArray()
            userServiceTerms?.serviceTerms?.map {
                Arguments.createMap().apply {
                    putString("tag", it.tag)
                    putBoolean("agreed", it.agreed)
                    putBoolean("required", it.required)
                    putBoolean("revocable", it.revocable)
                    it.agreedAt?.let { agreedAt ->
                      putString("agreedAt", dateFormat(agreedAt))
                    }
                }
            }?.forEach(serviceTerms::pushMap)
            if (serviceTerms.size() > 0) {
                result.putArray("serviceTerms", serviceTerms)
            }

            promise.resolve(result)
        }
    }

    companion object {
        private const val TAG = "RNKakaoLoginModule"
    }

    init {
        val kakaoAppKeyId = reactContext.resources.getIdentifier(
            "kakao_app_key", "string", reactContext.packageName
        )
        // getIdentifier() returns 0 when the resource is missing, and
        // getString(0) then throws a bare Resources$NotFoundException that says
        // nothing about Kakao. Fail with an actionable message instead.
        check(kakaoAppKeyId != 0) {
            "RNKakaoLogins: string resource `kakao_app_key` not found. " +
                "Add <string name=\"kakao_app_key\">{NATIVE_APP_KEY}</string> to " +
                "android/app/src/main/res/values/strings.xml, or pass `kakaoAppKey` " +
                "to the Expo config plugin. " +
                "https://github.com/crossplatformkorea/react-native-kakao-login#android"
        }
        val kakaoAppKey = reactContext.resources.getString(kakaoAppKeyId)
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
