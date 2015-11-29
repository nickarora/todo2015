package nick.arora.todo2015.data;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

public interface TodosServiceEndpoint {

    @GET("/1/classes/Todos")
    Observable<List<Todo>> getAllTodos();

    @POST("/1/classes/Todos")
    Observable<Parse> saveTodo(@Body Todo todo);

}
