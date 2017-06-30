package com.eleganceinfoways.pigeeback.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.R;
import com.eleganceinfoways.pigeeback.ServiceDetail;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

/**
 * Created by KHUSHI on 26-May-17.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    Context mContext;

    public ServiceAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_track_listing, parent, false);
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

        TextView tvdate, tvfromto, tvtime, tvratings, tvserviceprovider, tvfrom, lblto, tvto, tvedit, tvdelete,routefreq,tvroutefrequency;
        GetFontstyle getFontstyle;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            getFontstyle = new GetFontstyle(mContext);
            tvdate = (TextView) view.findViewById(R.id.tvdate);
            tvfromto = (TextView) view.findViewById(R.id.tvfromto);
            //tvtime = (TextView)view.findViewById(R.id.tvtime);
            //tvratings = (TextView)view.findViewById(R.id.tvratings);
            tvfrom = (TextView) view.findViewById(R.id.tvfrom);
            lblto = (TextView) view.findViewById(R.id.lblto);
            tvto = (TextView) view.findViewById(R.id.tvto);
            tvedit = (TextView) view.findViewById(R.id.tvedit);
            tvdelete = (TextView) view.findViewById(R.id.tvdelete);
            //tvserviceprovider = (TextView) view.findViewById(R.id.tvserviceprovider);
            routefreq = (TextView) view.findViewById(R.id.routefreq);
            tvroutefrequency = (TextView) view.findViewById(R.id.tvroutefrequency);
            setFontStyle();
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ServiceDetail.class);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }

        public void setFontStyle() {
            if (getFontstyle != null) {
                tvdate.setTypeface(getFontstyle.CoreSansMedium());
                tvfromto.setTypeface(getFontstyle.CoreSansMedium());
                //tvratings.setTypeface(getFontstyle.CoreSansMedium());
                //tvtime.setTypeface(getFontstyle.CoreSansMedium());
                //tvserviceprovider.setTypeface(getFontstyle.CoreSansMedium());
                tvfrom.setTypeface(getFontstyle.CoreSansMedium());
                lblto.setTypeface(getFontstyle.CoreSansMedium());
                tvto.setTypeface(getFontstyle.CoreSansMedium());
                routefreq.setTypeface(getFontstyle.CoreSansMedium());
                tvroutefrequency.setTypeface(getFontstyle.CoreSansMedium());
            }
        }
    }
}

