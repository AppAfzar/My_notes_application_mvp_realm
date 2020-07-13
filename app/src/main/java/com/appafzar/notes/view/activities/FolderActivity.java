package com.appafzar.notes.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.appafzar.notes.App;
import com.appafzar.notes.R;
import com.appafzar.notes.model.FolderModel;
import com.appafzar.notes.view.activities.base.ToolbarActivity;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.NoteModel;
import com.appafzar.notes.viewmodel.NoteViewModel;
import com.appafzar.notes.view.custom.NotesRecyclerView;
import com.appafzar.notes.view.dialog.AddNewObjectDialog;

import java.util.Calendar;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FolderActivity extends ToolbarActivity {
    private NotesRecyclerView recyclerView;
    private NoteViewModel noteVM;
    private Menu menu;
    private FolderModel folder;

    public static void start(Activity activity, int folderId) {
        Intent intent = new Intent(activity, FolderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Const.FolderEntity.FIELD_ID, folderId);
        activity.startActivity(intent);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        noteVM = new NoteViewModel(this);
        recyclerView = new NotesRecyclerView(this);
        includeContentView(recyclerView);

    }

    @Override
    public void initExtra(Intent intent) {
        int fid = intent.getIntExtra(Const.FolderEntity.FIELD_ID, 0);
        if (fid == 0) {
            finish();
            return;
        }
        new NoteViewModel(this).getFolder(fid).observe(this, folder -> {
            this.folder = folder;
            recyclerView.setData(folder);
            setTitle(folder.getName());
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.note_list, menu);
        menu.setGroupVisible(R.id.group_normal_mode, true);
        menu.setGroupVisible(R.id.group_delete_mode, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_start_delete_mode:
                recyclerView.enableDeletionMode(true);
                menu.setGroupVisible(R.id.group_normal_mode, false);
                menu.setGroupVisible(R.id.group_delete_mode, true);
                return true;
            case R.id.action_end_delete_mode:
                noteVM.deleteNoteCollection(recyclerView.getCountersToDelete());
                // Fall through
            case R.id.action_cancel_delete_mode:
                recyclerView.enableDeletionMode(false);
                menu.setGroupVisible(R.id.group_normal_mode, true);
                menu.setGroupVisible(R.id.group_delete_mode, false);
                return true;

            case R.id.action_add_text:
                AddNewObjectDialog.start(this, title -> {
                    final int newId = (int) Calendar.getInstance().getTimeInMillis();
                    App.realm.executeTransaction(realm ->
                    {
                        NoteModel newNote = realm.createObject(NoteModel.class, newId);
                        newNote.setTitle(title);
                        newNote.setPainting(false);
                        folder.getNotes().add(newNote);
                    });
                    NoteActivity.start(FolderActivity.this, newId);
                });
                return true;

            case R.id.action_add_drawing:
                AddNewObjectDialog.start(this, title -> {
                    final int newId = (int) Calendar.getInstance().getTimeInMillis();
                    App.realm.executeTransaction(realm -> {
                        NoteModel newNote = realm.createObject(NoteModel.class, newId);
                        newNote.setTitle(title);
                        newNote.setPainting(true);
                        folder.getNotes().add(newNote);
                    });
                    DrawingActivity.start(FolderActivity.this, newId);
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
