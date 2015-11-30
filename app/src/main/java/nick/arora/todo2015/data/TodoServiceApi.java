package nick.arora.todo2015.data;

import java.util.List;

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
