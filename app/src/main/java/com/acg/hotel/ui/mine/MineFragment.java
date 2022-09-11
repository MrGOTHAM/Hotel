package com.acg.hotel.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.acg.hotel.R;

/**
 * @Classname MineFragment
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/28 18:15
 * @Created by an
 */
public class MineFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }
}
