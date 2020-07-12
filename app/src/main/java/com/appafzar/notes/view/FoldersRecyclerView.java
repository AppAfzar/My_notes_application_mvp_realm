package com.appafzar.notes.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appafzar.notes.App;
import com.appafzar.notes.adapter.FoldersAdapter;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.entity.Folder;

import java.util.Collection;

import io.realm.Sort;

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
        adapter = new FoldersAdapter(getContext(),
                App.realm.where(Folder.class).sort(Const.FolderEntity.FIELD_NAME, Sort.ASCENDING).findAll());
        setAdapter(adapter);
    }

    public void enableDeletionMode(boolean isEnabled) {
        adapter.enableDeletionMode(isEnabled);
    }

    public Collection<Integer> getCountersToDelete() {
        return adapter.getCountersToDelete();
    }
}
