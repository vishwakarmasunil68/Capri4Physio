package com.capri4physio.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.listener.DialogListener;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Utils;


/**
 * Created by prabhunathy on 2/10/16.
 */
public class ChangePasswordDialog extends DialogFragment {

    private DialogListener<Bundle> mDialogListener;
    private EditText mEdtxtOldPassword;
    private EditText mEdtxtNewPassword;

    /**
     * Create a new instance of ChangePasswordDialog
     */
    public static ChangePasswordDialog newInstance() {
        ChangePasswordDialog f = new ChangePasswordDialog();
        return f;
    }

    public void setDialogListener(DialogListener<Bundle> mDialogListener) {
        this.mDialogListener = mDialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_change_password, container, false);
        getDialog().setTitle(getString(R.string.title_change_password));
        mEdtxtOldPassword = (EditText) v.findViewById(R.id.edtxt_old_password);
        mEdtxtNewPassword = (EditText) v.findViewById(R.id.edtxt_new_password);
        Button btnUpdate = (Button) v.findViewById(R.id.btn_save);
        Button btnCancel = (Button) v.findViewById(R.id.btn_cancel);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
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


    private void updatePassword() {

        if (!isValid())
            return;

        dismiss();
        Bundle b = new Bundle();
        b.putString(ApiConfig.OLD_PASSWORD, mEdtxtOldPassword.getText().toString().trim());
        b.putString(ApiConfig.NEW_PASSWORD, mEdtxtNewPassword.getText().toString().trim());
        mDialogListener.onDialogResult(b, 1);

    }

    /**
     * Validation to check user inputs
     *
     * @return
     */
    private boolean isValid() {

        String odlPassword = mEdtxtOldPassword.getText().toString().trim();
        String newPassword = mEdtxtNewPassword.getText().toString().trim();

        if (odlPassword.isEmpty()) {
            mEdtxtOldPassword.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_old_password));
            return false;
        }

        if (newPassword.isEmpty()) {
            mEdtxtNewPassword.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_new_password));
            return false;
        }

        return true;
    }
}
