package nick.arora.todo2015.todos;

import java.util.List;

import nick.arora.todo2015.data.models.Todo;

public interface TodosContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showNotes(List<Todo> todos);

    }

    interface UserActionListener {

        void loadNotes(boolean forceUpdate);

    }

}
