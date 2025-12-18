package agenda;

public class Contacto {

    private String nombre;
    private String apellido;
    private String telefono;

    public Contacto(String nombre, String apellido, String telefono) {
        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
        this.telefono = telefono.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono.trim();
    }

    // Compara dos contactos por nombre y apellido
    public boolean esIgual(Contacto c) {
        if (c == null) {
            return false;
        }

        return nombre.equalsIgnoreCase(c.nombre)
                && apellido.equalsIgnoreCase(c.apellido);
    }
}
