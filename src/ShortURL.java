import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class ShortURL {
    public final static String prefix = "srt.url/";
    private final String shortURL;
    private final String realURL;
    private int useLimit;
    private final long timeStart = System.currentTimeMillis();
    public final long timeMax = 86400000;
    private final User owner;
    private final Timer lifeTimer = new Timer();

    public ShortURL(String realURL, String shortURL, int useLimit, User owner) {
        this.shortURL = prefix + shortURL;
        this.realURL = realURL;
        this.useLimit = useLimit;
        this.owner = owner;

        setupDeletionTimer();

    }

    public String getShortURL() {
        return shortURL;
    }

    public String getRealURL() {
        return realURL;
    }

    public int getUseLimit() {
        return useLimit;
    }

    public void changeUseLimit(int useLimit) {
        this.useLimit = useLimit;
        System.out.println("Changed use limit to " + useLimit);
    }


    private void setupDeletionTimer() {
        lifeTimer.schedule(new TimerTask() {
            public void run() {
                deleteURL("lifetime ran out");
            }
        }, timeMax);
    }

    public void use() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(getRealURL()));
        useLimit--;
        if (useLimit == 0) {
            deleteURL("exhausted use limit");
        }
    }

    private void deleteURL(String reason) {
        URLShortener.shortenedLinks.remove(getShortURL());
        owner.removeLink(this, reason);
    }

    public String formatLifeTime() {
        long remainingTime = getLifeTime();
        return (remainingTime / (1000 * 60 * 60 * 24)) + " days, " +
                (remainingTime / (1000 * 60 * 60) % 24) + " hours, " +
                (remainingTime / (1000 * 60) % 60) + " minutes";
    }

    private long getLifeTime() {
        return timeMax + timeStart - System.currentTimeMillis();
    }

}
