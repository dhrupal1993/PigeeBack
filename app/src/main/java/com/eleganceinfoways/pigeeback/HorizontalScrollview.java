package com.eleganceinfoways.pigeeback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HorizontalScrollview extends AppCompatActivity {
    Button button;
    View horizontalScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scrollview);
        horizontalScrollview = findViewById(R.id.scrh);
        button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalScrollview.setVisibility(View.VISIBLE);
            }
        });
    }
}
