package com.appafzar.notes.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appafzar.notes.App;
import com.appafzar.notes.view.activities.MainActivity;
import com.appafzar.notes.repositoriy.Repository;
import com.appafzar.notes.model.FolderModel;

import java.util.Collection;
import java.util.List;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FolderViewModel extends ViewModel {
    private final Repository repository;
    private MutableLiveData<List<FolderModel>> mutableLiveData;

    public FolderViewModel(Activity activity) {
        this.repository = new Repository(App.realm);
    }

    public LiveData<List<FolderModel>> getFolders() {
        if (mutableLiveData == null) {
            mutableLiveData = repository.requestFolders();
        }
        return mutableLiveData;
    }

    public int addFolder(String folderName) {
        return repository.createFolder(folderName);
    }

    public void deleteNoteCollection(Collection<Integer> ids) {
        repository.deleteNoteCollection(ids);
    }

    /**
     * Calls model to get imported path and pass it to {@link MainActivity}
     */
    public void importDatabase() {
//
    }

    /**
     * Calls model to export data to database
     *
     * @return The String message of whether import was successful or failed.
     */
    public String exportDatabase() {
        return repository.exportFolders();
    }

}
