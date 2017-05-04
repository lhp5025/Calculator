package com.lukeponiatowski.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
    AdvancedCalculator -  Fragment the encapsulates the layout for the advanced calculator options
        - Nothing special here
*/
public class AdvancedCalculator extends Fragment {

    public AdvancedCalculator() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advanced_calculator, container, false);
    }

}
