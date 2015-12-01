package nick.arora.todo2015.data;

public class Parse {

    public String createdAt;
    public String updatedAt;
    public String objectId;

    public boolean isPersisted() {
        return (objectId != null);
    }

    public void setParseData(Parse parse) {
        createdAt = parse.createdAt;
        updatedAt = parse.updatedAt;
        objectId = parse.objectId;
    }

}
