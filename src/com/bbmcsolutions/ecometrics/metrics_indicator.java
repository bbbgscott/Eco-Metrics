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
            Toast.makeText(getActivity(), "mNum = "+mNum, Toast.LENGTH_SHORT).show();
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
        	ImageView imgView;
        	
        	switch(mNum)
        	{
        	case 0:
                v = inflater.inflate(R.layout.electricity, container, false);
                tv = v.findViewById(R.id.image1);
                SmartImageView myImage = (SmartImageView) v.findViewById(R.id.my_image);
                
                String imgUrl = "http://chart.apis.google.com/chart" +
         			   "?chxt=y" +
         			   "&chbh=a,0,5" +
         			   "&chs=700x425" +
         			   "&cht=bvg" +
         			   "&chco=A2C180,3D7930,FF9900" +
         			   "&chd=t:45.524,53.183,34.632|57.4,83.623,57.284|52.868,72.297,69.115" +
         			   "&chtt=Vertical+bar+chart";
                
                myImage.setImageUrl(imgUrl);

                return v;
        	case 1:
        		v = inflater.inflate(R.layout.propane, container, false);
        		tv = v.findViewById(R.id.image2);
                return v;
        	case 2:
        		v = inflater.inflate(R.layout.gasoline, container, false);
                return v;
        	case 3:
        		v = inflater.inflate(R.layout.natural, container, false);
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