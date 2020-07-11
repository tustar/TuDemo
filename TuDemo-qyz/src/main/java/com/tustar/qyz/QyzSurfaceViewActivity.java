package com.tustar.qyz;

import android.os.Bundle;

import com.tustar.qyz.R;
import androidx.appcompat.app.AppCompatActivity;

public class QyzSurfaceViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_surface_view);
        setTitle(R.string.qyz_surface_title);
    }
}
