package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.capri4physio.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTherapistActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_dob)
    EditText et_dob;
    @BindView(R.id.et_date_of_joining)
    EditText et_date_of_joining;
    @BindView(R.id.et_date_of_contract)
    EditText et_date_of_contract;
    @BindView(R.id.rg_gender)
    EditText rg_gender;
    @BindView(R.id.rg_male)
    EditText rg_male;
    @BindView(R.id.rg_female)
    EditText rg_female;
    @BindView(R.id.et_designation)
    EditText et_designation;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_pin_code)
    EditText et_pin_code;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_qualification)
    EditText et_qualification;
    @BindView(R.id.et_degree)
    EditText et_degree;
    @BindView(R.id.et_institute)
    EditText et_institute;
    @BindView(R.id.et_university)
    EditText et_university;
    @BindView(R.id.et_duration)
    EditText et_duration;
    @BindView(R.id.et_average)
    EditText et_average;
    @BindView(R.id.et_organization)
    EditText et_organization;
    @BindView(R.id.et_experience)
    EditText et_experience;
    @BindView(R.id.et_experience_duration)
    EditText et_experience_duration;
    @BindView(R.id.et_work)
    EditText et_work;
    @BindView(R.id.btn_save)
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_therapist);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
