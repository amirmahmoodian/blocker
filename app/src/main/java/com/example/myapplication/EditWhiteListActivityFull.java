package com.example.myapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.AppCompatActivity;

public class EditWhiteListActivityFull extends AppCompatActivity {
	ArrayList<String> no;
	ArrayList<String> no2;

	ArrayAdapter<String> lis;
	SQLiteDatabase db;
	ListView sp ;
	EditText phno;
	String delitem="";
	EditText StartWith;
	EditText EndsWith;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.white_list);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		sp =(ListView)findViewById(R.id.listView1);
		phno =(EditText)findViewById(R.id.editText1);
//		StartWith = findViewById(R.id.editText12);
//		EndsWith = findViewById(R.id.editText13);
		  SQLiteOpenHelper helper = new DBHelper(this, "TrackerActivity", null, 2);
	      db = helper.getWritableDatabase();
	     sp.setOnItemClickListener(new OnItemClickListener() {
	    	 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		// TODO Auto-generated method stub
	    		 delitem=String.valueOf(sp.getItemAtPosition(arg2));
	    	}
		});
	no = new ArrayList<String>();
		no2 = new ArrayList<String>();
	      popup();
	}
	
	public void popup()
	{
		no.clear();
		try
		{ 
			if(db!=null)
			{
				Cursor c = db.query("whitlist_call", null , null, null, null, null,"ph_no desc");
				while( c.moveToNext() ) 
				{
				String num = c.getString(0);
			    no.add( num );
			   
				 
				  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, no);
			     sp.setAdapter(adapter);
				}
				c.close();
			}
			else
			{
				
				Toast.makeText(getApplicationContext(),"blank", Toast.LENGTH_SHORT).show();
			}
		}
		catch (Exception e) {
			Log.d("Rohit error", e.getMessage()+e.getStackTrace());
			Toast.makeText(getApplicationContext(),"popup : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		  
	}
	public void delete(View v)
	{
		try
		{
		if(!delitem.equals(""))
		{
			String [] args = new String[1];
			args[0] = delitem;
		db.delete("whitlist_call", "ph_no = ? ", args );
		popup();
		Toast.makeText(getApplicationContext(), "Number deleted !", Toast.LENGTH_SHORT).show();
		}
		else
		{
		Toast.makeText(getApplicationContext(), "Select Number frist !", Toast.LENGTH_SHORT).show();
		
		}
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(),"del : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		  
	}

	public void add(View v)
	{
		try
		{
		if(!phno.getText().equals(""))
		{
		ContentValues val = new ContentValues();
		val.put("ph_no", phno.getText().toString());
		val.put("blk","0");
		
		
		db.insert("whitlist_call", null, val);
		popup();
		Toast.makeText(getApplicationContext(), "New Number Added !", Toast.LENGTH_SHORT).show();
		}
		else
		{
		Toast.makeText(getApplicationContext(), "Enter Number frist !", Toast.LENGTH_SHORT).show();
		
		}
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(),"add : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
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

	}