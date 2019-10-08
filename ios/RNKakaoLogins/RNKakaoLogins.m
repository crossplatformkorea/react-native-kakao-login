#import "RNKakaoLogins.h"
#import <React/RCTLog.h>
#import <KakaoOpenSDK/KakaoOpenSDK.h>

@implementation RNKakaoLogins

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

NSObject* handleNullableString(NSString* _Nullable string)
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

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(login:(RCTResponseSenderBlock)callback) {
    KOSession *session = [KOSession sharedSession];
    
    // ensure old session was closed
    [session close];
    
    [session openWithCompletionHandler:^(NSError *error) {
        if ([session isOpen]) {
            callback(@[[NSNull null], @{@"token": session.token.accessToken}]);
        } else {
            RCTLogInfo(@"Error=%@", error);
            callback(@[@"SignIn failed.\n", [NSNull null]]);
        }
    }];
}

RCT_EXPORT_METHOD(logout:(RCTResponseSenderBlock)callback) {
    KOSession *session = [KOSession sharedSession];
    
    [session logoutAndCloseWithCompletionHandler:^(BOOL success, NSError *error) {
        if(success){
            callback(@[[NSNull null], [NSNull null]]);
        } else {
            RCTLogInfo(@"Error=%@", error);
            callback(@[@"Logout failed.\n", [NSNull null]]);
        }
    }];
}

RCT_EXPORT_METHOD(getProfile:(RCTResponseSenderBlock)callback) {
    [KOSessionTask userMeTaskWithCompletion:^(NSError *error, KOUserMe* me) {
        if (error) {
            RCTLogInfo(@"Error=%@", error);
            callback(@[@"Error while getting profile.", [NSNull null]]);
            return;
        }
        
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
                                  };
        
        callback(@[[NSNull null], profile]);
    }];
}

@end
