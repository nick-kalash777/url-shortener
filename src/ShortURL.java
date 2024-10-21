import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShortURL {
    public final static String prefix = "srt.url/";
    private final String shortURL;
    private final String realURL;
    private int useLimit = 10;
    private int timesUsed = 0;
    private long timeCurrent;
    public final long timeMax = 300000;
    private final User owner;

    public ShortURL(String realURL, String shortURL, int useLimit, User owner) {
        this.shortURL = prefix + shortURL;
        this.realURL = realURL;
        this.useLimit = useLimit;
        this.owner = owner;

        //setupDeletionTimer(System.currentTimeMillis());

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
    }


    private void setupDeletionTimer(long currentTime) {
        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
            scheduler.schedule(new DeleteURL(), currentTime, TimeUnit.SECONDS);
        }
    }

    private static class DeleteURL implements Runnable {
        @Override
        public void run() {
            System.out.println("deleted");
        }
    }

}
