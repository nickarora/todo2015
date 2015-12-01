package nick.arora.todo2015.data.api;

import java.util.List;

import nick.arora.todo2015.data.models.Parse;
import nick.arora.todo2015.data.models.Todo;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

public interface TodosServiceEndpoint {

    @GET("/1/classes/Todos")
    Observable<List<Todo>> getAllTodos();

    @GET("/1/classes/Todos/{objectId}")
    Observable<Todo> getTodo(@Path("objectId") String objectId);

    @POST("/1/classes/Todos")
    Observable<Parse> saveTodo(@Body Todo todo);

    @PUT("/1/classes/Todos/{objectId}")
    Observable<Parse> updateTodo(@Path("objectId") String objectId, @Body Todo todo);

}
