package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Spannable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddMetric extends Activity {
	EditText txtLoc;
	EditText txtVal;
	TextView txtMetrics;
	DataBaseHelper dbHelper;
	Spinner spinType;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmetric);
        txtLoc=(EditText)findViewById(R.id.txtLoc);
        txtVal=(EditText)findViewById(R.id.txtVal);
        txtMetrics=(TextView)findViewById(R.id.txtMetrics);
        spinType=(Spinner)findViewById(R.id.spinType);
    }
	
	@Override
	public void onStart()
	{
		try
		{
		super.onStart();
		dbHelper=new DataBaseHelper(this);
		txtMetrics.setText(txtMetrics.getText()+String.valueOf(dbHelper.getMetricsCount()));
		
		Cursor c=dbHelper.getAllTypes();
		startManagingCursor(c);
		
		
		
		//SimpleCursorAdapter ca=new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item, c, new String [] {DatabaseHelper.colTypeName}, new int []{android.R.id.text1});
		SimpleCursorAdapter ca=new SimpleCursorAdapter(this,R.layout.typespinnerrow, c, new String [] {DataBaseHelper.colTypeName,"_id"}, new int []{R.id.txtTypeName});
		//ca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinType.setAdapter(ca);
		spinType.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View selectedView,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//never close cursor
		}
		catch(Exception ex)
		{
			CatchError(ex.toString());
		}
	}
	
	public void btnAddEmp_Click(View view)
	{
		boolean ok=true;
		try
		{
			Spannable spn=txtVal.getText();
			String loc=txtLoc.getText().toString();
			int val=Integer.valueOf(spn.toString());
			int typeID=Integer.valueOf((int)spinType.getSelectedItemId());
			Metric m=new Metric(loc,val,typeID);
			
			dbHelper.AddMetric(m);
			
		}
		catch(Exception ex)
		{
			ok=false;
			CatchError(ex.toString());
		}
		finally
		{
			if(ok)
			{
				//NotifyEmpAdded();
				Alerts.ShowEmpAddedAlert(this);
				txtMetrics.setText("Number of metrics "+String.valueOf(dbHelper.getMetricsCount()));
			}
		}
	}
	
	void CatchError(String Exception)
	{
		Dialog diag=new Dialog(this);
		diag.setTitle("Add new Metric");
		TextView txt=new TextView(this);
		txt.setText(Exception);
		diag.setContentView(txt);
		diag.show();
	}
	
	void NotifyEmpAdded()
	{
		Dialog diag=new Dialog(this);
		diag.setTitle("Add new Employee");
		TextView txt=new TextView(this);
		txt.setText("Employee Added Successfully");
		diag.setContentView(txt);
		diag.show();
		try {
			diag.wait(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			CatchError(e.toString());
		}
		diag.notify();
		diag.dismiss();
	}
	
}