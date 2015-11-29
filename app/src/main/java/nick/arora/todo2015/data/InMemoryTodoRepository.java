package nick.arora.todo2015.data;

import android.support.annotation.NonNull;

import java.util.List;

public class InMemoryTodoRepository implements TodoRepository {

    private final TodoServiceApi mTodoServiceApi;
    private List<Todo> mCachedTodos;

    public InMemoryTodoRepository(@NonNull TodoServiceApi todoServiceApi) {
        this.mTodoServiceApi = todoServiceApi;
    }


    @Override
    public void getTodos() {

    }

    @Override
    public void getTodo() {

    }

    @Override
    public void saveTodo(@NonNull Todo todo) {

    }

    @Override
    public void updateTodo(@NonNull Todo todo) {

    }

    @Override
    public void refreshData() {

    }

}
