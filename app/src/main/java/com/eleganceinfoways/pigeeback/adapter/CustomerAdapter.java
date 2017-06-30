package com.eleganceinfoways.pigeeback.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.CustomerDetail;
import com.eleganceinfoways.pigeeback.R;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

/**
 * Created by KHUSHI on 23-Jun-17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {


    Context mContext;

    public CustomerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvname, tvfromto, lbldeparturedate, tvdeparturedate, lblarivaldate, tvarrivaldate;
        GetFontstyle getFontstyle;

        public ViewHolder(View itemView) {
            super(itemView);

            getFontstyle = new GetFontstyle(mContext);
            tvname = (TextView) itemView.findViewById(R.id.tvname);
            tvfromto = (TextView) itemView.findViewById(R.id.tvfromto);
            lbldeparturedate = (TextView) itemView.findViewById(R.id.lbldeparturedate);
            tvdeparturedate = (TextView) itemView.findViewById(R.id.tvdeparturedate);
            lblarivaldate = (TextView) itemView.findViewById(R.id.lblarivaldate);
            tvarrivaldate = (TextView) itemView.findViewById(R.id.tvarrivaldate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CustomerDetail.class);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
            });

            setFontStyle();
        }


        public void setFontStyle() {
            if (getFontstyle != null) {
                tvname.setTypeface(getFontstyle.CoreSansMedium());
                tvfromto.setTypeface(getFontstyle.CoreSansMedium());
                lbldeparturedate.setTypeface(getFontstyle.CoreSansMedium());
                tvdeparturedate.setTypeface(getFontstyle.CoreSansMedium());
                lblarivaldate.setTypeface(getFontstyle.CoreSansMedium());
                tvarrivaldate.setTypeface(getFontstyle.CoreSansMedium());
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

}
