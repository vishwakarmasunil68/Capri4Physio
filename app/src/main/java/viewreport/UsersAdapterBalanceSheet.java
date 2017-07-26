package viewreport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;

import java.util.List;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterBalanceSheet extends RecyclerView.Adapter<UsersAdapterBalanceSheet.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps1> moviesList;
    String invo;
    public static String reason,patient_name,patient_Email;
Context con;
    Activity ctx;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, expense_amount, bill_amount;
        ImageView edit,mail,cancel,prescription,View;
        Button status;
        CardView cv;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_id);
            expense_amount = (TextView) view.findViewById(R.id.txt_patient_ea);
            bill_amount = (TextView) view.findViewById(R.id.txt_patient_ba);
//            cv = (CardView) view.findViewById(R.id.cv);

        }
    }


    public UsersAdapterBalanceSheet(List<InfoApps1> moviesList, Activity ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_expense, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps1 movie = moviesList.get(position);
        holder.title.setText(movie.getId());
        holder.title.setTypeface(Typeface.create("Montez-Regular.ttf", Typeface.BOLD));
        holder.expense_amount.setText(movie.getPaid_amount());
        holder.bill_amount.setText(movie.getBill_amount());


    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
