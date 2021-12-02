package com.android.projects.myapmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.android.projects.myapmdemo.memory.Memory;

import static java.lang.Thread.sleep;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Rabbit.INSTANCE.open(true, this);

        FPS fps = new FPS();
        Memory memory = new Memory();
        memory.open( getApplicationContext());
        fps.open();



//        new FpsTest().startFps();
//        try {
//            sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fps.close();
//            }
//        }, 10000);
    }


}