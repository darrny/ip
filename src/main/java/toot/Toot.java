package toot;

import java.nio.file.Path;

import toot.parser.CommandType;
import toot.parser.ParsedCommand;
import toot.parser.Parser;
import toot.storage.Storage;
import toot.task.Task;
import toot.task.TaskList;
import toot.ui.Ui;

/**
 * Coordinates Toot's user interface, command parsing, task list, and storage.
 */
public class Toot {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final String loadingError;

    /**
     * Creates a Toot instance backed by the given task data file.
     *
     * @param filePath Path of the task data file.
     */
    public Toot(Path filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loadedTasks;
        String error = null;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (TootException exception) {
            loadedTasks = new TaskList();
            error = exception.getMessage() + " Toot will start with an empty task list.";
        }
        this.tasks = loadedTasks;
        this.loadingError = error;
    }

    /**
     * Starts the command loop and processes input until the user exits or input ends.
     */
    public void run() {
        ui.showWelcome();
        if (loadingError != null) {
            ui.showError(loadingError);
        }
        ui.showLineWithBlankLine();

        while (ui.hasNextCommand()) {
            String commandText = ui.readCommand();
            ui.showLine();

            try {
                ParsedCommand command = Parser.parse(commandText);
                if (command.type() == CommandType.BYE) {
                    ui.showGoodbye();
                    ui.showLine();
                    return;
                }
                execute(command);
            } catch (TootException exception) {
                ui.showError(exception.getMessage());
            }

            ui.showLineWithBlankLine();
        }
    }

    /**
     * Executes one parsed command and persists any resulting task-list change.
     *
     * @param command Parsed user command.
     * @throws TootException If the command arguments or storage operation are invalid.
     */
    private void execute(ParsedCommand command) throws TootException {
        switch (command.type()) {
        case LIST:
            ui.showTasks(tasks.asList());
            break;
        case MARK:
            int markIndex = Parser.parseTaskIndex(command, tasks.size());
            Task markedTask = tasks.mark(markIndex);
            ui.showMarkedTask(markedTask);
            storage.save(tasks.asList());
            break;
        case UNMARK:
            int unmarkIndex = Parser.parseTaskIndex(command, tasks.size());
            Task unmarkedTask = tasks.unmark(unmarkIndex);
            ui.showUnmarkedTask(unmarkedTask);
            storage.save(tasks.asList());
            break;
        case DELETE:
            int deleteIndex = Parser.parseTaskIndex(command, tasks.size());
            Task deletedTask = tasks.delete(deleteIndex);
            ui.showDeletedTask(deletedTask, tasks.size());
            storage.save(tasks.asList());
            break;
        case TODO:
        case DEADLINE:
        case EVENT:
            Task addedTask = Parser.parseTask(command);
            tasks.add(addedTask);
            ui.showAddedTask(addedTask, tasks.size());
            storage.save(tasks.asList());
            break;
        case BYE:
            throw new AssertionError("The exit command must be handled before execution.");
        default:
            throw new AssertionError("Unhandled command type: " + command.type());
        }
    }

    /**
     * Returns the task data path. Tests can override it without touching real user data.
     *
     * @return Configured data file path, or {@code ./data/toot.txt} by default.
     */
    private static Path getDataFilePath() {
        String customPath = System.getProperty("toot.data.path");
        return customPath == null ? Path.of("data", "toot.txt") : Path.of(customPath);
    }

    /**
     * Launches Toot using its default data-file location.
     *
     * @param args Command-line arguments; currently unused.
     */
    public static void main(String[] args) {
        new Toot(getDataFilePath()).run();
    }
}
