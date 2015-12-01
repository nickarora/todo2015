package nick.arora.todo2015.data;

import android.support.annotation.NonNull;

import java.util.List;

import nick.arora.todo2015.data.models.Todo;
import rx.Observable;

public interface TodosRepository {

    Observable<List<Todo>> getTodos();

    Observable<Todo> getEachTodo();

    Observable<Todo> getEachTodo(boolean archived);

    Observable<Todo> getTodo(String id);

    Observable<Todo> saveTodo(@NonNull Todo todo);

    Observable<Todo> updateTodo(@NonNull Todo todo);

    void refreshData();
}
