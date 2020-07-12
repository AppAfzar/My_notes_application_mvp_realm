package com.appafzar.notes;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class App extends Application {

    public static Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize Realm Should only be done once when the application starts.
        initRealm();

    }

    /**
     * Initialize Realm. Should only be done once when the application starts.
     */
    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("notesDB")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();
    }

}