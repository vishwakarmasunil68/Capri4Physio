package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.capri4physio.R;
import com.capri4physio.util.TagUtils;
import com.squareup.picasso.Picasso;

public class MoterExamViewActivity extends AppCompatActivity {
    ImageView iv_moter_exam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moter_exam_view);
        iv_moter_exam= (ImageView) findViewById(R.id.iv_moter_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){
            String url=bundle.getString("image_url");
            Log.d(TagUtils.getTag(),"motor exam image url:-"+url);
            Picasso.with(this).load(url).into(iv_moter_exam);
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
