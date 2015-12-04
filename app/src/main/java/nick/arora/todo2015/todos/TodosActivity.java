package nick.arora.todo2015.todos;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.R;
import nick.arora.todo2015.data.models.Todo;

public class TodosActivity extends BaseActivity implements TodosContract.View {

    @Bind(R.id.toolbar) Toolbar toolBar;
    @Bind(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.todo_recycler) RecyclerView recycler;

    private TodosAdapter mTodosAdapter;
    private TodosContract.UserActionListener mActionsListener;
    private ItemTouchHelper mItemTouchHeper;

    private static final int NUM_COLUMNS = 2;

    /* Temporary */
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
    /* Temporary */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        ButterKnife.bind(this);

        mActionsListener = new TodosPresenter(this);
        mItemTouchHeper = new ItemTouchHelper(touchHelperCallback());

        initToolBar();
        initRefreshLayout();
        initTodoList();
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(() -> mActionsListener.loadTodos(true));
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
        // mTodosAdapter = new TodosAdapter(TODOS);
        mTodosAdapter = new TodosAdapter(new ArrayList<>(0));

        recycler.setAdapter(mTodosAdapter);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mItemTouchHeper.attachToRecyclerView(recycler);
        mActionsListener.initTodos();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        refreshLayout.post(() -> refreshLayout.setRefreshing(active));
    }

    @Override
    public void showTodos(List<Todo> todos) {
        mTodosAdapter.replaceData(todos);
    }

    @Override
    public void moveTodos(int fromPosition, int toPosition) {
        mTodosAdapter.onItemMoved(fromPosition, toPosition);
    }

    @Override
    public void removeTodo(int position) {
        mTodosAdapter.onItemDismiss(position);
    }

    private ItemTouchHelper.Callback touchHelperCallback() {
        return new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        mActionsListener.moveTodos(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mActionsListener.removeTodo(viewHolder.getAdapterPosition());
                    }
                };
            }

}
