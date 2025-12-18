package model;

public abstract class Persona {
    // ENCAPSULAMIENTO: Atributos privados
    private String nombre;

    // Constructor que inicializa los datos (como en el taller de Reservas)
    public Persona(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters (Requisito de todos tus laboratorios)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método abstracto: define QUÉ debe hacer un hijo,
     * pero no CÓMO. Cada hijo implementará su propia lógica.
     */
    public abstract void mostrarInfo();
}