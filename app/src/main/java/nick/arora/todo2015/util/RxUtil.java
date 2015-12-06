package nick.arora.todo2015.util;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxUtil {

    public static <T> Observable.Transformer<T, T> applyUiSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> applyBackgroundSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public abstract class TodoSubscriber<T> extends Subscriber<T> {

        @Override
        public void onCompleted() {}

        @Override
        public void onError(Throwable e) {
            Timber.d("RxJava Error: %s", e.toString());
        }

    }

}
