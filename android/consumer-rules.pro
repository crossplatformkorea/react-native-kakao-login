# Consumer ProGuard rules for @react-native-seoul/kakao-login.
#
# AGP applies these automatically to any app that depends on this library once
# code shrinking/obfuscation (minifyEnabled true) is enabled, so consumers no
# longer need to copy them into their own proguard-rules.pro. See issue #438.

# Kakao serializes with kotlinx.serialization (v2-common pulls
# kotlinx-serialization-json, v2-network the retrofit converter). The generated
# $$serializer classes bake the JSON names in as string constants, so model
# classes themselves may be obfuscated — but their fields may not:
# com.kakao.sdk.common.json.GenericEnumSerializer resolves @SerialName through
# enumClass.getDeclaredField(<constant name>), and finds the @UnknownValue
# marker the same way. kotlinx-serialization-core ships its own
# META-INF/proguard rules, so no further serialization keeps are needed here.
-keep class com.kakao.sdk.**.model.* { <fields>; }
-keep interface com.kakao.sdk.** { *; }
-keepattributes Signature,InnerClasses,EnclosingMethod
-keepattributes RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,RuntimeVisibleParameterAnnotations,RuntimeInvisibleParameterAnnotations,AnnotationDefault

# GenericEnumSerializer builds its name map from Class.getEnumConstants(), which
# ART implements by reflectively invoking the enum's static values(). R8 strips
# values() unless something keeps it, and getEnumConstants() then returns null —
# an NPE inside the serializer on me() / shippingAddresses() / serviceTerms() or
# on any API error. AGP's bundled proguard-android.txt does keep enums globally,
# but borrowing that from the consumer's proguardFiles list would leave these
# rules incomplete on their own.
-keepclassmembers enum com.kakao.sdk.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    <fields>;
}

# Legacy: Kakao SDK 2.20.x and earlier serialized with Gson (v2-common 2.20.1
# depends on gson 2.8.9, which ships no rules of its own). This is a no-op on
# the pinned 2.24.x and is kept only for consumers who pin an older
# RNKakaoLogins_kakaoSdkVersion. Scoped to the Kakao packages on purpose — the
# unscoped `-keep class * extends com.google.gson.TypeAdapter` from Kakao's docs
# would pin every TypeAdapter in the *consuming* app as well.
-keep class com.kakao.sdk.** extends com.google.gson.TypeAdapter

# Optional TLS providers referenced by OkHttp. Kakao pins okhttp 4.9.3, whose
# bundled okhttp3.pro predates https://github.com/square/okhttp/pull/6792, so
# without these R8 fails the release build outright with "Missing classes".
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**

# The Kakao SDK pins Retrofit 2.9.0 (com.kakao.sdk:v2-network). Retrofit 2.9.0
# does ship META-INF/proguard/retrofit2.pro and AGP does apply it, but that file
# predates the R8 full-mode hardening added in Retrofit 2.10/2.11. The block
# below backports exactly that delta — most importantly the rules preserving the
# generic return types (Call<AccessTokenResponse>, Call<UserResponse>) of the
# annotated com.kakao.sdk.auth.AuthApi / com.kakao.sdk.user.UserApi service
# interfaces, which R8 full mode (the AGP default since 8.0) otherwise erases.
# Verbatim from Retrofit 2.11.0:
# https://github.com/square/retrofit/blob/2.11.0/retrofit/src/main/resources/META-INF/proguard/retrofit2.pro
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
-keep,allowobfuscation,allowshrinking class retrofit2.Response
