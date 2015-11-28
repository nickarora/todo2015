package nick.arora.todo2015.data;

import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.base.Objects;

import java.util.UUID;

public class Todo {

    private final String mId;
    private final String mTitle;
    @Nullable private final String mDescription;
    private final boolean mArchived;

    public Todo(String mTitle, String mDescription, boolean mArchived) {
        this.mId = UUID.randomUUID().toString();
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mArchived = mArchived;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isArchived() {
        return mArchived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equal(mId, todo.mId) &&
                Objects.equal(mTitle, todo.mTitle) &&
                Objects.equal(mDescription, todo.mDescription) &&
                Objects.equal(mArchived, todo.mArchived);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription, mArchived);
    }
}
