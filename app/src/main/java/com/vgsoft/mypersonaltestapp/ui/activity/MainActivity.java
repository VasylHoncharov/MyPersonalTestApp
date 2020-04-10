package com.vgsoft.mypersonaltestapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vgsoft.mypersonaltestapp.R;
import com.vgsoft.mypersonaltestapp.TestApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestApp.getRes().getString(R.string.app_name);

    }
}
