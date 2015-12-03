package nick.arora.todo2015.todos;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.common.collect.Lists;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.R;
import nick.arora.todo2015.dagger.Injector;
import nick.arora.todo2015.data.models.Todo;

public class TodosActivity extends BaseActivity implements TodosContract.View {

    @Bind(R.id.toolbar) Toolbar toolBar;
    @Bind(R.id.recycler) RecyclerView recycler;

    private TodosAdapter mTodosAdapter;

    private static final int NUM_COLUMNS = 2;

    private static String MY_DEVICE = "A100";
    private static String OTHER_DEVICE = "B200";

    List<Todo> TODOS = Lists.newArrayList(
            new Todo(MY_DEVICE, "Test1", "Description1", false),
            new Todo(MY_DEVICE, "Test2", "Description2", false),
            new Todo(MY_DEVICE, "Test3", "Description3", false),
            new Todo(MY_DEVICE, "Test4", "Description4", true),
            new Todo(MY_DEVICE, "Test5", "Description5", true),
            new Todo(OTHER_DEVICE, "Test6", "Description6", false),
            new Todo(OTHER_DEVICE, "Test7", "Description7", true)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        initToolBar();
        initTodoList();
    }

    private void initToolBar() {
        setSupportActionBar(toolBar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initTodoList() {
        mTodosAdapter = new TodosAdapter(TODOS);
        recycler.setAdapter(mTodosAdapter);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));
    }

}
