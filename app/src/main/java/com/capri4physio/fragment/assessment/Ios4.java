package com.capri4physio.fragment.assessment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capri4physio.R;

public class Ios4 extends Fragment {
	public static TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10,textView11,textView12,textView13,textView14,
			textView15,textView16,textView17,textView18,textView19,textView20,textView21;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View ios = inflater.inflate(R.layout.ios_frag4, container, false);
		 textView1=(TextView)ios.findViewById(R.id.TextView8);
		 textView2=(TextView)ios.findViewById(R.id.TextView9);
		 textView8=(TextView)ios.findViewById(R.id.spinner7);
		 textView9=(TextView)ios.findViewById(R.id.spinner8);
		 textView15=(TextView)ios.findViewById(R.id.TextView15);
		 textView16=(TextView)ios.findViewById(R.id.TextView16);
	        return ios;
}}
