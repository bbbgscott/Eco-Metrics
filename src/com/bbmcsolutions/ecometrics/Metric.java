

package com.bbmcsolutions.ecometrics;

import android.content.Context;


public class Metric {
	
	int _id;
	String _metric;
	String _month;
	String _loc;
	double _cons;
	double _cost;
	
	public Metric(String aMetric, String aMonth, String aLoc, double aCons, double aCost)
	{
		this._metric = aMetric;
		this._loc = aLoc;
		this._month = aMonth;
		this._cons = aCons;
		this._cost = aCost;
	}
	public String getMetric()
	{
		return this._metric;
	}	
	public String getLoc()
	{
		return this._loc;
	}
	public String getMonth()
	{
		return this._month;
	}
	public double getCons()
	{
		return this._cons;
	}
	public double getCost()
	{
		return this._cost;
	}
	public void setMetric(String aMetric)
	{
		this._metric=aMetric;
	}	
	public void setLoc(String aLoc)
	{
		this._loc=aLoc;
	}
	public void setMonth(String aMonth)
	{
		this._month=aMonth;
	}
	public void setCons(double aCons)
	{
		this._cons=aCons;
	}
	public void setCost(double aCost)
	{
		this._cost=aCost;
	}
	
}