package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capri4physio.R;

/**
 * Created by Emobi-Android-002 on 12/6/2016.
 */
public class ViewPrint extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusotm_dialog_edit);
        String p_name=getIntent().getStringExtra("p_name");
        String staff_name=getIntent().getStringExtra("staff_name");
        String number=getIntent().getStringExtra("number");
        String bill_amount=getIntent().getStringExtra("bill_amount");
        String paid_amount=getIntent().getStringExtra("paid_amount");
        String dueamount=getIntent().getStringExtra("due_amount");
        String proname=getIntent().getStringExtra("pro_name");
        String proquantity=getIntent().getStringExtra("pro_quantity");
        String invodate=getIntent().getStringExtra("invodate");
        String status=getIntent().getStringExtra("status");

        TextView patient_name = (TextView) findViewById(R.id.Patient_name);
        patient_name.setText(p_name);
        TextView staffname = (TextView) findViewById(R.id.Staff_Name);
        staffname.setText(staff_name);
        final RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.print_layout);
        TextView bill_number = (TextView) findViewById(R.id.Bill_number);
        bill_number.setText(number);
        TextView billamount = (TextView) findViewById(R.id.Bill_amount);
        billamount.setText(bill_amount);
        TextView paidamount = (TextView) findViewById(R.id.Paid_amount);
        paidamount.setText(paid_amount);
        TextView due_amount = (TextView) findViewById(R.id.Due_amount);
        due_amount.setText(dueamount);
        TextView pro_name = (TextView) findViewById(R.id.Pro_name);
        pro_name.setText(proname);
        TextView pro_quantity = (TextView) findViewById(R.id.Pro_quantity);
        pro_quantity.setText(proquantity);
        TextView invo_date = (TextView) findViewById(R.id.Invoice_date);
        invo_date.setText(invodate);
        TextView invo_staaus = (TextView) findViewById(R.id.Invoice_status);
        invo_staaus.setText(status);

        //adding button click event
        Button dismissButton = (Button) findViewById(R.id.button);
        Button button_print = (Button) findViewById(R.id.button_print);

        button_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                print(relativeLayout);;
            }
        });
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               finish();
            }
        });


    }

    private void print(RelativeLayout relativeLayout) {
        // Get the print manager.
        PrintHelper printHelper = new PrintHelper(this);
        // Set the desired scale mode.
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        // Get the bitmap for the ImageView's drawable.
        Bitmap bitmap = overlay(relativeLayout);
        // Print the bitmap.
        printHelper.printBitmap("Print Bitmap", bitmap);
    }

    public Bitmap overlay(RelativeLayout frame_layout){
        Bitmap bitmap1 = Bitmap.createBitmap(frame_layout.getWidth(), frame_layout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        frame_layout.draw(canvas);
        frame_layout.setDrawingCacheEnabled(true);
        frame_layout.getDrawingCache();
        return frame_layout.getDrawingCache();
        /*image.setImageBitmap(bitmap1);
		*//*bitmap.setDrawingCacheEnabled(true);
		bitmap.buildDrawingCache();*//*
        return bitmap1.copy(Bitmap.Config.ARGB_8888,true);*/
    }

}
