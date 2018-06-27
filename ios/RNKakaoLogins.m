
#import "RNKakaoLogins.h"
#import <React/RCTLog.h>
#import <KakaoOpenSDK/KakaoOpenSDK.h>

@implementation RNKakaoLogins

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(login:(RCTResponseSenderBlock)callback) {
    KOSession *session = [KOSession sharedSession];
    // ensure old session was closed
    // [session close];
    
    [session openWithCompletionHandler:^(NSError *error) {
        if ([session isOpen]) {
            // signIn success
            NSString* token = session.accessToken;
            NSString* result = [NSString stringWithFormat:@"{token: %@}", token];
            callback(@[[NSNull null], result]);
        } else {
            // failed
            RCTLogInfo(@"error=%@", error);
            callback(@[@"signIn failed.\n", [NSNull null]]);
        }
    }];
}

RCT_EXPORT_METHOD(logout:(RCTResponseSenderBlock)callback) {
    [[KOSession sharedSession] close];
    callback(@[[NSNull null], @"logged out"]);
}

RCT_EXPORT_METHOD(getProfile:(RCTResponseSenderBlock)callback) {
    [KOSessionTask meTaskWithCompletionHandler:^(KOUser* result, NSError *error) {
        if (result) {
            // success
//            if (result.email) {
//                RCTLogInfo(@"email=%@", result.email);
//            } else {
//                // disagreed
//            }
            NSString* json = [NSString stringWithFormat:@"{id: %@, nickname: %@, email: %@, display_id: %@, phone_number: %@, email_verified: %@, kakaotalk_user: %@, profile_image_path: %@, thumb_image_path: %@, has_signed_up: %@}",
                              result.ID,
                              [result propertyForKey:KOUserNicknamePropertyKey],
                              result.email,
                              @"no display_id in ios",
                              @"no phonenumber in ios",
                              [result propertyForKey:KOUserIsVerifiedEmailPropertyKey],
                              @"no isKakaoUser in ios",
                              [result propertyForKey:KOUserProfileImagePropertyKey],
                              [result propertyForKey:KOUserThumbnailImagePropertyKey],
                              @"no hasSignedUp in ios"
            ];
            callback(@[[NSNull null], json]);
        } else {
            // failed
            callback(@[@"Get profile error.", [NSNull null]]);
        }
    }];
}

@end
