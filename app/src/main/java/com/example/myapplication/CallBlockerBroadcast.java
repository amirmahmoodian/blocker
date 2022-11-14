package com.example.myapplication;

import java.lang.reflect.Method;


import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.MODIFY_PHONE_STATE;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_BOOT_COMPLETED;
import static android.Manifest.permission.SEND_SMS;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

//import rohit.tracker.app.util.DataManger;

public class CallBlockerBroadcast extends BroadcastReceiver {


    private static final int PERMISSION_REQUEST_CODE = 1;
    private TelephonyManager m_telephonyManager;
    private ITelephony m_telephonyService;

    private AudioManager m_audioManager;
    Context context;
    private Activity MainActivity;


    public void onReceive(Context context, Intent intent) {

        this.context = context;
        m_telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = null;
            c = Class.forName(m_telephonyManager.getClass().getName());
            Method m = null;
            m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            m_telephonyService = (ITelephony) m.invoke(m_telephonyManager);
            m_audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            String incomingNumber = intent.getStringExtra(m_telephonyManager.EXTRA_INCOMING_NUMBER);
            System.out.println("***********"+incomingNumber);

            Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();

//            if (PhoneStateListener.LISTEN_CALL_STATE) {
//                case TelephonyManager.CALL_STATE_RINGING:
                    boolean whichService = DataManger.getInstance(context).isIncommingBlocked(incomingNumber.substring(0, incomingNumber.length()));
                    if (whichService) // if incoming Number need to be blocked
                    {
                        m_audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        m_telephonyService.endCall();
                    }
//                    break;
//                default:
//                    break;
//            }

//            m_telephonyManager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
//            incomingNumber = getIntent().getStringExtra(m_telephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
//                    boolean whichService = DataManger.getInstance(context).isIncommingBlocked(incomingNumber.substring(incomingNumber.length() - 10, incomingNumber.length()));
//                    if (whichService) // if incoming Number need to be blocked
//                    {
//                        m_audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                        m_telephonyService.endCall();
//                    }
                    break;
                default:
                    break;
            }
        }


    }
}
   