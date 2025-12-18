package model;

public class Contacto extends Persona {
    private String apellido;
    private String telefono;

    public Contacto(String nombre, String apellido, String telefono) {
        super(nombre);
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }

    @Override
    public void mostrarInfo() {
        System.out.println(getNombre() + " " + apellido);
    }
}