package nick.arora.todo2015.dagger;

import android.content.Context;

import nick.arora.todo2015.BaseApplication;

public interface AppContextComponent {
    BaseApplication application();
    Context applicationContext();
}
