package com.capri4physio.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.capri4physio.R;
import com.capri4physio.listener.DialogListener;
import com.capri4physio.net.ApiConfig;


/**
 * Created by prabhunathy on 2/10/16.
 */
public class PaymentTypeDialog extends DialogFragment {

    private DialogListener<String> mDialogListener;
    private RadioGroup mRadioGroup;
    private String mPaymentType = "Pay at premises";

    /**
     * Create a new instance of ChangePasswordDialog
     */
    public static PaymentTypeDialog newInstance(String paymentType) {
        PaymentTypeDialog f = new PaymentTypeDialog();
        Bundle bundle = new Bundle();
        bundle.putString("payment_type", paymentType);
        f.setArguments(bundle);
        return f;
    }

    public void setDialogListener(DialogListener<String> mDialogListener) {
        this.mDialogListener = mDialogListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPaymentType = getArguments().getString("payment_type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_payment_type, container, false);
        getDialog().setTitle(getString(R.string.title_select_payment_type));
        mRadioGroup = (RadioGroup) v.findViewById(R.id.radio_group);
        RadioButton radio = (RadioButton) v.findViewById(R.id.radio_paypremises);
        radio.setText(mPaymentType);

        Button btnOk = (Button) v.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) v.findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    mPaymentType = rb.getText().toString();
                }
            }
        });

        return v;

    }


    private void paymentType() {
        dismiss();
        mDialogListener.onDialogResult(mPaymentType, 1);
    }

}
