package nick.arora.todo2015.dagger;


import dagger.Component;
import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.data.TodosRepositoryModule;
import nick.arora.todo2015.data.api.TodoServiceModule;
import nick.arora.todo2015.persistedDeviceId.PersistedDeviceIdModule;
import nick.arora.todo2015.launcher.LauncherActivity;
import nick.arora.todo2015.todos.TodosActivity;
import nick.arora.todo2015.todos.TodosPresenter;

@PerApp
@Component(
    modules= {
        AppContextModule.class,
        TodoServiceModule.class,
        TodosRepositoryModule.class,
        PersistedDeviceIdModule.class
    }
)
public interface ApplicationComponent extends AppContextComponent {
    void inject(BaseActivity baseActivity);
    void inject(LauncherActivity launcherActivity);
    void inject(TodosPresenter todosPresenter);
}
