package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class config extends Activity {
    /** Called when the activity is first created. */
	
	TextView t;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        
        t = (TextView) findViewById(R.id.configTitle);
        
    }
}