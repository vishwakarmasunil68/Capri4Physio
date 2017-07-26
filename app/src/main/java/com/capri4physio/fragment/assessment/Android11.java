package com.capri4physio.fragment.assessment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.capri4physio.R;

public class Android11 extends Fragment {
	public static Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9;
	public static Spinner spinner10,spinner11,spinner12,spinner13,spinner50,spinner60,spinner70,spinner80,spinner90;
	public static EditText editText1,editText2,editText3,editText4,editText5,editText6;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.android_frag11, container, false);
		spinner1=(Spinner)rootView.findViewById(R.id.spinner1);
		spinner2=(Spinner)rootView.findViewById(R.id.spinner2);
		spinner3=(Spinner)rootView.findViewById(R.id.spinner3);
		spinner4=(Spinner)rootView.findViewById(R.id.spinner4);
		spinner5=(Spinner)rootView.findViewById(R.id.spinner5);
		spinner6=(Spinner)rootView.findViewById(R.id.spinner6);
		spinner8=(Spinner)rootView.findViewById(R.id.spinner8);
		spinner9=(Spinner)rootView.findViewById(R.id.spinner9);
		spinner10=(Spinner)rootView.findViewById(R.id.spinner10);
		spinner11=(Spinner)rootView.findViewById(R.id.spinner11);
		spinner12=(Spinner)rootView.findViewById(R.id.spinner12);
		spinner13=(Spinner)rootView.findViewById(R.id.spinner13);
		editText1=(EditText)rootView.findViewById(R.id.edtxt_1);
		editText2=(EditText)rootView.findViewById(R.id.edtxt_2);
		editText3=(EditText)rootView.findViewById(R.id.edtxt_3);
		editText4=(EditText)rootView.findViewById(R.id.edtxt_4);
		editText5=(EditText)rootView.findViewById(R.id.edtxt_5);
		editText6=(EditText)rootView.findViewById(R.id.edtxt_6);
	        return rootView;
}}
