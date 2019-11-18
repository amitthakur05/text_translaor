package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class Main2Activity extends AppCompatActivity {
SurfaceView surfaceView;
CameraSource cameraSource;
TextView mtextview;
Button okay;
TextRecognizer textRecognizer;

private static final String TAG="MainActivity";
private static final int requestPermissionID=101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       // fetch=findViewById(R.id.button2);
        surfaceView=findViewById(R.id.surfaceView);
        mtextview=findViewById(R.id.textView2);
        okay=findViewById(R.id.button3);
        fetchtext();

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textrecognize();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults)
    {
        if(requestCode!=requestPermissionID)
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            try {
                if(ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                puttextinsurface();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    private void puttextinsurface() {
        try {
            cameraSource.start(surfaceView.getHolder());
        }
        catch (Exception e)
        {

        }
    }
    private void fetchtext()
    {
       textRecognizer =new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational())
        {
            Toast.makeText(this, "Dependencies not loaded", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cameraSource=new CameraSource.Builder(Main2Activity.this,textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280,1024)
                    .setAutoFocusEnabled(true).setRequestedFps(2.0f).build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(Main2Activity.this,new String[]{Manifest.permission.CAMERA},requestPermissionID);
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    }
                    catch (Exception e)
                    {
                       e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
          //  mtextview.setText("hello");



        }
    }

    private void textrecognize()
    {
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if (items.size() != 0 ) {

                    mtextview.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for(int i=0;i<items.size();i++){
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                stringBuilder.append("\n");
                            }
                            String text=stringBuilder.toString();
                            Intent i=new Intent(Main2Activity.this,MainActivity.class);
                            i.putExtra("message",text);
                            startActivity(i);
                            // mtextview.setText(stringBuilder.toString());
                            cameraSource.stop();

                        }
                    });

                }
            }
        });
    }




}
