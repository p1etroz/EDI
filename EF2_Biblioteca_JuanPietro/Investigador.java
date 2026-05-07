import java.time.LocalDate;
public class Investigador extends Lector{
    protected int numPrestamos = 0;
    public Investigador(){
        super();
        int numPrestamos = 0;
    }
    public Investigador(int id, String nombre, int edad, LocalDate fAlta, boolean pen, int nPrest){
        super(id, nombre, edad, fAlta, pen);
        this.numPrestamos = nPrest;
    }

    
}