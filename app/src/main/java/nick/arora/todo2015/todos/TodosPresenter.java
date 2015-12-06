package nick.arora.todo2015.todos;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import nick.arora.todo2015.dagger.Injector;
import nick.arora.todo2015.data.InMemoryTodosRepository;
import nick.arora.todo2015.data.models.Todo;
import nick.arora.todo2015.util.RxUtil;
import rx.Subscriber;

import static com.google.common.base.Preconditions.checkNotNull;

public class TodosPresenter implements TodosContract.UserActionListener {

    @Inject InMemoryTodosRepository todosRepository;

    private TodosContract.View mView;

    public TodosPresenter(@NonNull TodosContract.View mView) {
        this.mView = checkNotNull(mView);
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    @Override
    public void initTodos() {
        mView.setProgressIndicator(true);
        loadTodos(false);
    }

    @Override
    public void loadTodos(boolean forceUpdate) {

        if (forceUpdate) todosRepository.refreshUnarchivedData();

        todosRepository.getUnarchivedTodos()
                .compose(RxUtil.applyUiSchedulers())
                .subscribe(new Subscriber<List<Todo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Todo> todos) {
                        mView.setProgressIndicator(false);
                        mView.showTodos(todos);
                    }
                });

    }

    @Override
    public void moveTodos(int fromPosition, int toPosition) {
        mView.moveTodos(fromPosition, toPosition);
    }

    @Override
    public void removeTodo(int position) {

        todosRepository.getUnarchivedTodos().subscribe(new Subscriber<List<Todo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Todo> todos) {
                persistRemovedTodo(todos, position);
            }
        });

        mView.removeTodo(position);
    }

    private void persistRemovedTodo(List<Todo> todos, int position) {
        List<Todo> changedTodos = todos.subList(position, todos.size());

        changedTodos.get(0).archive();
        for (int i = 1; i < changedTodos.size(); i++) {
            // update order for remaining todos
        }

        todosRepository.updateTodos(changedTodos).subscribe(new Subscriber<List<Todo>>() {
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
}
