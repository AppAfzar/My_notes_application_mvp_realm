package com.appafzar.notes.model;

import android.app.Activity;

import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.entity.Note;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class NoteModel extends BaseModel<Note> {

    private Activity activity;

    public NoteModel(Activity activity, Realm realm) {
        super(activity, realm, Note.class);
        this.activity = activity;
    }

    /**
     * This method saves/updates a note to database. We save text as html in order to save bold and italic property.
     * If isEditing is true means user is editing an existing note otherwise user is making new note.
     *
     * @param title user note title
     * @param text  user note text
     */
    public void createNote(String title, String text) {
        final int id = (int) Calendar.getInstance().getTimeInMillis();
        realm.executeTransactionAsync(realm -> {
            Note newNote = realm.createObject(Note.class, id);
            newNote.setTitle(title);
            newNote.setText(text);
            newNote.setPainting(true);
        });
    }

    /**
     * Checks if we are editing an existing drawing or making new one
     *
     * @return boolean The extra boolean sent from NoteActivity
     */
    public boolean isInEditMode() {
        return activity.getIntent().getBooleanExtra(Const.IS_EDITING, false);
    }
}
