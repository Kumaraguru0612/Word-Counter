# Java Word Counter

A beginner-friendly Core Java console application that analyzes multiline text input and reports detailed statistics, including total words, character counts (with and without whitespace), line count, and case-insensitive word frequencies.

## Features

- **Multiline Input**: Accepts single or multiple lines of text from the console until `END` is entered on a new line (or EOF is reached).
- **Word Count**: Counts total words accurately across all lines.
- **Character Counts**:
  - Total characters including spaces (excluding line-break characters).
  - Total characters excluding all whitespace (`spaces`, `tabs`, `newlines`).
- **Line Count**: Counts the total number of lines entered.
- **Word Frequency Table**: Displays every unique word sorted from highest frequency to lowest (and alphabetically for ties).
- **Case-Insensitive Matching**: Treats `Java`, `JAVA`, and `java` as the same word.
- **Punctuation Handling**: Ignores surrounding punctuation (like commas, periods, quotes, and exclamation marks) while preserving valid internal apostrophes and hyphens (e.g., `it's`, `well-known`).
- **Empty Input Handling**: Gracefully handles empty, whitespace-only, or punctuation-only input without errors.

## Requirements

- **Java Development Kit (JDK) 17 or later**
- No external libraries or frameworks required (Core Java only)

Verify your Java installation:

```bash
java -version
javac -version
```

## Project Structure

```text
Java-Word-Counter/
├── src/
│   └── Main.java
├── README.md
└── .gitignore
```

## Installation

1. Download or copy the `Java-Word-Counter` folder to your local machine.
2. Open a terminal or command prompt and navigate into the project directory:

```bash
cd Java-Word-Counter
```

## Compilation

Compile `src/Main.java` into an `out` directory:

```bash
javac -d out src/Main.java
```

## Execution

Run the compiled program:

```bash
java -cp out Main
```

## Sample Input

```text
Java is fun.
Java is powerful!
Learn Java today.
END
```

## Sample Output

```text
========================================
           JAVA WORD COUNTER            
========================================
Enter or paste your text below.
Type 'END' on a new line to finish:
----------------------------------------
Java is fun.
Java is powerful!
Learn Java today.
END

========================================
            TEXT STATISTICS             
========================================
Total Words                     : 9
Unique Words                    : 6
Total Characters (with spaces)  : 46
Characters (without whitespace) : 40
Total Lines                     : 3
----------------------------------------
Word Frequency (highest to lowest):
----------------------------------------
Word              Count
-------------------------
java            : 3
is              : 2
fun             : 1
learn           : 1
powerful        : 1
today           : 1
========================================
```

## Java Concepts Demonstrated

- **Console I/O & Resource Management**: Reading multiline user input with `Scanner` and `try-with-resources`.
- **String Manipulation**: Using `StringBuilder`, `trim()`, `isEmpty()`, `toLowerCase()`, `split()`, `replace()`, `replaceAll()`, and `Character.isWhitespace()`.
- **Regular Expressions**: Normalizing text and stripping punctuation cleanly.
- **Collections Framework**: Storing and updating word counts with `HashMap<String, Integer>` (`getOrDefault()`), and sorting `Map.Entry` items with `ArrayList` and `Comparator`.
- **Modular Method Design**: Separating input reading, counting logic, and formatted console reporting (`System.out.printf`) into focused, reusable methods.
