package studyscheduler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import studyscheduler.model.Task;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskController {

    @FXML private TextField taskField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private ListView<Task> taskList;
    @FXML private Label totalLabel;
    @FXML private Label completedLabel;
    @FXML private Label overdueLabel;

    private final ObservableList<Task> masterList = FXCollections.observableArrayList();

    private static final String FILE_NAME = "tasks.dat";

    @FXML
    public void initialize() {
        loadTasks();
        taskList.setItems(masterList);
        configureCells();
        sortByDate();
        updateCounters();
    }

    // =====================================================
    // LISTVIEW
    // =====================================================

    private void configureCells() {

        taskList.setCellFactory(lv -> new ListCell<>() {

            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(e -> {
                    Task task = getItem();
                    if (task != null) {
                        task.setCompleted(checkBox.isSelected());
                        refreshAll();
                    }
                });
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setGraphic(null);
                    setContextMenu(null);
                    return;
                }

                StringBuilder text = new StringBuilder(task.getTitle());

                if (task.getDueDate() != null) {
                    text.append(" - ").append(task.getDueDate());
                }

                if (task.getDescription() != null && !task.getDescription().isBlank()) {
                    text.append("\n").append(task.getDescription());
                }

                checkBox.setText(text.toString());
                checkBox.setSelected(task.isCompleted());

                if (task.isCompleted()) {
                    checkBox.setStyle("-fx-text-fill: green; -fx-strikethrough: true;");
                } else if (task.isOverdue()) {
                    checkBox.setStyle("-fx-text-fill: red;");
                } else {
                    checkBox.setStyle("");
                }

                MenuItem edit = new MenuItem("Editar");
                edit.setOnAction(e -> openEditDialog(task));

                MenuItem delete = new MenuItem("Excluir");
                delete.setOnAction(e -> {
                    masterList.remove(task);
                    refreshAll();
                });

                setContextMenu(new ContextMenu(edit, delete));
                setGraphic(checkBox);
            }
        });
    }

    // =====================================================
    // ADICIONAR
    // =====================================================

    @FXML
    private void handleAddTask() {

        String title = taskField.getText();
        if (title == null || title.isBlank()) return;

        masterList.add(new Task(
                title.trim(),
                descriptionField.getText(),
                datePicker.getValue()
        ));

        taskField.clear();
        descriptionField.clear();
        datePicker.setValue(null);

        refreshAll();
    }

    // =====================================================
    // EDITAR
    // =====================================================

    private void openEditDialog(Task task) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar tarefa");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setGraphic(null);

        ButtonType saveButton =
                new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes()
                .addAll(saveButton, ButtonType.CANCEL);

        TextField titleField = new TextField(task.getTitle());
        TextArea descriptionArea =
                new TextArea(task.getDescription() == null ? "" : task.getDescription());
        DatePicker editDatePicker = new DatePicker(task.getDueDate());

        descriptionArea.setPrefHeight(80);

        VBox content = new VBox(12,
                new Label("Título"), titleField,
                new Label("Descrição"), descriptionArea,
                new Label("Data de entrega"), editDatePicker
        );

        content.setStyle("-fx-padding: 20;");
        dialog.getDialogPane().setContent(content);

        dialog.showAndWait().ifPresent(response -> {
            if (response == saveButton && !titleField.getText().isBlank()) {

                task.setTitle(titleField.getText().trim());
                task.setDescription(descriptionArea.getText());
                task.setDueDate(editDatePicker.getValue());

                refreshAll();
            }
        });
    }

    // =====================================================
    // FILTROS
    // =====================================================

    @FXML
    private void filterAll() {
        taskList.setItems(masterList);
    }

    @FXML
    private void filterTodo() {
        taskList.setItems(masterList.filtered(t -> !t.isCompleted()));
    }

    @FXML
    private void filterCompleted() {
        taskList.setItems(masterList.filtered(Task::isCompleted));
    }

    @FXML
    private void filterOverdue() {
        taskList.setItems(masterList.filtered(Task::isOverdue));
    }

    // =====================================================
    // UTIL
    // =====================================================

    private void refreshAll() {
        sortByDate();
        updateCounters();
        taskList.refresh();
        saveTasks();
    }

    private void sortByDate() {
        masterList.sort(
                Comparator.comparing(
                        Task::getDueDate,
                        Comparator.nullsLast(LocalDate::compareTo)
                )
        );
    }

    private void updateCounters() {

        totalLabel.setText(String.valueOf(masterList.size()));
        completedLabel.setText(String.valueOf(
                masterList.stream().filter(Task::isCompleted).count()
        ));
        overdueLabel.setText(String.valueOf(
                masterList.stream().filter(Task::isOverdue).count()
        ));
    }

    // =====================================================
    // PERSISTÊNCIA
    // =====================================================

    private void saveTasks() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(new ArrayList<>(masterList));

        } catch (IOException ignored) {}
    }

    @SuppressWarnings("unchecked")
    private void loadTasks() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            masterList.addAll((List<Task>) ois.readObject());

        } catch (IOException | ClassNotFoundException ignored) {}
    }
}