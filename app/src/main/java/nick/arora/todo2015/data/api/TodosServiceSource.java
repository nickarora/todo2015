package nick.arora.todo2015.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import nick.arora.todo2015.BuildConfig;
import nick.arora.todo2015.data.deserializers.TodosDeserializer;
import nick.arora.todo2015.data.models.Parse;
import nick.arora.todo2015.data.models.Todo;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class TodosServiceSource {

    private static final String END_POINT = BuildConfig.PARSE_END_POINT;

    public Observable<List<Todo>> getTodos() {
        return buildTodosEndpoint().getAllTodos();
    }

    public Observable<Parse> saveTodo(Todo todo) {
        return buildTodoEndpoint().saveTodo(todo);
    }

    public Observable<Parse> updateTodo(Todo todo) {
        return buildTodoEndpoint().updateTodo(todo.objectId, todo);
    }

    public Observable<Todo> getTodo(String objectId) {
        return buildTodoEndpoint().getTodo(objectId);
    }

    private TodosServiceEndpoint buildTodoEndpoint() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(requestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL);

        return builder.build().create(TodosServiceEndpoint.class);
    }

    private TodosServiceEndpoint buildTodosEndpoint() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(requestInterceptor())
                .setConverter(new GsonConverter(todosGson()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        return builder.build().create(TodosServiceEndpoint.class);
    }

    private Gson todosGson() {
        return new GsonBuilder()
                .registerTypeAdapter(List.class, new TodosDeserializer())
                .create();
    }

    private RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-Parse-Application-Id", BuildConfig.PARSE_APP_ID);
                request.addHeader("X-Parse-REST-API-Key", BuildConfig.PARSE_REST_API_KEY);
            }
        };

    }
}
