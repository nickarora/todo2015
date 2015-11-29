package nick.arora.todo2015.data;

public interface TodoServiceApi {

    void getAllTodos();

    void getTodo(String id);

    void saveTodo(Todo todo);

    void updateTodo(Todo todo);

}
