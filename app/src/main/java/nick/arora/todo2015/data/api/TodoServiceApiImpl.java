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
        return mTodosServiceSource
                .getTodos()
                .map(new Func1<List<Todo>, List<Todo>>() {
                    @Override
                    public List<Todo> call(List<Todo> todos) {
                        return Filter.todosByDevice(todos, mDeviceId.getString());
                    }
                });
    }

    @Override
    public Observable<Todo> getEachTodo() {
        return getTodos()
                .flatMap(new Func1<List<Todo>, Observable<Todo>>() {
                    @Override
                    public Observable<Todo> call(List<Todo> todos) {
                        return Observable.from(todos);
                    }
                });
    }

    @Override
    public Observable<Todo> getEachUnarchivedTodo() {
        return getEachTodo()
                .filter(new Func1<Todo, Boolean>() {
                    @Override
                    public Boolean call(Todo todo) {
                        return !todo.isArchived();
                    }
                });
    }

    @Override
    public Observable<Todo> getEachArchivedTodo() {
        return getEachTodo()
                .filter(new Func1<Todo, Boolean>() {
                    @Override
                    public Boolean call(Todo todo) {
                        return todo.isArchived();
                    }
                });
    }

    @Override
    public Observable<Todo> getTodo(String id) {
        return mTodosServiceSource
                .getTodo(id)
                .filter(new Func1<Todo, Boolean>() {
                    @Override
                    public Boolean call(Todo todo) {
                        return todo.getDeviceId().equals(mDeviceId.getString());
                    }
                });
    }

    @Override
    public Observable<Todo> saveTodo(final Todo todo) {
        return mTodosServiceSource
                .saveTodo(todo)
                .map(new Func1<Parse, Todo>() {
                    @Override
                    public Todo call(Parse parse) {
                        todo.setParseData(parse);
                        return todo;
                    }
                });
    }

    @Override
    public Observable<Todo> updateTodo(final Todo todo) {
        return mTodosServiceSource
                .updateTodo(todo)
                .map(new Func1<Parse, Todo>() {
                    @Override
                    public Todo call(Parse parse) {
                        todo.setParseData(parse);
                        return todo;
                    }
                });
    }

}
