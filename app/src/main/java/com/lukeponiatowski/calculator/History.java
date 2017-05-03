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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {
    public ArrayList<ExpressionSolution> historyArray = new ArrayList<ExpressionSolution>();
    private ListView historyList;
    private ArrayAdapter historyAdapter;

    public History() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        historyList = (ListView) view.findViewById(R.id.historyList);
        //historyAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, this.historyArray);
        historyAdapter = new ExpressionSolutionArrayAdapter(getActivity().getApplicationContext(), R.layout.history_list_item, this.historyArray );
        historyList.setAdapter(historyAdapter);
        return view;
    }

    public void UpdateView(){
        if (historyList!= null) {
            historyList.setAdapter(historyAdapter);
        }
    }

    public void PushExpression(String exp, String sol){
        historyArray.add(0, new ExpressionSolution(exp, sol));
    }

    public void ClearHistory(){
        historyArray.clear();
    }

    protected class ExpressionSolution {
        public final String Expression;
        public final String Solution;
        public ExpressionSolution(String exp, String sol){
            Expression = exp;
            Solution = sol;
        }

    }

    private class ExpressionSolutionArrayAdapter extends ArrayAdapter<ExpressionSolution> {

        public ExpressionSolutionArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ExpressionSolution> objects) {
            super(context, resource, objects);
        }

        // Help from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ExpressionSolution expSol = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_list_item, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.expTextView)).setText(expSol.Expression);
            ((TextView) convertView.findViewById(R.id.expTextView)).setTextColor(getResources().getColor(R.color.charcoal));
            ((TextView) convertView.findViewById(R.id.solTextView)).setText(expSol.Solution);
            ((TextView) convertView.findViewById(R.id.solTextView)).setTextColor(getResources().getColor(R.color.charcoal));

            return convertView;
        }
    }
}