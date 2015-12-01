package nick.arora.todo2015.persistedDeviceId;

import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import java.util.UUID;

import nick.arora.todo2015.BuildConfig;

public class PersistedDeviceId {

    private SharedPreferences appPreferences;
    private String uuid;

    public PersistedDeviceId(SharedPreferences sharedPreferences) {
        this.appPreferences = sharedPreferences;
        retrieveIdFromPersistedData();
    }

    private void retrieveIdFromPersistedData() {
        uuid = appPreferences.getString(BuildConfig.TODO_DEVICE_KEY, null);
    }

    public String getString() {
        if (uuid == null) { initDeviceId(); }
        return uuid;
    }

    private void initDeviceId() {
        uuid = generateDeviceId();
        persistDeviceId();
    }

    private void persistDeviceId() {
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putString(BuildConfig.TODO_DEVICE_KEY, uuid).apply();
    }

    private String generateDeviceId() {
        return UUID.randomUUID().toString();
    }

}
