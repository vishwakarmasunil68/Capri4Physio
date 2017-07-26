package com.capri4physio.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.listener.DialogListener;


/**
 * Created by prabhunathy on 2/10/16.
 */
public class PersonalInfoDialog extends DialogFragment {

    private DialogListener<String> mDialogListener;
    private TextView mTxtSpeedTest;

    /**
     * Create a new instance of PersonalInfoDialog
     */
    public static PersonalInfoDialog newInstance() {
        PersonalInfoDialog f = new PersonalInfoDialog();
        return f;
    }

    public void setDialogListener(DialogListener<String> mDialogListener) {
        this.mDialogListener = mDialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_personal_info, container, false);
        getDialog().setTitle(getString(R.string.title_personal_info));
        Button btnContinue = (Button) v.findViewById(R.id.btn_save);
        Button btnCancel = (Button) v.findViewById(R.id.btn_cancel);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mDialogListener.onDialogResult("Continue", 1);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;

    }
}
