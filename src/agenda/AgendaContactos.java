package agenda;

public class AgendaContactos {

    // -------- ATRIBUTOS --------
    private Contacto[] contactos;
    private int contador;

    // -------- CONSTRUCTOR --------
    public AgendaContactos(int cantidad) {
        contactos = new Contacto[10]; // LÍMITE DE 10 CONTACTOS
        contador = 0;
    }

    // -------- AGREGAR CONTACTO --------
    public void anadirContacto(Contacto c) {
        if (agendaLlena() || existeContacto(c)) return;
        contactos[contador] = c;
        contador++;
    }

    // -------- VERIFICAR SI EXISTE --------
    public boolean existeContacto(Contacto c) {
        for (int i = 0; i < contador; i++) {
            if (contactos[i].esIgual(c)) return true;
        }
        return false;
    }

    // -------- ELIMINAR CONTACTO --------
    public void eliminarContacto(Contacto c) {
        for (int i = 0; i < contador; i++) {
            if (contactos[i].esIgual(c)) {
                for (int j = i; j < contador - 1; j++) {
                    contactos[j] = contactos[j + 1];
                }
                contactos[contador - 1] = null;
                contador--;
                return;
            }
        }
    }

    // -------- EDITAR CONTACTO --------
    public void editarContacto(Contacto viejo, Contacto nuevo) {
        eliminarContacto(viejo);
        anadirContacto(nuevo);
    }

    // -------- VERIFICAR SI ESTÁ LLENA --------
    public boolean agendaLlena() {
        return contador == contactos.length;
    }

    // -------- OBTENER CONTACTOS --------
    public Contacto[] getContactos() {
        Contacto[] copia = new Contacto[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = contactos[i];
        }
        return copia;
    }
}
