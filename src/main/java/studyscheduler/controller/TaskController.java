package studyscheduler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import studyscheduler.model.Task;

import java.time.LocalDate;

public class TaskController {

    @FXML
    private TextField taskField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<Task> taskList;

    @FXML
    private Label totalLabel;

    @FXML
    private Label completedLabel;

    @FXML
    private Label overdueLabel;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        taskList.setItems(tasks);

        // Clique para marcar como concluída
        taskList.setOnMouseClicked(event -> {
            Task selectedTask = taskList.getSelectionModel().getSelectedItem();

            if (selectedTask != null) {
                selectedTask.setCompleted(!selectedTask.isCompleted());
                taskList.refresh();
                updateCounters();
            }
        });

        taskList.setCellFactory(lv -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(e -> {
                    Task task = getItem();
                    if (task != null) {
                        task.setCompleted(checkBox.isSelected());
                        updateCounters();
                        updateItem(task, false); // Atualiza a cor
                    }
                });
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setGraphic(null);
                    setText(null);
                    setStyle("");
                } else {
                    checkBox.setText(task.getTitle() + " - " + task.getDueDate());
                    checkBox.setSelected(task.isCompleted());

                    if (task.isCompleted()) {
                        checkBox.setStyle("-fx-text-fill: green; -fx-strikethrough: true;");
                    } else if (task.isOverdue()) {
                        checkBox.setStyle("-fx-text-fill: red;");
                    } else {
                        checkBox.setStyle("-fx-text-fill: black;");
                    }

                    setGraphic(checkBox);
                }
            }
        });

        taskList.setOnMouseClicked(event -> {
            Task selectedTask = taskList.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {

                selectedTask.setCompleted(!selectedTask.isCompleted());

                taskList.refresh();


                updateCounters();
            }
        });
    }

    @FXML
    private void handleAddTask() {

        String title = taskField.getText();
        LocalDate date = datePicker.getValue();

        if (title == null || title.isEmpty() || date == null) {
            return;
        }

        Task newTask = new Task(title, date);
        tasks.add(newTask);

        taskField.clear();
        datePicker.setValue(null);

        updateCounters();
    }

    private void updateCounters() {

        int total = tasks.size();

        int completed = (int) tasks.stream()
                .filter(Task::isCompleted)
                .count();

        int overdue = (int) tasks.stream()
                .filter(Task::isOverdue)
                .count();

        totalLabel.setText(String.valueOf(total));
        completedLabel.setText(String.valueOf(completed));
        overdueLabel.setText(String.valueOf(overdue));
    }

    @FXML
    private Button deleteButton;

    @FXML
    private void handleDeleteTasks() {
        tasks.removeIf(Task::isCompleted);
        updateCounters();
    }
}
