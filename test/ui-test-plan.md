# UI Test Plan

## Test configuration

Run every test case in a fresh process so its task list starts empty.

### Program command

```shell
javac -d build/classes src/main/java/*.java && java -cp build/classes Toot
```

- Timeout (seconds): 10
- Comparison: Exact combined stdout/stderr after normalizing CRLF line endings to LF.
- Runtime: Java 25 (`25.0.3.fx-zulu` on macOS).

## Test cases

### TC-01: Exit immediately

**Aim:** Verify that the chatbot starts successfully and exits when the user enters `bye`.

#### Inputs

```text
bye
```

#### Expected output

```text
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
 _____           _
|_   _|__   ___ | |_
  | |/ _ \ / _ \| __|
  | | (_) | (_) | |_
  |_|\___/ \___/ \__|

Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა
Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
```

### TC-02: Reject malformed task commands without changing the list

**Aim:** Verify specific errors for every required task field while interleaved valid commands still create the correct list.

#### Inputs

```text
todo
todo read book
deadline return book
deadline /by Sunday
deadline return book /by
deadline return book /by Sunday
event meeting
event /from Mon /to Tue
event meeting /from /to Tue
event meeting /from Mon
event meeting /from Mon /to
event meeting /from Mon /to Tue
list
bye
```

#### Expected output

```text
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
 _____           _
|_   _|__   ___ | |_
  | |/ _ \ / _ \| __|
  | | (_) | (_) | |_
  |_|\___/ \___/ \__|

Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა
Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The todo description cannot be empty. Try: todo DESCRIPTION
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [T][ ] read book
Toot has 1 task in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! A deadline needs '/by' before its due date or time. Try: deadline DESCRIPTION /by DATE/TIME
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The deadline description cannot be empty. Try: deadline DESCRIPTION /by DATE/TIME
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The deadline date or time cannot be empty after '/by'.
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [D][ ] return book (by: Sunday)
Toot has 2 tasks in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! An event needs '/from' before its start time. Try: event DESCRIPTION /from START /to END
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The event description cannot be empty. Try: event DESCRIPTION /from START /to END
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The event start time cannot be empty after '/from'.
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! An event needs '/to' before its end time. Try: event DESCRIPTION /from START /to END
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The event end time cannot be empty after '/to'.
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [E][ ] meeting (from: Mon to: Tue)
Toot has 3 tasks in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] meeting (from: Mon to: Tue)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
```

### TC-03: Reject unknown commands and invalid task numbers

**Aim:** Verify empty and unknown command errors plus missing, nonnumeric, and out-of-range task numbers without corrupting task state.

#### Inputs

```text

blah
list extra
mark
mark one
mark 1
todo test
mark 0
mark 2
mark 1
unmark
unmark one
unmark 2
unmark 1
list
bye
```

#### Expected output

```text
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
 _____           _
|_   _|__   ___ | |_
  | |/ _ \ / _ \| __|
  | | (_) | (_) | |_
  |_|\___/ \___/ \__|

Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა
Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Toot didn't hear a command. Type a command such as 'todo read book'. (・・?)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Toot doesn't know that command. Try: todo, deadline, event, list, mark, unmark, or bye. (・・?)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Toot doesn't know that command. Try: todo, deadline, event, list, mark, unmark, or bye. (・・?)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Toot needs a task number after 'mark'. Try: mark 1
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The task number for 'mark' must be a whole number. Try: mark 1
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The task list is empty, so there is nothing to mark.
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [T][ ] test
Toot has 1 task in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Task 0 does not exist. Choose a number from 1 to 1.
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Task 2 does not exist. Choose a number from 1 to 1.
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Nice! I've marked this task as done:
  [T][X] test
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Toot needs a task number after 'unmark'. Try: unmark 1
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! The task number for 'unmark' must be a whole number. Try: unmark 1
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Oh crumbs! Task 2 does not exist. Choose a number from 1 to 1.
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
OK, I've marked this task as not done yet:
  [T][ ] test
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[T][ ] test
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
```

### TC-04: Add, list, mark, and unmark a task

**Aim:** Verify the main task workflow and its displayed completion markers.

#### Inputs

```text
todo read book
list
mark 1
list
unmark 1
list
bye
```

#### Expected output

```text
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
 _____           _
|_   _|__   ___ | |_
  | |/ _ \ / _ \| __|
  | | (_) | (_) | |_
  |_|\___/ \___/ \__|

Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა
Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [T][ ] read book
Toot has 1 task in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[T][ ] read book
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Nice! I've marked this task as done:
  [T][X] read book
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[T][X] read book
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
OK, I've marked this task as not done yet:
  [T][ ] read book
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[T][ ] read book
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
```

### TC-05: Add and mark a deadline

**Aim:** Verify that deadline due text is stored as entered and displayed through inherited task behavior.

#### Inputs

```text
deadline do homework /by no idea :-p
list
mark 1
list
bye
```

#### Expected output

```text
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
 _____           _
|_   _|__   ___ | |_
  | |/ _ \ / _ \| __|
  | | (_) | (_) | |_
  |_|\___/ \___/ \__|

Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა
Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [D][ ] do homework (by: no idea :-p)
Toot has 1 task in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[D][ ] do homework (by: no idea :-p)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Nice! I've marked this task as done:
  [D][X] do homework (by: no idea :-p)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[D][X] do homework (by: no idea :-p)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
```

### TC-06: Store all task types together

**Aim:** Verify that todos, deadlines, and events coexist in the polymorphic task list and retain their type-specific formatting.

#### Inputs

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
mark 3
list
bye
```

#### Expected output

```text
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
 _____           _
|_   _|__   ___ | |_
  | |/ _ \ / _ \| __|
  | | (_) | (_) | |_
  |_|\___/ \___/ \__|

Hewwo!! I'm Toot, ur teeny-tiny computey baby! ૮₍ ˶•⤙•˶ ₎ა
Gib me a command... Toot do a BIG twy!! (•̀ᴗ•́)و
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [T][ ] borrow book
Toot has 1 task in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [D][ ] return book (by: Sunday)
Toot has 2 tasks in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Toot addeded:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Toot has 3 tasks in the list now! (｡•̀ᴗ-)✧
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Nice! I've marked this task as done:
  [E][X] project meeting (from: Mon 2pm to: 4pm)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][X] project meeting (from: Mon 2pm to: 4pm)
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆

⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
Otay bye-bye! Toot go eepy now... zZz (｡-ω-)ﾉ
⋆｡°✩ ──────────────────────────────────────────────── ✩°｡⋆
```
