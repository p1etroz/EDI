import java.time.LocalDate;
public class Prestamo{
    private LocalDate fecha; //Los hago private para que solo se pueda acceder con getters
    private int duracionDias;
    private boolean devuelto;
    private Bibliotecario b;
    
    public Prestamo(){
        this.fecha=LocalDate.of(1111,1,1);
        this.duracionDias=0;
        this.devuelto=false;
    }
    
    public Prestamo(LocalDate f, int dD, boolean d, Bibliotecario bib){
        this.fecha=f;
        this.duracionDias=dD;
        this.devuelto=d;
        this.b=bib;
    }
    
    public LocalDate getFecha(){return this.fecha;}
    public void setFecha(LocalDate f){this.fecha=f;}
    
    public int getDias(){return this.duracionDias;}
    public void setDias(int dD){this.duracionDias=dD;}
    
    public boolean isDevuelto(){return this.devuelto;}
    public void setDevuelto(boolean d){this.devuelto=d;}
    
    public Bibliotecario getBib(){return this.b;}
    public void setBibliotecario(Bibliotecario bib){this.b=bib;}
    
    
    public boolean marcarComoDevuelto(){
        if (!devuelto) {devuelto=true;}
        return devuelto;
    }
    
    public boolean asignarBibliotecario(Bibliotecario b){
        this.b=b;
        return true;
    }
}