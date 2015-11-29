package nick.arora.todo2015.data;

import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.base.Objects;

import java.util.UUID;

public class Todo {

    private final String mDeviceId;
    private final String mTitle;
    @Nullable private final String mDescription;
    private final boolean mArchived;

    private String createdAt;
    private String updatedAt;
    private String objectId;

    public Todo(String mTitle, String mDescription, boolean mArchived) {
        this.mDeviceId = getDeviceId();
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mArchived = mArchived;
    }

    private String getDeviceId() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return mDeviceId;
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

    public boolean isPersisted() {
        return (objectId == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equal(mDeviceId, todo.mDeviceId) &&
                Objects.equal(mTitle, todo.mTitle) &&
                Objects.equal(mDescription, todo.mDescription) &&
                Objects.equal(mArchived, todo.mArchived);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mDeviceId, mTitle, mDescription, mArchived);
    }

}
