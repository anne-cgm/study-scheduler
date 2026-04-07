module com.acgm.studyscheduler {

    requires javafx.controls;
    requires javafx.fxml;

    // Permitir que o FXML acesse o controller
    opens studyscheduler.controller to javafx.fxml;

    // Se o FXML estiver em package "view" mesmo:
    opens view to javafx.fxml;

    // Exporta o pacote onde está o Main
    exports studyscheduler.app;
}