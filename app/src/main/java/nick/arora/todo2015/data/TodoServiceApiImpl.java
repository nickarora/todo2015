package nick.arora.todo2015.data;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class TodoServiceApiImpl implements TodoServiceApi {

    @Override
    public Observable<List<Todo>> getTodosList() {
        return TodosServiceSource.getTodos();
    }

    @Override
    public Observable<Todo> getAllTodos() {
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
        return getAllTodos()
                .filter(new Func1<Todo, Boolean>() {
                    @Override
                    public Boolean call(Todo todo) {
                        return !todo.isArchived();
                    }
                });
    }

    @Override
    public Observable<Todo> getArchivedTodos() {
        return getAllTodos()
                .filter(new Func1<Todo, Boolean>() {
                    @Override
                    public Boolean call(Todo todo) {
                        return todo.isArchived();
                    }
                });
    }

    @Override
    public Observable<Todo> getTodo(String id) {
        return TodosServiceSource.getTodo(id);
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
