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

    // --- ATRIBUTOS ---
    private AgendaContactos agenda;
    private ListView<String> lista = new ListView<>();

    @Override
    public void start(Stage stage) {

        // --- TÍTULO ---
        Label titulo = new Label("AGENDA DE CONTACTOS");
        titulo.setFont(Font.font(20));
        titulo.setStyle("-fx-text-fill: #e0e0e0;");

        // --- CREAR AGENDA ---
        Label lblCantidad = new Label("Número de contactos:");
        lblCantidad.setStyle("-fx-text-fill: #e0e0e0;");

        TextField txtCantidad = crearCampo("Cantidad máxima");
        Button btnCrearAgenda = crearBoton("Crear Agenda");

        VBox crearAgendaBox = new VBox(5, lblCantidad, txtCantidad, btnCrearAgenda);
        crearAgendaBox.setAlignment(Pos.CENTER);

        // --- CAMPOS ---
        TextField txtNombre = crearCampo("Nombre");
        TextField txtApellido = crearCampo("Apellido");
        TextField txtTelefono = crearCampo("Teléfono");

        VBox campos = new VBox(10, txtNombre, txtApellido, txtTelefono);
        campos.setAlignment(Pos.CENTER);

        // --- BOTONES ---
        Button btnAgregar = crearBoton("Agregar");
        Button btnEditar = crearBoton("Editar");
        Button btnEliminar = crearBoton("Eliminar");

        HBox botones = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        botones.setAlignment(Pos.CENTER);

        // --- LISTA ---
        lista.setStyle("""
            -fx-control-inner-background: #2a2a3c;
            -fx-background-color: #2a2a3c;
            -fx-text-fill: #e0e0e0;
            -fx-border-color: #5dade2;
        """);

        // ================= ACCIONES =================

        btnCrearAgenda.setOnAction(e -> {
            if (txtCantidad.getText().isEmpty()) {
                alerta("Error", "Debe ingresar la cantidad de contactos.");
                return;
            }

            int cantidad = Integer.parseInt(txtCantidad.getText());
            agenda = new AgendaContactos(cantidad);

            alerta("Correcto", "Agenda creada con " + cantidad + " contactos.");
            txtCantidad.setDisable(true);
            btnCrearAgenda.setDisable(true);
        });

        btnAgregar.setOnAction(e -> {

            if (agenda == null) {
                alerta("Error", "Primero debe crear la agenda.");
                return;
            }

            if (txtNombre.getText().isEmpty()
                || txtApellido.getText().isEmpty()
                || txtTelefono.getText().isEmpty()) {

                alerta("Error", "Debe llenar TODOS los campos.");
                return;
            }

            if (agenda.agendaLlena()) {
                alerta("Aviso", "Los contactos ya han sido agregados.");
                return;
            }

            agenda.anadirContacto(new Contacto(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText()
            ));

            limpiarCampos(txtNombre, txtApellido, txtTelefono);
            actualizarLista();
        });

        btnEditar.setOnAction(e -> {

            if (agenda == null) {
                alerta("Error", "No hay agenda creada.");
                return;
            }

            String seleccionado = lista.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                alerta("Error", "Seleccione un contacto.");
                return;
            }

            if (txtNombre.getText().isEmpty()
                || txtApellido.getText().isEmpty()
                || txtTelefono.getText().isEmpty()) {

                alerta("Error", "Debe llenar todos los campos.");
                return;
            }

            String[] datos = seleccionado.split(" - ");
            String[] nombre = datos[0].split(" ");

            agenda.editarContacto(
                    new Contacto(nombre[0], nombre[1], ""),
                    new Contacto(
                            txtNombre.getText(),
                            txtApellido.getText(),
                            txtTelefono.getText())
            );

            limpiarCampos(txtNombre, txtApellido, txtTelefono);
            actualizarLista();
        });

        btnEliminar.setOnAction(e -> {

            if (agenda == null) {
                alerta("Error", "No hay agenda creada.");
                return;
            }

            String seleccionado = lista.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                alerta("Error", "Seleccione un contacto.");
                return;
            }

            String[] datos = seleccionado.split(" - ");
            String[] nombre = datos[0].split(" ");

            agenda.eliminarContacto(new Contacto(nombre[0], nombre[1], ""));
            actualizarLista();
        });

        // --- LAYOUT ---
        VBox root = new VBox(20, titulo, crearAgendaBox, campos, botones, lista);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1e1e2e;");

        stage.setScene(new Scene(root, 450, 600));
        stage.setTitle("Agenda");
        stage.show();
    }

    // ================= MÉTODOS AUXILIARES =================

    private TextField crearCampo(String texto) {
        TextField campo = new TextField();
        campo.setPromptText(texto);
        campo.setStyle("""
            -fx-background-color: #2a2a3c;
            -fx-text-fill: #e0e0e0;
            -fx-prompt-text-fill: #9aa0a6;
            -fx-border-color: #5dade2;
        """);
        return campo;
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(120);
        boton.setStyle("-fx-background-color: #3949ab; -fx-text-fill: white;");
        return boton;
    }

    private void actualizarLista() {
        lista.getItems().clear();
        for (Contacto c : agenda.getContactos()) {
            lista.getItems().add(
                    c.getNombre() + " " + c.getApellido() + " - " + c.getTelefono()
            );
        }
    }

    private void limpiarCampos(TextField... campos) {
        for (TextField c : campos) c.clear();
    }

    private void alerta(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



