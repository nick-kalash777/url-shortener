# Link Shortener.

This is a Java app with a console GUI that shortens any given link. All shortened links are user-specific (users = randomly generated UUIDS). Both links/users are stored locally and are wiped at shutdown.

Shortened links are generated as random 8-character long Strings, containing both letters/digits. Shortened links are checked against duplicates/are unique.

## Usage:
Input any valid link, set the usage/time limits. Then you'll be given a short link that will open up a web page in the default browser whenever you paste it into the console.\
![image](https://github.com/user-attachments/assets/33fa6f0a-f47f-4262-88cc-1bac858943ab)


Default use limit for links: **10** times.\
Default lifetime for every new link: **24** hours (can be changed in settings.conf).

## Other commands:
**/l** to browse all of your shortened links.\
**/limit** LINK_NUMBER (use /l to check) USE_LIMIT to change use limit of a specific link.\
**/user** to switch to a new user.\
**/user UUID** to switch to a specific user.\
**/q** to shutdown the app.\
