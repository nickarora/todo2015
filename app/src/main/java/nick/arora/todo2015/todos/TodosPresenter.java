package nick.arora.todo2015.todos;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import nick.arora.todo2015.dagger.Injector;
import nick.arora.todo2015.data.InMemoryTodosRepository;
import nick.arora.todo2015.data.models.Todo;
import nick.arora.todo2015.util.RxUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

public class TodosPresenter implements TodosContract.UserActionListener {

    @Inject InMemoryTodosRepository todosRepository;

    private TodosContract.View mView;
    private CompositeSubscription mCompositeSubscription;

    public TodosPresenter(@NonNull TodosContract.View mView) {
        this.mView = checkNotNull(mView);
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    @Override
    public void startListening() {
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void stopListening() {
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void initTodos() {
        mView.setProgressIndicator(true);
        loadTodos(false);
    }

    @Override
    public void loadTodos(boolean forceUpdate) {
        if (forceUpdate) todosRepository.refreshUnarchivedData();
        mCompositeSubscription.add(loadUnarchivedTodos());
    }

    @Override
    public void moveTodos(int fromPosition, int toPosition) {
        mView.moveTodos(fromPosition, toPosition);
    }

    @Override
    public void removeTodo(int position) {
        mCompositeSubscription.add(persistRemovedTodo(position));
        mView.removeTodo(position);
    }

    private Subscription persistRemovedTodo(int position) {
        return todosRepository.getUnarchivedTodos()
                .map(todos -> todosAffectedByRemoval(todos, position))
                .flatMap(todos -> todosRepository.updateTodos(todos))
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

    private Subscription loadUnarchivedTodos() {
        return todosRepository.getUnarchivedTodos()
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

    private List<Todo> todosAffectedByRemoval(List<Todo> todos, int position) {
        List<Todo> changedTodos = todos.subList(position, todos.size());

        changedTodos.get(0).archive();
        for (int i = 1; i < changedTodos.size(); i++) {
            changedTodos.get(i).decrementOrder();
        }

        return changedTodos;
    }
}
