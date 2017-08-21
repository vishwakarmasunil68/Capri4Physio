package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.model.cources.CourcesResultPOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;

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


    StudentCourseResultPOJO studentCourseResultPOJO;
    CourcesResultPOJO courcesResultPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_bill);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        studentCourseResultPOJO= (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourseresultpojo");
        courcesResultPOJO=(CourcesResultPOJO)getIntent().getSerializableExtra("coursepojo");

        if(studentCourseResultPOJO.getScFullfees().length()>0){
            ll_full_fees.setVisibility(View.VISIBLE);
            ll_reg_fees.setVisibility(View.GONE);
            ll_rem_fees.setVisibility(View.GONE);

            tv_full_fees.setText(courcesResultPOJO.getC_fees());
            try{
                Double fees=Double.parseDouble(courcesResultPOJO.getC_fees());
                double gstfees=fees/1.18;
                double basefees=fees-gstfees;

                tv_full_fees.setText(getConvertedPrice(String.valueOf(fees)));
                tv_full_gst_fees.setText(getConvertedPrice(String.valueOf(basefees)));
                tv_full_base_fees.setText(getConvertedPrice(String.valueOf(gstfees)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            if(studentCourseResultPOJO.getScRegFees().length()>0){
                ll_full_fees.setVisibility(View.GONE);
                ll_reg_fees.setVisibility(View.VISIBLE);

                try{
                    Double fees=Double.parseDouble(courcesResultPOJO.getC_reg_fees());
                    double gstfees=fees/1.18;
                    double basefees=fees-gstfees;

                    tv_reg_fees.setText(getConvertedPrice(String.valueOf(fees)));
                    tv_reg_gst_fees.setText(getConvertedPrice(String.valueOf(basefees)));
                    tv_reg_base_fees.setText(getConvertedPrice(String.valueOf(gstfees)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(studentCourseResultPOJO.getScRemFees().length()>0){
                ll_full_fees.setVisibility(View.GONE);
                ll_rem_fees.setVisibility(View.VISIBLE);

                try{
                    Double fees=Double.parseDouble(courcesResultPOJO.getC_rem_fees());
                    double gstfees=fees/1.18;
                    double basefees=fees-gstfees;

                    tv_rem_fees.setText(getConvertedPrice(String.valueOf(fees)));
                    tv_rem_gst_fees.setText(getConvertedPrice(String.valueOf(basefees)));
                    tv_rem_base_fees.setText(getConvertedPrice(String.valueOf(gstfees)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
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
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
