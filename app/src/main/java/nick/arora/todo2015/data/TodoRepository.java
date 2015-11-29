package nick.arora.todo2015.data;

import android.support.annotation.NonNull;

import rx.Observable;

public interface TodoRepository {

    Observable<Todo> getAllTodos();

    Observable<Todo> getTodos(boolean archived);

    Observable<Todo> getTodo(String id);

    Observable<Todo> saveTodo(@NonNull Todo todo);

    Observable<Todo> updateTodo(@NonNull Todo todo);

    void refreshData();
}
