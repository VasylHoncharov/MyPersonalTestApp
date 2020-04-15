package com.vgsoft.mypersonaltestapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.vgsoft.mypersonaltestapp.R;

public class EmailFragment extends BaseFragment {

    public EmailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email, container, false);
        return view;
    }
}
