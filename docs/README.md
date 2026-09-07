# Toot User Guide

Toot keeps todos, deadlines, and events in a saved task list.

## Command guide

Enter `help` in the console or chatbot window to see:

```text
Toot command guide:
todo DESCRIPTION - Add a todo.
deadline DESCRIPTION /by YYYY-MM-DD - Add a deadline.
event DESCRIPTION /from START /to END - Add an event.
list - Show all tasks and their numbers.
find KEYWORD - Find descriptions (ignoring case).
mark NUMBER - Mark a task done.
unmark NUMBER - Mark a task not done.
delete NUMBER - Delete a task.
help - Show this guide.
bye - Exit.
Use task numbers from list for mark, unmark, and delete.
Examples: todo read book; deadline return book /by 2026-09-14
Example: event meeting /from Mon 2pm /to Mon 3pm
```

Commands use lowercase keywords. `help` takes no arguments and does not change
your tasks or saved data. Dates must be real calendar dates in `YYYY-MM-DD` format.
Task numbers start at 1; use `list` to get the current numbers.
