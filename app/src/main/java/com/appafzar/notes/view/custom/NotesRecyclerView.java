package com.appafzar.notes.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appafzar.notes.model.FolderModel;
import com.appafzar.notes.view.adapters.NotesAdapter;

import java.util.Collection;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
@SuppressLint("ViewConstructor")
public class NotesRecyclerView extends RecyclerView {
    private NotesAdapter adapter;

    public NotesRecyclerView(@NonNull Context context) {
        super(context);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setHasFixedSize(true);
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new NotesAdapter(context);
        setAdapter(adapter);
    }

    public void setData(FolderModel folder) {
        if (folder != null)
            adapter.setData(folder.getNotes());
    }

    public void enableDeletionMode(boolean isEnabled) {
        adapter.enableDeletionMode(isEnabled);
    }

    public Collection<Integer> getCountersToDelete() {
        return adapter.getCountersToDelete();
    }

}
