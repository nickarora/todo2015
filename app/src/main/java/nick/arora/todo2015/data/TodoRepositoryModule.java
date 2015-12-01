package nick.arora.todo2015.data;

import dagger.Module;
import dagger.Provides;
import nick.arora.todo2015.dagger.PerApp;
import nick.arora.todo2015.data.api.TodoServiceApiImpl;

@Module
public class TodoRepositoryModule {

    @Provides
    @PerApp
    public InMemoryTodoRepository provideInMemoryTodoRepository(TodoServiceApiImpl todoServiceApi) {
        return new InMemoryTodoRepository(todoServiceApi);
    }

}
