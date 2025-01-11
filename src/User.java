import java.util.ArrayList;

public class User {
    private ArrayList<ShortURL> links = new ArrayList<ShortURL>();
    private ArrayList<String> notifications = new ArrayList<String>();
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

    public void removeLink (ShortURL link, String reason) {
        links.remove(link);
        String notificationString = "Link " + link.getShortURL() + " has been removed (" + reason + ").";
        if (this == URLShortener.currentUser) {
            System.out.println(notificationString);
        } else {
            notifications.add(notificationString);
        }
    }

    public ArrayList<ShortURL> getLinks() {
        if (!notifications.isEmpty()) {
            for (String notification : notifications) {
                System.out.println(notification);
            }
            notifications.clear();
        }
        return links;
    }

}
