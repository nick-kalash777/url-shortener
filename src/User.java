import java.util.ArrayList;

public class User {
    private ArrayList<ShortURL> links = new ArrayList<ShortURL>();
    private final String uuid;

    public User (String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }

    public void addLink (ShortURL link) {
        links.add(link);
    }

    public ArrayList<ShortURL> getLinks() {
        return links;
    }

}
