public class Commands {
    public static abstract class Command {
        protected boolean needUser;

        public Command(boolean needUser) {
            this.needUser = needUser;
        }
        public Command() {
            this.needUser = true;
        }

        public final void execute(User currentUser, String[] args) {
            if (needUser) {
                if (currentUser == null) {
                    System.out.println("You haven't shortened any links.");
                    return;
                }
            }

            useCommand(currentUser, args);
        }

        protected abstract void useCommand(User currentUser, String[] args);

    }

    public static class Quit extends Command {

        public Quit() {
            super.needUser = false;
        }

        public void useCommand(User currentUser, String[] args) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
    }

    public static class UserLinks extends Command {
        protected void useCommand(User currentUser, String[] args) {
            int linkIndex = 0;
            for(ShortURL link : currentUser.getLinks()) {
                linkIndex++;
                System.out.println(linkIndex + ". " + link.getShortURL() + " | " + "Can be used " + link.getUseLimit() + " times.");
                System.out.println("Use /limit LINK_NUMBER USE_LIMIT to change the number of times the link can be used.");
            }
        }
    }

    public static class UseLimit extends Command {
        protected void useCommand(User currentUser, String[] args) {
            int linkIndex = 0;
            int useLimit = 0;
            for(int i = 0; i < 3; i++) {
                if (i == 1) linkIndex = Integer.parseInt(args[i]) - 1;
                else if (i == 2) useLimit = Integer.parseInt(args[i]);
            }

            try {
                ShortURL link = currentUser.getLinks().get(linkIndex);
                link.changeUseLimit(useLimit);
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }

        }
    }
}
