package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class main extends Activity {
    /** Called when the activity is first created. */
	
	ImageView image;
	Button metricsButt;
	Button configButt;
	Button contactButt;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView) findViewById(R.id.imageView1);
        
        final Button configButt = (Button) findViewById(R.id.configButt);
        configButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), config.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        //if there is no data then this button will be hidden
        final Button metricsButt = (Button) findViewById(R.id.metricsButt);
        metricsButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), metrics.class);
                startActivityForResult(myIntent, 0);
            }
        });
        final Button contactButt = (Button) findViewById(R.id.contactButt);
        contactButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), contacts.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        
    }
}