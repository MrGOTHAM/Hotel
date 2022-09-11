package com.acg.hotel.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.acg.hotel.R;

/**
 * @Classname OrderFragment
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/28 18:13
 * @Created by an
 */
public class OrderFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_order, container, false);

        return view;
    }
}
