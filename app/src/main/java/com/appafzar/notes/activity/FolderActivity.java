package com.appafzar.notes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.appafzar.notes.App;
import com.appafzar.notes.R;
import com.appafzar.notes.activity.base.ToolbarActivity;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.entity.Folder;
import com.appafzar.notes.model.entity.Note;
import com.appafzar.notes.model.interfaces.NoteInterface;
import com.appafzar.notes.presenter.NotePresenter;
import com.appafzar.notes.view.NotesRecyclerView;
import com.appafzar.notes.view.dialog.AddNewObjectDialog;

import java.util.Calendar;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FolderActivity extends ToolbarActivity implements NoteInterface {
    private NotesRecyclerView recyclerView;
    private NotePresenter presenter;
    private Folder folder;
    private Menu menu;

    public static void start(Activity activity, int folderId) {
        Intent intent = new Intent(activity, FolderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Const.FolderEntity.FIELD_ID, folderId);
        activity.startActivity(intent);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        presenter = new NotePresenter(this, this);

    }

    @Override
    public void initExtra(Intent intent) {
        folder = App.realm.where(Folder.class)
                .equalTo(Const.FolderEntity.FIELD_ID, intent.getIntExtra(Const.FolderEntity.FIELD_ID, 0))
                .findFirst();
        if (folder == null) {
            finish();
            return;
        }
        setTitle(folder.getName());
        recyclerView = new NotesRecyclerView(this, folder.getNotes());
        includeContentView(recyclerView);
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
                presenter.deleteCollection(recyclerView.getCountersToDelete());
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
                        Note newNote = realm.createObject(Note.class, newId);
                        newNote.setTitle(title);
                        folder.getNotes().add(newNote);
                    });
                    NoteActivity.start(FolderActivity.this, newId);
                });
                return true;

            case R.id.action_add_drawing:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
