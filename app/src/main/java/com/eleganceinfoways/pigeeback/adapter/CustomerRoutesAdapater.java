package com.eleganceinfoways.pigeeback.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.R;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

/**
 * Created by KHUSHI on 26-Jun-17.
 */

public class CustomerRoutesAdapater extends RecyclerView.Adapter<CustomerRoutesAdapater.ViewHolder> {


    Context mContext;

    public CustomerRoutesAdapater(Context context) {
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer_routes_list, parent, false);
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
            // tvtime = (TextView) itemView.findViewById(R.id.tvtime);
            setFontStyle();
        }


        public void setFontStyle() {
            if (getFontstyle != null) {

            }
        }


    }
}