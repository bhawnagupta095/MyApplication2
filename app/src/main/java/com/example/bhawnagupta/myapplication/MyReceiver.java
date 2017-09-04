package com.example.bhawnagupta.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private String incomingNumber;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Bundle bundle = intent.getExtras();

        if (action.equals("android.intent.action.PHONE_STATE")){
           String state = bundle.getString(TelephonyManager.EXTRA_STATE);
           if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){

               String incomingNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

               displayToast(context,"Incoming Call From"+incomingNumber);
              // sendAutoReply(incomingNumber);

           }else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){


           }else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){

           }

        }
        else if (action.equals("android.provider.Telephony.SMS_RECEIVED")){
            displayToast(context,"Incoming Message");

            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] smsMessage = new SmsMessage[pdus.length];

            for (int i=0; i<pdus.length; i++){

                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){

                    String format =  bundle.getString("format");
                    smsMessage[i] = SmsMessage.createFromPdu((byte[]) (pdus[i]),format);
                } else{

                    smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                incomingNumber = smsMessage[i].getOriginatingAddress();
                String incomingMsg = smsMessage[i].getMessageBody();
                displayToast(context,"Incoming Message Fom"+incomingNumber);
            }



        }

    }

    private void displayToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    /*private void sendAutoReply(String number){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number,null,"Sorry i am busy",null,null)
        sendAutoReply(incomingNumber);*/

    }




