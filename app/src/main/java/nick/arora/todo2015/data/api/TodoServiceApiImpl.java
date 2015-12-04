package nick.arora.todo2015.data.api;

import java.util.List;

import nick.arora.todo2015.data.models.Parse;
import nick.arora.todo2015.data.models.Todo;
import nick.arora.todo2015.persistedDeviceId.PersistedDeviceId;
import nick.arora.todo2015.util.Filter;
import rx.Observable;
import rx.functions.Func1;

public class TodoServiceApiImpl implements TodoServiceApi {

    private PersistedDeviceId mDeviceId;
    private TodosServiceSource mTodosServiceSource;

    public TodoServiceApiImpl(TodosServiceSource todosServiceSource, PersistedDeviceId deviceId) {
        this.mTodosServiceSource = todosServiceSource;
        this.mDeviceId = deviceId;
    }

    @Override
    public Observable<List<Todo>> getTodos() {
        return mTodosServiceSource.getTodos(mDeviceId.toString());
    }

    @Override
    public Observable<List<Todo>> getUnarchivedTodos() {
        return mTodosServiceSource.getUnarchivedTodos(mDeviceId.getString());
    }

    @Override
    public Observable<List<Todo>> getArchivedTodos() {
        return mTodosServiceSource.getArchivedTodos(mDeviceId.getString());
    }

    @Override
    public Observable<Todo> getEachTodo() {
        return getTodos().flatMap(Observable::from);
    }

    @Override
    public Observable<Todo> getEachUnarchivedTodo() {
        return getUnarchivedTodos().flatMap(Observable::from);
    }

    @Override
    public Observable<Todo> getEachArchivedTodo() {
        return getArchivedTodos().flatMap(Observable::from);
    }

    @Override
    public Observable<Todo> getTodo(String id) {
        return mTodosServiceSource.getTodo(id).filter((Todo todo) -> todo.getDeviceId().equals(mDeviceId.getString()));
    }

    @Override
    public Observable<Todo> saveTodo(final Todo todo) {
        return mTodosServiceSource.saveTodo(todo)
                .map((Parse parse) -> {
                    todo.setParseData(parse);
                    return todo;
                });
    }

    @Override
    public Observable<Todo> updateTodo(final Todo todo) {
        return mTodosServiceSource.updateTodo(todo)
                .map((Parse parse) -> {
                    todo.setParseData(parse);
                    return todo;
                });
    }

}
