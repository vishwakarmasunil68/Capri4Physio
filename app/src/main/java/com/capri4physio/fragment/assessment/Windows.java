package com.capri4physio.fragment.assessment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.capri4physio.R;

public class Windows extends Fragment {
	public static Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9;
	public static Spinner spinner10,spinner11,spinner12,spinner13,spinner14,spinner15,spinner16,spinner,spinner88;
	public static EditText editText1,editText2,editText3,editText4,editText5,editText6,edit,editText8,editText9;
	ScrollView scroll_windows;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
		    View rootView = inflater.inflate(R.layout.windows_frag, container, false);
		spinner1=(Spinner)rootView.findViewById(R.id.spinner1);
		spinner2=(Spinner)rootView.findViewById(R.id.spinner2);
		spinner3=(Spinner)rootView.findViewById(R.id.spinner3);
		spinner4=(Spinner)rootView.findViewById(R.id.spinner4);
		spinner5=(Spinner)rootView.findViewById(R.id.spinner5);
		spinner6=(Spinner)rootView.findViewById(R.id.spinner6);
		spinner7=(Spinner)rootView.findViewById(R.id.spinner7);
		spinner8=(Spinner)rootView.findViewById(R.id.spinner8);
		spinner9=(Spinner)rootView.findViewById(R.id.spinner9);
		spinner10=(Spinner)rootView.findViewById(R.id.spinner10);
		spinner11=(Spinner)rootView.findViewById(R.id.spinner11);
		spinner12=(Spinner)rootView.findViewById(R.id.spinner12);
		spinner13=(Spinner)rootView.findViewById(R.id.spinner13);
		spinner=(Spinner)rootView.findViewById(R.id.spinner);
		editText1=(EditText)rootView.findViewById(R.id.edtxt_1);
		editText2=(EditText)rootView.findViewById(R.id.edtxt_2);
		editText3=(EditText)rootView.findViewById(R.id.edtxt_3);
		editText4=(EditText)rootView.findViewById(R.id.edtxt_4);
		editText5=(EditText)rootView.findViewById(R.id.edtxt_5);
		editText6=(EditText)rootView.findViewById(R.id.edtxt_6);
		edit=(EditText)rootView.findViewById(R.id.edit);
		scroll_windows=(ScrollView) rootView.findViewById(R.id.scroll_windows);
		return rootView;
}
	public Bitmap takeScreenShots() {
		int h = 0;
		Bitmap bitmap = null;
		//get the actual height of scrollview
		for (int i = 0; i < scroll_windows.getChildCount(); i++) {
			h += scroll_windows.getChildAt(i).getHeight();
			scroll_windows.getChildAt(i).setBackgroundResource(R.color.white);
		}
		// create bitmap with target size
		bitmap = Bitmap.createBitmap(scroll_windows.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scroll_windows.draw(canvas);
		return bitmap;
	}

}
