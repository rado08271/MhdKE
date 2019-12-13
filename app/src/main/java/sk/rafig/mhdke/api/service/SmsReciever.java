package sk.rafig.mhdke.api.service;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import sk.rafig.mhdke.util.SmsSpecs;

public class SmsReciever extends BroadcastReceiver {

    private static final String TAG = SmsReciever.class.getSimpleName();
    private static final String pdu_type = "pdus";
    private OnReceiveListener onReceiveListener;

    public SmsReciever() {
    }


//    public SmsReciever(String serviceProviderNumber, String serviceProviderSmsCondition) {
//        this.serviceProviderNumber = serviceProviderNumber;
//        this.serviceProviderSmsCondition = serviceProviderSmsCondition;
//    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
//            String smsSender = "";
//            String smsBody = "";
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//                for (SmsMessage smsMessage: Telephony.Sms.Intents.getMessagesFromIntent(intent)){
//                    smsSender = smsMessage.getDisplayOriginatingAddress();
//                    smsBody += smsMessage.getMessageBody();
//                }
//            } else {
//                Bundle smsBundle = intent.getExtras();
//                if (smsBundle != null) {
//                    Object[] pdus = (Object[]) smsBundle.get("pdus");
//
//                    if( pdus == null){
//                        //no pdu key!!!!
//                        return;
//                    }
//
//                    SmsMessage[] messages = new SmsMessage[pdus.length];
//                    for (int i = 0; i < messages.length; i++){
//                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
//                        smsBody += messages[i].getMessageBody();
//                    }
//                    smsSender = messages[0].getOriginatingAddress();
//                }
//            }
//
//            if ( smsSender.equals(serviceProviderNumber) && smsBody.startsWith(serviceProviderSmsCondition)) {
//                if (onReceiveListener != null) {
//                    onReceiveListener.onTextReceived(smsBody);
//                }
//            }
//        }
//    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);

        if (pdus != null) {
            boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                if (isVersionM) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                String number = msgs[i].getOriginatingAddress();
                String body = msgs[i].getMessageBody();
                // Log and display the SMS message.

                if (SmsSpecs.INSTANCE.getServiceProviderNumber().equals(number)) {
                    if (onReceiveListener != null) {
                        onReceiveListener.onTextReceived(body);
                    } else {
                        Log.d(TAG, "Problem with receiving msg");
                    }
                }
            }
        }
    }

    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    public interface OnReceiveListener {
        //maybe add a phone number as well or sthg like that...
        void onTextReceived(String text);
    }
}
