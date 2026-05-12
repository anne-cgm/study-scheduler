package studyscheduler.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import studyscheduler.model.Task;
import studyscheduler.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Comparator;

public class TaskService {

    private final ObservableList<Task> masterList =
            FXCollections.observableArrayList();

    private final FilteredList<Task> filteredList =
            new FilteredList<>(masterList, t -> true);

    private final TaskRepository repository = new TaskRepository();

    public TaskService() {
        masterList.addAll(repository.load());
        sortByDate();
    }

    // =========================
    // LISTA VISÍVEL NA UI
    // =========================

    public ObservableList<Task> getTasks() {
        return filteredList;
    }

    public ObservableList<Task> getAllTasks() {
        return masterList;
    }

    public long countCompletedTasks() {
        return masterList.stream()
                .filter(Task::isCompleted)
                .count();
    }

    public long countOverdueTasks() {
        return masterList.stream()
                .filter(t -> !t.isCompleted() && t.isOverdue())
                .count();
    }

    // =========================
    // CRUD
    // =========================

    public void addTask(String title, String description, LocalDate date) {
        masterList.add(new Task(title.trim(), description, date));
        refresh();
    }

    public void removeTask(Task task) {
        masterList.remove(task);
        refresh();
    }

    // =========================
    // FILTER (ESSENCIAL)
    // =========================

    public void setFilter(String filter) {

        filteredList.setPredicate(task -> switch (filter) {

            case "completed" ->
                    task.isCompleted();

            case "overdue" ->
                    !task.isCompleted() && task.isOverdue();

            case "todo" ->
                    !task.isCompleted();

            default ->
                    true;
        });
    }

    // =========================
    // REFRESH / SAVE
    // =========================

    public void refresh() {
        sortByDate();
        repository.save(masterList);
    }

    private void sortByDate() {
        masterList.sort(
                Comparator.comparing(Task::isCompleted)
                        .thenComparing(
                                Task::getDueDate,
                                Comparator.nullsLast(LocalDate::compareTo)
                        )
        );
    }
}