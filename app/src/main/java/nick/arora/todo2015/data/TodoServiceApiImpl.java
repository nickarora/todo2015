package nick.arora.todo2015.data;

import java.util.List;

import nick.arora.todo2015.util.Filter;
import rx.Observable;
import rx.functions.Func1;

public class TodoServiceApiImpl implements TodoServiceApi {

    private String mDeviceId;

    public TodoServiceApiImpl(String mDeviceId) {
        this.mDeviceId = mDeviceId;
    }

    @Override
    public Observable<List<Todo>> getTodosList() {
        return TodosServiceSource
                .getTodos()
                .map(new Func1<List<Todo>, List<Todo>>() {
                    @Override
                    public List<Todo> call(List<Todo> todos) {
                        return Filter.todosByDevice(todos, mDeviceId);
                    }
                });
    }

    @Override
    public Observable<Todo> getEachTodo() {
        return TodosServiceSource
                .getTodos()
                .flatMap(new Func1<List<Todo>, Observable<Todo>>() {
                    @Override
                    public Observable<Todo> call(List<Todo> todos) {
                        return Observable.from(todos);
                    }
                });
    }

    @Override
    public Observable<Todo> getUnarchivedTodos() {
        return getEachTodo()
                .filter(new Func1<Todo, Boolean>() {
                    @Override
                    public Boolean call(Todo todo) {
                        return !todo.isArchived();
                    }
                });
    }

    @Override
    public Observable<Todo> getArchivedTodos() {
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
        return TodosServiceSource
                .getTodo(id)
                .filter(new Func1<Todo, Boolean>() {
                    @Override
                    public Boolean call(Todo todo) {
                        return todo.getDeviceId().equals(mDeviceId);
                    }
                });
    }

    @Override
    public Observable<Todo> saveTodo(final Todo todo) {
        return TodosServiceSource
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
        return TodosServiceSource
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
