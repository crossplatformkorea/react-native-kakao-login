#import "RNKakaoLogins.h"
#import <React/RCTLog.h>
#import <KakaoOpenSDK/KakaoOpenSDK.h>

@implementation RNKakaoLogins

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

NSObject* handleNullableString(NSString *_Nullable string)
{
    return string != nil ? string : [NSNull null];
}

NSObject* handleKOBoolean(KOOptionalBoolean boolean)
{
    switch(boolean){
        case KOOptionalBooleanTrue : return @(YES);
        case KOOptionalBooleanFalse: return @(NO);
        case KOOptionalBooleanNull : return [NSNull null];
    }
}

NSObject* handleNullableEnumGender(KOUserGender gender)
{

    if (gender == KOUserGenderMale) {
        return @"MALE";
    }

    if (gender == KOUserGenderFemale) {
        return @"FEMALE";

    }

    return [NSNull null];
}

NSObject* handleNullableEnumAgeRange(KOUserAgeRange ageRange)
{
    if (ageRange == KOUserAgeRangeType0) {
        return @"0~9";
    }
    if (ageRange == KOUserAgeRangeType10 ) {
        return @"10~14";
    }
    if (ageRange == KOUserAgeRangeType15 ) {
        return @"15~19";
    }
    if (ageRange == KOUserAgeRangeType20 ) {
        return @"20~29";
    }
    if (ageRange == KOUserAgeRangeType30 ) {
        return @"30~39";
    }
    if (ageRange == KOUserAgeRangeType40 ) {
        return @"40~49";
    }
    if (ageRange == KOUserAgeRangeType50 ) {
        return @"50~59";
    }
    if (ageRange == KOUserAgeRangeType60 ) {
        return @"60~69";
    }
    if (ageRange == KOUserAgeRangeType70 ) {
        return @"70~79";
    }
    if (ageRange == KOUserAgeRangeType80 ) {
        return @"80~89";
    }
    if (ageRange == KOUserAgeRangeType90 ) {
        return @"90";
    }
    return [NSNull null];
}

NSString* getErrorCode(NSError *error){
    int errorCode = (int)error.code;

    switch(errorCode){
        case KOErrorUnknown:
            return @"E_UNKNOWN";
        case KOErrorCancelled:
            return @"E_CANCELLED_OPERATION";
        case KOErrorOperationInProgress:
            return @"E_IN_PROGRESS_OPERATION";
        case KOErrorTokenNotFound:
            return @"E_TOKEN_NOT_FOUND";
        case KOErrorDeactivatedSession:
            return @"E_DEACTIVATED_SESSION";
        case KOErrorAlreadyLoginedUser:
            return @"E_ALREADY_LOGINED";
        case KOErrorHTTP:
            return @"E_HTTP_ERROR";
        case KOErrorBadResponse:
            return @"E_BAD_RESPONSE";
        case KOErrorNetworkError:
            return @"E_NETWORK_ERROR";
        case KOErrorNotSupported:
            return @"E_NOT_SUPPORTED";
        case KOErrorBadParameter:
            return @"E_BAD_PARAMETER";
        case KOErrorIllegalState:
            return @"E_ILLEGAL_STATE";

        default:
            return @(error.code).stringValue;
    }
}

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(login:(nullable NSArray<NSNumber *> *)authTypes
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    KOSession *session = [KOSession sharedSession];
    [session close]; // ensure old session was closed

    [session openWithCompletionHandler:^(NSError *error) {
        if ([session isOpen]) {
            NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
            [formatter setDateFormat:@"yyyy-MM-dd hh:mm:ss"];

            resolve(@{@"accessToken": session.token.accessToken,
                      @"refreshToken": session.token.refreshToken,
                      @"accessTokenExpiresAt": [formatter stringFromDate: session.token.accessTokenExpiresAt],
                      @"refreshTokenExpiresAt": session.token.refreshTokenExpiresAt != nil ? [formatter stringFromDate: session.token.refreshTokenExpiresAt] : [NSNull null],
                      @"scopes": session.token.scopes != nil ? session.token.scopes : @[]});
        } else {
            RCTLogInfo(@"Error=%@", error);
            reject(getErrorCode(error), error.localizedDescription, error);
        }
    } authTypes:authTypes];
}

RCT_EXPORT_METHOD(logout:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    KOSession *session = [KOSession sharedSession];

    [session logoutAndCloseWithCompletionHandler:^(BOOL success, NSError *error) {
        if(success){
            resolve(@"Logged Out");
        } else {
            RCTLogInfo(@"Error=%@", error);
            reject(getErrorCode(error), error.localizedDescription, error);
        }
    }];
}

RCT_EXPORT_METHOD(getProfile:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    [KOSessionTask userMeTaskWithCompletion:^(NSError *error, KOUserMe* me) {
        if (error) {
            RCTLogInfo(@"Error=%@", error);
            reject(getErrorCode(error), error.localizedDescription, error);
        } else {
            NSDictionary* profile = @{
                @"id": handleNullableString(me.ID),
                @"nickname": handleNullableString(me.account.profile.nickname),
                @"email": handleNullableString(me.account.email),
                @"display_id": handleNullableString(me.account.displayID),
                @"phone_number": handleNullableString(me.account.phoneNumber),
                @"profile_image_url": handleNullableString(me.account.profile.profileImageURL.absoluteString),
                @"thumb_image_url": handleNullableString(me.account.profile.thumbnailImageURL.absoluteString),
                @"is_email_verified": handleKOBoolean(me.account.isEmailVerified),
                @"is_kakaotalk_user": handleKOBoolean(me.account.isKakaotalkUser),
                @"has_signed_up": handleKOBoolean(me.hasSignedUp),
                @"gender": handleNullableEnumGender(me.account.gender),
                @"birthday": handleNullableString(me.account.birthday),
                @"birthyear": handleNullableString(me.account.birthyear),
                @"age_range": handleNullableEnumAgeRange(me.account.ageRange)
            };

            resolve(profile);
        }
    }];
}

RCT_EXPORT_METHOD(unlink:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    [KOSessionTask unlinkTaskWithCompletionHandler:^(BOOL success, NSError *error) {
        if (error) {
            RCTLogInfo(@"Error=%@", error);
            reject(getErrorCode(error), error.localizedDescription, error);
        }
        else {
            resolve(@"Unlinked");
        }
    }];
}

RCT_EXPORT_METHOD(updateScopes:(NSArray<NSString *>*) scopes
                  resolver:(RCTPromiseResolveBlock) resolve
                  rejecter:(RCTPromiseRejectBlock) reject)
{
    KOSession* session = [KOSession sharedSession];

    [session updateScopes: scopes
        completionHandler: ^(NSError *error) {
        if (error) {
            RCTLogInfo(@"Error=%@", error);
            reject(getErrorCode(error), error.localizedDescription, error);
        } else {
            [session openWithCompletionHandler:^(NSError *error) {
                if ([session isOpen]) {
                    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
                    [formatter setDateFormat:@"yyyy-MM-dd hh:mm:ss"];

                    resolve(@{@"accessToken": session.token.accessToken,
                              @"refreshToken": session.token.refreshToken,
                              @"accessTokenExpiresAt": [formatter stringFromDate: session.token.accessTokenExpiresAt],
                              @"refreshTokenExpiresAt": session.token.refreshTokenExpiresAt != nil ? [formatter stringFromDate: session.token.refreshTokenExpiresAt] : [NSNull null],
                              @"scopes": session.token.scopes != nil ? session.token.scopes : @[]});
                } else {
                    RCTLogInfo(@"Error=%@", error);
                    reject(getErrorCode(error), error.localizedDescription, error);
                }
            }];
        }
    }];
}

RCT_EXPORT_METHOD(getTokens:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    KOSession* session = [KOSession sharedSession];
    
    [KOSessionTask accessTokenInfoTaskWithCompletionHandler:^(KOAccessTokenInfo *accessTokenInfo, NSError *error) {
        if (error) {
            RCTLogInfo(@"Error=%@", error);
            reject(getErrorCode(error), error.localizedDescription, error);
        } else {
            resolve(@{@"accessToken": session.token.accessToken,
                     @"refreshToken": session.token.refreshToken});
        }
    }];
}

@end
