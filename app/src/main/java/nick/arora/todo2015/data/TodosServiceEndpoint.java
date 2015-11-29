package nick.arora.todo2015.data;

import java.util.List;

import nick.arora.todo2015.BuildConfig;
import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

public interface TodosServiceEndpoint {

    @Headers({
        "X-Parse-Application-Id: " + BuildConfig.PARSE_APP_ID,
        "X-Parse-REST-API-Key: " + BuildConfig.PARSE_REST_API_KEY
    })

    @GET("/1/classes/Todos")
    Observable<List<Todo>> getAllTodos();

}
