import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Java Word Counter
 * -----------------
 * A beginner-friendly console application that reads multiline text from the user
 * and reports word, character, line, and case-insensitive word frequency statistics.
 */
public class Main {

    private static final String END_COMMAND = "END";

    public static void main(String[] args) {
        printBanner();

        // Use try-with-resources so the Scanner is automatically closed when done.
        try (Scanner scanner = new Scanner(System.in)) {
            String inputText = readMultilineInput(scanner);
            analyzeAndPrintReport(inputText);
        }
    }

    /**
     * Prints the application header and input instructions.
     */
    private static void printBanner() {
        System.out.println("========================================");
        System.out.println("           JAVA WORD COUNTER            ");
        System.out.println("========================================");
        System.out.println("Enter or paste your text below.");
        System.out.println("Type '" + END_COMMAND + "' on a new line to finish:");
        System.out.println("----------------------------------------");
    }

    /**
     * Reads multiline input from the console until the user types END on its own line
     * or the input stream ends (EOF).
     *
     * @param scanner the Scanner reading from System.in
     * @return the combined input text with lines joined by '\n'
     */
    public static String readMultilineInput(Scanner scanner) {
        StringBuilder textBuilder = new StringBuilder();
        boolean hasAnyLine = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Stop reading when the sentinel keyword is entered on its own line.
            if (line.trim().equals(END_COMMAND)) {
                break;
            }

            if (hasAnyLine) {
                textBuilder.append("\n");
            }
            textBuilder.append(line);
            hasAnyLine = true;
        }

        return textBuilder.toString();
    }

    /**
     * Runs all counting methods on the provided text and prints a formatted summary.
     *
     * @param text the input text to analyze
     */
    public static void analyzeAndPrintReport(String text) {
        String safeText = (text == null) ? "" : text;

        int totalLines = countLines(safeText);
        int charsWithSpaces = countCharactersIncludingSpaces(safeText);
        int charsWithoutWhitespace = countCharactersExcludingWhitespace(safeText);
        Map<String, Integer> wordFrequencies = calculateWordFrequencies(safeText);
        int totalWords = countTotalWords(wordFrequencies);
        int uniqueWords = wordFrequencies.size();

        displayResults(
                safeText.isEmpty(),
                totalWords,
                uniqueWords,
                charsWithSpaces,
                charsWithoutWhitespace,
                totalLines,
                wordFrequencies
        );
    }

    /**
     * Counts the total number of lines in the text.
     * Returns 0 when the input is completely empty.
     *
     * @param text the input text
     * @return number of lines
     */
    public static int countLines(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        // Using limit -1 preserves trailing empty lines if the user entered blank lines.
        return text.split("\n", -1).length;
    }

    /**
     * Counts all characters in the text, including spaces,
     * while excluding line-break characters (\n and \r) between lines.
     *
     * @param text the input text
     * @return total character count including spaces
     */
    public static int countCharactersIncludingSpaces(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String textWithoutLineBreaks = text.replace("\n", "").replace("\r", "");
        return textWithoutLineBreaks.length();
    }

    /**
     * Counts only non-whitespace characters (excludes spaces, tabs, and line breaks).
     *
     * @param text the input text
     * @return total character count excluding whitespace
     */
    public static int countCharactersExcludingWhitespace(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    /**
     * Extracts words from the text and counts how many times each unique word appears.
     * Case and punctuation are ignored during counting.
     *
     * @param text the input text
     * @return a HashMap mapping each lowercase word to its frequency
     */
    public static Map<String, Integer> calculateWordFrequencies(String text) {
        Map<String, Integer> frequencies = new HashMap<>();

        if (text == null || text.trim().isEmpty()) {
            return frequencies;
        }

        // Convert to lowercase, break double-dashes into spaces, and replace
        // punctuation (except internal apostrophes/hyphens) with spaces.
        String normalized = text.toLowerCase()
                .replaceAll("-{2,}", " ")
                .replaceAll("[^\\p{L}\\p{N}'-]+", " ");

        String[] tokens = normalized.trim().split("\\s+");

        for (String token : tokens) {
            // Strip any leading or trailing apostrophes/hyphens (e.g., "'hello'" -> "hello").
            String word = token.replaceAll("^['-]+|['-]+$", "");

            if (!word.isEmpty()) {
                frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
            }
        }

        return frequencies;
    }

    /**
     * Calculates the total word count by summing all frequencies in the word map.
     * Keeping this tied to the frequency map ensures total words always match word counts.
     *
     * @param wordFrequencies map of unique words to their counts
     * @return total number of words
     */
    public static int countTotalWords(Map<String, Integer> wordFrequencies) {
        if (wordFrequencies == null || wordFrequencies.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (int count : wordFrequencies.values()) {
            total += count;
        }
        return total;
    }

    /**
     * Displays the statistics and sorted word frequency table to the console.
     */
    private static void displayResults(
            boolean isEmptyInput,
            int totalWords,
            int uniqueWords,
            int charsWithSpaces,
            int charsWithoutWhitespace,
            int totalLines,
            Map<String, Integer> wordFrequencies
    ) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("            TEXT STATISTICS             ");
        System.out.println("========================================");

        if (isEmptyInput) {
            System.out.println("No text was entered.");
        }

        System.out.printf("Total Words                     : %d%n", totalWords);
        System.out.printf("Unique Words                    : %d%n", uniqueWords);
        System.out.printf("Total Characters (with spaces)  : %d%n", charsWithSpaces);
        System.out.printf("Characters (without whitespace) : %d%n", charsWithoutWhitespace);
        System.out.printf("Total Lines                     : %d%n", totalLines);

        System.out.println("----------------------------------------");
        System.out.println("Word Frequency (highest to lowest):");
        System.out.println("----------------------------------------");

        if (wordFrequencies.isEmpty()) {
            System.out.println("No words to display.");
        } else {
            // Sort by frequency descending, then alphabetically for ties.
            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequencies.entrySet());
            sortedEntries.sort(
                    Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey())
            );

            int maxWordWidth = 15;
            for (Map.Entry<String, Integer> entry : sortedEntries) {
                maxWordWidth = Math.max(maxWordWidth, entry.getKey().length() + 2);
            }

            String rowFormat = "%-" + maxWordWidth + "s : %d%n";
            System.out.printf("%-" + maxWordWidth + "s   %s%n", "Word", "Count");
            System.out.println("-".repeat(maxWordWidth + 10));

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                System.out.printf(rowFormat, entry.getKey(), entry.getValue());
            }
        }

        System.out.println("========================================");
    }
}
