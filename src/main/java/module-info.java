module studyscheduler {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens studyscheduler.controller to javafx.fxml;

    opens view to javafx.fxml;

    exports studyscheduler.app;
}