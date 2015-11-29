package nick.arora.todo2015.data;

import java.util.List;

import rx.Subscriber;

public class TodoServiceApiImpl implements TodoServiceApi {

    @Override
    public void getAllTodos() {
        TodosServiceSource.getTodos()
                .subscribe(new Subscriber<List<Todo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Todo> todos) {
                    }
                });
    }

    @Override
    public void saveTodo(final Todo todo) {
        TodosServiceSource.saveTodo(todo).subscribe(new Subscriber<Parse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Parse parse) {
                todo.setParseData(parse);
            }
        });
    }

    @Override
    public void updateTodo(final Todo todo) {
        if (!todo.isPersisted()) return;

        TodosServiceSource.updateTodo(todo).subscribe(new Subscriber<Parse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Parse parse) {
                todo.setParseData(parse);
            }
        });
    }
}
