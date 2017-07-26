package com.capri4physio.fragment.assessment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter1 extends FragmentStatePagerAdapter {
    public TabPagerAdapter1(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
        case 0:
            //Fragement for Android Tab
            return new Android1();
        case 1:
           //Fragment for Ios Tab
            return new Ios1();
        case 2:
            //Fragment for Windows Tab
            return new Windows1();
        }
		return null;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3; //No of Tabs
	}


    }