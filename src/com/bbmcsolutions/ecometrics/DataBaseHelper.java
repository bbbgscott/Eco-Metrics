//

package com.bbmcsolutions.ecometrics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DataBaseHelper extends SQLiteOpenHelper {

	static final String dbName="ecometricsDB";
	static final String metricsTable="Metrics";
	static final String colID="MetricID";
	static final String colLoc="Loc";
	static final String colVal="Val";
	static final String colType="Type";
	
	static final String typeTable="Type";
	static final String colTypeID="TypeID";
	static final String colTypeName="TypeName";
	
	static final String viewMetrics="ViewMetrics";
	
	
	public DataBaseHelper(Context context) {
		super(context, dbName, null,33);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE "+metricsTable+" ("+colTypeID+ " INTEGER PRIMARY KEY , "+
				colTypeName+ " TEXT)");
		
		db.execSQL("CREATE TABLE "+metricsTable+" ("+colID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colLoc+" TEXT, "+colVal+" Integer, "+colType+" INTEGER NOT NULL ,FOREIGN KEY ("+colType+") REFERENCES "+metricsTable+" ("+colTypeID+"));");
		
		
		db.execSQL("CREATE TRIGGER fk_metrictype_typeid " +
				" BEFORE INSERT "+
				" ON "+metricsTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+colTypeID+" FROM "+metricsTable+" WHERE "+colTypeID+"=new."+colType+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		db.execSQL("CREATE VIEW "+viewMetrics+
				" AS SELECT "+metricsTable+"."+colID+" AS _id,"+
				" "+metricsTable+"."+colLoc+","+
				" "+metricsTable+"."+colVal+","+
				" "+metricsTable+"."+colTypeName+""+
				" FROM "+metricsTable+" JOIN "+metricsTable+
				" ON "+metricsTable+"."+colType+" ="+metricsTable+"."+colTypeID
				);
		//Inserts pre-defined departments
		InsertTypes(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS "+metricsTable);
		db.execSQL("DROP TABLE IF EXISTS "+metricsTable);
		
		db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger");
		db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger22");
		db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");
		db.execSQL("DROP VIEW IF EXISTS "+viewMetrics);
		onCreate(db);
	}
	
	 void AddMetric(Metric m)
	{
		 
		 
		 SQLiteDatabase db= this.getWritableDatabase();
		 
		
		ContentValues cv=new ContentValues();
		
		cv.put(colLoc, m.getLoc());
		cv.put(colVal, m.getVal());
		cv.put(colType, m.getType());
		//cv.put(colType,2);
		
		db.insert(metricsTable, colLoc, cv);
		db.close();
		
		
	}
	 
	 int getMetricsCount()
	 {
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+metricsTable, null);
		int x= cur.getCount();
		cur.close();
		return x;
	 }
	 
	 Cursor getAllMetrics()
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 
		 
		 
		 //Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colLoc+", "+colVal+" from "+metricsTable, new String [] {});
		 Cursor cur= db.rawQuery("SELECT * FROM "+viewMetrics,null);
		 return cur;
		 
	 }
	 
	 Cursor getAllTypes()
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT "+colTypeID+" as _id, "+colTypeName+" from "+metricsTable,new String [] {});
		 
		 return cur;
	 }
	 
	 void InsertTypes(SQLiteDatabase db)
	 {
		 ContentValues cv=new ContentValues();
			cv.put(colTypeID, 1);
			cv.put(colTypeName, "Electricty");
			db.insert(metricsTable, colTypeID, cv);
			cv.put(colTypeID, 2);
			cv.put(colTypeName, "Water");
			db.insert(metricsTable, colTypeID, cv);
			cv.put(colTypeID, 3);
			cv.put(colTypeName, "Garbage");
			db.insert(metricsTable, colTypeID, cv);
			db.insert(metricsTable, colTypeID, cv);
			
	 }
	 
	 public String GetType(int ID)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 
		 String[] params=new String[]{String.valueOf(ID)};
		 Cursor c=db.rawQuery("SELECT "+colTypeName+" FROM"+ metricsTable+" WHERE "+colTypeID+"=?",params);
		 c.moveToFirst();
		 int index= c.getColumnIndex(colTypeName);
		 return c.getString(index);
	 }
	 
	 public Cursor getMetricByType(String Type)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 String [] columns=new String[]{"_id",colLoc,colVal,colTypeName};
		 Cursor c=db.query(viewMetrics, columns, colTypeName+"=?", new String[]{Type}, null, null, null);
		 return c;
	 }
	 
	 public int GetTypeID(String Type)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 Cursor c=db.query(metricsTable, new String[]{colTypeID+" as _id",colTypeName},colTypeName+"=?", new String[]{Type}, null, null, null);
		 //Cursor c=db.rawQuery("SELECT "+colTypeID+" as _id FROM "+metricsTable+" WHERE "+colTypeName+"=?", new String []{Dept});
		 c.moveToFirst();
		 return c.getInt(c.getColumnIndex("_id"));
		 
		 }
	 
	 public int UpdateMetric(Metric m)
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(colLoc, m.getLoc());
		 cv.put(colVal, m.getVal());
		 cv.put(colType, m.getType());
		 return db.update(metricsTable, cv, colID+"=?", new String []{String.valueOf(m.getID())});
		 
	 }
	 
	 public void DeleteMetric(Metric m)
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 db.delete(metricsTable,colID+"=?", new String [] {String.valueOf(m.getID())});
		 db.close();
		 
		
		
	 }

}

