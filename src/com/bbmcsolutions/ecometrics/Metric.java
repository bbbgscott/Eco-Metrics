

package com.bbmcsolutions.ecometrics;

import android.content.Context;


public class Metric {
	
	int _id;
	String _loc;
	double _val;
	int _Type;
	
	public Metric(String loc,double val,int Type)
	{
		
		this._loc=loc;
		this._val=val;
		this._Type=Type;
	}
	
	public Metric(String loc,double val)
	{
		this._loc=loc;
		this._val=val;
	}
	
	public int getID()
	{
		return this._id;
	}
	public void SetID(int ID)
	{
		this._id=ID;
	}
	
	public String getLoc()
	{
		return this._loc;
	}
	
	public double getVal()
	{
		return this._val;
	}
	
	public void setLoc(String Name)
	{
		this._loc=Name;
	}
	public void setVal(int Val)
	{
		this._val=Val;
	}
	
	
	public void setType(int Type)
	{
		this._Type=Type;
	}
	
	public String getTypeName(Context con, int Type)
	{
		return new DataBaseHelper(con).GetType(Type);
	}
	public int getType()
	{
		return this._Type;
	}
}
