package nick.arora.todo2015.todos;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.R;
import nick.arora.todo2015.dagger.Injector;
import nick.arora.todo2015.data.InMemoryTodosRepository;
import nick.arora.todo2015.data.models.Todo;
import rx.Subscriber;

public class TodosActivity extends BaseActivity implements TodosContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        setContentView(R.layout.activity_todos);
    }

}
