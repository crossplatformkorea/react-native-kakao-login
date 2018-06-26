
#import "RNKakaoLogins.h"
#import <React/RCTLog.h>

@implementation RNKakaoLogins

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(login:(RCTResponseSenderBlock)callback) {
    callback(@[[NSNull null], @"login"]);
}

RCT_EXPORT_METHOD(logout:(RCTResponseSenderBlock)callback) {
    callback(@[[NSNull null], @"logout"]);
}

RCT_EXPORT_METHOD(getProfile:(RCTResponseSenderBlock)callback) {
    callback(@[[NSNull null], @"getProfile"]);
}

@end
