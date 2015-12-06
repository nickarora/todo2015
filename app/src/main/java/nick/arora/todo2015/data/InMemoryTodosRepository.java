package nick.arora.todo2015.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.Collections;
import java.util.List;

import nick.arora.todo2015.data.api.TodoServiceApi;
import nick.arora.todo2015.data.models.Todo;
import rx.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemoryTodosRepository implements TodosRepository {

    private final TodoServiceApi mTodoServiceApi;

    @VisibleForTesting
    List<Todo> mCachedArchivedTodos;
    List<Todo> mCachedUnarchivedTodos;

    public InMemoryTodosRepository(@NonNull TodoServiceApi todoServiceApi) {
        this.mTodoServiceApi = todoServiceApi;
    }

    @Override
    public Observable<List<Todo>> getUnarchivedTodos() {
        if (mCachedUnarchivedTodos == null) {
            return mTodoServiceApi.getUnarchivedTodos()
                    .map((List<Todo> todos) -> {
                        mCachedUnarchivedTodos = ImmutableList.copyOf(todos);
                        return todos;
                    });
        } else {
            return Observable.just(mCachedUnarchivedTodos);
        }
    }

    @Override
    public Observable<List<Todo>> getArchivedTodos() {
        if (mCachedArchivedTodos == null) {
            return mTodoServiceApi.getArchivedTodos()
                    .map((List<Todo> todos) -> {
                        mCachedArchivedTodos = ImmutableList.copyOf(todos);
                        return todos;
                    });
        } else {
            return Observable.just(mCachedArchivedTodos);
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
            refreshUnarchivedData();
            return t;
        });
    }

    @Override
    public Observable<Todo> updateTodo(@NonNull Todo todo) {
        checkNotNull(todo);

        return mTodoServiceApi.updateTodo(todo).map((Todo t) -> {
            refreshUnarchivedData();
            if (t.isArchived()) refreshArchivedData();
            return t;
        });
    }

    @Override
    public Observable<List<Todo>> updateTodos(@NonNull List<Todo> todos) {
        checkNotNull(todos);
        return mTodoServiceApi.updateTodos(todos)
                .map(changedTodos -> {
                    refreshUnarchivedData();
                    if (todoRemoved(changedTodos)) refreshArchivedData();
                    return changedTodos;
                });
    }

    @Override
    public void refreshUnarchivedData() {
        mCachedUnarchivedTodos = null;
    }

    @Override
    public void refreshArchivedData() {
        mCachedArchivedTodos = null;
    }

    private boolean todoRemoved(List<Todo> todos) {
        return Iterables.any(todos, input -> input.isArchived());
    }

}
