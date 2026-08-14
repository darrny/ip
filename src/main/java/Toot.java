import java.util.Scanner;

/**
 * Runs the Toot chatbot application.
 */
public class Toot {
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        String banner = " _____           _\n"
                + "|_   _|__   ___ | |_\n"
                + "  | |/ _ \\ / _ \\| __|\n"
                + "  | | (_) | (_) | |_\n"
                + "  |_|\\___/ \\___/ \\__|\n";
        String horizontalLine = "⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆";

        System.out.println(horizontalLine);
        System.out.print(banner + "\n");
        System.out.println("Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა");
        System.out.println("Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و");
        System.out.println(horizontalLine + "\n");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            System.out.println(horizontalLine);

            if (command.equals("bye")) {
                System.out.println("Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ");
                System.out.println(horizontalLine);
                break;
            }

            try {
                if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    String taskNumberText = command.substring("mark".length()).trim();
                    int taskIndex = parseTaskIndex(taskNumberText, taskCount, "mark");
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskIndex]);
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    String taskNumberText = command.substring("unmark".length()).trim();
                    int taskIndex = parseTaskIndex(taskNumberText, taskCount, "unmark");
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                } else {
                    Task newTask = parseTask(command);
                    if (taskCount == tasks.length) {
                        throw new TootException("Toot's list can hold only " + MAX_TASKS
                                + " tasks. Restart Toot to begin a new list. (｡•́︿•̀｡)");
                    }
                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println("Toot addeded:");
                    System.out.println("  " + newTask);
                    String taskWord = taskCount == 1 ? "task" : "tasks";
                    System.out.println("Toot has " + taskCount + " " + taskWord + " in the list now! (｡•̀ᴗ-)✧");
                }
            } catch (TootException exception) {
                System.out.println("Oh crumbs! " + exception.getMessage());
            }

            System.out.println(horizontalLine + "\n");
        }
    }

    /**
     * Creates the task requested by a valid add-task command.
     *
     * @param command Full command entered by the user.
     * @return Task represented by the command.
     * @throws TootException If the command is unknown or a required field is missing.
     */
    private static Task parseTask(String command) throws TootException {
        if (command.equals("event") || command.startsWith("event ")) {
            String arguments = command.substring("event".length()).trim();
            int fromIndex = findDelimiter(arguments, "/from", 0);
            if (fromIndex < 0) {
                throw new TootException("An event needs '/from' before its start time. "
                        + "Try: event DESCRIPTION /from START /to END");
            }
            int toIndex = findDelimiter(arguments, "/to", fromIndex + "/from".length());
            if (toIndex < 0) {
                throw new TootException("An event needs '/to' before its end time. "
                        + "Try: event DESCRIPTION /from START /to END");
            }
            String description = arguments.substring(0, fromIndex).trim();
            String from = arguments.substring(fromIndex + "/from".length(), toIndex).trim();
            String to = arguments.substring(toIndex + "/to".length()).trim();
            if (description.isEmpty()) {
                throw new TootException("The event description cannot be empty. "
                        + "Try: event DESCRIPTION /from START /to END");
            }
            if (from.isEmpty()) {
                throw new TootException("The event start time cannot be empty after '/from'.");
            }
            if (to.isEmpty()) {
                throw new TootException("The event end time cannot be empty after '/to'.");
            }
            return new Event(description, from, to);
        }
        if (command.equals("deadline") || command.startsWith("deadline ")) {
            String arguments = command.substring("deadline".length()).trim();
            int byIndex = findDelimiter(arguments, "/by", 0);
            if (byIndex < 0) {
                throw new TootException("A deadline needs '/by' before its due date or time. "
                        + "Try: deadline DESCRIPTION /by DATE/TIME");
            }
            String description = arguments.substring(0, byIndex).trim();
            String by = arguments.substring(byIndex + "/by".length()).trim();
            if (description.isEmpty()) {
                throw new TootException("The deadline description cannot be empty. "
                        + "Try: deadline DESCRIPTION /by DATE/TIME");
            }
            if (by.isEmpty()) {
                throw new TootException("The deadline date or time cannot be empty after '/by'.");
            }
            return new Deadline(description, by);
        }
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring("todo".length()).trim();
            if (description.isEmpty()) {
                throw new TootException("The todo description cannot be empty. Try: todo DESCRIPTION");
            }
            return new Todo(description);
        }
        if (command.isEmpty()) {
            throw new TootException("Toot didn't hear a command. Type a command such as 'todo read book'. (・・?)");
        }
        throw new TootException("Toot doesn't know that command. "
                + "Try: todo, deadline, event, list, mark, unmark, or bye. (・・?)");
    }

    /**
     * Converts a user-facing task number to its zero-based array index.
     *
     * @param taskNumberText Task number entered after {@code mark} or {@code unmark}.
     * @param taskCount Number of tasks currently stored.
     * @param action Command being performed, used to make error guidance specific.
     * @return Zero-based index of an existing task.
     * @throws TootException If the number is missing, invalid, or outside the task list.
     */
    private static int parseTaskIndex(String taskNumberText, int taskCount, String action) throws TootException {
        if (taskNumberText.isEmpty()) {
            throw new TootException("Toot needs a task number after '" + action + "'. Try: " + action + " 1");
        }

        final int taskNumber;
        try {
            taskNumber = Integer.parseInt(taskNumberText);
        } catch (NumberFormatException exception) {
            throw new TootException("The task number for '" + action + "' must be a whole number. "
                    + "Try: " + action + " 1");
        }

        if (taskCount == 0) {
            throw new TootException("The task list is empty, so there is nothing to " + action + ".");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new TootException("Task " + taskNumber + " does not exist. Choose a number from 1 to " + taskCount + ".");
        }
        return taskNumber - 1;
    }

    /**
     * Finds a slash-delimiter that appears as a separate token in command text.
     * This avoids mistaking text such as {@code /today} for the {@code /to} delimiter.
     *
     * @param text Command text to search.
     * @param delimiter Delimiter such as {@code /by}, {@code /from}, or {@code /to}.
     * @param fromIndex Index at which to begin searching.
     * @return Index of the delimiter, or {@code -1} if it is absent.
     */
    private static int findDelimiter(String text, String delimiter, int fromIndex) {
        int delimiterIndex = text.indexOf(delimiter, fromIndex);
        while (delimiterIndex >= 0) {
            int afterDelimiter = delimiterIndex + delimiter.length();
            boolean hasTokenStart = delimiterIndex == 0 || Character.isWhitespace(text.charAt(delimiterIndex - 1));
            boolean hasTokenEnd = afterDelimiter == text.length()
                    || Character.isWhitespace(text.charAt(afterDelimiter));
            if (hasTokenStart && hasTokenEnd) {
                return delimiterIndex;
            }
            delimiterIndex = text.indexOf(delimiter, delimiterIndex + 1);
        }
        return -1;
    }
}
