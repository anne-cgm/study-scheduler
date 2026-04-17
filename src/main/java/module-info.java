module studyscheduler {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens studyscheduler.controller to javafx.fxml;

    opens view to javafx.fxml;

    exports studyscheduler.app;
}