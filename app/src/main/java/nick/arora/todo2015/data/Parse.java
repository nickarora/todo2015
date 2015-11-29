package nick.arora.todo2015.data;

public class Parse {

    private String createdAt;
    private String updatedAt;
    private String objectId;

    public boolean isPersisted() {
        return (objectId == null);
    }

}
