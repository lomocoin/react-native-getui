package com.getui.reactnativegetui;

/**
 * Created by zhourh on 2017/3/7.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class PushIntentService extends GTIntentService {

    public PushIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        GetuiLogger.log("onReceiveServicePid = " +  pid);


    }

    @Override
    public void onReceiveClientId(Context context, String clientId) {
        GetuiLogger.log("onReceiveClientId = " + clientId);
        WritableMap param = Arguments.createMap();
        param.putString("type", GetuiModule.EVENT_TYPE_RECEIVE_CID);
        param.putString("cid", clientId);
        GetuiModule.sendEvent(GetuiModule.EVENT_RECEIVE_REMOTE_NOTIFICATION,  param);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String message = new String(msg.getPayload());
        GetuiLogger.log("onReceiveMessageData msg = " + message);
        WritableMap param = Arguments.createMap();
        param.putString("type", GetuiModule.EVENT_TYPE_PAYLOAD);
        param.putString("payload", message);
        try {
            GetuiModule.sendOtcNotification(context,message);
        }catch (Exception e){ }
        GetuiModule.sendEvent(GetuiModule.EVENT_RECEIVE_REMOTE_NOTIFICATION, param );
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        GetuiLogger.log("onReceiveOnlineState online = " + online);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        GetuiLogger.log("onReceiveCommandResult cmdMessage action = " + cmdMessage.getAction());
        GetuiModule.sendEvent(GetuiModule.EVENT_RECEIVE_REMOTE_NOTIFICATION,
                GetuiModule.EVENT_TYPE_CMD, "action", String.valueOf(cmdMessage.getAction()));
    }
}