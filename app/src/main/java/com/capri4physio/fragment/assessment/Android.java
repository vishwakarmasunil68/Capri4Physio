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

public class Android extends Fragment {
    public static Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner9;
    public static Spinner spinner10, spinner11, spinner12, spinner13, spinner, spinner60, spinner70, spinner80, spinner90;
    public static EditText editText1, editText2, editText3, editText4, editText5, editText6, edit;
    ScrollView scroll_frag_left;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.android_frag, container, false);
        spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
        spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
        spinner3 = (Spinner) rootView.findViewById(R.id.spinner3);
        spinner4 = (Spinner) rootView.findViewById(R.id.spinner4);
        spinner5 = (Spinner) rootView.findViewById(R.id.spinner5);
        spinner6 = (Spinner) rootView.findViewById(R.id.spinner6);
        spinner7 = (Spinner) rootView.findViewById(R.id.spinner7);
        spinner8 = (Spinner) rootView.findViewById(R.id.spinner8);
        spinner9 = (Spinner) rootView.findViewById(R.id.spinner9);
        spinner10 = (Spinner) rootView.findViewById(R.id.spinner10);
        spinner11 = (Spinner) rootView.findViewById(R.id.spinner11);
        spinner12 = (Spinner) rootView.findViewById(R.id.spinner12);
        spinner13 = (Spinner) rootView.findViewById(R.id.spinner13);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        editText1 = (EditText) rootView.findViewById(R.id.edtxt_1);
        editText2 = (EditText) rootView.findViewById(R.id.edtxt_2);
        editText3 = (EditText) rootView.findViewById(R.id.edtxt_3);
        editText4 = (EditText) rootView.findViewById(R.id.edtxt_4);
        editText5 = (EditText) rootView.findViewById(R.id.edtxt_5);
        editText6 = (EditText) rootView.findViewById(R.id.edtxt_6);
        edit = (EditText) rootView.findViewById(R.id.edit);
        scroll_frag_left = (ScrollView) rootView.findViewById(R.id.scroll_frag_left);
        return rootView;
    }

    public Bitmap takeScreenShots() {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scroll_frag_left.getChildCount(); i++) {
            h += scroll_frag_left.getChildAt(i).getHeight();
            scroll_frag_left.getChildAt(i).setBackgroundResource(R.color.white);
        }
        // create bitmap with target size
        bitmap = Bitmap.createBitmap(scroll_frag_left.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scroll_frag_left.draw(canvas);
        return bitmap;
    }

}
