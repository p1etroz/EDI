/**CLase Lector derivada de la clase persona mediante herencia, en esta clase trabajaremos con funciones tales como
 * mostrar si el lector está sancionado de la biblioteca, mostrar la informacion del mismo, añadir un prestamo nuevo,
 * contar cuantos hay y cuantos hay sin devolver, ademas de un metodo opcional añadido por mi mismo para facilitarme el 
 * trabajo de una funcion en Biblioteca.java
 * */
import java.time.LocalDate;
public class Lector extends Persona{
    private LocalDate fechaAlta;
    private boolean penalizado;
    private Prestamo[] prestamos;
    /**
     *Constructor por defecto para inicializar un objeto Lector 
     **/
    public Lector(){
        super();
        fechaAlta=LocalDate.of(1111,1,1);
        penalizado=true;
        this.prestamos=new Prestamo[10];
    }
    
    /**
     * Constructor Parametrizado con los atributos introducidos por cada parametro
     * @param i ID de la persona
     * @param n Nombre de la persona
     * @param e Edad de la persona
     * @param f Fecha de alta del usuario en la biblioteca
     * @param p Dice si el usuario está penalizado debido a la no devolucion de un libro
     **/
    public Lector(int i, String n, int e, LocalDate f, boolean p){
        super(i,n,e);
        this.fechaAlta=f;
        this.penalizado=p;
        this.prestamos=new Prestamo[10];
    }
    
    public boolean getPenalizado(){return penalizado;}
    public void setPenalizado(boolean p){this.penalizado=p;}
    
    public LocalDate getFecha(){return fechaAlta;}
    public void setFecha(LocalDate f){this.fechaAlta=f;}
    
    public Prestamo[] getPrestamos(){return prestamos;}
    
    public boolean getSancionado(){return this.penalizado;}
    
    /**
     * Funcion mostrarInformacion derivada del mostrarInformacion de la clase padre mediante super, además de
     * agregar el getSancionado para mostrar el estado del usuario respecto al uso de las instalaciones
     **/
    
    public String mostrarInformacion(){return super.mostrarInformacion()+" sancionado: "+getSancionado();}
    
    /**
     * Funcion añadirPrestamo donde comprobamos si hay un hueco en el array de prestamos para poder introducir
     * los datos de uno nuevo, en caso de true la introduccion ha sido exitosa y en caso de false el array Prestamos se
     * encuentra completo por lo que ha fallado la introduccion de los datos
     **/
    
    public boolean anadirPrestamo(Prestamo p){
        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i]==null){
               prestamos[i]=p;
               return true;
            }
        }
        return false;
    }
    
    /**
     * Funcion contarPrestamosDevueltos para recorrer el array y comprobar cuantos de estos prestamos han sido devueltos
     **/
    public int contarPrestamosDevueltos(){
        int cont=0;
        for (int i=0;i<prestamos.length;i++){
            if(prestamos[i]!=null){
                if (prestamos[i].isDevuelto()==true){
                    cont++;
                }
            }    
        }
        return cont;
    }
    
    
    /**
    *Funcion para contar cuantos prestamos hay en todo el array, es decir, comprobar que huecos tienen @param null
    **/
    public int contarPrestamos(){
        int cont=0;
        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i]!=null){
                cont++;
            }
        }    
        return cont;
    }
    
    public boolean prestamosSinDevolver(){
        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i]!=null){
                if (prestamos[i].isDevuelto()==false){
                    return true;
                }
            }
        }
        return false;
    }
    
    public float mediaDias(){
        float media=0.0f;
        float cont=0.0f;
        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i]!=null){
                cont++;
                media=media+(float)prestamos[i].getDias();
            }
        }
        return media/cont;
    }
    
    public int totalDias(){
        int total=0;
        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i]!=null){
                total=total+prestamos[i].getDias();
            }
        }
        return total;
    }
}