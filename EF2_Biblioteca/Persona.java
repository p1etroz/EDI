/**
 * Clase abstracta para los datos básicos de una persona.
 * De aquí heredarán Lector y Bibliotecario.
 */
public abstract class Persona {
    protected int id;
    protected String nombre;
    protected int edad;

    /**
     * Constructor por defecto.
     */
    public Persona() {
        this.id = 0;
        this.nombre = "";
        this.edad = 0;
    }

    /**
     * Constructor con parámetros.
     * @param id El identificador
     * @param nombre El nombre completo
     * @param edad La edad
     */
    public Persona(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return this.nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return this.edad; }
    public void setEdad(int edad) { this.edad = edad; }

    /**
     * Devuelve una cadena con los datos de la persona.
     */
    public String mostrarInformacion() {
        return "ID: " + this.id + " nombre: " + this.nombre + " edad: " + this.edad;
    }

    /**
     * Método abstracto para saber si alguien está sancionado.
     */
    public abstract boolean getSancionado();
}