package nick.arora.todo2015.data;

public class Parse {

    protected String createdAt;
    protected String updatedAt;
    public String objectId;

    public boolean isPersisted() {
        return (objectId != null);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setParseData(Parse parse) {
        createdAt = parse.createdAt;
        updatedAt = parse.updatedAt;
        objectId = parse.objectId;
    }

}
