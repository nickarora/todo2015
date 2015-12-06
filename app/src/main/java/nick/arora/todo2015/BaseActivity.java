package nick.arora.todo2015;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import nick.arora.todo2015.dagger.Injector;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
