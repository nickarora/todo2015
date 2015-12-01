package nick.arora.todo2015.util;

import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;

import nick.arora.todo2015.data.Todo;

import static com.google.common.truth.Truth.assertThat;

public class FilterTest {

    private static final String MY_DEVICE = "test_device_1";
    private static final String OTHER_DEVICE = "test_device_2";

    private static List<Todo> TODOS = Lists.newArrayList(
            new Todo(MY_DEVICE, "Test1", "Description1", false),
            new Todo(MY_DEVICE, "Test2", "Description2", false),
            new Todo(MY_DEVICE, "Test3", "Description3", false),
            new Todo(MY_DEVICE, "Test4", "Description4", true),
            new Todo(MY_DEVICE, "Test5", "Description5", true),
            new Todo(OTHER_DEVICE, "Test6", "Description6", false),
            new Todo(OTHER_DEVICE, "Test7", "Description7", true)
    );

    @Test
    public void todosByDevice_returnsListWithMatchingDeviceId() throws Exception {
        List<Todo> filteredList = Filter.todosByDevice(TODOS, MY_DEVICE);
        assertThat(filteredList).containsNoneOf(TODOS.get(5), TODOS.get(6));
        assertThat(filteredList).hasSize(5);
    }
}