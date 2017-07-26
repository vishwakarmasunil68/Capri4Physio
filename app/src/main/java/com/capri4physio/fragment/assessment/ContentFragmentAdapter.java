package com.capri4physio.fragment.assessment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Copyright (C) 2015 Mustafa Ozcan
 * Created on 06 May 2015 (www.mustafaozcan.net)
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * *
 * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class ContentFragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;
    private final Context c;
    private String titles[] ;

    public ContentFragmentAdapter(FragmentManager fragmentManager, Context context, String[] titles2) {
        super(fragmentManager);
        titles=titles2;
        c = context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
                return new Categories_Fragment();

            case 1:
                return new Favourite_Fragment();
            case 2:
                return new Favourite_Fragment();
            case 3:
                return new Categories_Fragment();
//            default:
//                return null;

        }
        return null;
    }

    // Returns the page title for the top indicator
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
