package com.bbmcsolutions.ecometrics;


import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class Utilities {
static public void ManageTypeSpinner(Context context,Spinner view)
{
	DataBaseHelper dbHelper=new DataBaseHelper(context);
	Cursor c=dbHelper.getAllTypes();
	//context.startManagingCursor(c);
	
	
	
	//SimpleCursorAdapter ca=new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item, c, new String [] {DatabaseHelper.colDeptName}, new int []{android.R.id.text1});
	SimpleCursorAdapter ca=new SimpleCursorAdapter(context,R.layout.typespinnerrow, c, new String [] {DataBaseHelper.colTypeName,"_id"}, new int []{R.id.txtTypeName});
	view.setAdapter(ca);
	
}
}
