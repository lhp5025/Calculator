package com.lukeponiatowski.calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.text.DecimalFormat;
/*
    MainActivity -  main activity class for handling the majority of input and output for the calculator
*/
public class MainActivity extends FragmentActivity {
    EditText inputOutputEditText; //EditText for input and output
    Fragment_Pager pagerAdapter; //Customized FragmentPageAdapter
    boolean shiftEnabled = false; //Shift variable for augmenting layout/inputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputOutputEditText = (EditText)findViewById(R.id.inputOutput); //find and set the input/output

        ViewPager pager = (ViewPager) findViewById(R.id.fragmentPager);//Get ViewPager from layout
        FragmentManager fm = getSupportFragmentManager(); //FragmentManager for all your fragment needs
        pagerAdapter = new Fragment_Pager(fm); //Init FragmentPageAdapter from FragmentManager
        pager.setAdapter(pagerAdapter); //Set the adapter to the view
        pager.setCurrentItem(1); //Set the current fragment to the simple calculator

        // Stop keyboard from opening when clicking on edit text
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
        inputOutputEditText.setShowSoftInputOnFocus(false); //More keyboard stopping
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Function for handling the input of non command keys, inputs selected into the input
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
            case R.id.sin:
                if(shiftEnabled) {
                    textToAdd = "asin(";
                } else {
                    textToAdd = "sin(";
                }

                break;
            case R.id.cos:
                if(shiftEnabled) {
                    textToAdd = "acos(";
                } else {
                    textToAdd = "cos(";
                }

                break;
            case R.id.tan:
                if(shiftEnabled) {
                    textToAdd = "atan(";
                } else {
                    textToAdd = "tan(";
                }

                break;
            case R.id.ln:
                textToAdd = "ln(";
                break;
            case R.id.l_paren:
                textToAdd = "(";
                break;
            case R.id.r_paren:
                textToAdd = ")";
                break;
            case R.id.var:
                textToAdd = "n";
                break;
            case R.id.sroot:
                textToAdd = "^(1/2)";
                break;
            case R.id.pi:
                textToAdd = "\u03C0";
                break;
            case R.id.e:
                textToAdd = "e";
                break;
            case R.id.log:
                textToAdd = "log(";
                break;
        }
        inputOutputEditText.getText().insert(inputOutputEditText.getSelectionEnd(),textToAdd);

    }

    //Function for extracting input, constructing the Expression, and formatting output
    public void Calculate(View v) {
        String expString = inputOutputEditText.getText().toString();
        if (expString.length() > 0){
            Expression exp = new Expression();
            boolean expValid = exp.parseString(expString);
            if (expValid){
                double result = exp.getValue().doubleValue();
                String solution;
                if (result % 1 == 0 && result < 10000000) {
                    DecimalFormat formatter = new DecimalFormat("0");
                    solution =  formatter.format(result);
                } else  if (result % 1 != 0 && result < 10000000) {
                    DecimalFormat formatter = new DecimalFormat("0.#####");
                    solution =  formatter.format(result);
                } else {
                    DecimalFormat formatter = new DecimalFormat("0.#####E0");
                    solution =  formatter.format(result);
                }


                inputOutputEditText.setText(  solution );
                pagerAdapter.historyFragment.PushExpression(expString,solution);
                pagerAdapter.historyFragment.UpdateView();
            }else {
                inputOutputEditText.setText(R.string.error);
            }
            inputOutputEditText.setSelection(inputOutputEditText.getText().length());
        }
    }

    //Erase the selection, or the character before the cursor
    public void Backspace(View v) {
        if (inputOutputEditText.getSelectionStart() > 0){
            inputOutputEditText.getText().replace(inputOutputEditText.getSelectionStart()-1,inputOutputEditText.getSelectionEnd(),"");
        } else {
            inputOutputEditText.getText().replace(inputOutputEditText.getSelectionStart(),inputOutputEditText.getSelectionEnd(),"");
        }

    }

    //Function for shift -> enables a alternate set of input from the view
    public void Shift(View v) {
        //Toggle shift
        if (shiftEnabled) {
            shiftEnabled = false;
            //-> make trig int std forms
            ((Button)findViewById(R.id.sin)).setText("sin");
            ((Button)findViewById(R.id.cos)).setText("tan");
            ((Button)findViewById(R.id.tan)).setText("tan");
        } else {
            shiftEnabled = true;
            //-> make trig int arc forms
            ((Button)findViewById(R.id.sin)).setText("asin");
            ((Button)findViewById(R.id.cos)).setText("atan");
            ((Button)findViewById(R.id.tan)).setText("atan");
        }
    }

    //Function for clearing input/output
    public void Clear(View v) {
        inputOutputEditText.setText("");
    }

    //On click function for opening (ActivityPlot), with the param of the expression string
    public void Plot(View v) {
        String expString = inputOutputEditText.getText().toString();
        Intent i = new Intent(this, ActivityPlot.class);
        i.putExtra("EXPRESSION", expString);
        startActivity(i);
    }

    //Menu item click functin for opening the (Economic1) Activity
    public void OpenEconomic1(MenuItem v) {
        Intent i = new Intent(this, Economic1.class);
        startActivity(i);
    }

    //Custom FragmentPagerAdapter for managing internal view fragments
    //code ref: http://stackoverflow.com/questions/15811496/android-swiping-using-viewpager-without-tabs
    public class Fragment_Pager extends FragmentPagerAdapter {
        //Init fragments and count
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
}
