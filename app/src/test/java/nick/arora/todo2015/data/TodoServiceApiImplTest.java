package nick.arora.todo2015.data;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import nick.arora.todo2015.util.Filter;
import rx.Observable;
import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;

public class TodoServiceApiImplTest {

    private static final String MY_DEVICE = "test_device_1";
    private static final String OTHER_DEVICE = "test_device_2";
    private static final String OBJECT_ID = "object_id";
    public static final String TIME_STAMP = "1";

    //mimics the data you might see on the server
    private static List<Todo> TODOS = Lists.newArrayList(
            new Todo(MY_DEVICE, "Test1", "Description1", false),
            new Todo(MY_DEVICE, "Test2", "Description2", false),
            new Todo(MY_DEVICE, "Test3", "Description3", false),
            new Todo(MY_DEVICE, "Test4", "Description4", true),
            new Todo(MY_DEVICE, "Test5", "Description5", true),
            new Todo(OTHER_DEVICE, "Test6", "Description6", false),
            new Todo(OTHER_DEVICE, "Test7", "Description7", true)
    );

    private List<Todo> myTodos;

    private TodoServiceApiImpl todoServiceApiImpl;

    @Mock
    private TodosServiceSource todosServiceSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(todosServiceSource.getTodos()).thenReturn(Observable.just(TODOS));
        myTodos = Filter.todosByDevice(TODOS, MY_DEVICE);
        todoServiceApiImpl = new TodoServiceApiImpl(todosServiceSource, MY_DEVICE);
    }

    @Test
    public void getTodos_returnsTheListOfTodos() throws Exception {
        TestSubscriber<List<Todo>> testSubscriber = new TestSubscriber<>();
        todoServiceApiImpl.getTodos().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(myTodos);
    }

    @Test
    public void getEachTodo_iteratesThroughEachTodo() throws Exception {
        TestSubscriber<Todo> testSubscriber = new TestSubscriber<>();
        todoServiceApiImpl.getEachTodo().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(myTodos);
    }

    @Test
    public void getEachUnarchivedTodo_iteratesThroughEachUnarchivedTodo() throws Exception {
        TestSubscriber<Todo> testSubscriber = new TestSubscriber<>();
        todoServiceApiImpl.getEachUnarchivedTodo().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(TODOS.get(0), TODOS.get(1), TODOS.get(2)));
    }

    @Test
    public void getEachArchivedTodo_iteratesThroughEachArchivedTodo() throws Exception {
        TestSubscriber<Todo> testSubscriber = new TestSubscriber<>();
        todoServiceApiImpl.getEachArchivedTodo().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(TODOS.get(3), TODOS.get(4)));
    }

    @Test
    public void getTodo_returnsTodoWithMatchingObjectId() throws Exception {
        when(todosServiceSource.getTodo(OBJECT_ID)).thenReturn(Observable.just(TODOS.get(3)));
        TestSubscriber<Todo> testSubscriber = new TestSubscriber<>();
        todoServiceApiImpl.getTodo(OBJECT_ID).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(TODOS.get(3));
    }

    @Test
    public void saveTodo_returnsTodoAndAssignsObjectIdField() throws Exception {
        Todo newTodo = new Todo(MY_DEVICE, "Test8", "Description8", false);
        Parse parseData = createParseData();
        when(todosServiceSource.saveTodo(newTodo)).thenReturn(Observable.just(parseData));

        TestSubscriber<Todo> testSubscriber = new TestSubscriber<>();
        todoServiceApiImpl.saveTodo(newTodo).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(newTodo);
        assertThat(newTodo.objectId).isEqualTo(OBJECT_ID);
    }

    @Test
    public void updateTodo_returnsTodoAndAssignsUpdatedAtField() throws Exception {
        Todo todo = new Todo(MY_DEVICE, "Test8", "Description8", false);
        todo.objectId = OBJECT_ID;
        todo.archive();

        Parse parseData = createParseData();
        when(todosServiceSource.updateTodo(todo)).thenReturn(Observable.just(parseData));

        TestSubscriber<Todo> testSubscriber = new TestSubscriber<>();
        todoServiceApiImpl.updateTodo(todo).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(todo);
        assertThat(todo.updatedAt).isEqualTo(TIME_STAMP);
        assertThat(todo.isArchived()).isTrue();
    }

    private Parse createParseData() {
        Parse parse = new Parse();
        parse.createdAt = TIME_STAMP;
        parse.updatedAt = TIME_STAMP;
        parse.objectId = OBJECT_ID;
        return parse;
    }
}