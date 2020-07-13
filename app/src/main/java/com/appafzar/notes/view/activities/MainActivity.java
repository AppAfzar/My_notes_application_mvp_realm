package com.appafzar.notes.view.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.appafzar.notes.R;
import com.appafzar.notes.viewmodel.FolderViewModel;
import com.appafzar.notes.view.activities.base.ToolbarActivity;
import com.appafzar.notes.view.custom.FoldersRecyclerView;
import com.appafzar.notes.view.dialog.AddNewObjectDialog;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class MainActivity extends ToolbarActivity {
    private FoldersRecyclerView recyclerView;
    private FolderViewModel viewModel;
    private Menu menu;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        viewModel = new FolderViewModel(this);
        recyclerView = new FoldersRecyclerView(this);
        includeContentView(recyclerView);
        setTitle(R.string.note_folders);

        new FolderViewModel(this).getFolders().observe(this, folderModels ->
                recyclerView.setData(folderModels)
        );
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
                        FolderActivity.start(MainActivity.this, viewModel.addFolder(name))
                );
                return true;

            case R.id.action_start_delete_mode:
                recyclerView.enableDeletionMode(true);
                menu.setGroupVisible(R.id.group_normal_mode, false);
                menu.setGroupVisible(R.id.group_delete_mode, true);
                return true;
            case R.id.action_end_delete_mode:
                viewModel.deleteNoteCollection(recyclerView.getCountersToDelete());
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