package com.lukeponiatowski.calculator;

import android.app.Activity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;

// http://www.android-graphview.org
public class ActivityPlot extends ChildActivity {
    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activiy_plot);
        graphView = (GraphView) findViewById(R.id.graph);

        // activate horizontal zooming and scrolling
        graphView.getViewport().setScalable(true);

        // activate horizontal scrolling
        graphView.getViewport().setScrollable(true);

        // activate horizontal and vertical zooming and scrolling
        graphView.getViewport().setScalableY(true);

        // activate vertical scrolling
        graphView.getViewport().setScrollableY(true);
    }
}
