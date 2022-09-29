package curso.clases.cursopersistenciarealm.Util;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DataRealm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("RealmData.realm")
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}
