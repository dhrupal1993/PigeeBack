package com.eleganceinfoways.pigeeback.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.OrderDetailActivity;
import com.eleganceinfoways.pigeeback.Orders;
import com.eleganceinfoways.pigeeback.R;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

/**
 * Created by KHUSHI on 22-Jun-17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    Context mContext;
    Orders orders;

    public OrderAdapter(Context context,Orders orders) {
        this.mContext = context;
        this.orders = orders;
    }


    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvname, tvfromto, lblcurrentlocation, tvcurrentlocation, lblstatus, tvstatus;
        GetFontstyle getFontstyle;

        public ViewHolder(View itemView) {
            super(itemView);

            getFontstyle = new GetFontstyle(mContext);
            tvname = (TextView) itemView.findViewById(R.id.tvname);
            tvfromto = (TextView) itemView.findViewById(R.id.tvfromto);
            lblcurrentlocation = (TextView) itemView.findViewById(R.id.lblcurrentlocation);
            tvcurrentlocation = (TextView) itemView.findViewById(R.id.tvcurrentlocation);
            lblstatus = (TextView) itemView.findViewById(R.id.lblstatus);
            tvstatus = (TextView) itemView.findViewById(R.id.tvstatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //orders.openFragment();


                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
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
                lblcurrentlocation.setTypeface(getFontstyle.CoreSansMedium());
                tvcurrentlocation.setTypeface(getFontstyle.CoreSansMedium());
                lblstatus.setTypeface(getFontstyle.CoreSansMedium());
                tvstatus.setTypeface(getFontstyle.CoreSansMedium());
            }
        }




        @Override
        public void onClick(View v) {


        }
    }


}

