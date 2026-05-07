/**Clase abstracta que define los atributos basicos de una persona 
 * que posteriormente será definida como un Lector o un bibliotecario
 */
public abstract class Persona{
    protected int id;
    protected String nombre;
    protected int edad;
    
    /**Constructor por defecto que genera una persona por defecto*/
    public Persona(){
        this.id=0;
        this.nombre="";
        this.edad=0;
    }
    
    public Persona(int id, String nombre, int edad){
        this.id=id;
        this.nombre=nombre;
        this.edad=edad;
    }
    public int getId(){return this.id;}
    public void setId(int id){this.id=id;}
    
    public String getNombre(){return this.nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
    
    public int getEdad(){return this.edad;}
    public void setEdad(int edad){this.edad=edad;}
    
    public String mostrarInformacion(){return "ID: "+this.id+" nombre: "+this.nombre+" edad: "+this.edad;}
    
    public abstract boolean getSancionado();
    
    
}