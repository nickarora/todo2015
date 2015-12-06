package nick.arora.todo2015.data.api;

import java.util.List;

import nick.arora.todo2015.data.models.Todo;
import nick.arora.todo2015.persistedDeviceId.PersistedDeviceId;
import rx.Observable;

public interface TodoServiceApi {

    Observable<List<Todo>> getUnarchivedTodos();

    Observable<List<Todo>> getArchivedTodos();

    Observable<Todo> getTodo(String id);

    Observable<Todo> saveTodo(Todo todo);

    Observable<Todo> updateTodo(Todo todo);

    Observable<List<Todo>> updateTodos(List<Todo> todos);

}
