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

RCT_EXPORT_METHOD(login:(RCTPromiseResolveBlock)resolve
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
                      @"refreshTokenExpiresAt": [formatter stringFromDate: session.token.refreshTokenExpiresAt], 
                      @"scopes": session.token.scopes});
        } else {
            RCTLogInfo(@"Error=%@", error);
            reject(getErrorCode(error), error.localizedDescription, error);
        }
    }];
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
                @"birthday": handleNullableString(me.account.birthday)
            };
            
            resolve(profile);
        }
    }];
}

@end
