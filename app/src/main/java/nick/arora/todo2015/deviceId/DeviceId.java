package nick.arora.todo2015.deviceId;

import android.content.SharedPreferences;

import java.util.UUID;

import nick.arora.todo2015.BuildConfig;

public class DeviceId {

    private SharedPreferences appPreferences;
    private String uuid;

    public DeviceId(SharedPreferences sharedPreferences) {
        this.appPreferences = sharedPreferences;
        retrieveIdFromPersistedData();
    }

    private void retrieveIdFromPersistedData() {
        appPreferences.getString(BuildConfig.TODO_DEVICE_KEY, null);
    }

    public String getUuid() {
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
