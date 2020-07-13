package com.appafzar.notes.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class NoteModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    @Required
    private String text;
    private byte[] drawing;
    private boolean painting;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getDrawing() {
        return drawing;
    }

    public void setDrawing(byte[] drawing) {
        this.drawing = drawing;
    }

    public boolean isPainting() {
        return painting;
    }

    public void setPainting(boolean painting) {
        this.painting = painting;
    }
}
