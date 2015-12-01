package nick.arora.todo2015.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import nick.arora.todo2015.BaseApplication;

@Module
public class AppContextModule {

    private final BaseApplication app;

    public AppContextModule(BaseApplication app){
        this.app = app;
    }

    @Provides
    @PerApp
    public BaseApplication application() {
        return this.app;
    }

    @Provides @PerApp
    public Context applicationContext() {
        return this.app.getApplicationContext();
    }

}
