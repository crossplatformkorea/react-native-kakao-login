#import "RNKakaoLogins.h"
#import <React/RCTLog.h>
#import <KakaoOpenSDK/KakaoOpenSDK.h>

@implementation RNKakaoLogins

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (NSString *) quotedStringOrNull: (NSString *)originalString
{
    return !!originalString ? [NSString stringWithFormat: @"\"%@\"", originalString] : @"null";
}

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(login:(RCTResponseSenderBlock)callback) {
    KOSession *session = [KOSession sharedSession];

    // ensure old session was closed
    [session close];

    [session openWithCompletionHandler:^(NSError *error) {
        if ([session isOpen]) {
            NSString* token = session.token.accessToken;
            NSString* result = [NSString stringWithFormat:@"{\"token\": \"%@\"}", token];

            callback(@[[NSNull null], result]);
        } else {
            RCTLogInfo(@"Error=%@", error);
            callback(@[@"SignIn failed.\n", [NSNull null]]);
        }
    }];
}

RCT_EXPORT_METHOD(logout:(RCTResponseSenderBlock)callback) {
    [[KOSession sharedSession] close];
    callback(@[[NSNull null], @"logged out"]);
}

RCT_EXPORT_METHOD(getProfile:(RCTResponseSenderBlock)callback) {
    [KOSessionTask userMeTaskWithCompletion:^(NSError *error, KOUserMe* result) {
        if (error) {
            RCTLogInfo(@"Error=%@", error);
            callback(@[@"Error while getting profile.", [NSNull null]]);

            return;
        }

        NSString* profile = [NSString stringWithFormat:
            @"{\"id\": %@,\
            \"nickname\": %@,\
            \"email\": %@,\
            \"display_id\": %@,\
            \"phone_number\": %@,\
            \"email_verified\": %@,\
            \"kakaotalk_user\": %@,\
            \"profile_image_path\": %@,\
            \"thumb_image_path\": %@,\
            \"has_signed_up\": %@}",
            [self quotedStringOrNull: result.ID],
            [self quotedStringOrNull: result.nickname],
            [self quotedStringOrNull: result.account.email],
            [self quotedStringOrNull: result.account.displayID],
            [self quotedStringOrNull: result.account.phoneNumber],
            result.account.isEmailVerified ? @"true" : @"false",
            result.account.isKakaotalkUser ? @"true" : @"false",
            [self quotedStringOrNull: [result.profileImageURL absoluteString]],
            [self quotedStringOrNull: [result.thumbnailImageURL absoluteString]],
            result.hasSignedUp ? @"true" : @"false"
        ];

        callback(@[[NSNull null], profile]);
    }];
}

@end
