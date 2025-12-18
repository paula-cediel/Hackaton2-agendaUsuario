package agenda;

public class Contacto {

    // -------- ATRIBUTOS --------
    private String nombre;
    private String apellido;
    private String telefono;

    // -------- CONSTRUCTOR --------
    public Contacto(String nombre, String apellido, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    // -------- GETTERS --------
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }

    // -------- SETTERS --------
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // -------- COMPARAR CONTACTOS --------
    public boolean esIgual(Contacto c) {
        return nombre.equalsIgnoreCase(c.nombre)
                && apellido.equalsIgnoreCase(c.apellido);
    }
}

