package nick.arora.todo2015.deviceId;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import nick.arora.todo2015.BaseApplication;
import nick.arora.todo2015.dagger.PerApp;

@Module
public class DeviceIdModule {

    @Provides
    @PerApp
    public SharedPreferences provideSharedPreferences(BaseApplication app) {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @PerApp
    public DeviceId provideDeviceId(SharedPreferences preferences) {
        return new DeviceId(preferences);
    }

}
