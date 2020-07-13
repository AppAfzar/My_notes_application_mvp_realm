package com.appafzar.notes.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appafzar.notes.view.adapters.FoldersAdapter;
import com.appafzar.notes.model.FolderModel;

import java.util.Collection;
import java.util.List;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FoldersRecyclerView extends RecyclerView {
    private FoldersAdapter adapter;

    public FoldersRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public FoldersRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setHasFixedSize(true);
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new FoldersAdapter(context);
        setAdapter(adapter);
    }

    public void setData(List<FolderModel> dataList) {
        adapter.setData(dataList);
    }

    public void enableDeletionMode(boolean isEnabled) {
        adapter.enableDeletionMode(isEnabled);
    }

    public Collection<Integer> getCountersToDelete() {
        return adapter.getCountersToDelete();
    }
}
