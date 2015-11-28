package nick.arora.todo2015.todos;

import android.os.Bundle;

import nick.arora.todo2015.BaseActivity;
import nick.arora.todo2015.R;

public class TodosActivity extends BaseActivity implements TodosContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
    }
}
