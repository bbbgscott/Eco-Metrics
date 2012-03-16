package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class metrics extends Activity {
    /** Called when the activity is first created. */
	
	TextView t;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        
        t = (TextView) findViewById(R.id.contactsText);
        
    }
}