package com.lukeponiatowski.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
    SimpleCalculator -  Fragment the encapsulates the layout for the simple calculator options
        - Nothing special here
*/
public class SimpleCalculator extends Fragment {

    public SimpleCalculator() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_calculator, container, false);
    }

}
