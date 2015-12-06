package nick.arora.todo2015.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nick.arora.todo2015.BuildConfig;
import nick.arora.todo2015.data.deserializers.ParseDeserializer;
import nick.arora.todo2015.data.deserializers.TodosDeserializer;
import nick.arora.todo2015.data.models.Parse;
import nick.arora.todo2015.data.models.Todo;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class TodosServiceSource {

    private static final String END_POINT = BuildConfig.PARSE_END_POINT;

    public Observable<List<Todo>> getUnarchivedTodos(String deviceId) {
        return buildTodosEndpoint().getTodos(archiveQuery(deviceId, false), "updatedAt");
    }

    public Observable<List<Todo>> getArchivedTodos(String deviceId) {
        return buildTodosEndpoint().getTodos(archiveQuery(deviceId, true), "updatedAt");
    }

    public Observable<Parse> saveTodo(Todo todo) {
        return buildTodoEndpoint().saveTodo(todo);
    }

    public Observable<Parse> updateTodo(Todo todo) {
        return buildTodoEndpoint().updateTodo(todo.objectId, todo);
    }

    public Observable<List<Parse>> updateTodos(List<Todo> todos) {
        return buildBatchTodosEndpoint().updateTodos(new TodoBatch(todos));
    }

    public Observable<Todo> getTodo(String objectId) {
        return buildTodoEndpoint().getTodo(objectId);
    }

    private String deviceQuery(String deviceId) {
        HashMap<String, String> query = new HashMap<>();
        query.put("mDeviceId", deviceId);
        return new Gson().toJson(query);
    }

    private String archiveQuery(String deviceId, boolean isArchived) {
        ArchiveQuery archiveQuery = new ArchiveQuery(deviceId, isArchived);
        return new Gson().toJson(archiveQuery);
    }

    private TodosServiceEndpoint buildTodoEndpoint() {
        return todoRestBuilder().build().create(TodosServiceEndpoint.class);
    }

    private TodosServiceEndpoint buildTodosEndpoint() {
        RestAdapter.Builder builder = todoRestBuilder().setConverter(new GsonConverter(todosGson()));
        return builder.build().create(TodosServiceEndpoint.class);
    }

    private TodosServiceEndpoint buildBatchTodosEndpoint() {
        RestAdapter.Builder builder = todoRestBuilder().setConverter(new GsonConverter(batchTodosGson()));
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

    private Gson batchTodosGson() {
        return new GsonBuilder()
                .registerTypeAdapter(List.class, new ParseDeserializer())
                .create();
    }

    private RequestInterceptor requestInterceptor() {
        return request -> {
            request.addHeader("X-Parse-Application-Id", BuildConfig.PARSE_APP_ID);
            request.addHeader("X-Parse-REST-API-Key", BuildConfig.PARSE_REST_API_KEY);
        };
    }

    private class ArchiveQuery {

        private String mDeviceId;
        private Boolean mArchived;

        private ArchiveQuery(String mDeviceId, Boolean mArchived) {
            this.mDeviceId = mDeviceId;
            this.mArchived = mArchived;
        }
    }

    public class TodoBatch {

        private List<Request> requests = new ArrayList<>();

        private class Request {
            private String method = "PUT";
            private String path = "/1/classes/Todos/";
            private Todo body;

            public Request(Todo todo) {
                path = path + todo.objectId;
                body = todo;
            }
        }

        public TodoBatch(List<Todo> todos) {
            for (Todo todo : todos) {
                requests.add(new Request(todo));
            }
        }

    }

}
