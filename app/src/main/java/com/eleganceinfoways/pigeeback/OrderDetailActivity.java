package com.eleganceinfoways.pigeeback;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderDetailActivity extends Activity {

    Toolbar toolbar;
    Spinner spstatus;
    EditText edcurlocation;
    TextView tvcustname, tvcustemail, lblstatus, lblcurlocation, tvcurlocation, lbldate, tvdate;
    Button btnupdate;
    GetFontstyle getFontstyle;
    Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setTitle("Order Detail");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getFontstyle = new GetFontstyle(OrderDetailActivity.this);
        tvcustname = (TextView)findViewById(R.id.tvcustname);
        tvcustemail = (TextView)findViewById(R.id.tvcustemail);
        lblstatus = (TextView)findViewById(R.id.lblstatus);
        lblcurlocation = (TextView)findViewById(R.id.lblcurlocation);
        edcurlocation = (EditText) findViewById(R.id.edcurlocation);
        lbldate = (TextView)findViewById(R.id.lbldate);
        tvdate = (TextView)findViewById(R.id.tvdate);
        btnupdate = (Button)findViewById(R.id.btnupdate);
        myCalendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd HH:mm ");
        tvdate.setText( sdf.format( new Date() ));

        setFontStyle();

        //Backbutton on toolbar
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onBackPressed();
              overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
              finish();
          }
      });



    }


    public void setFontStyle() {
        if (getFontstyle != null) {
            tvdate.setTypeface(getFontstyle.CoreSansMedium());
            lbldate.setTypeface(getFontstyle.CoreSansMedium());
            edcurlocation.setTypeface(getFontstyle.CoreSansMedium());
            lblcurlocation.setTypeface(getFontstyle.CoreSansMedium());
            lblstatus.setTypeface(getFontstyle.CoreSansMedium());
            tvcustemail.setTypeface(getFontstyle.CoreSansMedium());
            tvcustname.setTypeface(getFontstyle.CoreSansMedium());
            btnupdate.setTypeface(getFontstyle.CoreSansMedium());
        }
    }
}
