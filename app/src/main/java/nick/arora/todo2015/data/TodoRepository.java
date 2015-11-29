package nick.arora.todo2015.data;

import android.support.annotation.NonNull;

public interface TodoRepository {

    void getTodos();

    void getTodo();

    void saveTodo(@NonNull Todo todo);

    void updateTodo(@NonNull Todo todo);

    void refreshData();
}
