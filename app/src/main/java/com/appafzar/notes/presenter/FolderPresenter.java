package com.appafzar.notes.presenter;

import android.app.Activity;

import com.appafzar.notes.App;
import com.appafzar.notes.activity.MainActivity;
import com.appafzar.notes.model.FolderModel;
import com.appafzar.notes.model.interfaces.FolderInterface;

import java.util.Collection;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FolderPresenter {
    private final FolderModel model;

    public FolderPresenter(Activity activity, FolderInterface view) {
        this.model = new FolderModel(activity, App.realm);
    }

    public int addFolder(String folderName) {
        return model.createFolder(folderName);
    }

    public void deleteCollection(Collection<Integer> ids) {
        model.deleteCollection(ids);
    }

    /**
     * Calls model to get imported path and pass it to {@link MainActivity}
     */
    public void importDatabase() {
//        model.importDatabase();
    }

    /**
     * Calls model to export data to database
     *
     * @return The String message of whether import was successful or failed.
     */
    public String exportDatabase() {
        return model.exportDatabase();
    }

}
