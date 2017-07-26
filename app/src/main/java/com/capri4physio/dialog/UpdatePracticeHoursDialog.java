package com.capri4physio.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.capri4physio.R;
import com.capri4physio.listener.DialogListener;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by prabhunathy on 2/10/16.
 */
public class UpdatePracticeHoursDialog extends DialogFragment {

    private DialogListener<Bundle> mDialogListener;

    private static boolean isStartTimeClick = false;
    private static boolean isEndTimeClick = false;
    private static String mStartTime = "";
    private static String mEndTime = "";


    private static TextView mTxtStartTime;
    private static TextView mTxtEndTime;
    private ImageView mImgStartTime;
    private ImageView mImgEndTime;
    private Button mBtnSave;

    /**
     * Create a new instance of ChangePasswordDialog
     */
    public static UpdatePracticeHoursDialog newInstance(String sTime, String eTime) {
        UpdatePracticeHoursDialog f = new UpdatePracticeHoursDialog();

        Bundle bundle = new Bundle();
        bundle.putString(ApiConfig.START_TIME, sTime);
        bundle.putString(ApiConfig.END_TIME, eTime);
        f.setArguments(bundle);
        return f;
    }

    public void setDialogListener(DialogListener<Bundle> mDialogListener) {
        this.mDialogListener = mDialogListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mStartTime = getArguments().getString(ApiConfig.START_TIME);
            mEndTime = getArguments().getString(ApiConfig.END_TIME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_practice_hours, container, false);
        getDialog().setTitle(getString(R.string.title_change_password));
        mTxtStartTime = (TextView) view.findViewById(R.id.txt_start_time);
        mTxtEndTime = (TextView) view.findViewById(R.id.txt_end_time);
        mImgStartTime = (ImageView) view.findViewById(R.id.img_star_time);
        mImgEndTime = (ImageView) view.findViewById(R.id.img_end_time);

        mTxtStartTime.setText(mStartTime);
        mTxtEndTime.setText(mEndTime);
        mImgStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartTimeClick = true;
                isEndTimeClick = false;
                showTimePickerDialog();
            }
        });

        mImgEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartTimeClick =false;
                isEndTimeClick = true;
                showTimePickerDialog();
            }
        });

        Button btnUpdate = (Button) view.findViewById(R.id.btn_save);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePracticeHours();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;

    }


    private void updatePracticeHours() {

        dismiss();
        Bundle b = new Bundle();
        b.putString(ApiConfig.START_TIME, mTxtStartTime.getText().toString().trim());
        b.putString(ApiConfig.END_TIME, mTxtEndTime.getText().toString().trim());
        mDialogListener.onDialogResult(b, 1);

    }


    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }


    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            Date d = c.getTime();
            if (isStartTimeClick) {
                mStartTime = new SimpleDateFormat("hh:mm a").format(d);
                mTxtStartTime.setText(mStartTime);
                AppLog.i("Timepicker", "timepicker : " + mStartTime);
            }

            if (isEndTimeClick) {
                mEndTime = new SimpleDateFormat("hh:mm a").format(d);
                mTxtEndTime.setText(mEndTime);
                AppLog.i("Timepicker", "timepicker : " + mEndTime);
            }
        }
    }
}

