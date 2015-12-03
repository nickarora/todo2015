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
    public void initNotes() {
        mView.setProgressIndicator(true);
        loadNotes(false);
    }

    @Override
    public void loadNotes(boolean forceUpdate) {
        if (forceUpdate) todosRepository.refreshData();

        todosRepository.getTodos()
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
                        mView.showNotes(todos);
                    }
                });
    }
}
