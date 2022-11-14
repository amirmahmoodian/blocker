package com.example.myapplication;

import java.util.ArrayList;


import android.app.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

//import rohit.tracker.app.R;
//import rohit.tracker.app.util.DBHelper;

public class EditBlackListActivityFull extends AppCompatActivity {

    private static final String LOG_TAG = "";
    ArrayList<String> no;
    ArrayList<String> no2;

    ArrayAdapter<String> lis;
    SQLiteDatabase db;
    ListView sp;
    EditText phno;
    EditText StartWith;
    EditText EndsWith;
    String delitem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Call Blocker");

        sp = findViewById(R.id.listView1);
        phno = findViewById(R.id.editText1);
//        StartWith = findViewById(R.id.editText12);
//        EndsWith = findViewById(R.id.editText13);


        SQLiteOpenHelper helper = new DBHelper(this, "TrackerActivity", null, 2);
        db = helper.getWritableDatabase();

//        Cursor cur = db.query(
//                "blklst_call",
//                null,
//                null,
//                null,
//                null,
//                null,
//                null);


//        Cursor cur = db.rawQuery("Select * from blklst_call",null);
//        while (cur.moveToNext()) {
//            System.out.println("%%%%%%%%%%%%%%%%%%% "+"^^^^^^ "+cur.getString(0));
//
//        }
//        cur.close();


        sp.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                delitem = String.valueOf(sp.getItemAtPosition(arg2));
            }
        });

        no = new ArrayList<String>();
        no2 = new ArrayList<String>();

        popup();
//        popup2();
    }

    public void popup() {
        no.clear();
        try {
            if (db != null) {
                Cursor c = db.query(
                        "blklst_call",
                        null,
                        null,
                        null,
                        null,
                        null,
                        "ph_no desc");
                while (c.moveToNext()) {
                    String num = c.getString(0);
                    no.add(num);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_expandable_list_item_1, no);
                    sp.setAdapter(adapter);
                }
                c.close();
            } else {

                Toast.makeText(getApplicationContext(), "blank", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.d("Rohit error", e.getMessage() + e.getStackTrace());
            Toast.makeText(getApplicationContext(), "popup : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void popup2() {
        no2.clear();
        try {
            if (db != null) {
                Cursor c = db.query(
                        "blklst_call_StartWith",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                while (c.moveToNext()) {
                    String num = c.getString(0);
                    no2.add(num);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_expandable_list_item_1, no2);
                    sp.setAdapter(adapter);
                }
                c.close();
            } else {
                Toast.makeText(getApplicationContext(), "blank", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("Rohit error", e.getMessage() + e.getStackTrace());
            Toast.makeText(getApplicationContext(), "popup : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View v) {
        try {
            if (!delitem.equals("")) {
                String[] args = new String[1];
                args[0] = delitem;
                db.delete("blklst_call", "ph_no = ? ", args);
                popup();
                Toast.makeText(getApplicationContext(), "Number deleted !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Select Number frist !", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "del : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void add(View v) {
        try {
            if (!phno.getText().equals("")) {
                ContentValues val = new ContentValues();
                val.put("ph_no", phno.getText().toString());
                val.put("ct", "0");
                db.insert("blklst_call", null, val);
                popup();
                Toast.makeText(getApplicationContext(), "New Number Added !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Enter Number First !", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "add : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void add2(View v) {
        try {
            if (!StartWith.getText().equals("")) {
                ContentValues val = new ContentValues();
                val.put("StartWith", StartWith.getText().toString());
                val.put("ct", "0");
                db.insert("blklst_call_StartWith", null, val);
                popup2();
                Toast.makeText(getApplicationContext(), "New Number Added !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Enter Number First !", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "add : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void add3(View v) {
        try {
            if (!EndsWith.getText().equals("")) {
                ContentValues val = new ContentValues();
                val.put("EndsWith", EndsWith.getText().toString());
                val.put("ct", "0");
                db.insert("blklst_call_EndsWith", null, val);
                popup2();
                Toast.makeText(getApplicationContext(), "New Number Added to EndsWith !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Enter Number First !", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "add : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private static int PICK_CONTACT = 0;

    public void pickContact(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PICK_CONTACT) && (resultCode == RESULT_OK)) {
            Uri selectedContact = data.getData();
            Cursor cursor = getContentResolver().query(selectedContact,
                    null, null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                int nameIndex = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME);
                String name = cursor.getString(nameIndex);
                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                TextView messageView = (TextView)findViewById(R.id.editText1);
                messageView.setText(number);


                // TODO: Do something with the selected name and phone number.
            }
        }
    }
    @NonNull
    public String[] getContactList(View v){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        Log.i(LOG_TAG, "get Contact List: Fetching complete contact list");

        ArrayList<String> contact_names = new ArrayList<String>();

        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                System.out.println(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER.trim())));
                System.out.println(ContactsContract.Contacts.HAS_PHONE_NUMBER.trim());

                if (cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER.trim())).equalsIgnoreCase("1"))
                {
                    if (name!=null){
                        //contact_names[i]=name;

                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{id}, null);
                        while (pCur.moveToNext())
                        {
                            String PhoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            PhoneNumber = PhoneNumber.replaceAll("-", "");
                            if (PhoneNumber.trim().length() >= 10) {
                                PhoneNumber = PhoneNumber.substring(PhoneNumber.length() - 10);
                            }
                            contact_names.add(name + ":" + PhoneNumber);

                            //i++;
                            break;
                        }
                        pCur.close();
                        pCur.deactivate();
                        // i++;
                    }
                }
            }
            cur.close();
            cur.deactivate();
        }

        String[] contactList = new String[contact_names.size()];

        for(int j = 0; j < contact_names.size(); j++){
            contactList[j] = contact_names.get(j);
        }

        return contactList;
    }

}
