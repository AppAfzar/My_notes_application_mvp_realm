package com.appafzar.notes.view.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;

import com.appafzar.notes.databinding.ActivityToolbarBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Stack;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public abstract class ToolbarActivity extends BaseActivity {
    protected ActivityToolbarBinding binding;
    protected ActionBar actionBar;
    private Stack<Integer> loadingProgressStack = new Stack<>();

    @Override
    public void init(Bundle savedInstanceState) {
        binding = ActivityToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolbar();
    }

    @Override
    public void initExtra(Intent intent) {

    }

    public void includeContentView(View view, RelativeLayout.LayoutParams params) {
        binding.contentLayout.removeAllViews();
        if (params != null)
            binding.contentLayout.addView(view, params);
        else
            binding.contentLayout.addView(view);
    }

    public void includeContentView(View view) {
        includeContentView(view, null);
    }

    public void includeContentView(int viewResId) {
        includeContentView(View.inflate(this, viewResId, null));
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void showLoadingProgress() {
        loadingProgressStack.push(1);
    }

    public void hideLoadingProgress() {
        if (loadingProgressStack.size() > 0)
            loadingProgressStack.pop();

        if (loadingProgressStack.size() == 0)
            binding.baseProgress.setVisibility(View.GONE);
    }

    /**
     * Show a message to user using Snackbar.
     *
     * @param message The String message to show to user.
     */
    protected void showMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}
