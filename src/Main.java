import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Esto configura el título de la ventana que se abrirá
        primaryStage.setTitle("Agenda de Contactos - Hackatón");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Este es el comando que arranca JavaFX
        launch(args);
    }
}
