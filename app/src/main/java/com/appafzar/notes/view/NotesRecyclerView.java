package com.appafzar.notes.view;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appafzar.notes.adapter.NotesAdapter;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.entity.Note;

import java.util.Collection;

import io.realm.RealmList;
import io.realm.Sort;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
@SuppressLint("ViewConstructor")
public class NotesRecyclerView extends RecyclerView {
    private NotesAdapter adapter;

    public NotesRecyclerView(@NonNull Context context, RealmList<Note> notes) {
        super(context);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setHasFixedSize(true);
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new NotesAdapter(getContext(), notes.sort(Const.NoteEntity.FIELD_ID, Sort.DESCENDING));
        setAdapter(adapter);
    }

    public void enableDeletionMode(boolean isEnabled) {
        adapter.enableDeletionMode(isEnabled);
    }

    public Collection<Integer> getCountersToDelete() {
        return adapter.getCountersToDelete();
    }

}
