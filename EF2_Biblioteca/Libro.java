import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;

/**
 * Clase que representa un libro de la biblioteca.
 * Guarda sus datos y las listas de lectores que lo tienen o lo esperan.
 */
public class Libro {
    private String titulo;
    private String autor;
    private String isbn;
    private int stock;
    private int diasPrestamo;
    private List<Lector> lectores;
    private Queue<Lector> cola;

    /**
     * Constructor por defecto.
     * Inicializa las listas de lectores y la cola de espera.
     */
    public Libro() {
        this.titulo = "";
        this.autor = "";
        this.isbn = "";
        this.stock = 0;
        this.diasPrestamo = 0;
        this.lectores = new ArrayList<>();
        this.cola = new LinkedList<>();
    }

    /**
     * Constructor con los datos principales del libro.
     * @param tit Titulo del libro
     * @param aut Autor del libro
     * @param isbn Codigo ISBN unico
     * @param stk Cantidad disponible
     * @param dPrest Dias que se puede prestar
     */
    public Libro(String tit, String aut, String isbn, int stk, int dPrest) {
        this.titulo = tit;
        this.autor = aut;
        this.isbn = isbn;
        this.stock = stk;
        this.diasPrestamo = dPrest;
        this.lectores = new ArrayList<>();
        this.cola = new LinkedList<>();
    }

    public String getTitulo() { return this.titulo; }
    public void setTitulo(String t) { this.titulo = t; }

    public String getAutor() { return this.autor; }
    public void setAutor(String a) { this.autor = a; }

    public String getIsbn() { return this.isbn; }
    public void setIsbn(String i) { this.isbn = i; }

    public int getStock() { return this.stock; }
    public void setStock(int s) { this.stock = s; }

    public int getDiasPrestamo() { return this.diasPrestamo; }
    public void setDiasPrestamo(int d) { this.diasPrestamo = d; }

    public List<Lector> getLectores() { return this.lectores; }
    public Queue<Lector> getCola() { return this.cola; }

    /**
     * Compara si dos libros son iguales usando el ISBN.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro l = (Libro) o;
        return this.isbn.equals(l.isbn);
    }

    /**
     * Devuelve una cadena con la informacion del libro.
     */
    public String mostrarInformacion() {
        return "ISBN: " + isbn + " titulo: " + titulo + " autor: " + autor + " stock: " + stock;
    }

    /**
     * Metodo toString que usa mostrarInformacion.
     */
    @Override
    public String toString() {
        return mostrarInformacion();
    }
}