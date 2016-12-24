package com.example.bunhan.robotsurvey;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class page4 extends Activity implements SurfaceHolder.Callback
        , PreviewCallback {

    private Microgear microgear = new Microgear(this);
    MicrogearCallBack callback = new MicrogearCallBack();

    private String appid = ""; //APP_ID
    private String key = ""; //KEY
    private String secret = ""; //SECRET
    private String alias = "";
    String m = "...";
    TextView t2,t3,t4,t1,tv4_1;

    int bL = 0;

    boolean send = false;
    boolean microC = false;
    byte[] x;
    Bitmap bitmap;
    Camera mCamera;
    SurfaceView mPreview;

    String base64 = "test";
    String respoundB = "...";

    //final Handler handler = new Handler();
    // Timer timer = new Timer();

    boolean sendV = false;
    boolean ca = false;

    boolean reV = false;
    int wideC = 0;
    int hightC = 0;
    int ts = 250;

    //BluetoothSPP bt = new BluetoothSPP(this);



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            TextView myTextView =
                    (TextView)findViewById(R.id.textView);
            myTextView.append(string+"\n");
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.page4);

        mPreview = (SurfaceView)findViewById(R.id.preview4_1);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        t2 = (TextView)findViewById(R.id.textView2);
        t3 = (TextView)findViewById(R.id.textView3);
        t4 = (TextView)findViewById(R.id.textView444);
        t1 = (TextView)findViewById(R.id.textView);
        tv4_1 = (TextView)findViewById(R.id.tv4_1);

       // bt4_1 = (Button)findViewById(R.id.bt4_1);
        //bt4_1.setVisibility(View.INVISIBLE);


        //MicrogearCallBack callback = new MicrogearCallBack();

       // microgear.connect(appid, key, secret, alias);
        if(!reV){
            appid = page3.dataMG[0];
            key = page3.dataMG[1];
            secret = page3.dataMG[2];
            alias = page3.dataMG[3];

            wideC = Integer.parseInt(page3.dataMG2[0]);
            hightC = Integer.parseInt(page3.dataMG2[1]);

            reV = true;
        }
        microgear.connect(appid, key, secret, alias);
        //microgear.resettoken();
        microgear.setCallback(callback);
        microgear.subscribe("/control/"+alias);
        t3.setText("/control/"+alias);
        // mCamera = Camera.open();



        page2.bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                // Do something when data incoming
                 respoundB = message;

                //gotoPage4();
            }
        });






        if(reV) {
            page2.bt.send("y",true);
            setup();
        }



    }






    public void surfaceChanged(SurfaceHolder arg0
            , int arg1, int arg2, int arg3) {
        Log.d("CameraSystem", "surfaceChanged");
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> previewSize = params.getSupportedPreviewSizes();
        List<Camera.Size> pictureSize = params.getSupportedPictureSizes();
        // params.setPictureSize(pictureSize.get(0).width, pictureSize.get(0).height);
        // params.setPreviewSize(previewSize.get(0).width, previewSize.get(0).height);
        //params.setPictureSize(176,144);
        //params.setPreviewSize(176,144);
        params.setPictureSize(wideC,hightC);
        params.setPreviewSize(wideC,hightC);
        params.setJpegQuality(100);
        mCamera.setParameters(params);
        mCamera.setPreviewCallback(this);



        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
            mCamera.startPreview();
            ca = true;



        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void surfaceCreated(SurfaceHolder arg0) { }

    public void surfaceDestroyed(SurfaceHolder arg0) { }



    public void onPreviewFrame(final byte[] arg0, Camera arg1) {
        Log.d("Camera", "onPreviewFrame");
        if(arg0 != null && send == false) {


            // runOnUiThread(new Runnable() {
            //Bitmap bitmap;
            int w = mCamera.getParameters().getPreviewSize().width;
            int h = mCamera.getParameters().getPreviewSize().height;
            // int w = 176;
            //int h = 144;
            //int w = 320;
            //  int h = 240;
            int[] rgbs = new int[w * h];
            // public void run() {
            decodeYUV420(rgbs, arg0, w, h);
            bitmap = Bitmap.createBitmap(rgbs, w, h, Bitmap.Config.RGB_565);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
            bitmap = null;
            x = outStream.toByteArray();
            // base64 = Base64.encodeToString(x,Base64.DEFAULT);

            // นำตัวแปร rgb หรือ bitmap ไปใช้งานได้ตามต้องการ
            // microgear.chat("myX", base64);
            // microgear.publish("/video/1", base64,0,false);
            //base64 = "";
            send = true;
            // }
            //});

        }
    }

    public void decodeYUV420(int[] rgb, byte[] yuv420, int width, int height) {
        final int frameSize = width * height;

        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420[uvp++]) - 128;
                    u = (0xff & yuv420[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0; else if (r > 262143) r = 262143;
                if (g < 0) g = 0; else if (g > 262143) g = 262143;
                if (b < 0) b = 0; else if (b > 262143) b = 262143;

                rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2)
                        & 0xff00) | ((b >> 10) & 0xff);
            }
        }
    }


//}


    protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
        releaseCameraAndPreview();
        //callback = null;
        // microgear.setCallback(callback);


        //mCamera.stopPreview();
        page2.bt.stopService();
        //myClientTask.cancel(true);

    }

    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();


        Log.d("System", "onResume");

        // mCamera = Camera.open();
        // if(ca) {
        try {
            releaseCameraAndPreview();
            //if (camId == 0) {
            //    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            // }
            // else {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            // }
        } catch (Exception e) {
            // Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }
        // }



    }

    public void onPause() {
        Log.d("System", "onPause");
        super.onPause();
        /*if(mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
        }*/
        try {
            releaseCameraAndPreview();
        } catch (Exception e) {
            // Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

    }

    public void onStart() {
        super.onStart();
       /* if(!bt.isBluetoothEnabled()) {
            Intent intent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);





                setup();
            }
        }*/


    }

    public void setup() {
        // Button btnSend = (Button)findViewById(R.id.btnSend);
        //btnSend.setOnClickListener(new View.OnClickListener(){
        //  public void onClick(View v){
        // bt.send(in.getText().toString(),true);
        // bt.send(m,true);




        (new Thread(new Runnable()
        {
            //int count = 1;
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run(){

                                if(m.equals("start")){
                                    sendV = true;
                                    tv4_1.setText("Working....");
                                }else if(m.equals("stop")){
                                    sendV = false;
                                    tv4_1.setText("Stand by....");
                                }

                                if(send && microC&&sendV) {
                                    if((respoundB.length() < 4)&&(respoundB.length() >2 )) {// 123
                                        String t = "0";
                                        t = t.concat(respoundB);
                                        respoundB = t;
                                    }

                                    if((respoundB.length() < 3)&&(respoundB.length() >1 )) {// 12
                                        String t = "00";
                                        t = t.concat(respoundB);
                                        respoundB = t;
                                    }

                                    if((respoundB.length() < 2)) {// 1
                                        String t = "000";
                                        t = t.concat(respoundB);
                                        respoundB = t;
                                    }

                                    base64 = Base64.encodeToString(x,Base64.DEFAULT);
                                    //base64 = base64.substring(840);
                                    //microgear.chat("myX", base64);// microgear.publish("/video/1", base64,0,false);
                                    String temp = "";
                                    temp = temp.concat(respoundB);
                                    temp = temp.concat(base64);
                                    microgear.publish("/robot_video/"+alias, temp, 0, false);
                                    //microgear.publish("/video/1", temp, 0, true);

                                    // myClientTask = new MyClientTask("z");//send data to UDP


                                    //myClientTask.execute();
                                    bL = base64.length();
                                    t2.setText("control data : "+String.valueOf(m));
                                    //setup();
                                    page2.bt.send(m,true);

                                    t3.setText("data per fraem :"+String.valueOf(bL)+" byte");
                                    t4.setText("data from Arduino :"+String.valueOf(respoundB));

                                    bL = 0;
                                    //m = " ";

                                    base64 = "";
                                    //count++;
                                    send = false;
                                }
                            }
                        });
                        Thread.sleep(ts);
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        })).start();
        // }
        //});
    }






    class MicrogearCallBack implements MicrogearEventListener{
        @Override
        public void onConnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Now I'm connected with netpie");
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("Connected","Now I'm connected with netpie");
            //t1.setText("Now I'm connected with netpie");
            microC = true;
            //sendV = true;
            ts = 250;



        }

        @Override
        public void onMessage(String topic, String message) {
            // Message msg = handler.obtainMessage();
            // Bundle bundle = new Bundle();
            // bundle.putString("myKey", topic+" : "+message);
            // msg.setData(bundle);
            //  handler.sendMessage(msg);
            // Log.i("Message",topic+" : "+message);
            m = message;


        }

        @Override
        public void onPresent(String token) {
            // Message msg = handler.obtainMessage();
            // Bundle bundle = new Bundle();
            // bundle.putString("myKey", "New friend Connect :"+token);
            // msg.setData(bundle);
            // handler.sendMessage(msg);
            // Log.i("present","New friend Connect :"+token);
        }

        @Override
        public void onAbsent(String token) {
            // Message msg = handler.obtainMessage();
            // Bundle bundle = new Bundle();
            // bundle.putString("myKey", "Friend lost :"+token);
            // msg.setData(bundle);
            // handler.sendMessage(msg);
            // Log.i("absent","Friend lost :"+token);
        }

        @Override
        public void onDisconnect() {
            //Message msg = handler.obtainMessage();
            //Bundle bundle = new Bundle();
            //bundle.putString("myKey", "Disconnected");
            // msg.setData(bundle);
            //handler.sendMessage(msg);
            // Log.i("disconnect","Disconnected");
            // microC = false;
            // microgear.connect(appid, key, secret, alias);
            //t1.setText("Disconnected ");
            // microC = false;
            //sendV = false;
            //ts = 1000;
        }

        @Override
        public void onError(String error) {


            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+error);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("exception","Exception : "+error);

            //t1.setText("Exception : "+error);
            microC = false;
            //sendV = false;
            m = "1234";
            //ts = 100000;


        }

        @Override
        public void onInfo(String info) {
            //t1.setText("Info : "+info);
        }


    }

    private void releaseCameraAndPreview() {
        //mPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

}
