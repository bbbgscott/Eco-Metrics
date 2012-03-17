package com.bbmcsolutions.ecometrics;
import java.util.ArrayList;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;


public class data extends Activity {
    /** Called when the activity is first created. */
	
	String met = "Electricity";
	String loc = "Office";
	String mon = "January";
	double cost;
	double cons;
	boolean flag = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.data);
	    
	    createdb();
	    
	    
	    
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
	    
	    final EditText consumptionEdit = (EditText) findViewById(R.id.consumptionEditText);
	    
	    final EditText costEdit = (EditText) findViewById(R.id.costEditText);
	    
	    Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	/*Intent myIntent = new Intent(v.getContext(), metrics.class);
                startActivityForResult(myIntent, 0);*/
            	// Do something
            	
            	
            	//Log.d("Tagged", consumptionEdit.getText().toString().isEmpty());
            	if(consumptionEdit.getText().toString().isEmpty())
            	{
            		Toast.makeText(getBaseContext(), "Please enter Consumption", Toast.LENGTH_LONG).show();
            	}
            	else if(costEdit.getText().toString().isEmpty())
            	{
            		Toast.makeText(getBaseContext(), "Please enter Cost", Toast.LENGTH_LONG).show();
            	}
            	else
            	{
            	cons = Double.parseDouble(consumptionEdit.getText().toString());            	
            	cost = Double.parseDouble(costEdit.getText().toString()); 
            	Metric m=new Metric(met, loc, mon, cons, cost);
            	addentry(m);
            	Intent myIntent = new Intent(v.getContext(), data.class);
                startActivityForResult(myIntent, 0);	//Metric m=new Metric(met, loc, mon, cons, cost); 
            	}            	           	
            	//Toast.makeText(getBaseContext(), "metric: " +met+", location: "+loc+", month: "+mon+", consumption: "+cons+", cost: "+cost, Toast.LENGTH_LONG).show();
            }
        });
        
        Button viewButton = (Button) findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), metrics_indicator.class);
                startActivityForResult(myIntent, 0);	
            }
        });            
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
	    	
	    	String unit = null;
	    	TextView result;
	    	if (met.equals("Electricity")){
	    	unit = "kWh";
	    	result = (TextView)findViewById(R.id.unit_label);
	    	result.setText(unit);
	    	}
	    	else if(met.equals("Propane")){
	    		unit = "gal";
	    		result = (TextView)findViewById(R.id.unit_label);
		    	result.setText(unit);
	    	}
	    	else if(met.equals("Natural Gas")){
	    		unit = "thm";
	    		result = (TextView)findViewById(R.id.unit_label);
		    	result.setText(unit);
	    	}
	    	else if(met.equals("Gasoline"))
	    	{
	    		unit = "gal";
	    		result = (TextView)findViewById(R.id.unit_label);
		    	result.setText(unit);
	    	}
	    	else
	    	{
	    		System.exit(1);
	    	}
	    	
	    	
	    	
	    	//if(met.equals())
	    		//System.exit(1);//Toast.makeText(parent.getContext(), "The metric you chose is " +met+", your location is "+loc+", and your month is "+mon, Toast.LENGTH_LONG).show();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
	public void createdb(){
		SQLiteDatabase metricDB = null;
		String metricsTable = "metricsTable";
		
		try{
			
			metricDB = this.openOrCreateDatabase("Metrics", MODE_PRIVATE, null);
			
			metricDB.execSQL("CREATE TABLE IF NOT EXISTS " 
			+ metricsTable
			+ " (id INTEGER AUTO INCREMENT, metric VARCHAR, loc VARCHAR, month VARCHAR, cons DOUBLE, cost DOUBLE );");
		
		}
		catch(Exception e) {
			Log.e("Error", "Error", e);
			
		}
		finally{
			if(metricDB != null){
				metricDB.close();
				Log.d("log", "Database Opened");
				}			
		}			
	}
    public void addentry(Metric m)
    {
    	ArrayList<String> results  = new ArrayList<String>();
    	SQLiteDatabase metricDB = null;
		String metricsTable = "metricsTable";
		
    	
    	try{
			metricDB = this.openOrCreateDatabase("Metrics", MODE_PRIVATE, null);	
			metricDB.execSQL("INSERT INTO metricsTable (metric, loc, month, cons, cost) " + "VALUES('"+m.getMetric()+"','"+m.getLoc()+"','"+m.getMonth()+"',"+m.getCons()+","+m.getCost() +");");
			
		}
		catch(Exception e) {
			Log.e("Error", "Error", e);
			
		}
		finally{
			if(metricDB != null){
				//Cursor cursor = metricDB.query("SELECT cons" + " FROM " +metricsTable + " WHERE cons = 25;", null);
				//cursor.close();
				
				Cursor cur= metricDB.rawQuery("Select * from "+metricsTable, null);
				int colIndex = cur.getColumnIndex("metric");
				if(cur != null){
					if(cur.getCount() > 0){
						int i = 0;
						
						for(int k = 0; k < cur.getCount(); k++){
							i++;
							cur.moveToNext();
							String cons = cur.getString(colIndex);
							Log.d("List", cons);
							results.add(" " + i +": " +cons);
							//Log.d("List", cons.toString());
							
						}
					}
				
				}
				cur.close();				
				metricDB.close();				
				}
				
		}	
		
    	
    	
    	
    	
    }
}
    
    
    
    
    
    