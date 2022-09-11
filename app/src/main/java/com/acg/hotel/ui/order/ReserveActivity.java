package com.acg.hotel.ui.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.acg.hotel.MyApplication;
import com.acg.hotel.R;

public class ReserveActivity extends AppCompatActivity {
    private MyApplication mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
    }
}