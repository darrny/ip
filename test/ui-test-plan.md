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

### TC-02: Add, list, mark, and unmark a task

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

### TC-03: Add and mark a deadline

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

### TC-04: Store all task types together

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
