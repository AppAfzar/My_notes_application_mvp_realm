package com.appafzar.notes.model;

import android.content.Context;

import androidx.annotation.NonNull;

import com.appafzar.notes.helper.Log;
import com.appafzar.notes.helper.Tools;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 *
 * @param <T>
 */
abstract class BaseModel<T extends RealmObject> {

    protected Realm realm;
    private Class<T> typeParameterClass;

    public BaseModel(Context context, Realm realm, Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.realm = realm;
    }

    public T read(int id) {
        return realm.where(typeParameterClass).equalTo("id", id).findFirst();
    }

    public RealmResults<T> readAll() {
        return realm.where(typeParameterClass).findAll();
    }

    public void createOrUpdate(final String jsonString) {
        if (Tools.isNullOrEmpty(jsonString)) return;
        realm.executeTransactionAsync(realm -> {
            realm.createOrUpdateObjectFromJson(typeParameterClass, jsonString);
            Log.i("local " + typeParameterClass.getSimpleName() + " is updated Successfully!");
        });
    }

    public void createOrUpdateAll(final String jsonArrayString) {
        if (Tools.isNullOrEmpty(jsonArrayString)) return;
        realm.executeTransactionAsync(realm -> {
            realm.createOrUpdateAllFromJson(typeParameterClass, jsonArrayString);
            Log.i("local " + typeParameterClass.getSimpleName() + " list is updated Successfully!");
        });
    }

    public void replaceAll(final String jsonArrayString) {
        deleteAll();
        if (Tools.isNullOrEmpty(jsonArrayString)) return;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.createAllFromJson(typeParameterClass, jsonArrayString);
                Log.i("local " + typeParameterClass.getSimpleName() + " list is replaced Successfully!");
            }
        });
    }

    public void deleteAll() {
        realm.executeTransactionAsync(realm -> realm.delete(typeParameterClass));
    }

    public void delete(final int id) {
        realm.executeTransactionAsync(realm -> {
            realm.where(typeParameterClass).equalTo("id", id).findFirst().deleteFromRealm();
            Log.i(typeParameterClass.getSimpleName() + " with id " + id + " deleted from realm.");
        });
    }

    public void deleteCollection(Collection<Integer> ids) {
        // Create an new array to avoid concurrency problem.
        final Integer[] idsToDelete = new Integer[ids.size()];
        ids.toArray(idsToDelete);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Integer id : idsToDelete) {
                    T t = realm.where(typeParameterClass).equalTo("id", id).findFirst();
                    // Otherwise it has been deleted already.
                    if (t != null) t.deleteFromRealm();
                }
            }
        });
    }


}
