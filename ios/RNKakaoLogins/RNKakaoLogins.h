//
//  RNKakaoLogins.h
//  RNKakaoLogins
//
//  Created by hyochan on 2021/03/25.
//  Copyright © 2021 Facebook. All rights reserved.
//

@class RNKakaoLogins;

@interface RNKakaoLogins : NSObject
- (RNKakaoLogins *)returnSwiftClassInstance;
+ (BOOL)isKakaoTalkLoginUrl:(NSURL *)url;
+ (BOOL)handleOpenUrl:(NSURL *)url;
@end
