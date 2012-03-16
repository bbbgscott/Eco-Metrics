package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class config extends Activity {
    /** Called when the activity is first created. */
	
	Button startButt;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        
        startButt = (Button) findViewById(R.id.addFieldButt);
        startButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), data.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}