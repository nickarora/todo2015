package nick.arora.todo2015.persistedDeviceId;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import nick.arora.todo2015.BaseApplication;
import nick.arora.todo2015.dagger.PerApp;

@Module
public class PersistedDeviceIdModule {

    @Provides
    @PerApp
    public SharedPreferences provideSharedPreferences(BaseApplication app) {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @PerApp
    public PersistedDeviceId providePersistedDeviceId(SharedPreferences preferences) {
        return new PersistedDeviceId(preferences);
    }

}
