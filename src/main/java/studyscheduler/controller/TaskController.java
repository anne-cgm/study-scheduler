package studyscheduler.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import studyscheduler.model.Task;
import studyscheduler.service.TaskService;

public class TaskController {

    @FXML private TextField taskField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private ListView<Task> taskList;
    @FXML private Label totalLabel;
    @FXML private Label completedLabel;
    @FXML private Label overdueLabel;

    private final TaskService service = new TaskService();

    @FXML
    public void initialize() {
        taskList.setItems(service.getTasks());
        configureCells();
        updateCounters();
    }

    private void configureCells() {

        taskList.setCellFactory(lv -> new ListCell<>() {

            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(e -> {
                    Task task = getItem();
                    if (task != null) {
                        task.setCompleted(checkBox.isSelected());
                        service.refresh();
                        updateCounters();
                        taskList.refresh();
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
                    service.removeTask(task);
                    updateCounters();
                });

                setContextMenu(new ContextMenu(edit, delete));
                setGraphic(checkBox);
            }
        });
    }

    @FXML
    private void handleAddTask() {

        String title = taskField.getText();
        if (title == null || title.isBlank()) return;

        service.addTask(
                title,
                descriptionField.getText(),
                datePicker.getValue()
        );

        taskField.clear();
        descriptionField.clear();
        datePicker.setValue(null);

        updateCounters();
    }

    private void openEditDialog(Task task) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar tarefa");

        ButtonType saveButton =
                new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes()
                .addAll(saveButton, ButtonType.CANCEL);

        TextField titleField = new TextField(task.getTitle());
        TextArea descriptionArea =
                new TextArea(task.getDescription() == null ? "" : task.getDescription());
        DatePicker editDatePicker = new DatePicker(task.getDueDate());

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

                service.refresh();
                updateCounters();
                taskList.refresh();
            }
        });
    }

    @FXML
    private void filterAll() {
        taskList.setItems(service.getTasks());
    }

    @FXML
    private void filterTodo() {
        taskList.setItems(
                service.getTasks().filtered(t -> !t.isCompleted())
        );
    }

    @FXML
    private void filterCompleted() {
        taskList.setItems(
                service.getTasks().filtered(Task::isCompleted)
        );
    }

    @FXML
    private void filterOverdue() {
        taskList.setItems(
                service.getTasks().filtered(Task::isOverdue)
        );
    }

    private void updateCounters() {

        totalLabel.setText(String.valueOf(service.getTasks().size()));
        completedLabel.setText(String.valueOf(
                service.getTasks().stream().filter(Task::isCompleted).count()
        ));
        overdueLabel.setText(String.valueOf(
                service.getTasks().stream().filter(Task::isOverdue).count()
        ));
    }
}