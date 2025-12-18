import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AgendaContactos {

    private ArrayList<Contacto> contactos;
    private int tamañoMaximo;


    public ArrayList<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(ArrayList<Contacto> contacto) {
        this.contactos = contacto;
    }

    public AgendaContactos() {
        this.tamañoMaximo = 10;
        this.contactos = new ArrayList<>();
    }

    public AgendaContactos(int tamañoMaximo) {
        this.tamañoMaximo = tamañoMaximo;
        this.contactos= new ArrayList<>();
    }

    public boolean agendaLlena() {
        return contactos.size() >= tamañoMaximo;
    }

    public int espaciosLibres() {
        return tamañoMaximo - contactos.size();
    }

    public boolean existeContacto(Contacto c) {
        return contactos.contains(c);
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
        contactos.add(c);
        System.out.println("Contacto añadido correctamente.");
    }

    public String listarContactos() {

        if (contactos.isEmpty()) {
            return "La agenda está vacía.";
        }

        Collections.sort(contactos, Comparator
                .comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Contacto::getApellido, String.CASE_INSENSITIVE_ORDER));

        StringBuilder sb = new StringBuilder();

        for (Contacto c : contactos) {
            sb.append(c).append("\n");
        }

        return sb.toString();
    }

    public String buscarContacto(String nombre, String apellido) {

        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre) &&
                    c.getApellido().equalsIgnoreCase(apellido)) {

                return "Teléfono: " + c.getTelefono();
            }
        }

        return "Contacto no encontrado.";
    }

    public String eliminarContacto(Contacto c) {

        if (contactos.remove(c)) {
            return "Contacto eliminado correctamente.";
        } else {
            return "El contacto no existe.";
        }
    }

    public String modificarTelefono(String nombre, String apellido, String nuevoTelefono) {

        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre) &&
                    c.getApellido().equalsIgnoreCase(apellido)) {

                c.setTelefono(nuevoTelefono);
                return "Teléfono modificado correctamente.";
            }
        }

        return "Contacto no encontrado.";
    }


}
