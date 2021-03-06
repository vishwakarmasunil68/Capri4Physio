package com.capri4physio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.model.cources.CourcesResultPOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.util.AppPreferences;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentBillActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_full_fees)
    LinearLayout ll_full_fees;
    @BindView(R.id.ll_rem_fees)
    LinearLayout ll_rem_fees;
    @BindView(R.id.tv_full_base_fees)
    TextView tv_full_base_fees;
    @BindView(R.id.tv_full_gst_fees)
    TextView tv_full_gst_fees;
    @BindView(R.id.ll_reg_fees)
    LinearLayout ll_reg_fees;
    @BindView(R.id.tv_reg_fees)
    TextView tv_reg_fees;
    @BindView(R.id.tv_reg_base_fees)
    TextView tv_reg_base_fees;
    @BindView(R.id.tv_reg_gst_fees)
    TextView tv_reg_gst_fees;
    @BindView(R.id.tv_rem_fees)
    TextView tv_rem_fees;
    @BindView(R.id.tv_rem_base_fees)
    TextView tv_rem_base_fees;
    @BindView(R.id.tv_rem_gst_fees)
    TextView tv_rem_gst_fees;
    @BindView(R.id.tv_full_fees)
    TextView tv_full_fees;
    @BindView(R.id.btn_print)
    Button btn_print;


    StudentCourseResultPOJO studentCourseResultPOJO;
    CourcesResultPOJO courcesResultPOJO;

    String full_fees="";
    String rem_fees="";
    String reg_fees="";
    String full_gst="";
    String rem_gst="";
    String reg_gst="";
    String name="";
    String course="";
    String date="";
    String total="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_bill);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        studentCourseResultPOJO = (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourseresultpojo");
        courcesResultPOJO = (CourcesResultPOJO) getIntent().getSerializableExtra("coursepojo");
        date=studentCourseResultPOJO.getSc_date();
        course=courcesResultPOJO.getC_name();
        name= AppPreferences.getInstance(getApplicationContext()).getFirstName()+" "+AppPreferences.getInstance(getApplicationContext()).getLastName();
        if (studentCourseResultPOJO.getScFullfees().length() > 0) {
            ll_full_fees.setVisibility(View.VISIBLE);
            ll_reg_fees.setVisibility(View.GONE);
            ll_rem_fees.setVisibility(View.GONE);

            tv_full_fees.setText(courcesResultPOJO.getC_fees());
            try {
                Double fees = Double.parseDouble(courcesResultPOJO.getC_fees());
                double gstfees = fees / 1.18;
                double basefees = fees - gstfees;
                full_fees=String.valueOf(getConvertedPrice(String.valueOf(gstfees)));
                full_gst=String.valueOf(getConvertedPrice(String.valueOf(basefees)));

                tv_full_fees.setText(getConvertedPrice(String.valueOf(fees)));
                tv_full_gst_fees.setText(getConvertedPrice(String.valueOf(basefees)));
                tv_full_base_fees.setText(getConvertedPrice(String.valueOf(gstfees)));

                total=courcesResultPOJO.getC_fees();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            double reg_fees_tot = 0,rem_fees_tot=0;
            if (studentCourseResultPOJO.getScRegFees().length() > 0) {
                ll_full_fees.setVisibility(View.GONE);
                ll_reg_fees.setVisibility(View.VISIBLE);

                try {
                    Double fees = Double.parseDouble(courcesResultPOJO.getC_reg_fees());
                    reg_fees_tot=fees;
                    double gstfees = fees / 1.18;
                    double basefees = fees - gstfees;

                    reg_gst=String.valueOf(getConvertedPrice(String.valueOf(basefees)));
                    reg_fees=String.valueOf(getConvertedPrice(String.valueOf(gstfees)));

                    tv_reg_fees.setText(getConvertedPrice(String.valueOf(fees)));
                    tv_reg_gst_fees.setText(getConvertedPrice(String.valueOf(basefees)));
                    tv_reg_base_fees.setText(getConvertedPrice(String.valueOf(gstfees)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (studentCourseResultPOJO.getScRemFees().length() > 0) {
                ll_full_fees.setVisibility(View.GONE);
                ll_rem_fees.setVisibility(View.VISIBLE);

                try {
                    Double fees = Double.parseDouble(courcesResultPOJO.getC_rem_fees());
                    rem_fees_tot=fees;
                    double gstfees = fees / 1.18;
                    double basefees = fees - gstfees;

                    rem_gst=String.valueOf(getConvertedPrice(String.valueOf(basefees)));
                    rem_fees=String.valueOf(getConvertedPrice(String.valueOf(gstfees)));

                    tv_rem_fees.setText(getConvertedPrice(String.valueOf(fees)));
                    tv_rem_gst_fees.setText(getConvertedPrice(String.valueOf(basefees)));
                    tv_rem_base_fees.setText(getConvertedPrice(String.valueOf(gstfees)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            total=String.valueOf(reg_fees_tot+rem_fees_tot);
        }


        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentBillActivity.this,IncomeReportPrintActivity.class);
                intent.putExtra("type","studentreport");
                intent.putExtra("full_fees",full_fees);
                intent.putExtra("rem_fees",rem_fees);
                intent.putExtra("reg_fees",reg_fees);
                intent.putExtra("full_gst",full_gst);
                intent.putExtra("rem_gst",rem_gst);
                intent.putExtra("reg_gst",reg_gst);
                intent.putExtra("name",name);
                intent.putExtra("course",course);
                intent.putExtra("date",date);
                intent.putExtra("total",total);
                intent.putExtra("comt",courcesResultPOJO.getC_comt());
                intent.putExtra("id",studentCourseResultPOJO.getScId());
                intent.putExtra("stu_id",studentCourseResultPOJO.getScSid());

                startActivity(intent);

            }
        });
    }

    public String getConvertedPrice(String price) {
        try {
            double val = Double.parseDouble(price);
            DecimalFormat f = new DecimalFormat("##.##");
            return String.valueOf(f.format(val));
        } catch (Exception e) {
            e.printStackTrace();
            return price;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
