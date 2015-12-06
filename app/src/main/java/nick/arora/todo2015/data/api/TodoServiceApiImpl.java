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
    public Observable<List<Todo>> getUnarchivedTodos() {
        return mTodosServiceSource.getUnarchivedTodos(mDeviceId.getString());
    }

    @Override
    public Observable<List<Todo>> getArchivedTodos() {
        return mTodosServiceSource.getArchivedTodos(mDeviceId.getString());
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

    @Override
    public Observable<List<Todo>> updateTodos(List<Todo> todos) {
        return mTodosServiceSource.updateTodos(todos)
                .map(parses -> {
                    for (int i = 0; i < parses.size(); i++) {
                        todos.get(i).setParseData(parses.get(i));
                    }

                    return todos;
                });
    }

}
