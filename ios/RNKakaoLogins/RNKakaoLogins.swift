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
        let appKey: String? = Bundle.main.object(forInfoDictionaryKey: "KAKAO_APP_KEY") as? String
        KakaoSDKCommon.initSDK(appKey: appKey!)
    }

    @objc
    static func requiresMainQueueSetup() -> Bool {
      return true
    }

    @objc(isKakaoTalkLoginUrl:)
    public static func isKakaoTalkLoginUrl(url:URL) -> Bool {

        let appKey = try? KakaoSDKCommon.shared.appKey();

        if (appKey != nil) {
            return AuthApi.isKakaoTalkLoginUrl(url)
        }
        return false
    }

    @objc(handleOpenUrl:)
    public static func handleOpenUrl(url:URL) -> Bool {
        return AuthController.handleOpenUrl(url: url)
    }

    @objc(login:rejecter:)
    func login(_ resolve: @escaping RCTPromiseResolveBlock,
                rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss";

            if (UserApi.isKakaoTalkLoginAvailable()) {
                UserApi.shared.loginWithKakaoTalk {(oauthToken, error) in
                    if let error = error {
                        reject("RNKakaoLogins", error.localizedDescription, nil)
                    }
                    else {
                        resolve([
                            "accessToken": oauthToken?.accessToken ?? "",
                            "refreshToken": oauthToken?.refreshToken ?? "" as Any,
                            "accessTokenExpiresAt": dateFormatter.string(from: oauthToken!.expiredAt),
                            "refreshTokenExpiresAt": dateFormatter.string(from: oauthToken!.refreshTokenExpiredAt),
                            "scopes": oauthToken?.scopes ?? "",
                        ])
                    }
                }
            } else {
                UserApi.shared.loginWithKakaoAccount {(oauthToken, error) in
                    if let error = error {
                        reject("RNKakaoLogins", error.localizedDescription, nil)
                    }
                    else {
                        resolve([
                            "accessToken": oauthToken?.accessToken ?? "",
                            "refreshToken": oauthToken?.refreshToken ?? "" as Any,
                            "accessTokenExpiresAt": dateFormatter.string(from: oauthToken!.expiredAt),
                            "refreshTokenExpiresAt": dateFormatter.string(from: oauthToken!.refreshTokenExpiredAt),
                            "scopes": oauthToken?.scopes ?? "",
                        ]);
                    }
                }
            }
        }
    }

    @objc(loginWithKakaoAccount:rejecter:)
    func loginWithKakaoAccount(_ resolve: @escaping RCTPromiseResolveBlock,
                rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss";
            UserApi.shared.loginWithKakaoAccount {(oauthToken, error) in
                if let error = error {
                    reject("RNKakaoLogins", error.localizedDescription, nil)
                }
                else {
                    resolve([
                        "accessToken": oauthToken?.accessToken ?? "",
                        "refreshToken": oauthToken?.refreshToken ?? "" as Any,
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
                    reject("RNKakaoLogins", error.localizedDescription, nil)
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
                    reject("RNKakaoLogins", error.localizedDescription, nil)
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
                    reject("RNKakaoLogins", error.localizedDescription, nil)
                }
                else {
                    resolve([
                        "accessToken": accessTokenInfo?.id,
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
                    reject("RNKakaoLogins", error.localizedDescription, nil)
                }
                else {
                    resolve([
                        "id": user?.id as Any,
                        "email": user?.kakaoAccount?.email as Any,
                        "nickname": user?.kakaoAccount?.profile?.nickname as Any,
                        "profileImageUrl": user?.kakaoAccount?.profile?.profileImageUrl as Any,
                        "thumbnailImageUrl": user?.kakaoAccount?.profile?.thumbnailImageUrl as Any,
                        "phoneNumber": user?.kakaoAccount?.phoneNumber as Any,
                        "ageRange": user?.kakaoAccount?.ageRange as Any,
                        "birthday": user?.kakaoAccount?.birthday as Any,
                        "birthdayType": user?.kakaoAccount?.birthdayType as Any,
                        "birthyear": user?.kakaoAccount?.birthyear as Any,
                        "gender": user?.kakaoAccount?.gender as Any,
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
}
