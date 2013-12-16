package com.wit.android.examples.fragments;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.wit.android.examples.R;

public class MainActivityd extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maind);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activityd, menu);
        return true;
    }
    
}
