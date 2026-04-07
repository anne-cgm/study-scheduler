package studyscheduler.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import studyscheduler.model.Task;
import studyscheduler.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Comparator;

public class TaskService {

    private final ObservableList<Task> masterList =
            FXCollections.observableArrayList();

    private final TaskRepository repository = new TaskRepository();

    public TaskService() {
        masterList.addAll(repository.load());
        sortByDate();
    }

    public ObservableList<Task> getTasks() {
        return masterList;
    }

    public void addTask(String title, String description, LocalDate date) {
        masterList.add(new Task(title.trim(), description, date));
        refresh();
    }

    public void removeTask(Task task) {
        masterList.remove(task);
        refresh();
    }

    public void refresh() {
        sortByDate();
        repository.save(masterList);
    }

    private void sortByDate() {
        masterList.sort(
                Comparator.comparing(
                        Task::getDueDate,
                        Comparator.nullsLast(LocalDate::compareTo)
                )
        );
    }
}