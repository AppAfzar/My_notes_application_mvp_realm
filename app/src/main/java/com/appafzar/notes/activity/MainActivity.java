package com.appafzar.notes.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.appafzar.notes.R;
import com.appafzar.notes.activity.base.ToolbarActivity;
import com.appafzar.notes.model.interfaces.FolderInterface;
import com.appafzar.notes.presenter.FolderPresenter;
import com.appafzar.notes.view.FoldersRecyclerView;
import com.appafzar.notes.view.dialog.AddNewObjectDialog;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class MainActivity extends ToolbarActivity implements FolderInterface {
    private FoldersRecyclerView recyclerView;
    private FolderPresenter presenter;
    private Menu menu;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        presenter = new FolderPresenter(this, this);
        recyclerView = new FoldersRecyclerView(this);
        includeContentView(recyclerView);
        setTitle(R.string.note_folders);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.folder_list, menu);
        menu.setGroupVisible(R.id.group_normal_mode, true);
        menu.setGroupVisible(R.id.group_delete_mode, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                AddNewObjectDialog.start(this, name ->
                        FolderActivity.start(MainActivity.this, presenter.addFolder(name))
                );
                return true;

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}