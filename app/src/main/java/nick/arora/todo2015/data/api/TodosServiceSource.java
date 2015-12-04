package nick.arora.todo2015.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
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

    public Observable<List<Todo>> getUnarchivedTodos() {
        return buildTodosEndpoint().getTodosByArchiveState(archiveQuery(false));
    }

    public Observable<List<Todo>> getArchivedTodos() {
        return buildTodosEndpoint().getTodosByArchiveState(archiveQuery(true));
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

    private String archiveQuery(boolean isArchived) {
        HashMap<String, Boolean> archiveQuery = new HashMap<>();
        archiveQuery.put("mArchived", isArchived);
        return new Gson().toJson(archiveQuery);
    }

    private TodosServiceEndpoint buildTodoEndpoint() {
        return todoRestBuilder().build().create(TodosServiceEndpoint.class);
    }

    private TodosServiceEndpoint buildTodosEndpoint() {
        RestAdapter.Builder builder = todoRestBuilder().setConverter(new GsonConverter(todosGson()));
        return builder.build().create(TodosServiceEndpoint.class);
    }

    private RestAdapter.Builder todoRestBuilder() {
        return new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(requestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL);
    }

    private Gson todosGson() {
        return new GsonBuilder()
                .registerTypeAdapter(List.class, new TodosDeserializer())
                .create();
    }

    private RequestInterceptor requestInterceptor() {
        return request -> {
            request.addHeader("X-Parse-Application-Id", BuildConfig.PARSE_APP_ID);
            request.addHeader("X-Parse-REST-API-Key", BuildConfig.PARSE_REST_API_KEY);
        };
    }

}
