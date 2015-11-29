package nick.arora.todo2015.todos;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.R;
import nick.arora.todo2015.data.Todo;
import nick.arora.todo2015.data.TodosServiceSource;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class TodosActivity extends BaseActivity implements TodosContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);


        testTodosSource();
    }

    private void testTodosSource() {
        TodosServiceSource.getTodos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Todo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Todo> todos) {
                        for (int i = 0; i < todos.size(); i++) {
                            Log.d("_TODOS", "onNext: " + todos.get(i).getTitle());
                        }
                    }
                });
    }
}
