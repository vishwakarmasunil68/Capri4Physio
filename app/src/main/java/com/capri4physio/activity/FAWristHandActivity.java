package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.capri4physio.R;

import butterknife.ButterKnife;

public class FAWristHandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fawrist_hand);
        ButterKnife.bind(this);
    }
}
