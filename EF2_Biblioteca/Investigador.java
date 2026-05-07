import java.time.LocalDate;

/**
 * Clase para representar a un investigador.
 * Es un tipo especial de Lector que tiene un contador de prestamos.
 */
public class Investigador extends Lector {
    private int numPrestamos = 0;

    /**
     * Constructor por defecto de investigador.
     */
    public Investigador() {
        super();
        this.numPrestamos = 0;
    }

    /**
     * Constructor con todos los parametros.
     * @param id ID del investigador
     * @param nombre Nombre del investigador
     * @param edad Edad
     * @param fAlta Fecha de alta en el sistema
     * @param pen Si esta penalizado
     * @param nPrest Numero de prestamos maximos a recibir
     */
    public Investigador(int id, String nombre, int edad, LocalDate fAlta, boolean pen, int nPrest) {
        super(id, nombre, edad, fAlta, pen);
        this.numPrestamos = nPrest;
    }

    public int getNumPrestamos() { return this.numPrestamos; }
    public void setNumPrestamos(int nPrest) { this.numPrestamos = nPrest; }

    /**
     * Compara investigadores usando el ID de la persona.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Investigador)) return false;
        Investigador inv = (Investigador) o;
        return this.getId() == inv.getId();
    }

    /**
     * Llama al mostrarInformacion del padre y añade el numero de prestamos.
     */
    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion() + " numPrestamos: " + numPrestamos;
    }

    /**
     * Devuelve la informacion en formato String.
     */
    @Override
    public String toString() {
        return mostrarInformacion();
    }
}