package nick.arora.todo2015.data;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import nick.arora.todo2015.data.api.TodoServiceApi;
import nick.arora.todo2015.data.models.Todo;
import rx.Observable;

import static com.google.common.truth.Truth.*;
import static org.mockito.Mockito.*;


public class InMemoryTodoRepositoryTest {

    private static final String MY_DEVICE = "test_device_1";
    private static final String OBJECT_ID = "object_id";

    private static List<Todo> TODOS = Lists.newArrayList(
            new Todo(MY_DEVICE, "Test1", "Description1", false),
            new Todo(MY_DEVICE, "Test2", "Description2", false)
    );

    @Mock
    TodoServiceApi todoServiceApi;

    private InMemoryTodoRepository inMemoryTodoRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(todoServiceApi.getTodos()).thenReturn(Observable.just(TODOS));
        when(todoServiceApi.getTodo(any(String.class))).thenReturn(Observable.just(TODOS.get(0)));
        when(todoServiceApi.saveTodo(any(Todo.class))).thenReturn(Observable.just(TODOS.get(0)));
        when(todoServiceApi.updateTodo(any(Todo.class))).thenReturn(Observable.just(TODOS.get(0)));
        inMemoryTodoRepository = new InMemoryTodoRepository(todoServiceApi);
    }

    @Test
    public void getTodos_requestsAllTodosfromApi() throws Exception {
        inMemoryTodoRepository.getTodos().subscribe();
        verify(todoServiceApi).getTodos();
    }

    @Test
    public void getTodos_cachesAfterFirstApiCall() throws Exception {
        twoLoadCallsToRepository();
        // one call to the API despite two calls to load
        verify(todoServiceApi).getTodos();
    }

    @Test
    public void invalidateCache_doesNotCallTheServiceApi() throws Exception {
        twoLoadCallsToRepository();

        inMemoryTodoRepository.refreshData();
        inMemoryTodoRepository.getTodos().subscribe();

        // first and third call trigger the api
        verify(todoServiceApi, times(2)).getTodos();
    }

    @Test
    public void getTodo_requestsSingleTodoFromApi() throws Exception {
        inMemoryTodoRepository.getTodo(OBJECT_ID).subscribe();
        verify(todoServiceApi).getTodo(OBJECT_ID);
    }

    @Test
    public void saveTodo_savesNoteUsingApiAndInvalidatesCache() throws Exception {
        Todo todo = new Todo("device_id", "title", "description", false);
        inMemoryTodoRepository.saveTodo(todo).subscribe();
        verify(todoServiceApi).saveTodo(todo);
        assertThat(inMemoryTodoRepository.mCachedTodos).isNull();
    }

    @Test
    public void updateTodo_updatesNoteUsingApiAndInvalidatesCache() throws Exception {
        Todo todo = new Todo("device_id", "title", "description", false);
        inMemoryTodoRepository.updateTodo(todo).subscribe();
        verify(todoServiceApi).updateTodo(todo);
        assertThat(inMemoryTodoRepository.mCachedTodos).isNull();
    }

    private void twoLoadCallsToRepository() {
        inMemoryTodoRepository.getTodos().subscribe();
        inMemoryTodoRepository.getTodos().subscribe();
    }


}