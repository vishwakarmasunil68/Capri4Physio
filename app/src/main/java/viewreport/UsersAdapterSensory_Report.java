/*
package viewreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.fragment.assessment.InfoApps;

import java.util.List;

*/
/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 *//*


public class UsersAdapterSensory_Report extends RecyclerView.Adapter<UsersAdapterSensory_Report.MyViewHolder> {
    Boolean flag=false;
    private List<InfoReport> moviesList;
Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView edit,medication,prescription;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_name);
            genre = (TextView) view.findViewById(R.id.txt_patient_id);

        }
    }


    public UsersAdapterSensory_Report(List<InfoReport> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_sens_report_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoReport movie = moviesList.get(position);
        if (movie.getNumber().length()>0){
            holder.title.setText(movie.getNumber());
        }
        else
        {
            holder.title.setVisibility(View.GONE);
        }
        String date=movie.getNumber();


        holder.genre.setText(movie.getDataAdd());






    }
    public String getDate(String s){
        String date="";
        try{
            String[] str=s.split("-");
            date=str[2]+"-"+str[1]+"-"+str[0];
        }
        catch (Exception e){
            date=s;
        }
        return date;
    }

    @Override
    public int getItemCount() {
        Log.e("sizepre",String.valueOf(moviesList.size()));
        return moviesList.size();
    }
}
*/
