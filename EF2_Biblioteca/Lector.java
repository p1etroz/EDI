/**
 * Clase que representa a un lector de la biblioteca.
 * Hereda de la clase Persona.
 */
import java.time.LocalDate;

public class Lector extends Persona {
    private LocalDate fechaAlta;
    private boolean penalizado;
    private Prestamo[] prestamos;

    /**
     * Constructor que pone valores por defecto.
     */
    public Lector() {
        super();
        fechaAlta = LocalDate.of(1111, 1, 1);
        penalizado = false;
        this.prestamos = new Prestamo[10];
    }

    /**
     * Constructor con parámetros para crear un lector.
     * @param i El id
     * @param n El nombre
     * @param e La edad
     * @param f Fecha de alta
     * @param p Si está penalizado o no
     */
    public Lector(int i, String n, int e, LocalDate f, boolean p) {
        super(i, n, e);
        this.fechaAlta = f;
        this.penalizado = p;
        this.prestamos = new Prestamo[10];
    }

    public boolean getPenalizado() { return penalizado; }
    public void setPenalizado(boolean p) { this.penalizado = p; }
    
    public LocalDate getFecha() { return fechaAlta; }
    public void setFecha(LocalDate f) { this.fechaAlta = f; }
    
    public Prestamo[] getPrestamos() { return prestamos; }
    public boolean getSancionado() { return this.penalizado; }

    /**
     * Devuelve la información del lector en un String.
     */
    public String mostrarInformacion() {
        return super.mostrarInformacion() + " sancionado: " + getSancionado();
    }

    /**
     * Añade un préstamo al array si hay sitio.
     * @param p El préstamo a añadir
     * @return true si se añadió, false si está lleno
     */
    public boolean anadirPrestamo(Prestamo p) {
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] == null) {
                prestamos[i] = p;
                return true;
            }
        }
        return false;
    }

    /**
     * Cuenta los préstamos que ya se han devuelto.
     * @return numero de devueltos
     */
    public int contarPrestamosDevueltos() {
        int cont = 0;
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] != null) {
                if (prestamos[i].isDevuelto() == true) {
                    cont++;
                }
            }
        }
        return cont;
    }

    /**
     * Cuenta el total de préstamos que tiene el lector (huecos no nulos).
     */
    public int contarPrestamos() {
        int cont = 0;
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] != null) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Mira si el lector tiene algún préstamo pendiente de devolver.
     */
    public boolean prestamosSinDevolver() {
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] != null) {
                if (prestamos[i].isDevuelto() == false) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calcula la media de días de los préstamos.
     */
    public float mediaDias() {
        float media = 0.0f;
        float cont = 0.0f;
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] != null) {
                cont++;
                media = media + (float) prestamos[i].getDias();
            }
        }
        return media / cont;
    }

    /**
     * Suma el total de días de todos los préstamos.
     */
    public int totalDias() {
        int total = 0;
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] != null) {
                total = total + prestamos[i].getDias();
            }
        }
        return total;
    }
}