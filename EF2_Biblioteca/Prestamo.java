import java.time.LocalDate;

/**
 * Clase que gestiona los prestamos de libros.
 * Registra quien lo presta, quien lo recibe y si ya se devolvió.
 */
public class Prestamo {
    private LocalDate fecha; 
    private int duracionDias;
    private boolean devuelto;
    private Bibliotecario b;
    private int id;
    private Lector l;
    private Libro lib;

    /**
     * Constructor vacio con valores por defecto.
     */
    public Prestamo() {
        this.fecha = LocalDate.of(1111, 1, 1);
        this.duracionDias = 0;
        this.devuelto = false;
        this.id = 0;
        this.l = null;
        this.lib = null;
    }

    /**
     * Constructor completo para crear un prestamo.
     * @param f Fecha del prestamo
     * @param dD Cuantos dias dura
     * @param d Estado de devolucion
     * @param bib Bibliotecario que lo gestiona
     * @param id ID del prestamo
     * @param lec Lector que se lleva el libro
     * @param libr Libro prestado
     */
    public Prestamo(LocalDate f, int dD, boolean d, Bibliotecario bib, int id, Lector lec, Libro libr) {
        this.fecha = f;
        this.duracionDias = dD;
        this.devuelto = d;
        this.b = bib;
        this.id = id;
        this.l = lec;
        this.lib = libr;
    }

    public LocalDate getFecha() { return this.fecha; }
    public void setFecha(LocalDate f) { this.fecha = f; }
    
    public int getDias() { return this.duracionDias; }
    public void setDias(int dD) { this.duracionDias = dD; }
    
    public boolean isDevuelto() { return this.devuelto; }
    public void setDevuelto(boolean d) { this.devuelto = d; }
    
    public Bibliotecario getBibliotecario() { return this.b; }
    public void setBibliotecario(Bibliotecario bib) { this.b = bib; }
    
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    
    public Lector getLector() { return this.l; }
    public void setLector(Lector lec) { this.l = lec; }
    
    public Libro getLibro() { return this.lib; }
    public void setLibro(Libro l) { this.lib = l; }

    /**
     * Cambia el estado a devuelto si no lo estaba ya.
     * @return true si se ha podido marcar, false si ya estaba devuelto
     */
    public boolean marcarComoDevuelto() {
        if (!devuelto) {
            devuelto = true;
            return true;
        }
        return false;        
    }

    /**
     * Asigna un bibliotecario al prestamo.
     */
    public boolean asignarBibliotecario(Bibliotecario b) {
        this.b = b;
        return true;
    }

    /**
     * Compara si dos prestamos son iguales usando el ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestamo)) return false;
        Prestamo p = (Prestamo) o;
        return this.id == p.id;
    }

    /**
     * Devuelve los datos basicos del prestamo en texto.
     */
    @Override
    public String toString() {
        return "ID: " + id + " fecha: " + fecha + " dias: " + duracionDias + " devuelto: " + devuelto;
    }
}