import java.time.LocalDate;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import java.util.Iterator;
import java.time.temporal.ChronoUnit;

/**
 * Clase principal que gestiona la biblioteca.
 * Contiene las listas de libros, prestamos y el array de personas.
 */
public class Biblioteca {
    boolean cv = true; 
    private final Scanner sc = new Scanner(System.in);

    private Persona[] personas;
    private int totalPersonas = 0;
    private List<Libro> libros;
    private List<Prestamo> prestamos;

    /**
     * Constructor que inicializa las estructuras de datos.
     */
    public Biblioteca() {
        personas = new Persona[25];
        libros = new LinkedList<>();
        prestamos = new ArrayList<>();
    }

    public List<Libro> getLibros() { return this.libros; }
    public List<Prestamo> getPrestamos() { return this.prestamos; }

    /**
     * Busca un libro en la lista usando su ISBN.
     * @param isbn El codigo a buscar
     * @return El libro encontrado o null
     */
    public Libro buscarLibroPorISBN(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equals(isbn)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Añade una persona al array si hay hueco.
     * @param p La persona a añadir
     * @return true si se añadio, false si no hay sitio
     */
    public boolean anadirPersonaArgumentos(Persona p) {
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] == null) {
                personas[i] = p;
                totalPersonas++;
                return true;
            }
        }
        return false;
    }

    /**
     * Busca una persona por su numero de ID.
     * @param b ID a buscar
     * @return La persona o null si no existe
     */
    public Persona buscarPersonaPorId(int b) {
        for (int i = 0; i < totalPersonas; i++) {
            if (personas[i] != null) {
                if (b == personas[i].getId()) {
                    return personas[i];
                }
            }
        }
        return null;
    }

    /**
     * Devuelve cuantas personas hay registradas.
     */
    public int contarPersonas() {
        return totalPersonas;
    }

    /**
     * Registra un nuevo prestamo comprobando todas las condiciones.
     * @param id ID del prestamo
     * @param isbn ISBN del libro
     * @param idLector ID del lector
     * @param fecha Fecha actual
     * @param idBib ID del bibliotecario
     * @return true si se pudo realizar, false si hay algun impedimento
     */
    public boolean registrarPrestamo(int id, String isbn, int idLector, LocalDate fecha, int idBib) {
        Libro lib = buscarLibroPorISBN(isbn);
        if (lib == null) return false;

        Persona checkLec = buscarPersonaPorId(idLector);
        if (checkLec == null || !(checkLec instanceof Lector)) return false;

        Lector l = (Lector) checkLec;
        if (l.getPenalizado()) { return false; }

        Persona aux = buscarPersonaPorId(idBib);
        if (aux == null || !(aux instanceof Bibliotecario)) return false;
        Bibliotecario bib = (Bibliotecario) aux;

        if (l instanceof Investigador) {
            Investigador inv = (Investigador) l;
            int contLibs = 0;
            for (int i = 0; i < inv.getPrestamos().length; i++) {
                if (inv.getPrestamos()[i] != null && !(inv.getPrestamos()[i].isDevuelto())) {
                    contLibs++;
                }
            }
            if (contLibs >= inv.getNumPrestamos()) return false;
            if (!bib.getSeccion().equals("Investigador")) return false;
        }

        if (lib.getStock() == 0) {
            lib.getCola().add(l);
            return false;
        }

        Prestamo p = new Prestamo(fecha, lib.getDiasPrestamo(), false, bib, id, l, lib);
        l.anadirPrestamo(p);
        prestamos.add(p);
        lib.setStock(lib.getStock() - 1);
        return true;
    }

    /**
     * Calcula la edad media de todas las personas.
     */
    public float calcularPromedioEdad() {
        float suma = 0.0f;
        float totalEdades = 0.0f;
        for (int i = 0; i < totalPersonas; i++) {
            suma = suma + (float) personas[i].getEdad();
            totalEdades++;
        }
        return suma / totalEdades;
    }

    /**
     * Cuenta cuantas personas tienen una edad concreta.
     */
    public int contarPorEdad(int e) {
        int cont = 0;
        for (int i = 0; i < totalPersonas; i++) {
            if (personas[i].getEdad() == e) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Calcula la media de dias de prestamo de un lector.
     */
    int calcularMediaDuracion(int id) {
        int suma = 0;
        int total = 0;
        Persona checkLector = buscarPersonaPorId(id);
        Lector l = null;
        if (checkLector instanceof Lector) {
            l = (Lector) checkLector;
            for (int i = 0; i < l.getPrestamos().length; i++) {
                if (l.getPrestamos()[i] != null) {
                    if (!l.getPrestamos()[i].isDevuelto()) { total = Integer.MIN_VALUE; }
                    suma = suma + l.getPrestamos()[i].getDias();
                    total++;
                }
            }
        }
        if (checkLector instanceof Bibliotecario) { return -1; }

        if (total > 0) {
            return suma / total;
        } else return -1;
    }

    /**
     * Cuenta prestamos gestionados por un bibliotecario.
     */
    public long cuantosPrestamos(int id) {
        long prestamosCont = 0;
        Lector l = null;
        Persona aux = null;
        Persona checkBibliotecario = buscarPersonaPorId(id);
        Bibliotecario b = null;
        if (checkBibliotecario instanceof Lector) { return -1; }
        if (checkBibliotecario instanceof Bibliotecario) {
            b = (Bibliotecario) checkBibliotecario;
            for (int i = 0; i < totalPersonas; i++) {
                if (personas[i] != null) {
                    aux = personas[i];
                    if (aux instanceof Lector) {
                        l = (Lector) aux;
                        for (int j = 0; j < l.getPrestamos().length; j++) {
                            if (l.getPrestamos()[j] != null && l.getPrestamos()[j].getBibliotecario() == b) {
                                prestamosCont++;
                            }
                        }
                    }
                }
            }
        }
        return prestamosCont;
    }

    /**
     * Busca el nombre del lector que mas dias ha tenido libros prestados.
     */
    public String nombreMayorDuracion() {
        Persona aux = null;
        Lector masDias = new Lector();
        Lector l = null;
        for (int i = 0; i < totalPersonas; i++) {
            aux = personas[i];
            if (aux instanceof Lector) {
                l = (Lector) aux;
                if (l.totalDias() >= masDias.totalDias()) {
                    masDias = l;
                }
            }
        }
        if (masDias.totalDias() <= 0) { return "Mayor duración: N/A"; }
        return "Mayor duración: " + masDias.getNombre();
    }

    //He borrado el menu porque no hace falta
    public void menu() {
        
    }

    /**
     * Crea un libro nuevo y lo añade a la lista.
     * @param tit Titulo del libro
     * @param autor Autor del libro a crear
     * @param isbn Identificador logico del libro
     * @param stock Numero que determina cuantos quedan a la venta/prestamizacion
     * @param diasP Indica cuantos dias tiene de duracion maxima el prestamo
     * @return true si se añadio, false si el ISBN ya existia
     */
    public boolean anadirLibro(String tit, String autor, String isbn, int stock, int diasP) {
        Libro l = new Libro(tit, autor, isbn, stock, diasP);
        for (Libro lRecorrido : libros) {
            if (lRecorrido.getIsbn().equals(l.getIsbn())) { return false; }
        }
        libros.add(l);
        return true;
    }

    /**
     * Elimina un libro, vacia su cola y cierra sus prestamos.
     * @param isbn ISBN del libro a borrar
     * @return Cuantos prestamos se marcaron como devueltos
     */
    public int eliminarLibro(String isbn) {
        int cont = 0;
        Libro libr = buscarLibroPorISBN(isbn);
        if (libr == null) { return 0; }

        libr.getCola().clear();

        Iterator<Prestamo> it = prestamos.iterator();

        while (it.hasNext()) {
            Prestamo p = it.next();
            if (p != null && p.getLibro().getIsbn().equals(isbn) && p.isDevuelto() == false) {
                p.setDevuelto(true);
                cont++;
            }
        }
        libros.remove(libr);
        return cont;
    }

    /**
     * Gestiona la devolucion de un libro y penaliza si hay retraso.
     * @param idPrestamo Es el localizador logico del prestamo
     * @param fechaDevolucion Fecha en la que se devuelve el libro para comparar con fechaAlta
     * @param idBib ID del bibliotecario que ha realizado el prestamo
     * @return true si la devolucion fue correcta
     */
    public boolean devolverPrestamo(int idPrestamo, LocalDate fechaDevolucion, int idBib) {
        Persona checkBib = buscarPersonaPorId(idBib);
        if (!(checkBib instanceof Bibliotecario) || checkBib == null) { return false; }

        Bibliotecario bib = (Bibliotecario) checkBib;
        Prestamo p = null;

        Iterator<Prestamo> it = prestamos.iterator();

        while (it.hasNext()) {
            Prestamo checkNull = it.next();
            if (checkNull != null && checkNull.getId() == idPrestamo) {
                p = checkNull;
            }
        }

        if (p == null) { return false; }

        long diasRetraso = ChronoUnit.DAYS.between(p.getFecha(), fechaDevolucion);
        Lector l = p.getLector();

        if (diasRetraso > p.getDias()) {
            l.setPenalizado(true);
        }

        p.setDevuelto(true);
        p.getLibro().getLectores().add(l);
        p.getLibro().setStock(p.getLibro().getStock() + 1);

        Libro libr = p.getLibro();

        if (libr.getCola().peek() != null) {
            Lector nuevoLector = libr.getCola().poll();
            registrarPrestamo(prestamos.size() + 1, libr.getIsbn(), nuevoLector.getId(), fechaDevolucion, bib.getId());
        }

        return true;
    }

    /**
     * Quita la sancion a un lector.
     * @param ID del lector al que le vas a quitar la sancion
     * @return false en caso de que no este penalizado, no sea un lector o no se encuentre mediante la id
     */
    public boolean desSancionarPersona(int id) {
        Persona checkSanc = buscarPersonaPorId(id);
        if (checkSanc instanceof Lector) {
            Lector lQuitarSancion = (Lector) checkSanc;
            if (lQuitarSancion.getPenalizado() == true) {
                lQuitarSancion.setPenalizado(false);
                return true;
            } else { return false; }
        }
        return false;
    }

    /**
     * Cuenta prestamos de un bibliotecario en una fecha especifica.
     * @return Integer number that represents prestamos realizados por x bib 
     * en base a una fecha especifica
     */
    public int contarPrestamosDadasUnaFechaConBibliotecario(LocalDate fecha, int idBib) {
        Persona bibPers = buscarPersonaPorId(idBib);
        int contarPrest = 0;
        if (bibPers instanceof Bibliotecario) {
            Bibliotecario bib = (Bibliotecario) bibPers;
            Iterator<Prestamo> it = prestamos.iterator();
            while (it.hasNext()) {
                Prestamo prest = it.next();
                if (prest.getBibliotecario().equals(bib) && prest.getFecha().equals(fecha)) {
                    contarPrest++;
                }
            }
        }
        return contarPrest;
    }

    /**
     * Cuenta libros con duracion superior a la dada los cuales tengan sus lectores penalizados
     * @param duracion Duracion la cual debe tener mayor el libro para entrar en el filtro
     * @return Contador de los libros
     */
    public int contarLibrosMayorDuracioDadaConSusLectoresPenalizados(int duracion){
        int cont = 0;
        Iterator<Libro> it = libros.iterator();
        while(it.hasNext()){
            Libro libr = it.next();
            if(libr.getDiasPrestamo() > duracion){
                List <Lector> listaLibro = libr.getLectores();
                Iterator<Lector> it2 = listaLibro.iterator();
                while(it2.hasNext()){
                    Lector l = it2.next();
                    if(l != null && l.getPenalizado()){
                        cont++;
                    }
                }
            }
        }
        return cont;
    }

    /**
     * Funcion para ver que libro tiene la cola mas larga
     * @return Objeto libro con la mayor Queue
     */
    public Libro libroConMasColaEspera() {
        Iterator<Libro> it = libros.iterator();
        Libro libMayorCola = new Libro();
        while (it.hasNext()) {
            Libro aux = it.next();
            if (libMayorCola.getCola().size() < aux.getCola().size()) {
                libMayorCola = aux;
            }
        }
        return libMayorCola;
    }

    /**
     * Busca el que actualmente tiene mas prestamos
     * Looks after the book with most loans
     * @return Object book with the highest ammount of loans
     */
    public Libro libroConMasPrestamos() {
        Iterator<Libro> it = libros.iterator();
        Libro masPrest = null;
        int maxPrestamos = -1;
        while (it.hasNext()) {
            int contActual = 0;
            Libro l = it.next();
            if (l != null) {
                
                //En esta funcion he empleado el for-each porque solamente necesitamos
                //leer la lista y comparar datos sin la necesidad de modificar los de esta
                //misma por lo que es mas comodo y rapido usar un for-each
                //(El uso del for-each me lo han explicado compañeros de años anteriores por curiosidad mia)
                for (Prestamo p : prestamos) {
                    if (p != null && p.getLibro() != null && p.getLibro().equals(l)) {
                        contActual++;
                    }
                }
                if (contActual > maxPrestamos) {
                    maxPrestamos = contActual;
                    masPrest = l;
                }
            }
        }
        return masPrest;
    }
}