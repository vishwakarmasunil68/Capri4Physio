package com.capri4physio.fragment.assessment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.capri4physio.R;


public  class Categories_Fragment1 extends Fragment {
	public static String rate,rate1,rate2,rate3,rate4,rate5,rate6,rate7,
	      rate8,rate9,rate10,rate11,rate12,
			rate13,rate15,rate16,rate17,rate18,rate19,rate20,rate21;
	RadioGroup rg,rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,
			rg10,rg11,rg12,rg13,rg15,rg16,rg17,rg18,rg19,rg20,rg21;
	ImageView img_edit,img_refresh;
	TextView name,email,weight,height,age,bloodgroup;
	@Override

	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.categorie1, container, false);

		return rootView;
	}

}


