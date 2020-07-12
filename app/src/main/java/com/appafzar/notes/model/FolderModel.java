package com.appafzar.notes.model;

import android.content.Context;

import com.appafzar.notes.helper.Tools;
import com.appafzar.notes.model.entity.Folder;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class FolderModel extends BaseModel<Folder> {

    public FolderModel(Context context, Realm realm) {
        super(context, realm, Folder.class);
    }

    public int createFolder(String name) {
        if (Tools.isNullOrEmpty(name)) return 0;
        final int id = (int) Calendar.getInstance().getTimeInMillis();
        realm.executeTransactionAsync(realm -> {
            Folder folder = realm.createObject(Folder.class, id);
            folder.setName(name);
            folder.setNotes(new RealmList<>());
        });
        return id;
    }

    public void getImportedPath() {

    }

    public String exportDatabase() {
        return null;
    }
}
