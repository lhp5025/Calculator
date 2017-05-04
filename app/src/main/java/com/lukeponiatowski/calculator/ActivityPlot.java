package com.lukeponiatowski.calculator;

import android.app.Activity;
import android.icu.math.MathContext;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
/*  code ref: http://www.android-graphview.org
    ActivityPlot -  Activity ploting an expression
        - uses the variable 'n'
*/
public class ActivityPlot extends ChildActivity {
    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activiy_plot);

        graphView = (GraphView) findViewById(R.id.graph); //Get graph view from the layout

        String expressionStringInput = getIntent().getStringExtra("EXPRESSION"); //Extract expression string from intent

        ArrayList<DataPoint> points= new ArrayList<>(); //ArrayList of the points of the plot
        //Calculate the value of the expression for the values of n->[-10,10]
        for(int x = -10; x <= 10; x++){
            Expression exp = new Expression(); //Construct new Expression object
            String seriesExpression = new String(expressionStringInput);//Deep copy input expression string
            seriesExpression = seriesExpression.replace("n", String.valueOf(x) ); //Replace the varialbe in the string with the value of x
            //Parse and evaluate string
            exp.parseString(seriesExpression);
            double val = exp.getValue().doubleValue();
            //Test wether infinity or not (in androud 1/0 = infinity)
            if (val*2 == val*3){
                //Ignore if infinite
            } else {
                points.add(new DataPoint(x, exp.getValue().doubleValue() )); //Add new data point with the value of the expression
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(  points.toArray(new DataPoint[points.size()] ) ); //Intermediary object of GraphView

        graphView.addSeries(series); //Apply data to graph
    }
}
