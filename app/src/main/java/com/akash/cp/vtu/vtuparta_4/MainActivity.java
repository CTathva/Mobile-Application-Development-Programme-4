package com.akash.cp.vtu.vtuparta_4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Base {
    public static Integer[] image = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    public static final String TAG = "MainActivity";
    Button mButton;
    Bitmap mBitmap;
    WallpaperManager mWallpaperManager;
    final Handler mHandler = new Handler();
    int count = 0;
    boolean bool=false;
public static final int INTERVAL=1000;// 1 second is equal to 1000 millisecond.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        listener();
    }

    @Override
    public void init() {
        mButton = (Button) findViewById(R.id.button);
    }
    @Override
    public void listener() {
        bool=true;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setText("Stop");
                Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT).show();
                if (bool) {
                    bool=false;
                    final Runnable r = new Runnable() {
                        public void run() {
                            Log.d(TAG, "run: " + count);
                            mHandler.postDelayed(this, INTERVAL);
                            if (count++ < image.length-1) {
                                if (Build.VERSION.SDK_INT >23) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mBitmap = BitmapFactory.decodeResource(getResources(), image[count]);
                                            mWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                            try {
                                                mWallpaperManager.setBitmap(mBitmap);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, INTERVAL);
                                }
                            } else {
                                count = 0;
                                mBitmap = BitmapFactory.decodeResource(getResources(), image[count]);
                                mWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                try {
                                    mWallpaperManager.setBitmap(mBitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                mHandler.postDelayed(this, INTERVAL);

                            }
                        }
                    };
                    mHandler.postDelayed(r, INTERVAL);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.removeMessages(0);
                    bool=true;
                    mButton.setText("Start");
                }
            }
        });
    }

}






