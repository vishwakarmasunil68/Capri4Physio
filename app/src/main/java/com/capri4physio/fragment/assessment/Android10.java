package com.capri4physio.fragment.assessment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.capri4physio.R;

public class Android10 extends Fragment {
	public static Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9;
	public static Spinner spinner10,spinner11,spinner12,spinner13,spinner14,spinner15,spinner16,spinner1_2,spinner1_1;
	public static EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7,editText8,editText9;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.android_frag10, container, false);
		spinner1=(Spinner)rootView.findViewById(R.id.spinner1);
		spinner2=(Spinner)rootView.findViewById(R.id.spinner2);
		spinner3=(Spinner)rootView.findViewById(R.id.spinner3);
		spinner4=(Spinner)rootView.findViewById(R.id.spinner4);
		spinner5=(Spinner)rootView.findViewById(R.id.spinner5);
		spinner6=(Spinner)rootView.findViewById(R.id.spinner6);
		spinner7=(Spinner)rootView.findViewById(R.id.spinner7);
		spinner8=(Spinner)rootView.findViewById(R.id.spinner8);
		spinner1_1=(Spinner)rootView.findViewById(R.id.spinner1_1);
		spinner1_2=(Spinner)rootView.findViewById(R.id.spinner1_2);
		spinner9=(Spinner)rootView.findViewById(R.id.spinner9);
		spinner10=(Spinner)rootView.findViewById(R.id.spinner10);
		spinner11=(Spinner)rootView.findViewById(R.id.spinner11);
		spinner12=(Spinner)rootView.findViewById(R.id.spinner12);
		spinner13=(Spinner)rootView.findViewById(R.id.spinner13);
		spinner14=(Spinner)rootView.findViewById(R.id.spinner14);
		spinner15=(Spinner)rootView.findViewById(R.id.spinner15);
		spinner16=(Spinner)rootView.findViewById(R.id.spinner16);
		editText1=(EditText)rootView.findViewById(R.id.edtxt_1);
		editText2=(EditText)rootView.findViewById(R.id.edtxt_2);
		editText3=(EditText)rootView.findViewById(R.id.edtxt_3);
		editText4=(EditText)rootView.findViewById(R.id.edtxt_4);
		editText5=(EditText)rootView.findViewById(R.id.edtxt_5);
		editText6=(EditText)rootView.findViewById(R.id.edtxt_6);
		editText7=(EditText)rootView.findViewById(R.id.edtxt_7);
		editText8=(EditText)rootView.findViewById(R.id.edtxt_8);
		editText9=(EditText)rootView.findViewById(R.id.edtxt_9);
	        return rootView;
}}
