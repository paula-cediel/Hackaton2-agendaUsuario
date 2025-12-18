import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AgendaContactos {

    private ArrayList<Contacto> contacto;
    private int tamañoMaximo;

    public AgendaContactos() {
        this.tamañoMaximo = 10;
        this.contacto = new ArrayList<>();
    }

    public AgendaContactos(int tamañoMaximo) {
        this.tamañoMaximo = tamañoMaximo;
        this.contacto = new ArrayList<>();
    }

    public boolean agendaLlena() {
        return contacto.size() >= tamañoMaximo;
    }

    public int espaciosLibres() {
        return tamañoMaximo - contacto.size();
    }

    public boolean existeContacto(Contacto c) {
        return contacto.contains(c);
    }

    public void añadirContacto(Contacto c) {
        if (agendaLlena()) {
            System.out.println("La agenda está llena.");
            return;
        }
        if (existeContacto(c)) {
            System.out.println("El contacto ya existe.");
            return;
        }
        contacto.add(c);
        System.out.println("Contacto añadido correctamente.");
    }

    public void listarContactos() {
        if (contacto.isEmpty()) {
            System.out.println("La agenda está vacía.");
            return;
        }

        Collections.sort(contacto, Comparator
                .comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Contacto::getApellido, String.CASE_INSENSITIVE_ORDER));

        for (Contacto c : contacto) {
            System.out.println(c);
        }
    }

    public void buscarContacto(String nombre, String apellido) {
        for (Contacto c : contacto) {
            if (c.getNombre().equalsIgnoreCase(nombre) &&
                    c.getApellido().equalsIgnoreCase(apellido)) {
                System.out.println("Teléfono: " + c.getTelefono());
                return;
            }
        }
        System.out.println("Contacto no encontrado.");
    }

    public void eliminarContacto(Contacto c) {
        if (contacto.remove(c)) {
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        for (Contacto c : contacto) {
            if (c.getNombre().equalsIgnoreCase(nombre) &&
                    c.getApellido().equalsIgnoreCase(apellido)) {
                c.setTelefono(nuevoTelefono);
                System.out.println("Teléfono modificado correctamente.");
                return;
            }
        }
        System.out.println("Contacto no encontrado.");
    }
}
