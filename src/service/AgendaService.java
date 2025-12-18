package service;

import model.Contacto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.control.TextArea;

public class AgendaService {
    private ArrayList<Contacto> lista;
    private int tamanoMaximo;

    public AgendaService() { this(10); } // Tamaño por defecto

    public AgendaService(int tamano) {
        this.lista = new ArrayList<>();
        this.tamanoMaximo = tamano;
    }

    public void agregar(Contacto nuevo) throws Exception {
        if (lista.size() >= tamanoMaximo) throw new Exception("La agenda está llena."); //

        for (Contacto c : lista) {
            // Validación de duplicados sin distinguir mayúsculas
            if (c.getNombre().equalsIgnoreCase(nuevo.getNombre()) &&
                    c.getApellido().equalsIgnoreCase(nuevo.getApellido())) {
                throw new Exception("El contacto ya existe.");
            }
        }
        lista.add(nuevo);
    }

    public void listarContactos(TextArea area) {
        area.clear();
        if (lista.isEmpty()) {
            area.setText("No hay contactos.");
            return;
        }

        // REQUISITO: Ordenar alfabéticamente ignorando mayúsculas
        Collections.sort(lista, Comparator.comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Contacto::getApellido, String.CASE_INSENSITIVE_ORDER));

        area.setText("--- LISTA DE CONTACTOS ---\n");
        for (Contacto c : lista) {
            // FORMATO REQUERIDO: Nombre Apellido - Teléfono
            area.appendText(c.getNombre() + " " + c.getApellido() + " - " + c.getTelefono() + "\n");
        }
    }

    // Método requerido para ver espacio
    public String obtenerEspacioLibre() {
        return "Espacios libres: " + (tamanoMaximo - lista.size());
    }
}