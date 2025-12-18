package agenda;

import java.util.Arrays;

public class AgendaContactos {

    private Contacto[] contactos;
    private int contador;

    // Constructor por defecto (10 contactos)
    public AgendaContactos() {
        this.contactos = new Contacto[10];
        this.contador = 0;
    }

    // Constructor con tamaño definido
    public AgendaContactos(int tamanio) {
        this.contactos = new Contacto[tamanio];
        this.contador = 0;
    }

    public void anadirContacto(Contacto c) {

        if (agendaLlena()) {
            System.out.println("La agenda está llena.");
            return;
        }

        if (c.getNombre().isEmpty() || c.getApellido().isEmpty()) {
            System.out.println("El nombre y apellido no pueden estar vacíos.");
            return;
        }

        if (existeContacto(c)) {
            System.out.println("El contacto ya existe.");
            return;
        }

        contactos[contador] = c;
        contador++;
        System.out.println("Contacto agregado correctamente.");
    }

    public boolean existeContacto(Contacto c) {
        for (int i = 0; i < contador; i++) {
            if (contactos[i].esIgual(c)) {
                return true;
            }
        }
        return false;
    }

    public void listarContactos() {

        if (contador == 0) {
            System.out.println("📭 La agenda está vacía.");
            return;
        }

        // Copia para ordenar
        Contacto[] copia = Arrays.copyOf(contactos, contador);

        // Ordenar por nombre y apellido (burbuja simple)
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = i + 1; j < copia.length; j++) {
                String actual = copia[i].getNombre() + copia[i].getApellido();
                String siguiente = copia[j].getNombre() + copia[j].getApellido();

                if (actual.compareToIgnoreCase(siguiente) > 0) {
                    Contacto temp = copia[i];
                    copia[i] = copia[j];
                    copia[j] = temp;
                }
            }
        }

        for (Contacto c : copia) {
            System.out.println(c.getNombre() + " " + c.getApellido()
                    + " - " + c.getTelefono());
        }
    }

    public void buscaContacto(String nombre, String apellido) {
        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(nombre)
                    && contactos[i].getApellido().equalsIgnoreCase(apellido)) {

                System.out.println("Teléfono: " + contactos[i].getTelefono());
                return;
            }
        }
        System.out.println("Contacto no encontrado.");
    }

    public void eliminarContacto(Contacto c) {

        for (int i = 0; i < contador; i++) {
            if (contactos[i].esIgual(c)) {

                for (int j = i; j < contador - 1; j++) {
                    contactos[j] = contactos[j + 1];
                }

                contactos[contador - 1] = null;
                contador--;

                System.out.println("✅ Contacto eliminado.");
                return;
            }
        }

        System.out.println("El contacto no existe.");
    }

    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {

        for (int i = 0; i < contador; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(nombre)
                    && contactos[i].getApellido().equalsIgnoreCase(apellido)) {

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
        int libres = contactos.length - contador;
        System.out.println("Espacios libres: " + libres);
    }
}

