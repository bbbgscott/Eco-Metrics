package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class contacts extends Activity {
    /** Called when the activity is first created. */
	
	Button linkButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        
        linkButton = (Button) findViewById(R.id.urlButt);
        linkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                       
            	Intent myIntent = new Intent( Intent.ACTION_VIEW , Uri.parse( "http://www.eco-metrics.net/tour" ) );
                startActivity( myIntent );

            }
        });
    }
}
