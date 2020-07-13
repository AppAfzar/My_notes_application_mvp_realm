package com.appafzar.notes.repositoriy;

import android.text.Editable;
import android.text.Html;

import androidx.lifecycle.MutableLiveData;

import com.appafzar.notes.App;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.helper.Tools;
import com.appafzar.notes.model.FolderModel;
import com.appafzar.notes.model.NoteModel;
import com.appafzar.notes.view.custom.CustomDrawingView;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class Repository {

    private Realm realm;

    public Repository(Realm realm) {
        this.realm = realm;
    }

    public MutableLiveData<FolderModel> requestFolder(int folderId) {
        final MutableLiveData<FolderModel> mutableLiveData = new MutableLiveData<>();

        mutableLiveData.setValue(App.realm.where(FolderModel.class).equalTo(Const.FolderEntity.FIELD_ID, folderId).findFirst());

        return mutableLiveData;
    }

    public MutableLiveData<List<FolderModel>> requestFolders() {
        final MutableLiveData<List<FolderModel>> mutableLiveData = new MutableLiveData<>();

        mutableLiveData.setValue(App.realm.where(FolderModel.class).sort(Const.FolderEntity.FIELD_NAME, Sort.ASCENDING).findAll());

        return mutableLiveData;
    }


    /**
     * This method saves/updates a note to database. We save text as html in order to save bold and italic property.
     * If isEditing is true means user is editing an existing note otherwise user is making new note.
     *
     * @param title user note title
     * @param text  user note text
     */
    public void addNoteToFolder(FolderModel folder, String title, Editable text) {
        if (folder == null) return;
        final int id = (int) Calendar.getInstance().getTimeInMillis();
        realm.executeTransactionAsync(realm -> {
            NoteModel newNote = realm.createObject(NoteModel.class, id);
            newNote.setTitle(title);
            newNote.setText(Html.toHtml(text));
            newNote.setPainting(true);
            folder.getNotes().add(newNote);
        });
    }

    /**
     * This method saves a folder to database.
     *
     * @param name user note name
     */
    public int createFolder(String name) {
        if (Tools.isNullOrEmpty(name)) return 0;
        final int id = (int) Calendar.getInstance().getTimeInMillis();
        realm.executeTransactionAsync(realm -> {
            FolderModel folder = realm.createObject(FolderModel.class, id);
            folder.setName(name);
            folder.setNotes(new RealmList<>());
        });
        return id;
    }


    public void addDrawingToFolder(FolderModel folder, String title, CustomDrawingView painting) {
        // TODO: 2020-07-13
    }

    public void clearAllFolders() {
        realm.executeTransactionAsync(realm ->
                realm.delete(FolderModel.class)
        );

    }

    public void importFolders(String asJSON) {
        realm.executeTransactionAsync(realm ->
                realm.createOrUpdateAllFromJson(FolderModel.class, asJSON)
        );
    }

    public String exportFolders() {
        return realm.where(FolderModel.class).findAll().asJSON();
    }

    public void deleteNote(int noteId) {
        realm.executeTransactionAsync(realm -> {
            realm.where(NoteModel.class).equalTo("id", noteId).findFirst().deleteFromRealm();
        });
    }

    public void deleteNoteCollection(Collection<Integer> ids) {
        // Create an new array to avoid concurrency problem.
        final Integer[] idsToDelete = new Integer[ids.size()];
        ids.toArray(idsToDelete);
        realm.executeTransactionAsync(realm -> {
            for (Integer id : idsToDelete) {
                NoteModel note = realm.where(NoteModel.class).equalTo("id", id).findFirst();
                // Otherwise it has been deleted already.
                if (note != null) note.deleteFromRealm();
            }
        });
    }

    public void createDrawing(FolderModel folder, String title, CustomDrawingView painting) {

    }
}
