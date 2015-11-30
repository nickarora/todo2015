package nick.arora.todo2015.util;

import java.util.ArrayList;
import java.util.List;

import nick.arora.todo2015.data.Todo;

public class Filter {

    public static List<Todo> todosByDevice(List<Todo> todoList, String deviceId) {

        List<Todo> results = new ArrayList<Todo>();

        for (Todo todo : todoList) {
            if (todo.getDeviceId().equals(deviceId)) {
                results.add(todo);
            }
        }
        return results;
    }
}
