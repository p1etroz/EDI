/**
 * Clase que representa al personal de la biblioteca.
 * Hereda de Persona.
 */
public class Bibliotecario extends Persona {
    private String seccion;

    /**
     * Constructor por defecto.
     */
    public Bibliotecario() {
        super();
        seccion = "";
    }

    /**
     * Constructor para crear un bibliotecario con datos.
     * @param i ID del empleado
     * @param n Nombre
     * @param e Edad
     * @param s Sección en la que trabaja
     */
    public Bibliotecario(int i, String n, int e, String s) {
        super(i, n, e);
        this.seccion = s;
    }

    public String getSeccion() { return this.seccion; }
    public void setSeccion(String s) { this.seccion = s; }

    /**
     * Los bibliotecarios nunca están sancionados.
     * @return siempre devuelve false
     */
    public boolean getSancionado() { return false; }

    /**
     * Muestra los datos del bibliotecario y su sección.
     */
    public String mostrarInformacion() {
        return super.mostrarInformacion() + " seccion: " + this.seccion;
    }
}