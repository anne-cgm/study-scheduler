module studyscheduler {

    requires javafx.controls;
    requires javafx.fxml;

    opens studyscheduler.controller to javafx.fxml;

    opens view to javafx.fxml;

    exports studyscheduler.app;
}