package com.example.bunhan.robotsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button bt1_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1_1 = (Button)findViewById(R.id.bt1_1);


        bt1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if ble is open,Goto page2
                Intent i = new Intent(getApplicationContext(),page2.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
