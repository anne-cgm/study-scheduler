package studyscheduler.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import studyscheduler.model.Task;
import studyscheduler.model.Weather;
import studyscheduler.service.TaskService;
import studyscheduler.service.WeatherService;

public class TaskController {

    // INPUTS
    @FXML private TextField taskTitleField;
    @FXML private TextField taskDescriptionField;
    @FXML private DatePicker taskDatePicker;

    // LISTA
    @FXML private VBox taskListContainer;

    // COUNTERS
    @FXML private Label totalLabel;
    @FXML private Label completedLabel;
    @FXML private Label overdueLabel;
    @FXML private Label taskCountLabel;

    // WEATHER
    @FXML private Label weatherIconLabel;
    @FXML private Label weatherStatusLabel;
    @FXML private Label weatherTempLabel;
    @FXML private Label weatherTipLabel;

    // SERVICE (ÚNICO)
    private final TaskService service = new TaskService();

    @FXML
    public void initialize() {
        service.setFilter("all");
        renderTasks();
        updateCounters();
        loadWeather();
    }

    // =========================
    // WEATHER
    // =========================

    private void loadWeather() {
        try {
            WeatherService weatherService = new WeatherService();
            Weather clima = weatherService.buscarClima();

            weatherStatusLabel.setText(clima.getDescricao());
            weatherTempLabel.setText(Math.round(clima.getTemperatura()) + "°C");
            weatherTipLabel.setText(clima.getMensagem());

            String desc = clima.getDescricao().toLowerCase();

            if (desc.contains("chuva")) {
                weatherIconLabel.setText("🌧");
            } else if (desc.contains("nuv")) {
                weatherIconLabel.setText("☁");
            } else {
                weatherIconLabel.setText("☀");
            }

        } catch (Exception e) {
            weatherStatusLabel.setText("Clima indisponível");
            weatherTempLabel.setText("--°C");
            weatherTipLabel.setText("Não foi possível carregar o clima");
            weatherIconLabel.setText("☁");
        }
    }

    // =========================
    // ADD TASK
    // =========================

    @FXML
    private void handleAddTask() {

        String title = taskTitleField.getText();

        if (title == null || title.isBlank()) return;

        service.addTask(
                title,
                taskDescriptionField.getText(),
                taskDatePicker.getValue()
        );

        taskTitleField.clear();
        taskDescriptionField.clear();
        taskDatePicker.setValue(null);

        renderTasks();
        updateCounters();
    }

    // =========================
    // RENDER
    // =========================

    private void renderTasks() {

        taskListContainer.getChildren().clear();

        for (Task task : service.getTasks()) {
            taskListContainer.getChildren().add(createTaskCard(task));
        }
    }

    // =========================
    // TASK CARD
    // =========================

    private HBox createTaskCard(Task task) {

        CheckBox completedBox = new CheckBox();
        completedBox.setSelected(task.isCompleted());

        completedBox.setOnAction(e -> {
            task.setCompleted(completedBox.isSelected());
            service.refresh();
            renderTasks();
            updateCounters();
        });

        String desc = task.getDescription() == null ? "" : task.getDescription();

        Label title = new Label(
                desc.isBlank()
                        ? task.getTitle()
                        : task.getTitle() + " - " + desc
        );

        Label date = new Label(
                task.getDueDate() == null ? "Sem data" : task.getDueDate().toString()
        );

        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().addAll(completedBox, title, date);

        if (task.isCompleted()) {
            card.getStyleClass().add("task-item-completed");
        } else if (task.isOverdue()) {
            card.getStyleClass().add("task-item-overdue");
        } else {
            card.getStyleClass().add("task-item");
        }

        createContextMenu(task, card);

        return card;
    }

    // =========================
    // CONTEXT MENU
    // =========================

    private void createContextMenu(Task task, HBox card) {

        ContextMenu menu = new ContextMenu();

        MenuItem edit = new MenuItem("Editar");
        MenuItem delete = new MenuItem("Excluir");

        edit.setOnAction(e -> editTask(task));

        delete.setOnAction(e -> {
            service.removeTask(task);
            renderTasks();
            updateCounters();
        });

        menu.getItems().addAll(edit, delete);

        card.setOnContextMenuRequested(e ->
                menu.show(card, e.getScreenX(), e.getScreenY())
        );
    }

    // =========================
    // EDIT
    // =========================

    private void editTask(Task task) {

        taskTitleField.setText(task.getTitle());
        taskDescriptionField.setText(task.getDescription());

        if (task.getDueDate() != null) {
            taskDatePicker.setValue(task.getDueDate());
        }

        service.removeTask(task);

        renderTasks();
        updateCounters();
    }

    // =========================
    // FILTERS
    // =========================

    @FXML
    private void filterAll() {
        service.setFilter("all");
        renderTasks();
        updateCounters();
    }

    @FXML
    private void filterCompleted() {
        service.setFilter("completed");
        renderTasks();
        updateCounters();
    }

    @FXML
    private void filterOverdue() {
        service.setFilter("overdue");
        renderTasks();
        updateCounters();
    }

    @FXML
    private void filterTodo() {
        service.setFilter("todo");
        renderTasks();
        updateCounters();
    }

    // =========================
    // COUNTERS
    // =========================

    private void updateCounters() {

        int total = service.getAllTasks().size();
        long completed = service.countCompletedTasks();
        long overdue = service.countOverdueTasks();
        int visible = service.getTasks().size();

        totalLabel.setText(String.valueOf(total));
        completedLabel.setText(String.valueOf(completed));
        overdueLabel.setText(String.valueOf(overdue));
        taskCountLabel.setText(String.valueOf(visible));
    }
}
