package duke;

import duke.exception.DukeException;
import duke.task.Task;

import java.util.ArrayList;

/**
 * To perform operations on the list of tasks
 */
public class TaskList {
    public static ArrayList<Task> list = new ArrayList<>();

    /**
     * Adds a task to the list and prints a completion message
     *
     * @param task is the Task object is being added to the list
     */
    public void addTask(Task task) {
        list.add(task);

        Ui.printAddTaskMessage(task);
    }

    /**
     * Marks a task as done with the given index and prints a completion message
     *
     * @param taskIndex is the index of the task needed to be marked as done
     */
    public void doneTask(int taskIndex) {
        list.get(taskIndex).markAsDone();

        Ui.printDoneTaskMessage(taskIndex);
    }

    /**
     * Deletes a task with the given index and prints a completion message
     *
     * @param taskIndex is the index of the task needed to be deleted
     * @throws DukeException if index provided is larger than the list size or is lesser than 1
     */
    public void deleteTask(int taskIndex) throws DukeException {
        if (taskIndex >= list.size()) {
            throw new DukeException("Task index does not exist!");
        } else if (taskIndex <= -1) {
            throw new DukeException("Task index must be larger than 0");
        }

        Ui.printDeleteTaskMessage(taskIndex);

        list.remove(taskIndex);
    }

    /**
     * Prints all the tasks present in the list
     */
    public void listTasks() {
        if (list.isEmpty()) {
            Ui.printEmptyListMessage();
        } else {
            Ui.printTaskList();
        }
    }

    /**
     * Searches and prints the task based on the keyword provided. Catches exception if there are no matching tasks
     *
     * @param keyword is the word that needs to be searched for
     */
    public void findTask(String keyword) throws DukeException {
        if (list.isEmpty()) {
            Ui.printEmptyListMessage();
        } else {
            try {
                Ui.printLineSeparator();
                System.out.println("Here are the matching tasks in your list:");

                for (int i = 0; i < list.size(); i++) {
                    String taskDescription = list.get(i).getDataForFind().toLowerCase();
                    Ui.printMatchingTasks(keyword, taskDescription, i);
                }

                Ui.printLineSeparator();
            } catch (DukeException e) {
                throw new DukeException("No matching tasks found, try another keyword");
            }
        }
    }

}
