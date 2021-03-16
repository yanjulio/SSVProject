package com.soft.ssvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide(); // this is for hiding the action bar
        // This is for hiding the status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ProgressBar progressBar = findViewById(R.id.progress_main);

        Thread my_thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        my_thread.start();
    }
}