package com.capri4physio.fragment.assessment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;


public  class Categories_Fragment extends Fragment {
	public static String rate = " ",rate1="",rate2="",rate3="",rate4="",rate5="",rate6="",rate7="",
	      rate8="",rate9="",rate10="",rate11="",rate12="",
			rate13="",rate15="",rate16="",rate17="",rate18="",rate19="",rate20="",rate21="";
	RadioGroup rg,rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,
			rg10,rg11,rg12,rg13,rg15,rg16,rg17,rg18,rg19,rg20,rg21;

//	public static EditText sapp_right,sapp_left;
	ImageView img_edit,img_refresh;
	TextView name,email,weight,height,age,bloodgroup;
	@Override

	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.categories, container, false);
		rg=(RadioGroup)rootView.findViewById(R.id.rg);
		rg1=(RadioGroup)rootView.findViewById(R.id.rg1);
		rg2=(RadioGroup)rootView.findViewById(R.id.rg2);
		rg3=(RadioGroup)rootView.findViewById(R.id.rg3);
		rg4=(RadioGroup)rootView.findViewById(R.id.rg4);
		rg5=(RadioGroup)rootView.findViewById(R.id.rg5);
		rg6=(RadioGroup)rootView.findViewById(R.id.rg6);
		rg7=(RadioGroup)rootView.findViewById(R.id.rg7);
		rg8=(RadioGroup)rootView.findViewById(R.id.rg8);
		rg9=(RadioGroup)rootView.findViewById(R.id.rg9);
		rg10=(RadioGroup)rootView.findViewById(R.id.rg10);
		rg11=(RadioGroup)rootView.findViewById(R.id.rg11);
		rg12=(RadioGroup)rootView.findViewById(R.id.rg12);
		rg13=(RadioGroup)rootView.findViewById(R.id.rg13);
		rg15=(RadioGroup)rootView.findViewById(R.id.rg15);
		rg16=(RadioGroup)rootView.findViewById(R.id.rg16);
		rg17=(RadioGroup)rootView.findViewById(R.id.rg17);
		rg18=(RadioGroup)rootView.findViewById(R.id.rg18);
		rg19=(RadioGroup)rootView.findViewById(R.id.rg19);
		rg20=(RadioGroup)rootView.findViewById(R.id.rg20);
		rg21=(RadioGroup)rootView.findViewById(R.id.rg21);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg.getCheckedRadioButtonId();
				View radioButton = rg.findViewById(id);
				int radioId = rg.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg.getChildAt(radioId);
				rate = (String) btn.getText();
				Toast.makeText(getActivity(), rate, Toast.LENGTH_LONG).show();
				int pos = rg.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg3.getCheckedRadioButtonId();
				View radioButton = rg3.findViewById(id);
				int radioId = rg3.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg3.getChildAt(radioId);
				rate3 = (String) btn.getText();
				Toast.makeText(getActivity(), rate3, Toast.LENGTH_LONG).show();
				int pos = rg3.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg4.getCheckedRadioButtonId();
				View radioButton = rg4.findViewById(id);
				int radioId = rg4.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg4.getChildAt(radioId);
				rate4 = (String) btn.getText();
				Toast.makeText(getActivity(), rate4, Toast.LENGTH_LONG).show();
				int pos = rg4.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg5.getCheckedRadioButtonId();
				View radioButton = rg5.findViewById(id);
				int radioId = rg5.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg5.getChildAt(radioId);
				rate5 = (String) btn.getText();
				Toast.makeText(getActivity(), rate5, Toast.LENGTH_LONG).show();
				int pos = rg5.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg6.getCheckedRadioButtonId();
				View radioButton = rg6.findViewById(id);
				int radioId = rg6.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg6.getChildAt(radioId);
				rate6 = (String) btn.getText();
				Toast.makeText(getActivity(), rate6, Toast.LENGTH_LONG).show();
				int pos = rg6.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg7.getCheckedRadioButtonId();
				View radioButton = rg7.findViewById(id);
				int radioId = rg7.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg7.getChildAt(radioId);
				rate7 = (String) btn.getText();
				Toast.makeText(getActivity(), rate7, Toast.LENGTH_LONG).show();
				int pos = rg7.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg1.getCheckedRadioButtonId();
				View radioButton = rg1.findViewById(id);
				int radioId = rg1.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg1.getChildAt(radioId);
				rate1 = (String) btn.getText();
				Toast.makeText(getActivity(), rate1, Toast.LENGTH_LONG).show();
				int pos = rg1.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg8.getCheckedRadioButtonId();
				View radioButton = rg8.findViewById(id);
				int radioId = rg8.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg8.getChildAt(radioId);
				rate8 = (String) btn.getText();
				Toast.makeText(getActivity(), rate8, Toast.LENGTH_LONG).show();
				int pos = rg8.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg9.getCheckedRadioButtonId();
				View radioButton = rg9.findViewById(id);
				int radioId = rg9.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg9.getChildAt(radioId);
				rate9 = (String) btn.getText();
				Toast.makeText(getActivity(), rate9, Toast.LENGTH_LONG).show();
				int pos = rg9.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg2.getCheckedRadioButtonId();
				View radioButton = rg2.findViewById(id);
				int radioId = rg2.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg.getChildAt(radioId);
				rate2 = (String) btn.getText();
				Toast.makeText(getActivity(), rate2, Toast.LENGTH_LONG).show();
				int pos = rg2.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg10.getCheckedRadioButtonId();
				View radioButton = rg10.findViewById(id);
				int radioId = rg10.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg10.getChildAt(radioId);
				rate10 = (String) btn.getText();
				Toast.makeText(getActivity(), rate10, Toast.LENGTH_LONG).show();
				int pos = rg10.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg11.getCheckedRadioButtonId();
				View radioButton = rg11.findViewById(id);
				int radioId = rg11.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg11.getChildAt(radioId);
				rate11 = (String) btn.getText();
				Toast.makeText(getActivity(), rate11, Toast.LENGTH_LONG).show();
				int pos = rg11.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg12.getCheckedRadioButtonId();
				View radioButton = rg12.findViewById(id);
				int radioId = rg12.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg12.getChildAt(radioId);
				rate12 = (String) btn.getText();
				Toast.makeText(getActivity(), rate12, Toast.LENGTH_LONG).show();
				int pos = rg12.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg13.getCheckedRadioButtonId();
				View radioButton = rg13.findViewById(id);
				int radioId = rg13.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg13.getChildAt(radioId);
				rate13 = (String) btn.getText();
				Toast.makeText(getActivity(), rate13, Toast.LENGTH_LONG).show();
				int pos = rg13.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg15.getCheckedRadioButtonId();
				View radioButton = rg15.findViewById(id);
				int radioId = rg15.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg15.getChildAt(radioId);
				rate15 = (String) btn.getText();
				Toast.makeText(getActivity(), rate15, Toast.LENGTH_LONG).show();
				int pos = rg15.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg16.getCheckedRadioButtonId();
				View radioButton = rg16.findViewById(id);
				int radioId = rg16.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg16.getChildAt(radioId);
				rate16 = (String) btn.getText();
				Toast.makeText(getActivity(), rate16, Toast.LENGTH_LONG).show();
				int pos = rg16.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg17.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg17.getCheckedRadioButtonId();
				View radioButton = rg17.findViewById(id);
				int radioId = rg17.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg17.getChildAt(radioId);
				rate17 = (String) btn.getText();
				Toast.makeText(getActivity(), rate17, Toast.LENGTH_LONG).show();
				int pos = rg17.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg18.getCheckedRadioButtonId();
				View radioButton = rg18.findViewById(id);
				int radioId = rg18.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg18.getChildAt(radioId);
				rate18 = (String) btn.getText();
				Toast.makeText(getActivity(), rate18, Toast.LENGTH_LONG).show();
				int pos = rg18.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg19.getCheckedRadioButtonId();
				View radioButton = rg19.findViewById(id);
				int radioId = rg19.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg19.getChildAt(radioId);
				rate19 = (String) btn.getText();
				Toast.makeText(getActivity(), rate19, Toast.LENGTH_LONG).show();
				int pos = rg19.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg20.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg20.getCheckedRadioButtonId();
				View radioButton = rg20.findViewById(id);
				int radioId = rg20.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg20.getChildAt(radioId);
				rate20 = (String) btn.getText();
				Toast.makeText(getActivity(), rate20, Toast.LENGTH_LONG).show();
				int pos = rg20.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		rg21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = rg21.getCheckedRadioButtonId();
				View radioButton = rg21.findViewById(id);
				int radioId = rg21.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg.getChildAt(radioId);
				rate21 = (String) btn.getText();
				Toast.makeText(getActivity(), rate21, Toast.LENGTH_LONG).show();
				int pos = rg21.indexOfChild(rootView.findViewById(checkedId));
			}
		});
		return rootView;
	}

}


