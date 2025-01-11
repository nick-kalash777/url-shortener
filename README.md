This is a Java app with a console GUI that shortens any given link. All shortened links are user-specific (users = randomly generated UUIDS). Both links/users are stored locally and are wiped at shutdown.

Shortened links are generated as random 8-character long Strings, containing both letters/digits. Shortened links are checked against duplicates/are unique.

Usage:
Input any valid link and set the usage limit. Then you'll be given a short link that will open up a web page in the default browser whenever you paste it into the console.
![image](https://github.com/user-attachments/assets/43ab5995-3579-4235-a1d4-f40e7adf2433)

Default use limit for links: 10 times.
Lifetime for every new link: 24 hours.

Other commands:
/l to browse all of your shortened links.
/limit LINK_NUMBER (use /l to check) USE_LIMIT to change use limit of a specific link.
/user to switch to a new user.
/user UUID to switch to a specific user.
/q to shutdown the app.
