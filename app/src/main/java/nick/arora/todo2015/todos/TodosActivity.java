package nick.arora.todo2015.todos;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.R;
import nick.arora.todo2015.dagger.Injector;

public class TodosActivity extends BaseActivity implements TodosContract.View {

    @Bind(R.id.toolbar) Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolBar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
