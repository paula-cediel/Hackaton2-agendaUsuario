package agenda;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;

public class AgendaFX extends Application {

    // -------- ATRIBUTOS --------
    private AgendaContactos agenda = null;
    private ListView<String> lista = new ListView<>();

    @Override
    public void start(Stage stage) {

        // -------- TÍTULO --------
        Label titulo = new Label("AGENDA DE CONTACTOS");
        titulo.setFont(Font.font(20));
        titulo.setStyle("-fx-text-fill: #e0e0e0;");

        // =====================================================
        // -------- SECCIÓN: CREAR AGENDA ----------------------
        // =====================================================

        Label lblCantidad = new Label("Número de contactos (máx. 10):");
        lblCantidad.setStyle("-fx-text-fill: #e0e0e0;");

        TextField txtCantidad = crearCampo("Ej: 5");

        Button btnCrearAgenda = crearBoton("Crear Agenda");

        VBox crearAgendaBox = new VBox(5, lblCantidad, txtCantidad, btnCrearAgenda);
        crearAgendaBox.setAlignment(Pos.CENTER);

        // -------- ACCIÓN CREAR AGENDA --------
        btnCrearAgenda.setOnAction(e -> {

            if (txtCantidad.getText().isEmpty()) {
                alerta("Error", "Debe ingresar un número.");
                return;
            }

            int cantidad;

            try {
                cantidad = Integer.parseInt(txtCantidad.getText());
            } catch (NumberFormatException ex) {
                alerta("Error", "Debe ingresar solo números.");
                return;
            }

            if (cantidad <= 0) {
                alerta("Error", "El número debe ser mayor que cero.");
                return;
            }

            if (cantidad > 10) {
                alerta("Error", "No se pueden agregar más de 10 contactos.");
                return;
            }

            agenda = new AgendaContactos(cantidad);

            alerta("Correcto", "Agenda creada con " + cantidad + " contactos.");

            txtCantidad.setDisable(true);
            btnCrearAgenda.setDisable(true);
        });


        // -------- SECCIÓN: CAMPOS DE CONTACTO ----------------


        TextField txtNombre = crearCampo("Nombre");
        TextField txtApellido = crearCampo("Apellido");
        TextField txtTelefono = crearCampo("Teléfono (10 dígitos)");

        // -------- VALIDACIÓN TELÉFONO --------
        txtTelefono.textProperty().addListener((obs, oldText, newText) -> {

            // Solo números
            if (!newText.matches("\\d*")) {
                txtTelefono.setText(newText.replaceAll("[^\\d]", ""));
            }

            // Máximo 10 dígitos
            if (txtTelefono.getText().length() > 10) {
                txtTelefono.setText(txtTelefono.getText().substring(0, 10));
            }
        });

        VBox campos = new VBox(10, txtNombre, txtApellido, txtTelefono);
        campos.setAlignment(Pos.CENTER);


        // -------- SECCIÓN: BOTONES ---------------------------

        Button btnAgregar = crearBoton("Agregar");
        Button btnEditar = crearBoton("Editar");
        Button btnEliminar = crearBoton("Eliminar");

        HBox botones = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        botones.setAlignment(Pos.CENTER);

        // =====================================================
        // -------- SECCIÓN: LISTA -----------------------------
        // =====================================================

        lista.setStyle("""
            -fx-control-inner-background: #2a2a3c;
            -fx-background-color: #2a2a3c;
            -fx-text-fill: #e0e0e0;
            -fx-border-color: #5dade2;
        """);

        // -------- ACCIONES DE BOTONES ------------------------


        // -------- AGREGAR --------
        btnAgregar.setOnAction(e -> {

            if (agenda == null) {
                alerta("Error", "Primero debe crear la agenda.");
                return;
            }

            if (agenda.agendaLlena()) {
                alerta("Aviso", "Ya se alcanzó el límite de contactos.");
                return;
            }

            if (txtNombre.getText().isEmpty()
                    || txtApellido.getText().isEmpty()
                    || txtTelefono.getText().isEmpty()) {

                alerta("Error", "Debe llenar todos los campos.");
                return;
            }

            if (txtTelefono.getText().length() != 10) {
                alerta("Error", "El teléfono debe tener exactamente 10 dígitos.");
                return;
            }

            agenda.anadirContacto(new Contacto(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText()
            ));

            alerta("Correcto", "Contacto agregado.");
            limpiarCampos(txtNombre, txtApellido, txtTelefono);
            actualizarLista();
        });

        // -------- EDITAR --------
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

            if (txtTelefono.getText().length() != 10) {
                alerta("Error", "Teléfono inválido.");
                return;
            }

            String[] partes = seleccionado.split(" - ");
            String[] nombreAnterior = partes[0].split(" ");

            agenda.editarContacto(
                    new Contacto(nombreAnterior[0], nombreAnterior[1], ""),
                    new Contacto(
                            txtNombre.getText(),
                            txtApellido.getText(),
                            txtTelefono.getText()
                    )
            );

            alerta("Correcto", "Contacto editado.");
            limpiarCampos(txtNombre, txtApellido, txtTelefono);
            actualizarLista();
        });

        // -------- ELIMINAR --------
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

            String[] partes = seleccionado.split(" - ");
            String[] nombre = partes[0].split(" ");

            agenda.eliminarContacto(new Contacto(nombre[0], nombre[1], ""));
            alerta("Correcto", "Contacto eliminado.");
            actualizarLista();
        });

        // =====================================================
        // -------- LAYOUT PRINCIPAL ---------------------------
        // =====================================================

        VBox root = new VBox(
                20,
                titulo,
                crearAgendaBox,
                campos,
                botones,
                lista
        );

        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1e1e2e;");

        stage.setScene(new Scene(root, 450, 620));
        stage.setTitle("Agenda");
        stage.show();
    }

    // =====================================================
    // -------- MÉTODOS AUXILIARES -------------------------
    // =====================================================

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

        Contacto[] contactos = agenda.getContactos();

        Arrays.sort(contactos, (c1, c2) ->
                (c1.getNombre() + c1.getApellido())
                        .compareToIgnoreCase(c2.getNombre() + c2.getApellido())
        );

        for (Contacto c : contactos) {
            lista.getItems().add(
                    c.getNombre() + " " + c.getApellido()
                            + " - " + c.getTelefono()
            );
        }
    }

    private void limpiarCampos(TextField... campos) {
        for (TextField c : campos) {
            c.clear();
        }
    }

    private void alerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
