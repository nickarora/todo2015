package nick.arora.todo2015;

import android.content.Intent;
import android.os.Bundle;

import nick.arora.todo2015.todos.TodosActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(getDestinationIntent());
        finish();
    }

    private Intent getDestinationIntent() {
        return new Intent(LauncherActivity.this, TodosActivity.class);
    }

}
