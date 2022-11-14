package com.example.myapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
//import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

//import rohit.tracker.app.activity.TrackerActivity;
//import rohit.tracker.app.util.DataManger;
//import rohit.tracker.app.util.SMSOps;


class CallListenerService extends Service {

    TelephonyManager m_telephonyManager;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        return super.onStartCommand(intent, flags, startID);
    }


//    @Override
//    public IBinder onBind(Intent arg0) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//
//        super.onCreate();
//    }
//
//    @Override
//    public void onStart(Intent intent, int startId) {
//        // TODO Auto-generated method stub
//        m_telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        if(DataManger.getInstance(this).checkTrackerService()){
////           SMSOps.getInstance(this).checkSimChange();
//        }
//        super.onStart(intent, startId);
//    }
//


}
