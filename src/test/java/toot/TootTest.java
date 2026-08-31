package toot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests the response API shared by the graphical interface.
 */
public class TootTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void getResponse_sequenceOfCommands_updatesStateAndFormatsResponses() {
        Toot toot = new Toot(temporaryDirectory.resolve("toot.txt"));

        assertEquals("Toot addeded:\n"
                + "  [T][ ] read book\n"
                + "Toot has 1 task in the list now! (｡•̀ᴗ-)✧",
                toot.getResponse("todo read book"));
        assertEquals("Here are the tasks in your list:\n"
                + "1.[T][ ] read book",
                toot.getResponse("list"));
        assertEquals("Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ",
                toot.getResponse("bye"));
    }
}
