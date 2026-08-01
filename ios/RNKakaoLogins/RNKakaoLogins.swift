//
//  RNKakaoLogins.swift
//  RNKakaoLogins
//
//  Created by hyochan on 2021/03/18.
//  Copyright © 2021 Facebook. All rights reserved.
//

import Foundation

import KakaoSDKCommon
import KakaoSDKAuth
import KakaoSDKUser

@objc(RNKakaoLogins)
class RNKakaoLogins: NSObject {

    public override init() {
        super.init()

        // `appKey!` used to force-unwrap here, so an app that installed the pod
        // without adding `KAKAO_APP_KEY` to Info.plist crashed the moment React
        // Native instantiated this module — at launch, with no usable message.
        // Log and skip initialization instead; the Kakao SDK then reports
        // `SdkError.ClientFailed(.MustInitAppKey)` from the first API call.
        guard let appKey = Bundle.main.object(forInfoDictionaryKey: "KAKAO_APP_KEY") as? String,
              !appKey.isEmpty else {
            NSLog("""
            [RNKakaoLogins] `KAKAO_APP_KEY` is missing from Info.plist, so the Kakao SDK was not \
            initialized. Add `KAKAO_APP_KEY` (your native app key) to Info.plist. \
            https://github.com/crossplatformkorea/react-native-kakao-login#ios
            """)
            return
        }

        if let customScheme = Bundle.main.object(forInfoDictionaryKey: "KAKAO_APP_SCHEME") as? String,
           !customScheme.isEmpty {
            KakaoSDK.initSDK(appKey: appKey, customScheme: customScheme)
        } else {
            KakaoSDK.initSDK(appKey: appKey)
        }
    }

    @objc
    static func requiresMainQueueSetup() -> Bool {
      return true
    }

    /// Turns a Kakao SDK error into a message that says what actually happened.
    ///
    /// `SdkError` is a plain Swift enum and does not adopt `LocalizedError`, so
    /// `localizedDescription` degrades to Foundation's generic
    /// "The operation couldn't be completed. (KakaoSDKCommon.SdkError error 2.)"
    /// — where the trailing number is only the enum case index (0 ClientFailed,
    /// 1 ApiFailed, 2 AuthFailed, 3 AppsFailed) and the reason is lost. Unwrap
    /// the associated values instead so callers see e.g.
    /// "AuthFailed(AccessDenied): ...".
    private static func describe(_ error: Error) -> String {
        guard let sdkError = error as? SdkError else {
            return error.localizedDescription
        }

        switch sdkError {
        case .ClientFailed(let reason, let errorMessage):
            return "ClientFailed(\(reason)): \(errorMessage ?? "no message")"
        case .ApiFailed(let reason, let errorInfo):
            return "ApiFailed(\(reason)): \(errorInfo?.msg ?? "no message")"
        case .AuthFailed(let reason, let errorInfo):
            return "AuthFailed(\(reason)): \(errorInfo?.errorDescription ?? "no message")"
        case .AppsFailed(let reason, let errorInfo):
            return "AppsFailed(\(reason)): \(errorInfo?.errorMsg ?? "no message")"
        }
    }

    @objc(isKakaoTalkLoginUrl:)
    public static func isKakaoTalkLoginUrl(url:URL) -> Bool {

        let appKey = try? KakaoSDK.shared.appKey();

        if (appKey != nil) {
            return AuthApi.isKakaoTalkLoginUrl(url)
        }
        return false
    }

    @objc(handleOpenUrl:)
    public static func handleOpenUrl(url:URL) -> Bool {
        return AuthController.handleOpenUrl(url: url)
    }

    @objc(login:resolver:rejecter:)
    func login(_ nonce: String?,
                resolver resolve: @escaping RCTPromiseResolveBlock,
                rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss";
            
            // If nonce is provided, use loginWithKakaoAccount (supports OpenID Connect)
            // Otherwise, try KakaoTalk login first, then fallback to KakaoAccount
            if (UserApi.isKakaoTalkLoginAvailable() && (nonce == nil || nonce!.isEmpty)) {
                UserApi.shared.loginWithKakaoTalk {(oauthToken, error) in
                    if let error = error {
                        reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                    }
                    else {
                        resolve([
                            "accessToken": oauthToken?.accessToken ?? "",
                            "refreshToken": oauthToken?.refreshToken ?? "" as Any,
                            "idToken": oauthToken?.idToken ?? "",
                            "accessTokenExpiresAt": dateFormatter.string(from: oauthToken!.expiredAt),
                            "refreshTokenExpiresAt": dateFormatter.string(from: oauthToken!.refreshTokenExpiredAt),
                            "scopes": oauthToken?.scopes ?? "",
                        ])
                    }
                }
            } else {
                // Use loginWithKakaoAccount for OpenID Connect support (nonce)
                UserApi.shared.loginWithKakaoAccount(prompts: nil, loginHint: nil, nonce: nonce) {(oauthToken, error) in
                    if let error = error {
                        reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                    }
                    else {
                        resolve([
                            "accessToken": oauthToken?.accessToken ?? "",
                            "refreshToken": oauthToken?.refreshToken ?? "" as Any,
                            "idToken": oauthToken?.idToken ?? "",
                            "accessTokenExpiresAt": dateFormatter.string(from: oauthToken!.expiredAt),
                            "refreshTokenExpiresAt": dateFormatter.string(from: oauthToken!.refreshTokenExpiredAt),
                            "scopes": oauthToken?.scopes ?? "",
                        ]);
                    }
                }
            }
        }
    }

    @objc(loginWithKakaoAccount:resolver:rejecter:)
    func loginWithKakaoAccount(_ nonce: String?,
                                resolver resolve: @escaping RCTPromiseResolveBlock,
                                rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            
            // Use loginWithKakaoAccount for OpenID Connect support (nonce)
            UserApi.shared.loginWithKakaoAccount(prompts: nil, loginHint: nil, nonce: nonce) {(oauthToken, error) in
                if let error = error {
                    reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                }
                else {
                    resolve([
                        "accessToken": oauthToken?.accessToken ?? "",
                        "refreshToken": oauthToken?.refreshToken ?? "" as Any,
                        "idToken": oauthToken?.idToken ?? "",
                        "accessTokenExpiresAt": dateFormatter.string(from: oauthToken!.expiredAt),
                        "refreshTokenExpiresAt": dateFormatter.string(from: oauthToken!.refreshTokenExpiredAt),
                        "scopes": oauthToken?.scopes ?? "",
                    ]);
                }
            }
        }
    }

    @objc(logout:rejecter:)
    func logout(_ resolve: @escaping RCTPromiseResolveBlock,
               rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            UserApi.shared.logout {(error) in
                if let error = error {
                    reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                }
                else {
                    resolve("Successfully logged out")
                }
            }
        }
    }

    @objc(unlink:rejecter:)
    func unlink(_ resolve: @escaping RCTPromiseResolveBlock,
               rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            UserApi.shared.unlink {(error) in
                if let error = error {
                    reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                }
                else {
                    resolve("Successfully unlinked")
                }
            }
        }
    }

    @objc(getAccessToken:rejecter:)
    func getAccessToken(_ resolve: @escaping RCTPromiseResolveBlock,
               rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            UserApi.shared.accessTokenInfo {(accessTokenInfo, error) in
                if let error = error {
                    reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                }
                else {
                    resolve([
                        "accessToken": TokenManager.manager.getToken()?.accessToken,
                        "expiresIn": accessTokenInfo?.expiresIn,
                    ])
                }
            }
        }
    }

    @objc(getProfile:rejecter:)
    func getProfile(_ resolve: @escaping RCTPromiseResolveBlock,
               rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            UserApi.shared.me() {(user, error) in
                if let error = error {
                    reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                }
                else {
                    resolve([
                        "id": user?.id as Any,
                        "name": user?.kakaoAccount?.name as Any,
                        "email": user?.kakaoAccount?.email as Any,
                        "nickname": user?.kakaoAccount?.profile?.nickname as Any,
                        "profileImageUrl": user?.kakaoAccount?.profile?.profileImageUrl?.absoluteString as Any,
                        "thumbnailImageUrl": user?.kakaoAccount?.profile?.thumbnailImageUrl?.absoluteString as Any,
                        "phoneNumber": user?.kakaoAccount?.phoneNumber as Any,
                        "ageRange": user?.kakaoAccount?.ageRange?.rawValue as Any,
                        "birthday": user?.kakaoAccount?.birthday as Any,
                        "birthdayType": user?.kakaoAccount?.birthdayType as Any,
                        "birthyear": user?.kakaoAccount?.birthyear as Any,
                        "gender": user?.kakaoAccount?.gender?.rawValue as Any,
                        "isEmailValid": user?.kakaoAccount?.isEmailValid as Any,
                        "isEmailVerified": user?.kakaoAccount?.isEmailVerified as Any,
                        "isKorean": user?.kakaoAccount?.isKorean as Any,
                        "ageRangeNeedsAgreement": user?.kakaoAccount?.ageRangeNeedsAgreement as Any,
                        "birthdayNeedsAgreement": user?.kakaoAccount?.birthdayNeedsAgreement as Any,
                        "birthyearNeedsAgreement": user?.kakaoAccount?.birthyearNeedsAgreement as Any,
                        "emailNeedsAgreement": user?.kakaoAccount?.emailNeedsAgreement as Any,
                        "genderNeedsAgreement": user?.kakaoAccount?.genderNeedsAgreement as Any,
                        "isKoreanNeedsAgreement": user?.kakaoAccount?.isKoreanNeedsAgreement as Any,
                        "phoneNumberNeedsAgreement": user?.kakaoAccount?.phoneNumberNeedsAgreement as Any,
                        "profileNeedsAgreement": user?.kakaoAccount?.profileNeedsAgreement as Any,
                    ])
                }
            }
        }
    }

    @objc(shippingAddresses:rejecter:)
    func shippingAddresses(_ resolve: @escaping RCTPromiseResolveBlock,
               rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss";

            UserApi.shared.shippingAddresses() {(shippingAddresses, error) in
                if let error = error {
                    reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                }
                else {
                    resolve([
                        "userId": shippingAddresses?.userId as Any,
                        "needsAgreement": shippingAddresses?.needsAgreement as Any,
                        "shippingAddresses": shippingAddresses?.shippingAddresses?.map { shippingAddress in [
                            "id": shippingAddress.id as Any,
                            "name": shippingAddress.name as Any,
                            "isDefault": shippingAddress.isDefault as Any,
                            "updatedAt": dateFormatter.string(from: shippingAddress.updatedAt!) as Any,
                            "type": shippingAddress.type?.rawValue as Any,
                            "baseAddress": shippingAddress.baseAddress as Any,
                            "detailAddress": shippingAddress.detailAddress as Any,
                            "receiverName": shippingAddress.receiverName as Any,
                            "receiverPhoneNumber1": shippingAddress.receiverPhoneNumber1 as Any,
                            "receiverPhoneNumber2": shippingAddress.receiverPhoneNumber2 as Any,
                            "zoneNumber": shippingAddress.zoneNumber as Any,
                            "zipCode": shippingAddress.zipCode as Any,
                        ]} as Any,
                    ])
                }
            }
        }
    }

    @objc(serviceTerms:rejecter:)
    func serviceTerms(_ resolve: @escaping RCTPromiseResolveBlock,
                rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            UserApi.shared.serviceTerms {(userServiceTerms, error) in
                if let error = error {
                    reject("RNKakaoLogins", RNKakaoLogins.describe(error), error)
                }
                else {
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"

                    var result: [String: Any] = [:]

                    if let useId = userServiceTerms?.id {
                        result["userId"] = useId
                    }

                    if let serviceTerms = userServiceTerms?.serviceTerms {
                        result["serviceTerms"] = serviceTerms.map {
                            var terms = [
                                "tag": $0.tag,
                                "agreed": $0.agreed,
                                "required": $0.required,
                                "revocable": $0.revocable
                            ]

                            if let agreedAt = $0.agreedAt {
                                terms["agreedAt"] = dateFormatter.string(from: agreedAt)
                            }

                            return terms
                        }
                    }

                    resolve(result)
                }
            }
        }
    }
}
