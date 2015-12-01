package nick.arora.todo2015;

import android.app.Application;

import nick.arora.todo2015.dagger.Injector;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.INSTANCE.initializeApplicationComponent(this);
    }

}
