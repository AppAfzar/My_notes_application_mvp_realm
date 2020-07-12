package com.appafzar.notes.helper;

public class Const {

    // Colors
    public static final int BLACK = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;

    //brushes
    public static final int PEN = 3;
    public static final int BRUSH = 4;
    public static final int MARKER = 5;

    public static final String IS_EDITING = "isEditing";

    /* Inner class that defines the entity contents for categories */
    public static abstract class FolderEntity {
        public final static String FIELD_ID = "id";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_NOTES = "notes";
    }

    /* Inner class that defines the entity contents for notes */
    public static abstract class NoteEntity {
        public final static String FIELD_ID = "id";
        public static final String FIELD_TITLE = "title";
        public static final String FIELD_TEXT = "text";
        public static final String FIELD_DRAWING = "drawing";
    }

}
