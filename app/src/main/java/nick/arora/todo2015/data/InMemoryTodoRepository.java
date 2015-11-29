package nick.arora.todo2015.data;

import android.support.annotation.NonNull;

import java.util.List;

import nick.arora.todo2015.util.RxUtil;
import rx.Observable;
import rx.Subscriber;

public class InMemoryTodoRepository implements TodoRepository {

    private final TodoServiceApi mTodoServiceApi;
    private List<Todo> mCachedTodos;

    public InMemoryTodoRepository(@NonNull TodoServiceApi todoServiceApi) {
        this.mTodoServiceApi = todoServiceApi;
    }

    @Override
    public Observable<Todo> getAllTodos() {
        return mTodoServiceApi.getAllTodos();
    }

    @Override
    public Observable<Todo> getTodos(boolean archived) {
        if (archived) {
            return mTodoServiceApi.getArchivedTodos();
        } else {
            return  mTodoServiceApi.getUnarchivedTodos();
        }
    }

    @Override
    public Observable<Todo> getTodo(String id) {
        return mTodoServiceApi.getTodo(id);
    }

    @Override
    public Observable<Todo> saveTodo(@NonNull Todo todo) {
        return mTodoServiceApi.saveTodo(todo);
    }

    @Override
    public Observable<Todo> updateTodo(@NonNull Todo todo) {
        return mTodoServiceApi.updateTodo(todo);
    }

    @Override
    public void refreshData() {
        mTodoServiceApi.getTodosList()
                .compose(RxUtil.<List<Todo>>applyBackgroundSchedulers())
                .subscribe(new Subscriber<List<Todo>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(List<Todo> todos) {
                        mCachedTodos = todos;
                    }
                });
    }

}
