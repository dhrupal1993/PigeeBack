package com.eleganceinfoways.pigeeback.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.R;
import com.eleganceinfoways.pigeeback.ServiceProviderDetails;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

/**
 * Created by KHUSHI on 25-May-17.
 */

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ViewHolder> {

    Context mContext;


    public ServiceProviderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvname,tvfromto,lbldeparturedate,tvdeparturedate,lblarivaldate,tvarrivaldate,tvtime;
        ImageView imgvehicle;
        GetFontstyle getFontstyle;


        public ViewHolder(View view) {
            super(view);
            getFontstyle = new GetFontstyle(mContext);
            tvname = (TextView) view.findViewById(R.id.tvname);
            tvfromto = (TextView) view.findViewById(R.id.tvfromto);
            lbldeparturedate = (TextView) view.findViewById(R.id.lbldeparturedate);
            tvdeparturedate = (TextView) view.findViewById(R.id.tvdeparturedate);
            tvarrivaldate = (TextView) view.findViewById(R.id.tvarrivaldate);
            lblarivaldate = (TextView) view.findViewById(R.id.lblarivaldate);
            tvtime = (TextView) view.findViewById(R.id.tvtime);
            imgvehicle = (ImageView) view.findViewById(R.id.imgvehicle);

            setFontStyle();
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, ServiceProviderDetails.class);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }

        public void setFontStyle() {
            if (getFontstyle != null) {
                tvname.setTypeface(getFontstyle.CoreSansMedium());
                tvarrivaldate.setTypeface(getFontstyle.CoreSansMedium());
                tvdeparturedate.setTypeface(getFontstyle.CoreSansMedium());
                tvfromto.setTypeface(getFontstyle.CoreSansMedium());
                lblarivaldate.setTypeface(getFontstyle.CoreSansMedium());
                lbldeparturedate.setTypeface(getFontstyle.CoreSansMedium());
            }
        }
    }

}
