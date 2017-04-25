package com.lukeponiatowski.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


    }

    public void onClickNumberInput(View v) {
        TextView inOut = (TextView) findViewById(R.id.simpleIO);
        String inOutText = inOut.getText().toString();
        switch (v.getId()) {
            case R.id.point:
                inOutText += '.';
                break;
            case R.id.zero:
                inOutText += '0';
                break;
            case R.id.one:
                inOutText += '1';
                break;
            case R.id.two:
                inOutText += '2';
                break;
            case R.id.three:
                inOutText += '3';
                break;
            case R.id.four:
                inOutText += '4';
                break;
            case R.id.five:
                inOutText += '5';
                break;
            case R.id.six:
                inOutText += '6';
                break;
            case R.id.seven:
                inOutText += '7';
                break;
            case R.id.eight:
                inOutText += '8';
                break;
            case R.id.nine:
                inOutText += '9';
                break;
        }

        inOut.setText(inOutText);

        final HorizontalScrollView inOutScroll = (HorizontalScrollView) findViewById(R.id.inOutScroll);
        inOutScroll.postDelayed(new Runnable() {
            public void run() {
                inOutScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 1L);
    }
}
