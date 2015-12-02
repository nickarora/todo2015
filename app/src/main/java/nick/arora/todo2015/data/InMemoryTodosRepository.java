package nick.arora.todo2015.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.List;

import nick.arora.todo2015.data.api.TodoServiceApi;
import nick.arora.todo2015.data.models.Todo;
import rx.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemoryTodosRepository implements TodosRepository {

    private final TodoServiceApi mTodoServiceApi;

    @VisibleForTesting
    List<Todo> mCachedTodos;

    public InMemoryTodosRepository(@NonNull TodoServiceApi todoServiceApi) {
        this.mTodoServiceApi = todoServiceApi;
    }

    @Override
    public Observable<List<Todo>> getTodos() {
        if (mCachedTodos == null) {

            return mTodoServiceApi.getTodos()
                    .map((List<Todo> todos) -> {
                        mCachedTodos = ImmutableList.copyOf(todos);
                        return todos;
                    });
        } else {
            return Observable.just(mCachedTodos);
        }
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

        return mTodoServiceApi.saveTodo(todo).map((Todo t) -> {
            refreshData();
            return t;
        });
    }

    @Override
    public Observable<Todo> updateTodo(@NonNull Todo todo) {
        checkNotNull(todo);

        return mTodoServiceApi.updateTodo(todo).map((Todo t) -> {
            refreshData();
            return t;
        });
    }

    @Override
    public void refreshData() {
        mCachedTodos = null;
    }

}
