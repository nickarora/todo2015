package nick.arora.todo2015.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import nick.arora.todo2015.BuildConfig;
import nick.arora.todo2015.data.deserializers.TodosDeserializer;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.schedulers.Schedulers;

public class TodosServiceSource {

    private static final String END_POINT = BuildConfig.PARSE_END_POINT;

    public static Observable<List<Todo>> getTodos() {
        return buildTodosEndpoint().getAllTodos().subscribeOn(Schedulers.newThread());
    }

    public static TodosServiceEndpoint buildTodosEndpoint() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setConverter(new GsonConverter(todosGson()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        return builder.build().create(TodosServiceEndpoint.class);
    }

    public static Gson todosGson() {
        return new GsonBuilder()
                .registerTypeAdapter(List.class, new TodosDeserializer())
                .create();
    }
}
