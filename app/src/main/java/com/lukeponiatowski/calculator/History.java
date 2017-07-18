package com.lukeponiatowski.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/*
    History -  Fragment for managing the history of executed expressions
*/
public class History extends Fragment {
    public static ArrayList<ExpressionSolution> historyArray = new ArrayList<ExpressionSolution>();//Array list of past expression, w/ solutions
    private ListView historyList; //List view in the layout
    private ArrayAdapter historyAdapter; //Adapter for the ListView and the 'historyArray'

    public History() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false); //Inflate the view
        historyList = (ListView) view.findViewById(R.id.historyList); //Get ListView
        historyAdapter = new ExpressionSolutionArrayAdapter(getActivity().getApplicationContext(), R.layout.history_list_item, this.historyArray ); //Construct new custom adapter for the ListView
        historyList.setAdapter(historyAdapter); //Apply adapter
        //Create onLongClick listener for the list items -> used to recall their associated expression to the input bar in (MainActivity)
        historyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((EditText)getActivity().findViewById(R.id.inputOutput)).setText( historyArray.get(position).Expression );
                return true;
            }
        });
        return view;
    }

    //Update the list view data from the adapter
    public void UpdateView(){
        if (historyList!= null) {
            historyList.setAdapter(historyAdapter);
        }
    }

    //Add expression string and solution to internal ArrayList
    public void PushExpression(String exp, String sol){
        for (int i = 0; i < historyArray.size(); i++) {
            if(historyArray.get(i).Expression.equals(exp)){
                historyArray.remove(i);
            }
        }
        historyArray.add(0, new ExpressionSolution(exp, sol));
    }

    //Erase the history
    public void ClearHistory(){
        historyArray.clear();
    }

    //Class for housing Expression/Solution String pairs
    private class ExpressionSolution {
        public final String Expression;
        public final String Solution;
        public ExpressionSolution(String exp, String sol){
            Expression = exp;
            Solution = sol;
        }

    }

    //Customized ArrayAdapter to adapt (ExpressionSolution) into the layout 'history_list_item.xml'
    // Help from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    private class ExpressionSolutionArrayAdapter extends ArrayAdapter<ExpressionSolution> {
        //Constructor
        public ExpressionSolutionArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ExpressionSolution> objects) {
            super(context, resource, objects);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ExpressionSolution expSol = getItem(position);
            //Make sure item view is initialized
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_list_item, parent, false);
            }
            //Set the appropriate text views, and adjust their color
            ((TextView) convertView.findViewById(R.id.expTextView)).setText(expSol.Expression);
            ((TextView) convertView.findViewById(R.id.expTextView)).setTextColor(getResources().getColor(R.color.charcoal));
            ((TextView) convertView.findViewById(R.id.solTextView)).setText(expSol.Solution);
            ((TextView) convertView.findViewById(R.id.solTextView)).setTextColor(getResources().getColor(R.color.charcoal));

            return convertView;
        }
    }
}