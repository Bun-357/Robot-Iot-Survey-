package com.example.bunhan.robotsurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

/**
 * Created by bunhan on 12/14/2016.
 */

public class page3 extends Activity {
    TextView tv3_1;
    Button bt3_1;
    Spinner s3_1;
    public  static String[] dataMG = new String[4];
    public  static String[] dataMG2 = new String[2];
    String tem = " ",tem2 = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        tv3_1 = (TextView)findViewById(R.id.tv3_1);
        bt3_1 = (Button)findViewById(R.id.bt3_1);
        bt3_1.setVisibility(View.INVISIBLE);

        s3_1 = (Spinner)findViewById(R.id.spinner1);

        dataMG2[0] = "176";
        dataMG2[1] = "144";


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
       s3_1.setAdapter(adapter);
        s3_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        dataMG2[0] = "176";
                        dataMG2[1] = "144";

                        //tv3_1.setText("Complete load robot data:"+dataMG2[0]+","+dataMG2[1]);
                        bt3_1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        dataMG2[0] = "320";
                        dataMG2[1] = "240";

                        //tv3_1.setText("Complete load robot data:"+dataMG2[0]+","+dataMG2[1]);
                        bt3_1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        // Whatever you want to happen when the thrid item gets selected
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //conet ble
        // bt = new BluetoothSPP(this);
        if(!page2.bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }


        page2.bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                // Do something when data incoming
               // respoundB = message;

                tem = message;
              dataMG = tem.split("\\.");
                tv3_1.setText("Complete load robot data");
                //bt3_1.setVisibility(View.VISIBLE);
                //gotoPage4();
            }
        });


        bt3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPage4();
            }
        });

        page2.bt.send("x",true);
    }




    public void gotoPage4(){
        Intent i = new Intent(getApplicationContext(),page4.class);
        startActivity(i);
        finish();
    }
}
