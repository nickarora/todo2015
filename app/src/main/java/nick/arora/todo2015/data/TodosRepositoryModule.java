package nick.arora.todo2015.data;

import dagger.Module;
import dagger.Provides;
import nick.arora.todo2015.dagger.PerApp;
import nick.arora.todo2015.data.api.TodoServiceApiImpl;

@Module
public class TodosRepositoryModule {

    @Provides
    @PerApp
    public InMemoryTodosRepository provideInMemoryTodoRepository(TodoServiceApiImpl todoServiceApi) {
        return new InMemoryTodosRepository(todoServiceApi);
    }

}
