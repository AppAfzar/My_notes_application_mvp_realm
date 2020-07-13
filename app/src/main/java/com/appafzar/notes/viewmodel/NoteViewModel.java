package com.appafzar.notes.viewmodel;

import android.app.Activity;
import android.text.Editable;

import androidx.lifecycle.MutableLiveData;

import com.appafzar.notes.App;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.FolderModel;
import com.appafzar.notes.model.NoteModel;
import com.appafzar.notes.repositoriy.Repository;
import com.appafzar.notes.view.custom.CustomDrawingView;

import java.util.Collection;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class NoteViewModel {
    private Repository repository;
    private MutableLiveData<FolderModel> mutableLiveData;

    public NoteViewModel(Activity context) {
        this.repository = new Repository(App.realm);
    }

    public MutableLiveData<FolderModel> getFolder(int folderId) {
        if (mutableLiveData == null) {
            mutableLiveData = repository.requestFolder(folderId);
        }
        return mutableLiveData;
    }

    /**
     * Save user note using model saveNote method.
     */
    public void createNote(FolderModel folder, String title, Editable text) {
        repository.addNoteToFolder(folder, title, text);
    }


    public void deleteNote(int id) {
        repository.deleteNote(id);
    }

    public void deleteNoteCollection(Collection<Integer> ids) {
        repository.deleteNoteCollection(ids);
    }

    public void createDrawing(FolderModel folder, String title, CustomDrawingView painting) {
        repository.createDrawing(folder, title, painting);
    }

    public NoteModel getNote(int nid) {
        return App.realm.where(NoteModel.class).equalTo(Const.FolderEntity.FIELD_ID, nid).findFirst();
    }
}
