package com.capri4physio.fragment.assessment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter5 extends FragmentStatePagerAdapter {
    public TabPagerAdapter5(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
        case 0:
            //Fragement for Android Tab
            return new Android5();
        case 1:
           //Fragment for Ios Tab
            return new Ios5();
        case 2:
            //Fragment for Windows Tab
            return new Windows5();
        }
		return null;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3; //No of Tabs
	}


    }