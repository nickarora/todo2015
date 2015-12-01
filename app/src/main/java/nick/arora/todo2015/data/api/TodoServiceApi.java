package nick.arora.todo2015.data.api;

import java.util.List;

import nick.arora.todo2015.data.models.Todo;
import rx.Observable;

public interface TodoServiceApi {

    Observable<List<Todo>> getTodos();

    Observable<Todo> getEachTodo();

    Observable<Todo> getEachUnarchivedTodo();

    Observable<Todo> getEachArchivedTodo();

    Observable<Todo> getTodo(String id);

    Observable<Todo> saveTodo(Todo todo);

    Observable<Todo> updateTodo(Todo todo);

}