module com.acgm.studyscheduler {

    // Dependências do JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    // Permitir que o FXMLLoader acesse os pacotes de view e controller
    opens studyscheduler.view to javafx.fxml;
    opens studyscheduler.controller to javafx.fxml;

    // Se você quiser deixar Main público (não obrigatório)
    exports studyscheduler;
}