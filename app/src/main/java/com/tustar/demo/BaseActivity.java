package com.tustar.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tustar on 4/10/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
