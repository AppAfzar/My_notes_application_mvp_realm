package com.appafzar.notes.view.activities.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appafzar.notes.helper.Log;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    //region AppCompatActivity
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        initExtra(getIntent());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        init(savedInstanceState);
        initExtra(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    //endregion

    //region Helper Methods
    public abstract void init(Bundle savedInstanceState);

    public abstract void initExtra(Intent intent);

    public void hideSoftKeyboard(View windowToken) {
        try {
            if (windowToken == null) windowToken = getWindow().getDecorView().getRootView();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {
            Log.e("hideSoftKeyboard" + ex);
        }
    }

    public void hideSoftKeyboard() {
        hideSoftKeyboard(null);
    }

    public void finishActivity() {
        hideSoftKeyboard();
        this.finish();
    }
    //endregion

}