package duke;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * To make sense of user commands by extracting keywords, descriptions and time/date
 */
public class Parser {
    private static final int INDEX_AFTER_TODO = 4;
    private static final int INDEX_AFTER_DEADLINE = 8;
    private static final int INDEX_AFTER_EVENT = 5;
    protected static LocalDate dueTime;

    /**
     * Extracts the description, creates a todo Task object
     *
     * @param input is the command given by the user
     * @return the todo object created under Task
     * @throws DukeException if no description is present after the todo command
     */
    public static Task getTodoDetails(String input) throws DukeException {
        if (input.substring(INDEX_AFTER_TODO).isBlank()) {
            throw new DukeException("Task description is missing");
        }

        // To extract the description after the four-letter word "todo"
        String todoDescription = input.substring(INDEX_AFTER_TODO).trim();

        Task todo = new Todo(todoDescription);

        return todo;
    }

    /**
     * Extracts the description and day/date, creates a deadline Task object
     *
     * @param input is the command given by the user
     * @return the deadline object created under Task
     * @throws DukeException if /by is not present in the command or if no description is present after the deadline command
     */
    public static Task getDeadlineDetails(String input) throws DukeException {
        if (input.substring(INDEX_AFTER_DEADLINE).isBlank()) {
            throw new DukeException("Task description is missing");
        }

        if (!input.contains("/by")) {
            throw new DukeException("DEADLINE task description is missing \"/by\" [Format: deadline task description /by deadline time/day/date]");
        }

        // To extract description between the eight-letter word "deadline" and "/by"
        int endIndex = input.indexOf("/");
        String deadlineDescription = input.substring(INDEX_AFTER_DEADLINE, endIndex).trim();
        String deadlineDate = getDateFromCommand(input);

        dueTime = LocalDate.parse(deadlineDate);

        Task deadline = new Deadline(deadlineDescription, dueTime);

        return deadline;
    }

    /**
     * Extracts the description and the time, creates an event Task object
     *
     * @param input is the command given by the user
     * @return the event object created under Task
     * @throws DukeException if /at is not present in the command or if no description is present after the event command
     */
    public static Task getEventDetails(String input) throws DukeException {
        if (input.substring(INDEX_AFTER_EVENT).isBlank()) {
            throw new DukeException("Task description is missing");
        }

        if (!input.contains("/at")) {
            throw new DukeException("EVENT task description is missing \"/at\" [Format: event task description /at event time/day/place]");
        }

        // To extract description between the five-letter word "event" and "/at"
        int endIndex = input.indexOf("/");
        String eventDescription = input.substring(INDEX_AFTER_EVENT, endIndex).trim();
        String eventDate = getDateFromCommand(input);

        Task event = new Event(eventDescription, eventDate);

        return event;
    }

    public static String getUserInput(Scanner in) {
        String input;
        input = in.nextLine();

        return input;
    }

    /**
     * @param input is the command given by the user
     * @return the substring after the three-letter words /by or /at
     */
    protected static String getDateFromCommand(String input) {
        int startIndex = input.indexOf("/");

        return input.substring(startIndex + 3).trim();
    }

    /**
     * Extracts the index from the done/delete command
     *
     * @param input is the command given by the user
     * @return the index of the task present in the task list
     */
    public static int getIndex(String input) {
        String[] temp = input.split(" ");
        int index = Integer.parseInt(temp[1]);
        index = index - 1;

        return index;
    }

    /**
     * @param input is the command given by the user
     * @return the lowercase form of the first word present in the command
     */
    public static String getFirstWordFromCommand(String input) {
        return input.toLowerCase().split(" ")[0];
    }
}
