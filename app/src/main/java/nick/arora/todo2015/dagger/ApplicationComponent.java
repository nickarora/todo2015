package nick.arora.todo2015.dagger;


import dagger.Component;
import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.launcher.LauncherActivity;

@PerApp
@Component(
    modules= {
        AppContextModule.class
    }
)
public interface ApplicationComponent extends AppContextComponent {
    void inject(BaseActivity baseActivity);
    void inject(LauncherActivity launcherActivity);
}
