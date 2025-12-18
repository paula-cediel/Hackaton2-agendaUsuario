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

    // ---------- ATRIBUTOS ----------
    private AgendaContactos agenda;
    private ListView<String> lista = new ListView<>();

    @Override
    public void start(Stage stage) {

        // ---------- TÍTULO ----------
        Label titulo = new Label("AGENDA DE CONTACTOS");
        titulo.setFont(Font.font(20));
        titulo.setStyle("-fx-text-fill: #e0e0e0;");

        // ---------- CREAR AGENDA ----------
        Label lblTamanio = new Label("Número de contactos:");
        lblTamanio.setStyle("-fx-text-fill: #e0e0e0;");

        TextField txtTamanio = new TextField();
        txtTamanio.setPromptText("Cantidad máxima");

        Button btnCrearAgenda = crearBoton("Crear Agenda");

        VBox crearAgendaBox = new VBox(5, lblTamanio, txtTamanio, btnCrearAgenda);
        crearAgendaBox.setAlignment(Pos.CENTER);

        // ---------- CAMPOS DE CONTACTO ----------
        TextField txtNombre = crearCampo("Nombre");
        TextField txtApellido = crearCampo("Apellido");
        TextField txtTelefono = crearCampo("Teléfono");

        VBox campos = new VBox(10, txtNombre, txtApellido, txtTelefono);
        campos.setAlignment(Pos.CENTER);

        // ---------- BOTONES ----------
        Button btnAgregar = crearBoton("Agregar");
        Button btnEditar = crearBoton("Editar");
        Button btnEliminar = crearBoton("Eliminar");

        HBox botones = new HBox(10, btnAgregar, btnEditar, btnEliminar);
        botones.setAlignment(Pos.CENTER);

        // ---------- ACCIONES ----------

        // Crear agenda
        btnCrearAgenda.setOnAction(e -> {
            int tamanio = Integer.parseInt(txtTamanio.getText());
            agenda = new AgendaContactos(tamanio);
            mostrarAlerta("Correcto", "Agenda creada con " + tamanio + " contactos.");
            txtTamanio.setDisable(true);
            btnCrearAgenda.setDisable(true);
        });

        // Agregar contacto
        btnAgregar.setOnAction(e -> {

            if (agenda.agendaLlena()) {
                mostrarAlerta("Aviso", "Agenda llena.");
                return;
            }

            Contacto c = new Contacto(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText()
            );

            agenda.anadirContacto(c);
            actualizarLista();
        });

        // Editar contacto
        btnEditar.setOnAction(e -> {

            String seleccionado = lista.getSelectionModel().getSelectedItem();
            if (seleccionado == null) return;

            String[] partes = seleccionado.split(" - ");
            String[] nombreViejo = partes[0].split(" ");

            Contacto viejo = new Contacto(nombreViejo[0], nombreViejo[1], "");
            Contacto nuevo = new Contacto(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText()
            );

            agenda.editarContacto(viejo, nuevo);
            actualizarLista();
        });

        // Eliminar contacto
        btnEliminar.setOnAction(e -> {

            String seleccionado = lista.getSelectionModel().getSelectedItem();
            if (seleccionado == null) return;

            String[] partes = seleccionado.split(" - ");
            String[] nombre = partes[0].split(" ");

            Contacto c = new Contacto(nombre[0], nombre[1], "");
            agenda.eliminarContacto(c);
            actualizarLista();
        });

        // ---------- LAYOUT PRINCIPAL ----------
        VBox root = new VBox(20, titulo, crearAgendaBox, campos, botones, lista);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1e1e2e;");

        Scene scene = new Scene(root, 450, 600);
        stage.setTitle("Agenda");
        stage.setScene(scene);
        stage.show();
    }

    // ---------- MÉTODOS AUXILIARES ----------
    private TextField crearCampo(String texto) {
        TextField campo = new TextField();
        campo.setPromptText(texto);
        return campo;
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setPrefWidth(120);
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        launch(args);
    }
}


