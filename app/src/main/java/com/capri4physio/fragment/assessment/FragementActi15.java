package com.capri4physio.fragment.assessment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.capri4physio.R;

public class FragementActi15 extends FragmentActivity {
	ViewPager Tab;
    TabPagerAdapter15 TabAdapter;
	ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_main15);

        TabAdapter = new TabPagerAdapter15(getSupportFragmentManager());

        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                    	actionBar = getActionBar();
                    	actionBar.setSelectedNavigationItem(position);                    }
                });
        Tab.setAdapter(TabAdapter);

        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){

			@Override
			public void onTabReselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			 public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

	            Tab.setCurrentItem(tab.getPosition());
	        }

			@Override
			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}};
			//Add New Tab
			actionBar.addTab(actionBar.newTab().setText("Left").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("Parameters").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("Right").setTabListener(tabListener));

    }



    
}
