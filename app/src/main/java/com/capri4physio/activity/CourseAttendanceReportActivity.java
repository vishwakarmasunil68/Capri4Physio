package com.capri4physio.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.capri4physio.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseAttendanceReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    @BindView(R.id.btn_add_attendance)
    Button btn_add_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_attendance_report);
        ButterKnife.bind(this);

        btn_add_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDateDialog();
            }
        });
    }
    EditText editText;
    public void showSelectDateDialog(){
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_attendance_date);
        dialog1.setTitle("Wallet Amount");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_select= (Button) dialog1.findViewById(R.id.btn_select);
        Button btn_to_hour= (Button) dialog1.findViewById(R.id.btn_to_hour);
        Button btn_from_hour= (Button) dialog1.findViewById(R.id.btn_from_hour);
        Button btn_ok= (Button) dialog1.findViewById(R.id.btn_ok);
        final EditText et_date= (EditText) dialog1.findViewById(R.id.et_date);
        final EditText et_to_hour= (EditText) dialog1.findViewById(R.id.et_to_hour);
        final EditText et_from_hour= (EditText) dialog1.findViewById(R.id.et_from_hour);


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText=et_date;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CourseAttendanceReportActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Date of Birth");
            }
        });

        btn_to_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText=et_from_hour;
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(CourseAttendanceReportActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "From Time Picker");
            }
        });

        btn_from_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText=et_to_hour;
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(CourseAttendanceReportActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "To Time Picker");
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String day = "";
        String month = "";
        if(dayOfMonth>9){
            day=String.valueOf(dayOfMonth);
        }else{
            day="0"+dayOfMonth;
        }
        if ((monthOfYear + 1) > 9) {
            month=String.valueOf(monthOfYear+1);
        } else {
            month="0"+(monthOfYear+1);
        }
        String date=day+"-"+month+"-"+year;
        if(editText!=null){
            editText.setText(date);
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = "";
        if (hourOfDay < 10) {
            hour = "0" + hourOfDay;
        } else {
            hour = String.valueOf(hourOfDay);
        }

        String minutes = "";

        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = String.valueOf(minute);
        }

        String time = hour + ":" + minutes;
        if(editText!=null){
            editText.setText(time);
        }
    }
}
