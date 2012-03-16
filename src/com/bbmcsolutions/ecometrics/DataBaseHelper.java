//This class creates the blank database and defines the field names

package com.bbmcsolutions.ecometrics;


import mina.android.DatabaseDemo.Employee;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DataBaseHelper extends SQLiteOpenHelper {

	static final String dbName="EcoMetricsDemo";
	static final String metricTable="metrics";
	static final String colID="metricID";
	static final String colMetric="MetricName";
	static final double colValue=0;
	static final String colUnit="Unit";
	static final String colLoc="Loc";
	
	static final String viewMetrics="ViewMetrics";
	
	public DataBaseHelper(Context context) {
		super(context, dbName, null,33);
		
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(SQLiteDatabase db) {
		  // TODO Auto-generated method stub
		  
		//Create basic table
		db.execSQL("CREATE TABLE "+metricTable+" ("+colID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+colMetric+ " TEXT, "+colValue+"INTEGER NOT NULL , "+colUnit+ " TEXT , "+colLoc+"TEXT )");
			   
		 }

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//drop old table
		db.execSQL("DROP TABLE IF EXISTS "+metricTable);
		//add new table
		onCreate(db);
	}
	void AddMetric(data metric)
		{
			 
			 
			SQLiteDatabase db= this.getWritableDatabase();
			 
			
			ContentValues cv=new ContentValues();
			
			cv.put(colMetric, metric.getName());
			cv.put(colValue, metric.getAge());
			cv.put(colUnit, metric.getUnit());
			cv.put(colLoc, m.getLoc());
					
			db.insert(metricTable, colMetric, cv);
			db.close();			
		}
	
	 public int UpdateMetric(Metrics m)
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(colMetric, m.getMetric());
		 cv.put(colValue, m.getValue());
		 cv.put(colUnit, m.getUnit());
		 cv.put(colLoc, m.getLoc());
		 return db.update(metricTable, cv, colID+"=?", new String []{String.valueOf(m.getID())});
		 
	 }
	 
	 public void DeleteMetric(Metrics m)
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 db.delete(metricTable,colID+"=?", new String [] {String.valueOf(m.getID())});
		 db.close();
		 
		
		
	 }
	
}
