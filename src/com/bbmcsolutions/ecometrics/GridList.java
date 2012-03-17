package com.bbmcsolutions.ecometrics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class GridList extends Activity {
	DataBaseHelper dbHelper;
	static public GridView grid;
	TextView txtTest;
	Spinner spinDept1;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.gridview);
        grid=(GridView)findViewById(R.id.grid);
        txtTest=(TextView)findViewById(R.id.txtTest);
        spinDept1=(Spinner)findViewById(R.id.spinDept1);
        
        Utilities.ManageTypeSpinner(getParent(), spinDept1);
        final DataBaseHelper db=new DataBaseHelper(this);
        try
        {
         
         spinDept1.setOnItemSelectedListener(new OnItemSelectedListener() {
        	 
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				LoadGrid();
	    		//sca.notifyDataSetChanged();
	    		
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
       
        }
        catch(Exception ex)
        {
        	txtTest.setText(ex.toString());
        }
        
        
       
        try
        {
        grid.setOnItemClickListener(new OnItemClickListener()
        {

        	public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				try
				{
			
				SQLiteCursor cr=(SQLiteCursor)parent.getItemAtPosition(position);
				String loc=cr.getString(cr.getColumnIndex(DataBaseHelper.colLoc));
				int val=cr.getInt(cr.getColumnIndex(DataBaseHelper.colVal));
				String Type=cr.getString(cr.getColumnIndex(DataBaseHelper.colTypeName));
				Metric m=new Metric(loc, val,db.GetTypeID(Type));
				m.SetID((int)id);
				AlertDialog diag= Alerts.ShowEditDialog(GridList.this,m);
				diag.setOnDismissListener(new OnDismissListener() {
					
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						txtTest.setText("dismissed");
						//((SimpleCursorAdapter)grid.getAdapter()).notifyDataSetChanged();
						LoadGrid();
					}
				});
				diag.show();
				}
				catch(Exception ex)
				{
					Alerts.CatchError(GridList.this, ex.toString());
				}
			}

			
        }
        );
        }
        catch(Exception ex)
        {
        	
        }

    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	//LoadGrid();
    }
    
    public void LoadGrid()
    {
    	dbHelper=new DataBaseHelper(this);
    	try
    	{
    		//Cursor c=dbHelper.getAllEmployees();
    		View v=spinDept1.getSelectedView();
			TextView txt=(TextView)v.findViewById(R.id.txtTypeName);
			String Type=String.valueOf(txt.getText());
    		Cursor c=dbHelper.getMetricByType(Type);
    		startManagingCursor(c);
    		
    		String [] from=new String []{DataBaseHelper.colLoc,DataBaseHelper.colVal,DataBaseHelper.colTypeName};
    		int [] to=new int [] {R.id.colLoc,R.id.colVal,R.id.colType};
    		SimpleCursorAdapter sca=new SimpleCursorAdapter(this,R.layout.gridrow,c,from,to);
    		grid.setAdapter(sca);
    		
    		
    		
    	}
    	catch(Exception ex)
    	{
    		AlertDialog.Builder b=new AlertDialog.Builder(this);
    		b.setMessage(ex.toString());
    		b.show();
    	}
    }
	
}
