package com.bbmcsolutions.ecometrics;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//ViewPagerIndicatorActivity
public class metrics_indicator extends FragmentActivity {
	PagerAdapter mPagerAdapter;
    ViewPager  mViewPager;
    metrics mIndicator;
    static Double officeTotals[] = new Double[4];
    static Double wareTotals[] = new Double[4];
	
    public static Double getWareHouseTotal(int num) {
    	return wareTotals[num];
    }
    public static Double getOfficeTotal(int num) {
    	return officeTotals[num];
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metrics);
        
        // Create our custom adapter to supply pages to the viewpager.
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mViewPager.setAdapter(mPagerAdapter);
        
        // Start at a custom position
        mViewPager.setCurrentItem(0);
        
        // Find the indicator from the layout
        mIndicator = (metrics)findViewById(R.id.indicator);
		
        // Set the indicator as the pageChangeListener
        mViewPager.setOnPageChangeListener(mIndicator);
        
        // Initialize the indicator. We need some information here:
        // * What page do we start on.
        // * How many pages are there in total
        // * A callback to get page titles
        mIndicator.init(0, mPagerAdapter.getCount(), mPagerAdapter);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
		Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);
		mIndicator.setFocusedTextColor(new int[]{255, 0, 0});
		
		// Set images for previous and next arrows.
		mIndicator.setArrows(prev, next);
		
		mIndicator.setOnClickListener(new OnIndicatorClickListener());
		
		SQLiteDatabase metricDB = null;
    	metricDB = this.openOrCreateDatabase("Metrics", MODE_PRIVATE, null);
    	String metricsTable = "metricsTable";
        
    	
    	for(int w=0;w<4;w++) {
    		String m = null;
    		officeTotals[w]=0.0;
			wareTotals[w]=0.0;
    		switch(w) {
    		case 0:
    			m="Electricity";
    			break;
    		case 1:
    			m="Propane";
    			break;
    		case 2:
    			m="Natural Gas";
    			break;
    		case 3:
    			m="Gasoline";
    			break;
    		}
        Cursor cur= metricDB.rawQuery("Select * FROM "+metricsTable+ " WHERE metric = '" + m + "'", null);
        
        int colMetric = cur.getColumnIndex("metric");
        int colLoc= cur.getColumnIndex("loc");
        int colMonth = cur.getColumnIndex("month");
        int colCons = cur.getColumnIndex("cons");
        int colCost = cur.getColumnIndex("cost");
        int colIndex = cur.getColumnIndex("id");
        Integer in = cur.getCount();
        Log.d("Location", in.toString());
		double officeTotal = 0;
		double wareTotal = 0;
		if(cur != null){
			if(cur.getCount() > 0){
				for(int i = 0; i < cur.getCount()-1; i++){
					
					cur.moveToNext();
					double cons = cur.getDouble(colCons+1);
										
					String month = cur.getString(colMonth+1);
					String loc = cur.getString(colLoc+1);
					String metric = cur.getString(colMetric+1);
	
					if(loc.toUpperCase().equals("OFFICE")) {
						officeTotal += cons;
					}
					else if(loc.toUpperCase().equals("WAREHOUSE")) {
						wareTotal += cons;
					}		
				}
				
				officeTotals[w]=officeTotal;
				wareTotals[w]=wareTotal;
				
				
			}
		}
		cur.close();
    	}			
		metricDB.close();
		
    }
	
    class OnIndicatorClickListener implements metrics.OnClickListener{
		public void onCurrentClicked(View v) {
			Toast.makeText(metrics_indicator.this, "Hello", Toast.LENGTH_SHORT).show();
		}
		
		public void onNextClicked(View v) {
			mViewPager.setCurrentItem(Math.min(mPagerAdapter.getCount() - 1, mIndicator.getCurrentPosition() + 1));
		}

		public void onPreviousClicked(View v) {
			mViewPager.setCurrentItem(Math.max(0, mIndicator.getCurrentPosition() - 1));
		}
    	
    }
    
    class PagerAdapter extends FragmentPagerAdapter implements metrics.PageInfoProvider{
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			return ArrayListFragment.newInstance(pos);
		}

		@Override
		public int getCount() {
			return 4;
		}
		
		public String getTitle(int pos){
		String[] ListItems = new String[]{"Electricity","Propane","Gasoline","Natural Gas"};
		return ListItems[pos];
		}
    }
    
    public static class ArrayListFragment extends ListFragment 
    {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) 
        {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }
        
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	View v;
        	View tv;
        	SmartImageView myImage;
        	String imgUrl;
        	Double wareTot;
            Double officeTot;
            Double max;
        	
        	switch(mNum)
        	{
        	case 0:
                v = inflater.inflate(R.layout.electricity, container, false);
                myImage = (SmartImageView) v.findViewById(R.id.my_image);
                wareTot = getWareHouseTotal(mNum);
                officeTot = getOfficeTotal(mNum);
                max = wareTot+officeTot;
                
                
                imgUrl = "http://chart.apis.google.com/chart" +
         			   "?chs=700x425" +
         			   "&cht=p3" +
         			   "&chco=336699|33CC33" +
         			   "&chds=0,"+max+
         			   "&chd=t:"+wareTot+","+officeTot+
         			   "&chtt=Consumption+Totals";
                
                if(max!=0)
                	myImage.setImageUrl(imgUrl);
                else
                	Toast.makeText(getActivity(), "Not enough iformation", Toast.LENGTH_LONG).show();
                return v;
        	case 1:
        		v = inflater.inflate(R.layout.propane, container, false);
                myImage = (SmartImageView) v.findViewById(R.id.my_image);
                wareTot = getWareHouseTotal(mNum);
                officeTot = getOfficeTotal(mNum);
                max = wareTot+officeTot;
                
                imgUrl = "http://chart.apis.google.com/chart" +
          			   "?chs=700x425" +
          			   "&cht=p3" +
          			   "&chco=336699|33CC33" +
          			   "&chds=0,"+max+
          			   "&chd=t:"+wareTot+","+officeTot+
          			   "&chtt=Consumption+Totals";
                
                if(max!=0)
                	myImage.setImageUrl(imgUrl);
                else
                	Toast.makeText(getActivity(), "Not enough iformation", Toast.LENGTH_LONG).show();
                return v;
        	case 2:
        		v = inflater.inflate(R.layout.natural, container, false);
                myImage = (SmartImageView) v.findViewById(R.id.my_image);
                wareTot = getWareHouseTotal(mNum);
                officeTot = getOfficeTotal(mNum);
                max = wareTot+officeTot;
                
                
                imgUrl = "http://chart.apis.google.com/chart" +
         			   "?chs=700x425" +
         			   "&cht=p3" +
         			   "&chco=336699|33CC33" +
         			   "&chds=0,"+max+
         			   "&chd=t:"+wareTot+","+officeTot+
         			   "&chtt=Consumption+Totals";
                
                if(max!=0)
                	myImage.setImageUrl(imgUrl);
                else
                	Toast.makeText(getActivity(), "Not enough iformation", Toast.LENGTH_LONG).show();
                return v;
        	case 3:
        		v = inflater.inflate(R.layout.gasoline, container, false);
                myImage = (SmartImageView) v.findViewById(R.id.my_image);
                wareTot = getWareHouseTotal(mNum);
                officeTot = getOfficeTotal(mNum);
                max = wareTot+officeTot;
                
                
                imgUrl = "http://chart.apis.google.com/chart" +
         			   "?chs=700x425" +
         			   "&cht=p3" +
         			   "&chco=336699|33CC33" +
         			   "&chds=0,"+max+
         			   "&chd=t:"+wareTot+","+officeTot+
         			   "&chtt=Consumption+Totals";
                
                if(max!=0)
                	myImage.setImageUrl(imgUrl);
                else
                	Toast.makeText(getActivity(), "Not enough iformation", Toast.LENGTH_LONG).show();
                return v;
        	}
        	return null;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            /*setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, list));*/
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }    
}