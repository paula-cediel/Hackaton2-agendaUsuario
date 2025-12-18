package app;

import model.Contacto;
import service.AgendaService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private AgendaService agenda = new AgendaService();

    @Override
    public void start(Stage primaryStage) {
        Label lblTitulo = new Label("Agenda de Contactos - Misión Hackatón");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtApellido = new TextField();
        txtApellido.setPromptText("Apellido");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");

        Button btnGuardar = new Button("Añadir Contacto");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);

        Button btnListar = new Button("Listar Contactos");
        btnListar.setMaxWidth(Double.MAX_VALUE);

        TextArea areaLista = new TextArea();
        areaLista.setEditable(false);
        areaLista.setPromptText("Los contactos aparecerán aquí...");

        // --- LÓGICA DE BOTONES ---
        btnGuardar.setOnAction(e -> {
            try {
                String nom = txtNombre.getText().trim();
                String ape = txtApellido.getText().trim();
                String tel = txtTelefono.getText().trim();

                if (nom.isEmpty() || ape.isEmpty() || tel.isEmpty()) {
                    throw new Exception("Todos los campos son obligatorios.");
                }

                Contacto nuevo = new Contacto(nom, ape, tel);
                agenda.agregar(nuevo);

                areaLista.setText("Sistema: " + nom + " guardado con éxito.");
                txtNombre.clear();
                txtApellido.clear();
                txtTelefono.clear();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        btnListar.setOnAction(e -> {
            agenda.listarContactos(areaLista);
        });

        // --- DISEÑO ESTILO DRACULA (Corrección de llaves aquí) ---
        VBox layout = new VBox(12);
        layout.getChildren().addAll(lblTitulo, txtNombre, txtApellido, txtTelefono, btnGuardar, btnListar, areaLista);

        layout.setStyle("-fx-padding: 25; -fx-alignment: center; -fx-background-color: #282a36;");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #ff79c6;");

        String estiloInputs = "-fx-background-color: #44475a; -fx-text-fill: #f8f8f2; -fx-prompt-text-fill: #6272a4;";
        txtNombre.setStyle(estiloInputs);
        txtApellido.setStyle(estiloInputs);
        txtTelefono.setStyle(estiloInputs);

        btnGuardar.setStyle("-fx-background-color: #50fa7b; -fx-text-fill: #282a36; -fx-font-weight: bold; -fx-cursor: hand;");
        btnListar.setStyle("-fx-background-color: #bd93f9; -fx-text-fill: #282a36; -fx-font-weight: bold; -fx-cursor: hand;");

        areaLista.setStyle("-fx-control-inner-background: #282a36; -fx-text-fill: #f1fa8c; -fx-font-family: 'Consolas'; -fx-border-color: #6272a4;");

        Scene scene = new Scene(layout, 400, 550);
        primaryStage.setTitle("Agenda Dracula Edition - Eri");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}