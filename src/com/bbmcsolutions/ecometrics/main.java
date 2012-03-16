package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class main extends Activity {
    /** Called when the activity is first created. */
	
	ImageView image;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView) findViewById(R.id.imageView1);
        
        
    }
}