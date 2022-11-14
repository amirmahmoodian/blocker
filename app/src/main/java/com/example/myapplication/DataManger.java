package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DataManger {
    static DataManger instance;
    static SQLiteDatabase db;
    static SQLiteOpenHelper helper;
    static Context con;

    private DataManger() {
        // not accessible
    }

    public static DataManger getInstance(Context con) {
        if (instance == null) {
            instance = new DataManger();
        }
        con = con;
        helper = new DBHelper(con, "TrackerActivity", null, 2);
        db = helper.getWritableDatabase();
        return instance;
    }

    public void updateCallBlackListService(@NonNull Boolean falg) {
        if (falg) {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
            ContentValues val = new ContentValues();
            val.put("ser", "call");
            val.put("stat", "black");
            db.insert("actservice", null, val);
//            Toast.makeText(con, "Black List is on !", Toast.LENGTH_SHORT).show();
        } else {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
        }
    }

    public void updateCallWhiteListService(boolean falg) {
        if (falg) {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
            ContentValues val = new ContentValues();
            val.put("ser", "call");
            val.put("stat", "white");
            db.insert("actservice", null, val);
        } else {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
        }
    }

    public String getActiveCallService() {
        String activeService = "";
        Cursor cur = db.query("actservice", null, "ser='call'", null, null, null, null);
        while (cur.moveToNext()) {
            activeService = cur.getString(1);
        }
        cur.close();
        return activeService;
    }

    public boolean isIncommingBlocked(String incomming) {
        String active_service;
//        Cursor cur = db.query("actservice", null, null, null, null, null, null);
//        while (cur.moveToNext()) {
//            active_service = cur.getString(1);
//        }
//        cur.close();
        active_service=getActiveCallService();
        Boolean whichService = false;
        if (active_service.equals("black")) {
            whichService = isBlackListed(incomming);

        } else if (active_service.equals("white")) {
            whichService = isWhiteListed(incomming);
        }
        return whichService;
    }

    private Boolean isBlackListed(String incomingNumber) {
        Boolean flag = false;

        Cursor cur = db.query(
                "blklst_call",
                null,
                null,
                null,
                null,
                null,
                null);
        Cursor cur2 = db.query(
                "blklst_call_StartWith",
                null,
                null,
                null,
                null,
                null,
                null);
        Cursor cur3 = db.rawQuery("Select * from blklst_call_EndsWith",null);


//        Cursor cur = db.rawQuery("Select * from blklst_call",null);
        while (cur.moveToNext()) {
            System.out.println("%%%%%%%%%%%%%%%%%%% "+incomingNumber+"^^^^^^ "+cur.getString(0)+
                    " @@@@@ "+incomingNumber.equals(cur.getString(0)));
            if (incomingNumber.equals(cur.getString(0))) {
//                flag = true;
                cur.close();
                return true;
            }
            else if(incomingNumber.contains(cur.getString(0))){
            }
        }
        cur.close();
        while (cur2.moveToNext()) {
            System.out.println("%%%%%%%%%%%%%%%%%%% "+incomingNumber+"^^^^^^ "+cur2.getString(0)+
                    " @@@@@ "+incomingNumber.equals(cur2.getString(0)));
            if (incomingNumber.startsWith(cur2.getString(0))) {
//                flag = true;
                cur2.close();
                return true;
            }
        }
        cur2.close();
        while (cur3.moveToNext()) {
            System.out.println("%%%%%%%%%%%%%%%%%%% "+incomingNumber+"^^^^^^ "+cur3.getString(0)+
                    " @@@@@ "+incomingNumber.equals(cur3.getString(0)));
            if (incomingNumber.endsWith(cur3.getString(0))) {
                cur3.close();
                return true;
            }
        }
        cur3.close();
        return false;
    }


    private Boolean isWhiteListed(String incomingNumber) {
        Boolean flag = true;
        Cursor cur = db.query(
                "whitlist_call",
                null,
                null,
                null,
                null,
                null,
                null);
        Cursor cur2 = db.query(
                "whitlist_call_StartWith",
                null,
                null,
                null,
                null,
                null,
                null);

        Cursor cur3 = db.rawQuery("Select * from whitlist_call_EndsWith",null);


//        Cursor cur = db.rawQuery("Select * from blklst_call",null);
        while (cur.moveToNext()) {
            System.out.println("%%%%%%%%%%%%%%%%%%% "+incomingNumber+"^^^^^^ "+cur.getString(0)+
                    " @@@@@ "+incomingNumber.equals(cur.getString(0)));
            if (incomingNumber.equals(cur.getString(0))) {
//                flag = true;
                cur.close();
                return false;
            }
        }
        cur.close();
        while (cur2.moveToNext()) {
            System.out.println("%%%%%%%%%%%%%%%%%%% "+incomingNumber+"^^^^^^ "+cur2.getString(0)+
                    " @@@@@ "+incomingNumber.equals(cur2.getString(0)));
            if (incomingNumber.startsWith(cur2.getString(0))) {
//                flag = true;
                cur2.close();
                return false;
            }
        }
        cur2.close();
        while (cur3.moveToNext()) {
            System.out.println("%%%%%%%%%%%%%%%%%%% "+incomingNumber+"^^^^^^ "+cur3.getString(0)+
                    " @@@@@ "+incomingNumber.equals(cur3.getString(0)));
            if (incomingNumber.endsWith(cur3.getString(0))) {
                cur3.close();
                return false;
            }
        }
        cur3.close();
//        while (cur.moveToNext()) {
//            System.out.println("%%%%%%%%%%%%%%%%%%% "+incomingNumber+"^^^^^^ "+cur.getString(0)+
//                    " @@@@@ "+incomingNumber.equals(cur.getString(0)));
//            if (incomingNumber.equals(cur.getString(0))) {
//                flag = false;
//                break;
//            }
//        }
        return true;
    }

    public boolean checkTrackerService() {
        String onoff = "";
        Cursor cursor =null;
        try {
            db = helper.getWritableDatabase();
            cursor = db.rawQuery("Select * from track_info",null);
            while (cursor.moveToNext()) {
                onoff = cursor.getString(6);
            }
            if (onoff.equals("true")) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            cursor.close();
            Log.d("Error", e.getMessage());
        }
        return false;
    }

    public String checkSimIfChanged() {
        String number = "";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("Select * from track_info",null);
            while (cursor.moveToNext()) {
                number = cursor.getString(0);
                break;
            }
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        } finally {
            cursor.close();
            return number;

        }
    }

    public ArrayList<String> getAllSupportNumbers(){
        ArrayList<String> numbers = new ArrayList<String>();
        Cursor cursor = db.rawQuery("Select * from track_info",null);
        while (cursor.moveToNext()) {
            numbers.add( cursor.getString(1));
            numbers.add( cursor.getString(2));
            numbers.add( cursor.getString(3));
            numbers.add( cursor.getString(4));
            numbers.add( cursor.getString(5));
        }
        return numbers;
    }


    public void addSupportNumbers(String[] numbers,String simSerialNumber){
        ContentValues val = new ContentValues();
        val.put("sim", simSerialNumber);
        for(String num: numbers) {
            val.put("hlp", num);
        }
        db.insert("track_info", null, val);
    }
}
