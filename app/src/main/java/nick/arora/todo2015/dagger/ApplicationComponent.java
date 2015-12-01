package nick.arora.todo2015.dagger;


import dagger.Component;
import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.data.TodoRepositoryModule;
import nick.arora.todo2015.data.api.TodoServiceModule;
import nick.arora.todo2015.deviceId.DeviceIdModule;
import nick.arora.todo2015.launcher.LauncherActivity;
import nick.arora.todo2015.todos.TodosActivity;

@PerApp
@Component(
    modules= {
        AppContextModule.class,
        TodoServiceModule.class,
        TodoRepositoryModule.class,
        DeviceIdModule.class
    }
)
public interface ApplicationComponent extends AppContextComponent {
    void inject(BaseActivity baseActivity);
    void inject(LauncherActivity launcherActivity);
    void inject(TodosActivity todosActivity);
}
