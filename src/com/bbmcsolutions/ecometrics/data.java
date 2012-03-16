package com.bbmcsolutions.ecometrics;
import com.bbmcsolutions.ecometrics.AddMetric;
import com.bbmcsolutions.ecometrics.GridList;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class data extends Activity {
	DataBaseHelper dbHelper;
    /** Called when the activity is first created. */
	
	String met;
	String loc;
	String mon;
	double cost;
	double cons;
	boolean flag = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.data);

	    Spinner metricSpinner = (Spinner) findViewById(R.id.metricSpinner);
	    ArrayAdapter<CharSequence> metricAdapter = ArrayAdapter.createFromResource(
	            this, R.array.metricArray, android.R.layout.simple_spinner_item);
	    metricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    metricSpinner.setAdapter(metricAdapter);
	    
	    metricSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	    
	    Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
	    ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(
	            this, R.array.locationArray, android.R.layout.simple_spinner_item);
	    locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    locationSpinner.setAdapter(locationAdapter);
	    
	    locationSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	    
	    Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
	    ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(
	            this, R.array.monthArray, android.R.layout.simple_spinner_item);
	    monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    monthSpinner.setAdapter(monthAdapter);
	    
	    monthSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	    	    
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener 
	{
	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
	    {
	    	if(flag == true)
	    		flag = false;
	    	else
	    	{
		    	switch(parent.getId())
		    	{
			    	case R.id.metricSpinner:
			    		met = parent.getItemAtPosition(pos).toString();
			    		break;
			    	case R.id.locationSpinner:
			    		loc = parent.getItemAtPosition(pos).toString();
			    		break;
			    	case R.id.monthSpinner:
			    		mon = parent.getItemAtPosition(pos).toString();
			    		break;
		    	}
	    	}
	    	if(met != "" && loc != "" && mon != "")
	    		Toast.makeText(parent.getContext(), "The metric you chose is " +met+", your location is "+loc+", and your month is "+mon, Toast.LENGTH_LONG).show();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
    
}
    
    
    
    
    
    
