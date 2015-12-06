package nick.arora.todo2015.todos;

import java.util.List;

import nick.arora.todo2015.data.models.Todo;

public interface TodosContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showTodos(List<Todo> todos);

        void moveTodos(int fromPosition, int toPosition);

        void removeTodo(int position);

    }

    interface UserActionListener {

        void startListening();

        void stopListening();

        void initTodos();

        void loadTodos(boolean forceUpdate);

        void moveTodos(int fromPosition, int toPosition);

        void removeTodo(int position);

    }

}
