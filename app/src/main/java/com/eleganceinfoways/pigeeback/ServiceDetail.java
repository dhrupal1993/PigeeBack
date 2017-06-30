package com.eleganceinfoways.pigeeback;

        import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

/**
 * Created by KHUSHI on 25-May-17.
 */

public class ServiceDetail extends Activity implements View.OnClickListener
{

    Toolbar toolbar;
    GetFontstyle getFontstyle;
    ImageView imgedit,imgdelete;
    TextView tvtitle,tvratings,tvaddressline1,tvaddressline2,tvcity,tvphone,tvtransportdetail,lblfrom,lblto,
            lbldepart,tvdepart,tvto,tvfrom,lbldeliverydetail,lbldeparturedate,lblarrivaldate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setTitle("Service Detail");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getFontstyle = new GetFontstyle(ServiceDetail.this);
        tvtitle = (TextView)findViewById(R.id.tvtitle);
        lbldeliverydetail = (TextView)findViewById(R.id.lbldeliverydetail);
        tvto = (TextView)findViewById(R.id.tvto);
        tvfrom = (TextView)findViewById(R.id.tvfrom);
        tvdepart = (TextView)findViewById(R.id.tvdepart);
        lbldepart = (TextView)findViewById(R.id.lbldepart);
        lblto = (TextView)findViewById(R.id.lblto);
        lblfrom = (TextView)findViewById(R.id.lblfrom);
        lbldeparturedate = (TextView)findViewById(R.id.lbldeparturedate);
        lblarrivaldate = (TextView)findViewById(R.id.lblarrivaldate);
        tvtransportdetail = (TextView)findViewById(R.id.tvtransportdetail);
        tvphone = (TextView)findViewById(R.id.tvphone);
        tvcity = (TextView)findViewById(R.id.tvcity);
        tvaddressline2 = (TextView)findViewById(R.id.tvaddressline2);
        tvaddressline1 = (TextView)findViewById(R.id.tvaddressline1);
        tvratings = (TextView)findViewById(R.id.tvratings);
        imgedit = (ImageView) findViewById(R.id.imgedit);
        imgdelete = (ImageView)findViewById(R.id.imgdelete);

        setFontStyle();

        if(tvphone != null)
        {
            tvphone.setOnClickListener(this);
        }

       /* btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = "https://www.google.com/maps/dir/?api=1&origin=Madrid,Spain&destination=Barcelona,Spain&waypoints=Zaragoza,Spain%7CHuesca,Spain&travelmode=driving&dir_action=navigate";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

               *//* Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=pragati society +patan"));
                startActivity(intent);*//*
            }
        });*/

        //Backbutton on toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceDetail.super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setFontStyle() {
        if(getFontstyle != null)
        {
            tvtitle.setTypeface(getFontstyle.CoreSansBold());
            tvaddressline1.setTypeface(getFontstyle.CoreSansMedium());
            tvaddressline2.setTypeface(getFontstyle.CoreSansMedium());
            tvcity.setTypeface(getFontstyle.CoreSansMedium());
            tvdepart.setTypeface(getFontstyle.CoreSansMedium());
            tvfrom.setTypeface(getFontstyle.CoreSansMedium());
            tvphone.setTypeface(getFontstyle.CoreSansMedium());
            tvratings.setTypeface(getFontstyle.CoreSansMedium());
            tvto.setTypeface(getFontstyle.CoreSansMedium());
            tvtransportdetail.setTypeface(getFontstyle.CoreSansBold());
            lbldeliverydetail.setTypeface(getFontstyle.CoreSansBold());
            lbldepart.setTypeface(getFontstyle.CoreSansBold());
            lblfrom.setTypeface(getFontstyle.CoreSansBold());
            lblto.setTypeface(getFontstyle.CoreSansBold());
            lblarrivaldate.setTypeface(getFontstyle.CoreSansMedium());
            lbldeparturedate.setTypeface(getFontstyle.CoreSansMedium());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tvphone :
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+tvphone.getText().toString()));
                startActivity(callIntent);
                break;
        }

    }
}
