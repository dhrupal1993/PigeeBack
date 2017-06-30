package com.eleganceinfoways.pigeeback;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.utils.GetFontstyle;

public class SplashActivity extends AppCompatActivity {

    GetFontstyle getFontstyle;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        getFontstyle = new GetFontstyle(SplashActivity.this);

        if(tvTitle != null)
            tvTitle.setTypeface(getFontstyle.CoreSansBold());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                // close this activity
                finish();
            }
        }, 3000);

    }


}
