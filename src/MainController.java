import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController {

    /* =======================
       CAMPOS FXML
       ======================= */

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TextField txtEspaciosLibres;

    @FXML
    private Button btnAgregar;

    /* =======================
       MODELO
       ======================= */

    private AgendaContactos agenda;

    /* =======================
       INICIALIZAR AGENDA
       ======================= */

    @FXML
    private void iniciarAgenda() {

        int capacidad = 10; // valor por defecto

        if (!txtCapacidad.getText().trim().isEmpty()) {
            try {
                capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            } catch (NumberFormatException e) {
                mostrarAlerta(
                        "Error",
                        "La capacidad debe ser un número",
                        Alert.AlertType.ERROR
                );
                return;
            }
        }

        agenda = new AgendaContactos(capacidad);
        txtCapacidad.setEditable(false);

        actualizarEspaciosLibres();
        controlarBotonAgregar();

        mostrarAlerta(
                "Agenda iniciada",
                "Capacidad establecida en: " + capacidad,
                Alert.AlertType.INFORMATION
        );
    }

    /* =======================
       AGREGAR CONTACTO
       ======================= */

    @FXML
    private void agregarContacto() {

        if (!agendaIniciada()) return;

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta(
                    "Error",
                    "Todos los campos son obligatorios",
                    Alert.AlertType.ERROR
            );
            return;
        }

        Contacto contacto = new Contacto(nombre, apellido, telefono);

        agenda.añadirContacto(contacto);

        actualizarEspaciosLibres();
        controlarBotonAgregar();
        limpiarCampos();

        mostrarAlerta(
                "Agenda",
                "Contacto agregado correctamente",
                Alert.AlertType.INFORMATION
        );
    }

    /* =======================
       LISTAR CONTACTOS
       ======================= */

    @FXML
    private void listarContactos() {

        if (!agendaIniciada()) return;

        String resultado = agenda.listarContactos();

        mostrarAlerta(
                "Lista de contactos",
                resultado,
                Alert.AlertType.INFORMATION
        );
    }

    /* =======================
       BUSCAR CONTACTO
       ======================= */

    @FXML
    private void buscarContacto() {

        if (!agendaIniciada()) return;

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty()) {
            mostrarAlerta(
                    "Error",
                    "Ingrese nombre y apellido",
                    Alert.AlertType.ERROR
            );
            return;
        }

        String resultado = agenda.buscarContacto(nombre, apellido);

        mostrarAlerta(
                "Resultado de búsqueda",
                resultado,
                Alert.AlertType.INFORMATION
        );
    }

    /* =======================
       ELIMINAR CONTACTO
       ======================= */

    @FXML
    private void eliminarContacto() {

        if (!agendaIniciada()) return;

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty()) {
            mostrarAlerta(
                    "Error",
                    "Ingrese nombre y apellido",
                    Alert.AlertType.ERROR
            );
            return;
        }

        Contacto contacto = new Contacto(nombre, apellido, "");
        String resultado = agenda.eliminarContacto(contacto);

        actualizarEspaciosLibres();
        controlarBotonAgregar();

        mostrarAlerta(
                "Eliminar contacto",
                resultado,
                Alert.AlertType.INFORMATION
        );
    }

    /* =======================
       MODIFICAR TELÉFONO
       ======================= */

    @FXML
    private void modificarTelefono() {

        if (!agendaIniciada()) return;

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta(
                    "Error",
                    "Complete todos los campos",
                    Alert.AlertType.ERROR
            );
            return;
        }

        String resultado = agenda.modificarTelefono(nombre, apellido, telefono);

        mostrarAlerta(
                "Modificar teléfono",
                resultado,
                Alert.AlertType.INFORMATION
        );
    }

    /* =======================
       MÉTODOS AUXILIARES
       ======================= */

    private boolean agendaIniciada() {
        if (agenda == null) {
            mostrarAlerta(
                    "Error",
                    "Primero debe iniciar la agenda",
                    Alert.AlertType.ERROR
            );
            return false;
        }
        return true;
    }

    private void actualizarEspaciosLibres() {
        if (agenda != null) {
            txtEspaciosLibres.setText(
                    String.valueOf(agenda.espaciosLibres())
            );
        }
    }

    private void controlarBotonAgregar() {
        if (agenda != null) {
            btnAgregar.setDisable(agenda.agendaLlena());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
