package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.fragment.BillingActivity;

/**
 * Created by Emobi-Android-002 on 8/31/2016.
 */
public class BillAmountDialog extends Activity {
    TextView text_bill_amount,due_amount;
    TextView text_bill__GST;
    public static String pay;
    Button btn;
    public static EditText ed_disc_amount;
    ImageView img;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billdialog);
        text_bill_amount=(TextView)findViewById(R.id.text_bill__amount);
        text_bill__GST = (TextView) findViewById(R.id.text_bill__GST);
        due_amount=(TextView)findViewById(R.id.text_due_amount);
        ed_disc_amount= (EditText) findViewById(R.id.ed_discount);
        img= (ImageView) findViewById(R.id.img_discount_okay);
        linearLayout= (LinearLayout) findViewById(R.id.linearamount);
        btn= (Button) findViewById(R.id.btn_dia);
        Bundle extras=getIntent().getExtras();
         final String finalpayableamoun=extras.getString("finalpayableamoun");
        Log.e("finalpayableamoun",finalpayableamoun);
       final int value=Integer.parseInt(finalpayableamoun);
        text_bill_amount.setText(finalpayableamoun +"Rs.");
        text_bill__GST.setText(BillingActivity.admingst_tax+ " "+ "%");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

//                    linearLayout.setVisibility(View.VISIBLE);
                    String discoutnvalue= ed_disc_amount.getText().toString();
                    ed_disc_amount.setText(discoutnvalue+ " "+"%");
                    final int disvalue=Integer.parseInt(discoutnvalue);
                    final int finalpayment=(value)-(disvalue);
                    pay=String.valueOf(finalpayment);
                    due_amount.setText(pay +"Rs.");

                    Log.e("finalpayment",String.valueOf(finalpayment));
                }
                catch (Exception e){
                    Toast.makeText(BillAmountDialog.this," First enter min. discount value",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (due_amount.getText().toString().length()> 0) {
                    finish();
                }
                else {
                    Toast.makeText(BillAmountDialog.this,"first enter the pay value",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
