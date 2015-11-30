package nick.arora.todo2015.data;

import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableList;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemoryTodoRepository implements TodoRepository {

    private final TodoServiceApi mTodoServiceApi;
    private List<Todo> mCachedTodos;

    public InMemoryTodoRepository(@NonNull TodoServiceApi todoServiceApi) {
        this.mTodoServiceApi = todoServiceApi;
    }

    @Override
    public Observable<List<Todo>> getTodosList() {
        return mTodoServiceApi
                .getTodosList()
                .map(new Func1<List<Todo>, List<Todo>>() {
                    @Override
                    public List<Todo> call(List<Todo> todos) {
                        mCachedTodos = ImmutableList.copyOf(todos);
                        return todos;
                    }
                });
    }

    @Override
    public Observable<Todo> getEachTodo() {
        return mTodoServiceApi.getEachTodo();
    }

    @Override
    public Observable<Todo> getEachTodo(boolean archived) {
        if (archived) {
            return mTodoServiceApi.getEachArchivedTodo();
        } else {
            return  mTodoServiceApi.getEachUnarchivedTodo();
        }
    }

    @Override
    public Observable<Todo> getTodo(@NonNull String id) {
        checkNotNull(id);
        return mTodoServiceApi.getTodo(id);
    }

    @Override
    public Observable<Todo> saveTodo(@NonNull Todo todo) {
        checkNotNull(todo);

        refreshData();
        return mTodoServiceApi.saveTodo(todo);
    }

    @Override
    public Observable<Todo> updateTodo(@NonNull Todo todo) {
        checkNotNull(todo);

        refreshData();
        return mTodoServiceApi.updateTodo(todo);
    }

    @Override
    public void refreshData() {
        mCachedTodos = null;
    }

}
