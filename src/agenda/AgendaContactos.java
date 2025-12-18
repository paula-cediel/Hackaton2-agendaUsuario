package agenda;

import java.util.Arrays;

public class AgendaContactos {

    // ---------- ATRIBUTOS ----------
    private Contacto[] contactos;
    private int contador;

    // ---------- CONSTRUCTOR ----------
    public AgendaContactos(int tamanio) {
        contactos = new Contacto[tamanio];
        contador = 0;
    }

    // ---------- AGREGAR CONTACTO ----------
    public void anadirContacto(Contacto c) {

        if (agendaLlena()) {
            return;
        }

        if (existeContacto(c)) {
            return;
        }

        contactos[contador] = c;
        contador++;
    }

    // ---------- VERIFICAR CONTACTO ----------
    public boolean existeContacto(Contacto c) {
        for (int i = 0; i < contador; i++) {
            if (contactos[i].esIgual(c)) {
                return true;
            }
        }
        return false;
    }

    // ---------- ELIMINAR CONTACTO ----------
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

    // ---------- EDITAR CONTACTO ----------
    public void editarContacto(Contacto viejo, Contacto nuevo) {
        eliminarContacto(viejo);
        anadirContacto(nuevo);
    }

    // ---------- AGENDA LLENA ----------
    public boolean agendaLlena() {
        return contador == contactos.length;
    }

    // ---------- OBTENER CONTACTOS ----------
    public Contacto[] getContactos() {
        return Arrays.copyOf(contactos, contador);
    }
}

