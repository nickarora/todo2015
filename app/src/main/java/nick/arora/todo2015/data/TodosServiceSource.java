package nick.arora.todo2015.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import nick.arora.todo2015.BuildConfig;
import nick.arora.todo2015.data.deserializers.TodosDeserializer;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.schedulers.Schedulers;

public class TodosServiceSource {

    private static final String END_POINT = BuildConfig.PARSE_END_POINT;

    public static Observable<List<Todo>> getTodos() {
        return buildTodosEndpoint().getAllTodos().subscribeOn(Schedulers.newThread());
    }

    public static Observable<Parse> saveTodo(Todo todo) {
        return buildTodoEndpoint().saveTodo(todo).subscribeOn(Schedulers.newThread());
    }

    public static TodosServiceEndpoint buildTodoEndpoint() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(requestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL);

        return builder.build().create(TodosServiceEndpoint.class);
    }

    public static TodosServiceEndpoint buildTodosEndpoint() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(requestInterceptor())
                .setConverter(new GsonConverter(todosGson()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        return builder.build().create(TodosServiceEndpoint.class);
    }

    public static Gson todosGson() {
        return new GsonBuilder()
                .registerTypeAdapter(List.class, new TodosDeserializer())
                .create();
    }

    public static RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-Parse-Application-Id", BuildConfig.PARSE_APP_ID);
                request.addHeader("X-Parse-REST-API-Key", BuildConfig.PARSE_REST_API_KEY);
            }
        };

    }
}
