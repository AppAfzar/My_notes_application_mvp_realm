package com.appafzar.notes.presenter;

import android.app.Activity;
import android.text.Editable;

import com.appafzar.notes.App;
import com.appafzar.notes.model.NoteModel;
import com.appafzar.notes.model.interfaces.NoteInterface;

import java.util.Collection;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class NotePresenter {
    private NoteModel model;

    public NotePresenter(Activity context, NoteInterface view) {
        this.model = new NoteModel(context, App.realm);
    }

    /**
     * Save user note using model saveNote method.
     */
    public void createNote(String title, Editable text) {
        model.createNote(title, text);
    }


    public void delete(int id) {
        model.delete(id);
    }

    public void deleteCollection(Collection<Integer> ids) {
        model.deleteCollection(ids);
    }
}
