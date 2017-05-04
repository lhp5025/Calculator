package com.lukeponiatowski.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
/*
    ChildActivity - abstract Activity class for activites spawned off the main
        - with up navigation enable to .finish() the Activity
*/
public abstract class ChildActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If up navigation pressed -> finish Activity
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
