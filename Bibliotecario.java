public class Bibliotecario extends Persona{
    public String seccion;
    public Bibliotecario(){
        super();
        seccion="";
    }
    public Bibliotecario(int i, String n, int e, String s){
        super(i, n, e);
        this.seccion=s;
    }
    
    
    public String getSeccion(){return this.seccion;}
    public void setSeccion(String s){this.seccion=s;}
    
    public boolean getSancionado(){return false;}
    
    public String mostrarInformacion(){
        return super.mostrarInformacion()+" seccion: "+this.seccion;
    }
}