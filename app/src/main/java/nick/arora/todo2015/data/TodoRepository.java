package nick.arora.todo2015.data;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

public interface TodoRepository {

    Observable<List<Todo>> getTodosList();

    Observable<Todo> getEachTodo();

    Observable<Todo> getEachTodo(boolean archived);

    Observable<Todo> getTodo(String id);

    Observable<Todo> saveTodo(@NonNull Todo todo);

    Observable<Todo> updateTodo(@NonNull Todo todo);

    void refreshData();
}
