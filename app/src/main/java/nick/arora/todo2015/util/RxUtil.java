package nick.arora.todo2015.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtil {

    public static <T> Observable.Transformer<T, T> applyUiSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> applyBackgroundSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

}
