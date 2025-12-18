package agenda;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AgendaFX extends Application {

    // Objetos principales
    private AgendaContactos agenda = new AgendaContactos();
    private ListView<String> lista = new ListView<>();

    @Override
    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("AGENDA DE CONTACTOS");
        titulo.setFont(Font.font(20));
        titulo.setStyle("-fx-text-fill: #e0e0e0;");

        // ===== CAMPOS DE TEXTO =====
        TextField txtNombre = crearCampo("Nombre");
        TextField txtApellido = crearCampo("Apellido");
        TextField txtTelefono = crearCampo("Teléfono");

        // ===== BOTONES =====
        Button btnAgregar = crearBoton("Agregar");
        Button btnEliminar = crearBoton("Eliminar");
        Button btnListar = crearBoton("Listar");

        // ===== ACCIÓN AGREGAR =====
        btnAgregar.setOnAction(e -> {

            Contacto c = new Contacto(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText()
            );

            agenda.anadirContacto(c);
            actualizarLista();

            txtNombre.clear();
            txtApellido.clear();
            txtTelefono.clear();
        });

        // ===== ACCIÓN ELIMINAR (DESDE LA LISTA) =====
        btnEliminar.setOnAction(e -> {

            String seleccionado = lista.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                System.out.println("Seleccione un contacto.");
                return;
            }

            // Formato: Nombre Apellido - Teléfono
            String parteNombre = seleccionado.split(" - ")[0];
            String[] datos = parteNombre.split(" ");

            Contacto c = new Contacto(datos[0], datos[1], "");
            agenda.eliminarContacto(c);

            actualizarLista();
        });

        // ===== ACCIÓN LISTAR =====
        btnListar.setOnAction(e -> actualizarLista());

        // ===== LAYOUT =====
        VBox campos = new VBox(10, txtNombre, txtApellido, txtTelefono);
        campos.setAlignment(Pos.CENTER);

        HBox botones = new HBox(10, btnAgregar, btnEliminar, btnListar);
        botones.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, titulo, campos, botones, lista);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1e1e2e;");

        lista.setStyle("""
            -fx-control-inner-background: #2a2a3c;
            -fx-text-fill: #e0e0e0;
        """);

        Scene scene = new Scene(root, 420, 520);
        stage.setTitle("Agenda");
        stage.setScene(scene);
        stage.show();
    }

    // ===== MÉTODOS AUXILIARES (SIMPLES) =====

    private TextField crearCampo(String texto) {
        TextField campo = new TextField();
        campo.setPromptText(texto);
        campo.setStyle("""
            -fx-background-color: #2a2a3c;
            -fx-text-fill: #e0e0e0;
            -fx-prompt-text-fill: #aaaaaa;
        """);
        return campo;
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(100);
        boton.setStyle("""
            -fx-background-color: #5dade2;
            -fx-text-fill: black;
            -fx-font-weight: bold;
        """);
        return boton;
    }

    private void actualizarLista() {
        lista.getItems().clear();

        Contacto[] contactos = agenda.getContactos();

        for (int i = 0; i < contactos.length; i++) {
            Contacto c = contactos[i];
            lista.getItems().add(
                    c.getNombre() + " " +
                            c.getApellido() + " - " +
                            c.getTelefono()
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
