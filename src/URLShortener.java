import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class URLShortener {
    public static long linkMaxLifeTime = 86400000;
    public static User currentUser = null;
    //HashMap for quick finding of inputted short links
    public static HashMap<String, ShortURL> shortenedLinks = new HashMap<>();
    //HashMap for quick finding of users based on UUID
    public static HashMap<String, User> users = new HashMap<>();
    // all console commands
    public static Map<String, Commands.Command> commands = Map.of(
            "/l", new Commands.UserLinks(),
            "/q", new Commands.Quit(),
            "/user", new Commands.ChangeUser(),
            "/limit", new Commands.UseLimit());


    public static void main(String[] args) {
        System.out.println("""
                Link Shortener v1.0.\

                Copy-paste your link to shorten it.\

                Use /q to quit. Use /l to check your links.""");

        loadConfig();

        while (true) {

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.startsWith("/")) {
                String[] parameters = input.split("\\s+");
                String command = parameters[0];
                try {
                    commands.get(command).execute(currentUser, parameters);
                } catch (NullPointerException e) {
                    System.out.println("This command does not exist.");
                }
                continue;
            } else if (input.startsWith(ShortURL.prefix)) {
                try {
                    shortenedLinks.get(input).use();
                    continue;
                } catch (NullPointerException e) {
                    System.out.println("This short link does not exist.");
                    continue;
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                    e.printStackTrace();
                    continue;
                }
            }

            System.out.println("How many times can this link be used? Leave empty for default (10).");
            int useLimit;
            try {
                useLimit = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                useLimit = 10;
            }
            System.out.println("For how long will this link be active (in minutes)? Leave empty for default/max.");
            long timeMax;
            try {
                timeMax = Long.parseLong(scanner.nextLine());
                //convert to milliseconds
                timeMax *= 60000;
                if (timeMax > linkMaxLifeTime) {
                    timeMax = linkMaxLifeTime;
                    System.out.println("Using default lifetime (your desired lifetime is too long).");
                }
            } catch (NumberFormatException e) {
                timeMax = linkMaxLifeTime;
            }
            createShortURL(input, useLimit, timeMax);


        }
    }

    private static void checkUser() {
        if (currentUser == null) {
            createUser();
        }
    }

    public static void createUser() {
        currentUser = new User(UUID.randomUUID().toString());
        users.put(currentUser.getUUID(), currentUser);
        System.out.println("Your UUID: " + currentUser.getUUID());
    }

    private static void createShortURL(String url, int useLimit, long timeMax) {
        if (!isValidURL(url)) {
            System.out.println("Invalid URL.");
            return;
        }

        checkUser();

        ShortURL shortURL = makeShortURL(url, useLimit, timeMax);
        shortenedLinks.put(shortURL.getShortURL(), shortURL);
        currentUser.addLink(shortURL);
        System.out.println("Your shortened link: " + shortURL.getShortURL());
        System.out.println("It will be active for " + shortURL.formatLifeTime());

    }

    private static ShortURL makeShortURL(String realURL, int useLimit, long timeMax) {
        String shortURL;
        final int urlLength = 8;
        do {
            shortURL = getRandomString(urlLength);
        } while (shortenedLinks.containsKey(ShortURL.prefix + shortURL));

        return new ShortURL(realURL, shortURL, useLimit, timeMax, currentUser);

    }

    private static String getRandomString(int stringLength) {
        final String letters = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "0123456789";

        final String lettersAndDigits = letters + digits;

        final Random random = new Random();

        char[] newChars = new char[stringLength];
        for (int i = 0; i < stringLength; i++) {
            newChars[i] = lettersAndDigits.charAt(random.nextInt(lettersAndDigits.length()));
        }

        return new String(newChars);
    }

    private static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    private static void loadConfig() {
        try(BufferedReader br = new BufferedReader(new FileReader("settings.conf"))) {
            linkMaxLifeTime = Long.parseLong(br.readLine());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading config file. Default link lifetime (" + linkMaxLifeTime + " ms) is used.");
        }
    }

}

