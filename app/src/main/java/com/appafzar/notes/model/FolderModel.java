package com.appafzar.notes.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FolderModel extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private RealmList<NoteModel> notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<NoteModel> getNotes() {
        return notes;
    }

    public void setNotes(RealmList<NoteModel> notes) {
        this.notes = notes;
    }
}
