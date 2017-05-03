package com.lukeponiatowski.calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/*
    SRC: http://stackoverflow.com/questions/15811496/android-swiping-using-viewpager-without-tabs
*/

public class MainActivity extends FragmentActivity {
    EditText inputOutputEditText;
    Fragment_Pager pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputOutputEditText = (EditText)findViewById(R.id.inputOutput);

        /** Getting a reference to the ViewPager defined the layout file */
        ViewPager pager = (ViewPager) findViewById(R.id.fragmentPager);

        /** Getting fragment manager */
        FragmentManager fm = getSupportFragmentManager();

        /** Instantiating FragmentPagerAdapter */
        pagerAdapter = new Fragment_Pager(fm);

        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(1);

        // Update the EditText so it won't popup Android's own keyboard, since I have my own.
        // src -> http://stackoverflow.com/questions/13586354/android-hide-soft-keyboard-from-edittext-while-not-losing-cursor
        inputOutputEditText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });
        inputOutputEditText.setShowSoftInputOnFocus(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onClickInput(View v) {
        String textToAdd = "";

        switch (v.getId()) {
            case R.id.point:
                textToAdd = ".";
                break;
            case R.id.zero:
                textToAdd = "0";
                break;
            case R.id.one:
                textToAdd = "1";
                break;
            case R.id.two:
                textToAdd = "2";
                break;
            case R.id.three:
                textToAdd = "3";
                break;
            case R.id.four:
                textToAdd = "4";
                break;
            case R.id.five:
                textToAdd = "5";
                break;
            case R.id.six:
                textToAdd = "6";
                break;
            case R.id.seven:
                textToAdd = "7";
                break;
            case R.id.eight:
                textToAdd = "8";
                break;
            case R.id.nine:
                textToAdd = "9";
                break;
            case R.id.add:
                textToAdd = "+";
                break;
            case R.id.subtract:
                textToAdd = "-";
                break;
            case R.id.divide:
                textToAdd = "/";
                break;
            case R.id.multiply:
                textToAdd = "*";
                break;
            case R.id.exponent:
                textToAdd = "^";
                break;
        }
        inputOutputEditText.getText().insert(inputOutputEditText.getSelectionEnd(),textToAdd);

    }

    public void Calculate(View v) {
        String expString = inputOutputEditText.getText().toString();
        if (expString.length() > 0){
            Expression exp = new Expression();
            boolean expValid = exp.parseString(expString);
            if (expValid){
                String solution = exp.getValue().toString();
                inputOutputEditText.setText(solution );
                pagerAdapter.historyFragment.PushExpression(expString,solution);
                pagerAdapter.historyFragment.UpdateView();
            }else {
                inputOutputEditText.setText(R.string.error);
            }
            inputOutputEditText.setSelection(inputOutputEditText.getText().length());
        }

    }

    public void Backspace(View v) {
        if (inputOutputEditText.getSelectionStart() > 0){
            inputOutputEditText.getText().replace(inputOutputEditText.getSelectionStart()-1,inputOutputEditText.getSelectionEnd(),"");
        } else {
            inputOutputEditText.getText().replace(inputOutputEditText.getSelectionStart(),inputOutputEditText.getSelectionEnd(),"");
        }

    }

    public void Clear(View v) {
        inputOutputEditText.setText("");
    }

    public class Fragment_Pager extends FragmentPagerAdapter {

        public final History historyFragment = new History();
        public final SimpleCalculator simpleCalculator = new SimpleCalculator();
        public final AdvancedCalculator advancedCalculator = new AdvancedCalculator();

        final int PAGE_COUNT = 3;
        public Fragment_Pager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return historyFragment;
                case 1:
                    return simpleCalculator;
                case 2:
                    return advancedCalculator;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }

    public void openMoreCalc(MenuItem v) {
        Intent i = new Intent(this, MoreCalc.class);
        startActivity(i);
    }

    public void openSettings(View v) {
//        Intent i = new Intent(this, MoreCalc.class);
//        startActivity(i);
    }
}
