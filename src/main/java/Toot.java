import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the Toot chatbot application.
 */
public class Toot {
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
        ArrayList<Task> tasks = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            System.out.println(horizontalLine);

            try {
                CommandType commandType = CommandType.from(command);
                switch (commandType) {
                case BYE:
                    System.out.println("Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ");
                    System.out.println(horizontalLine);
                    return;
                case LIST:
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    break;
                case MARK:
                    int markIndex = parseTaskIndex(commandType.getArguments(command), tasks.size(),
                            commandType.getKeyword());
                    tasks.get(markIndex).markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(markIndex));
                    break;
                case UNMARK:
                    int unmarkIndex = parseTaskIndex(commandType.getArguments(command), tasks.size(),
                            commandType.getKeyword());
                    tasks.get(unmarkIndex).markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(unmarkIndex));
                    break;
                case DELETE:
                    int deleteIndex = parseTaskIndex(commandType.getArguments(command), tasks.size(),
                            commandType.getKeyword());
                    Task removedTask = tasks.remove(deleteIndex);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    String remainingTaskWord = tasks.size() == 1 ? "task" : "tasks";
                    System.out.println("Now you have " + tasks.size() + " " + remainingTaskWord + " in the list.");
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    Task newTask = parseTask(commandType, commandType.getArguments(command));
                    tasks.add(newTask);
                    System.out.println("Toot addeded:");
                    System.out.println("  " + newTask);
                    String taskWord = tasks.size() == 1 ? "task" : "tasks";
                    System.out.println("Toot has " + tasks.size() + " " + taskWord + " in the list now! (｡•̀ᴗ-)✧");
                    break;
                default:
                    throw new AssertionError("Unhandled command type: " + commandType);
                }
            } catch (TootException exception) {
                System.out.println("Oh crumbs! " + exception.getMessage());
            }

            System.out.println(horizontalLine + "\n");
        }
    }

    /**
     * Creates the task requested by a recognised add-task command.
     *
     * @param commandType Type of task to create.
     * @param arguments Text following the command keyword.
     * @return Task represented by the command.
     * @throws TootException If a required field is missing.
     */
    private static Task parseTask(CommandType commandType, String arguments) throws TootException {
        switch (commandType) {
        case EVENT:
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
            String eventDescription = arguments.substring(0, fromIndex).trim();
            String from = arguments.substring(fromIndex + "/from".length(), toIndex).trim();
            String to = arguments.substring(toIndex + "/to".length()).trim();
            if (eventDescription.isEmpty()) {
                throw new TootException("The event description cannot be empty. "
                        + "Try: event DESCRIPTION /from START /to END");
            }
            if (from.isEmpty()) {
                throw new TootException("The event start time cannot be empty after '/from'.");
            }
            if (to.isEmpty()) {
                throw new TootException("The event end time cannot be empty after '/to'.");
            }
            return new Event(eventDescription, from, to);
        case DEADLINE:
            int byIndex = findDelimiter(arguments, "/by", 0);
            if (byIndex < 0) {
                throw new TootException("A deadline needs '/by' before its due date or time. "
                        + "Try: deadline DESCRIPTION /by DATE/TIME");
            }
            String deadlineDescription = arguments.substring(0, byIndex).trim();
            String by = arguments.substring(byIndex + "/by".length()).trim();
            if (deadlineDescription.isEmpty()) {
                throw new TootException("The deadline description cannot be empty. "
                        + "Try: deadline DESCRIPTION /by DATE/TIME");
            }
            if (by.isEmpty()) {
                throw new TootException("The deadline date or time cannot be empty after '/by'.");
            }
            return new Deadline(deadlineDescription, by);
        case TODO:
            if (arguments.isEmpty()) {
                throw new TootException("The todo description cannot be empty. Try: todo DESCRIPTION");
            }
            return new Todo(arguments);
        default:
            throw new IllegalArgumentException("Cannot create a task from command type: " + commandType);
        }
    }

    /**
     * Converts a user-facing task number to its zero-based list index.
     *
     * @param taskNumberText Task number entered after {@code mark}, {@code unmark}, or {@code delete}.
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
