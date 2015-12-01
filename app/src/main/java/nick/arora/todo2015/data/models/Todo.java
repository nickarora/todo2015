package nick.arora.todo2015.data.models;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import java.util.UUID;


public class Todo extends Parse {

    private String mDeviceId;
    private String mTitle;
    @Nullable private  String mDescription;
    private boolean mArchived;

    public Todo(String mDeviceId, String mTitle, String mDescription, boolean mArchived) {
        this.mDeviceId = mDeviceId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mArchived = mArchived;
    }

    private String generateDeviceId() {
        return UUID.randomUUID().toString();
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void archive() { mArchived = true; }

    public boolean isArchived() {
        return mArchived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equal(mDeviceId, todo.mDeviceId) &&
                Objects.equal(mTitle, todo.mTitle) &&
                Objects.equal(mDescription, todo.mDescription) &&
                Objects.equal(mArchived, todo.mArchived) &&
                Objects.equal(createdAt, todo.createdAt) &&
                Objects.equal(updatedAt, todo.updatedAt) &&
                Objects.equal(objectId, todo.objectId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mDeviceId, mTitle, mDescription, mArchived, createdAt, updatedAt, objectId);
    }

    @Override
    public String toString() {
        return String.format("Todo { %s, %s, %s, %b }", mTitle, mDescription, mDeviceId, mArchived);
    }
}
