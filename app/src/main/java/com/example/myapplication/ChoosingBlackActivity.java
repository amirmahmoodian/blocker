package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ChoosingBlackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosblackemethod);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Choose blocking Method: ");
    }
    public void FullNumber(View v) {
        Intent i = new Intent(this, EditBlackListActivityFull.class);
        startActivity(i);
    }
    public void StartsWth(View v) {
        Intent i = new Intent(this, EditBlackListActivityStartsWith.class);
        startActivity(i);
    }
    public void EndsWith(View v) {
        Intent i = new Intent(this, EditBlackListActivityEndsWith.class);
        startActivity(i);
    }
}
