package nick.arora.todo2015.data.api;

import dagger.Module;
import dagger.Provides;
import nick.arora.todo2015.dagger.PerApp;

@Module
public class TodoServiceModule {

    @Provides @PerApp
    public TodosServiceSource provideTodoServiceSource() {
        return new TodosServiceSource();
    }

    @Provides @PerApp
    public TodoServiceApiImpl provideTodoServiceApiImpl(TodosServiceSource todosServiceSource, String deviceId) {
        return new TodoServiceApiImpl(todosServiceSource, deviceId);
    }

}
