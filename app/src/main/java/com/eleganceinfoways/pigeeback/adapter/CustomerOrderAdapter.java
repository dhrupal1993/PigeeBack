package com.eleganceinfoways.pigeeback.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.Orders;
import com.eleganceinfoways.pigeeback.R;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

/**
 * Created by KHUSHI on 26-Jun-17.
 */

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder> {


    Context mContext;


    public CustomerOrderAdapter(Context context) {
        this.mContext = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_customer_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvtime, tvname, tvfromto, lblstatus, tvstatus, lbldeliverdate, tvdeliverdatetime, tvcurrentlocation, lblcurrentlocation;
        GetFontstyle getFontstyle;


        public ViewHolder(View itemView) {
            super(itemView);

            getFontstyle = new GetFontstyle(mContext);
            tvname = (TextView) itemView.findViewById(R.id.tvname);
            tvfromto = (TextView) itemView.findViewById(R.id.tvfromto);
            lblstatus = (TextView) itemView.findViewById(R.id.lblstatus);
            tvstatus = (TextView) itemView.findViewById(R.id.tvstatus);
            lbldeliverdate = (TextView) itemView.findViewById(R.id.lbldeliverdate);
            tvdeliverdatetime = (TextView) itemView.findViewById(R.id.tvdeliverdatetime);
            tvcurrentlocation = (TextView) itemView.findViewById(R.id.tvcurrentlocation);
            lblcurrentlocation = (TextView) itemView.findViewById(R.id.lblcurrentlocation);
            tvtime = (TextView) itemView.findViewById(R.id.tvtime);
            setFontStyle();
        }


        public void setFontStyle() {
            if (getFontstyle != null) {
                tvname.setTypeface(getFontstyle.CoreSansMedium());
                tvfromto.setTypeface(getFontstyle.CoreSansMedium());
                tvcurrentlocation.setTypeface(getFontstyle.CoreSansMedium());
                lblcurrentlocation.setTypeface(getFontstyle.CoreSansMedium());
                tvstatus.setTypeface(getFontstyle.CoreSansMedium());
                lblstatus.setTypeface(getFontstyle.CoreSansMedium());
                tvdeliverdatetime.setTypeface(getFontstyle.CoreSansMedium());
                lbldeliverdate.setTypeface(getFontstyle.CoreSansMedium());
            }
        }


    }
}