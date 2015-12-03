package nick.arora.todo2015.todos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nick.arora.todo2015.R;
import nick.arora.todo2015.data.models.Todo;

import static com.google.common.base.Preconditions.checkNotNull;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    private List<Todo> mTodos;

    public TodosAdapter(List<Todo> mTodos) {
        this.mTodos = mTodos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View todoView = inflater.inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = mTodos.get(position);
        holder.title.setText(todo.getTitle());
        holder.description.setText(todo.getDescription());
    }

    public void replaceData(List<Todo> todos) {
        setList(todos);
        notifyDataSetChanged();
    }

    private void setList(@NonNull List<Todo> todos) {
        checkNotNull(todos);
        mTodos.clear();
        mTodos.addAll(todos);
    }

    @Override
    public int getItemCount() { return mTodos.size();}

    public Todo getItem(int position) {
        return mTodos.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.todo_detail_title);
            description = (TextView) itemView.findViewById(R.id.todo_detail_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Todo todo = getItem(position);
            //TODO: Take Action Here
        }
    }
}
