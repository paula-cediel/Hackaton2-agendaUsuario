package agenda;

import java.util.Arrays;

public class AgendaContactos {

    private Contacto[] contactos;
    private int contador;

    public AgendaContactos() {
        contactos = new Contacto[10];
        contador = 0;
    }

    public AgendaContactos(int tamanio) {
        contactos = new Contacto[tamanio];
        contador = 0;
    }

    public void anadirContacto(Contacto c) {

        if (agendaLlena()) {
            System.out.println("La agenda está llena.");
            return;
        }

        if (c.getNombre().isEmpty() || c.getApellido().isEmpty()) {
            System.out.println("Nombre y apellido obligatorios.");
            return;
        }

        if (existeContacto(c)) {
            System.out.println("El contacto ya existe.");
            return;
        }

        contactos[contador] = c;
        contador++;
        System.out.println("Contacto agregado.");
    }

    public boolean existeContacto(Contacto c) {
        for (int i = 0; i < contador; i++) {
            if (contactos[i].esIgual(c)) {
                return true;
            }
        }
        return false;
    }

    public void eliminarContacto(Contacto c) {

        for (int i = 0; i < contador; i++) {
            if (contactos[i].esIgual(c)) {

                for (int j = i; j < contador - 1; j++) {
                    contactos[j] = contactos[j + 1];
                }

                contactos[contador - 1] = null;
                contador--;

                System.out.println("Contacto eliminado.");
                return;
            }
        }

        System.out.println("El contacto no existe.");
    }

    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {

        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(nombre.trim())
                    && contactos[i].getApellido().equalsIgnoreCase(apellido.trim())) {

                contactos[i].setTelefono(nuevoTelefono);
                System.out.println("Teléfono modificado.");
                return;
            }
        }

        System.out.println("Contacto no encontrado.");
    }

    public boolean agendaLlena() {
        return contador == contactos.length;
    }

    public void espaciosLibres() {
        System.out.println("Espacios libres: " + (contactos.length - contador));
    }

    // MÉTODO CLAVE PARA JAVAFX
    public Contacto[] getContactos() {
        return Arrays.copyOf(contactos, contador);
    }
}
