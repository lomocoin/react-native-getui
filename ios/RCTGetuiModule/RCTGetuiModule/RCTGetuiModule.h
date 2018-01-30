//
//  RCTGetuiModule.h
//  RCTGetuiModule
//
//  Created by admin on 17/2/27.
//  Copyright © 2017年 getui. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

#import "GeTuiSdk.h"

#define GT_DID_RECEIVE_REMOTE_NOTIFICATION @"GtDidReciveRemoteNotification"
#define GT_DID_CLICK_NOTIFICATION @"GtDidClickNotification"
#define GT_DID_REGISTE_CLIENTID @"GtDidRegisteClient"

@interface RCTGetuiModule : RCTEventEmitter <RCTBridgeModule>

@end

