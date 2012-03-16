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
	Button button;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView) findViewById(R.id.imageView1);
        
        final Button configButt = (Button) findViewById(R.id.configButt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });
        
        //if there is no data then this button will be hidden
        final Button button = (Button) findViewById(R.id.metricsButt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("clicked", "msg");
            }
        });
        final Button contactButt = (Button) findViewById(R.id.contactButt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), contacts.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        
    }
}