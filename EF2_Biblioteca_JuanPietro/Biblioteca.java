// ==========================
// CLASE PRINCIPAL: BIBLIOTECA
// ==========================

import java.time.LocalDate;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import java.util.Iterator;
import java.time.temporal.ChronoUnit;

public class Biblioteca {
    boolean cv = true; // Variable de control para el menú. True cuando se suba VPL y false para pruebas locales
    private final Scanner sc = new Scanner(System.in);

    //TODO 
    // agregar atributos necesarios para almacenar personas y constructores
    private Persona[] personas;
    private int totalPersonas=0;
    private List<Libro> libros;
    private List<Prestamo> prestamos;
    
    
    
    
    public Biblioteca(){
        personas=new Persona[25];
        libros = new LinkedList<>();
        prestamos = new ArrayList<>();
    }
    
    public List<Libro> getLibros(){ return this.libros; }
    public List<Prestamo> getPrestamos(){ return this.prestamos; }

    //TODO
    //Agrear métodos necesarios para gestionar la biblioteca (añadir persona, buscar por ID, contar personas, registrar préstamo, etc.)
    //En esta funcion usamos el for each porque no nos hace falta modificar el contenido
    //de la lista de libros, solamente tenemos que buscar concidencias mediante el ISBN
    public Libro buscarLibroPorISBN(String isbn){
        for(Libro l : libros){
            if(l.getIsbn().equals(isbn)){
                return l;
            }
        }
        return null;
    }
    // =====================================================        
    // (1) AÑADIR PERSONA 
    // =====================================================
    public boolean anadirPersonaArgumentos(Persona p) {
        for(int i=0;i<personas.length;i++){
            if(personas[i]==null){
                personas[i] = p;
                totalPersonas++;
                return true;
            }
        }
        return false;
    }
    // =====================================================
    // (2) BuscarPersonaPorID
    // =====================================================
    public Persona buscarPersonaPorId(int b){
        for (int i=0;i<totalPersonas;i++){
            if (personas[i]!=null){  //Comprobacion de si null para evitar nullPointer
               if(b==personas[i].getId()){
                return personas[i];
               }
            }
        }
        return null;
    }

    // =====================================================
    // (3) Contar personas
    // =====================================================
    public int contarPersonas(){
        return totalPersonas;
    }
    // =====================================================
    // (4) Registrar préstamo
    // =====================================================
    public boolean registrarPrestamo(int id, String isbn, int idLector, LocalDate fecha, int idBib){
    Libro lib = buscarLibroPorISBN(isbn);
    if(lib == null) return false;
    
    Persona checkLec = buscarPersonaPorId(idLector);
    if(checkLec == null || !(checkLec instanceof Lector)) return false;
    
    Lector l = (Lector) checkLec;
    if(l.getPenalizado()) {return false;}
    
    Persona aux = buscarPersonaPorId(idBib);
    if(aux == null || !(aux instanceof Bibliotecario)) return false;
    Bibliotecario bib = (Bibliotecario) aux;
    
    if(l instanceof Investigador){
        Investigador inv = (Investigador) l;
        int contLibs = 0;
        for(int i=0; i<inv.getPrestamos().length;i++){
            if(inv.getPrestamos()[i] != null && !(inv.getPrestamos()[i].isDevuelto())){
                contLibs++;
            }
        }
        if(contLibs >= inv.getNumPrestamos()) return false;
        if(!bib.getSeccion().equals("Investigador")) return false;
    }
    
    if(lib.getStock() == 0){
        lib.getCola().add(l);
        return false;
    }
    
    Prestamo p = new Prestamo(fecha, lib.getDiasPrestamo(), false, bib, id, l, lib);
    l.anadirPrestamo(p);
    prestamos.add(p);
    lib.setStock(lib.getStock() - 1);
    return true;
}
    // =====================================================
    // (5) Calcular promedio de edad
    // =====================================================
    public float calcularPromedioEdad(){
        float suma=0.0f;
        float totalEdades=0.0f;
        for (int i=0;i<totalPersonas;i++){
            suma=suma+(float) personas[i].getEdad();
            totalEdades++;
        }
        return suma/totalEdades;
    }
    // =====================================================
    // (6) Contar por edad
    // =====================================================
    public int contarPorEdad(int e){
        int cont=0;
        for (int i=0;i<totalPersonas;i++){
            if(personas[i].getEdad()==e){
                cont++;
            }
        }
        return cont;
    }
    // =====================================================
    // (7) Media duración de préstamos de un lector
    // =====================================================
    int calcularMediaDuracion(int id){
        int suma=0;
        int total=0;
        Persona checkLector = buscarPersonaPorId(id);
        Lector l=null;
        if (checkLector instanceof Lector){
            l=(Lector) checkLector;
                for (int i=0;i<l.getPrestamos().length;i++){
                    if (l.getPrestamos()[i]!=null){
                        if(!l.getPrestamos()[i].isDevuelto()){total=Integer.MIN_VALUE;}
                        suma=suma+l.getPrestamos()[i].getDias();
                        total++;
                    }
                }
            }
        if (checkLector instanceof Bibliotecario){return -1;}    
        
        if(total>0){
        return suma/total;
        }
        else return -1;
    }
    // =====================================================
    // (8) Prestamos gestionados por un bibliotecario
    // =====================================================
    public long cuantosPrestamos(int id){
        long prestamos=0;
        Lector l = null;
        Persona aux=null;
        Persona checkBibliotecario = buscarPersonaPorId(id);
        Bibliotecario b=null;
        if (checkBibliotecario instanceof Lector){return -1;}
        if (checkBibliotecario instanceof  Bibliotecario){
            b=(Bibliotecario) checkBibliotecario;
            for (int i=0; i<totalPersonas;i++){
                if (personas[i]!=null){
                    aux = personas[i];
                    if (aux instanceof Lector){
                        l= (Lector) aux;
                        for (int j=0;j<l.getPrestamos().length;j++){
                            if (l.getPrestamos()[j]!=null && l.getPrestamos()[j].getBibliotecario()==b){
                                prestamos++;
                            }
                        }
                    }
                }
            }
        }
        return prestamos;
    }
    // =====================================================
    // (9) Nombre del lector con mayor duración total de préstamos
    // =====================================================
    public String nombreMayorDuracion(){
        Persona aux=null;
        Lector masDias= new Lector();
        Lector l=null;
        for(int i=0; i<totalPersonas;i++){
            aux=personas[i];
            if (aux instanceof Lector){
                l=(Lector) aux;
                if (l.totalDias()>=masDias.totalDias()){
                    masDias=l;
                }
            }
        }
        if (masDias.totalDias()<=0){return "Mayor duración: N/A";}
        return "Mayor duración: "+masDias.getNombre();
    }

    // =====================================================
    // MENÚtrue => sin salida para modo VPL
    // =====================================================
    public void menu() {
        int op=0;
        do {
            if (!cv) {
                System.out.println("\n===== BIBLIOTECA =====");
                System.out.println("1. Añadir persona");
                System.out.println("2. Buscar persona por ID");
                System.out.println("3. Contar personas");
                System.out.println("4. Registrar préstamo");
                System.out.println("5. Calcular promedio edad");
                System.out.println("6. Contar por edad");
                System.out.println("7. Media duración (lector)");
                System.out.println("8. Préstamos gestionados (bibliotecario)");
                System.out.println("9. Nombre mayor duración");
                System.out.println("10. Salir");
                System.out.print("Opción: ");
            }

            String line = sc.nextLine();
            op = Integer.parseInt(line);

            switch (op) {
                case 1:
                    if (!cv) {
                        System.out.println("1. Lector");
                        System.out.println("2. Bibliotecario");
                        System.out.print("Tipo: ");
                    }
                    int tipo = Integer.parseInt(sc.nextLine());

                    if (!cv) System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());

                    if (!cv) System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    if (!cv) System.out.print("Edad: ");
                    int edad = Integer.parseInt(sc.nextLine());
                    
                    if (tipo == 1) {
                        if (!cv) System.out.println("Fecha de registro(AAAA-MM-DD): ");
                        LocalDate fecha=LocalDate.parse(sc.nextLine());
                        if (!cv)System.out.println("¿Está sancionado?(true/false): ");
                        boolean penalizado=Boolean.parseBoolean(sc.nextLine());
                        Lector auxL=new Lector(id, nombre, edad, fecha, penalizado);
                        anadirPersonaArgumentos(auxL);
                    }
                    
                    else if (tipo == 3){
                        if(!cv) System.out.println("Fecha de registro(AAAA-MM-DD: ");
                        LocalDate fecha = LocalDate.parse(sc.nextLine());
                        if(!cv)System.out.println("¿Está sancionado?(true/false): ");
                        boolean penalizado = Boolean.parseBoolean(sc.nextLine());
                        if(!cv)System.out.println("¿Cual es su numero de prestamos?");
                        int nPrest = Integer.parseInt(sc.nextLine());
                        Investigador auxI = new Investigador(id, nombre, edad, fecha, penalizado, nPrest);
                        anadirPersonaArgumentos(auxI);
                    }
                    
                    else if (tipo == 2){
                        if(!cv) System.out.println("Cual es la seccion de trabajo?");
                        String seccion=sc.nextLine();
                        Bibliotecario auxB=new Bibliotecario(id, nombre, edad, seccion);
                        anadirPersonaArgumentos(auxB);
                    }
                    break;
                case 2:
                    if(!cv) System.out.println("Añade la ID de la persona que quieras buscar");
                    int busqueda=Integer.parseInt(sc.nextLine());
                    Persona p=buscarPersonaPorId(busqueda);
                    if (p!=null){
                        System.out.println("Persona encontrada: "+p.getNombre());
                    }
                    else{
                        System.out.println("Persona no encontrada.");
                    }
                    break;

                case 3:
                    int totalP=contarPersonas();
                    System.out.println("Total personas: "+totalP);
                    break;

                case 4:
                    //Pendiente de realizar
                    break;

                case 5:
                    float media=calcularPromedioEdad();
                    String mediaFormat= String.format("%.2f", media).replace('.',',');
                    System.out.println("Promedio edad: " + mediaFormat);
                    break;

                case 6:
                    break;

                case 7:
                    if (!cv) System.out.println("Introduce el lector del cual quieres calcular la media de duracion");
                    int idDuracion=Integer.parseInt(sc.nextLine());
                    if (calcularMediaDuracion(idDuracion)!=-1){
                        System.out.println("Media duración: "+calcularMediaDuracion(idDuracion));
                    }
                    else{System.out.println("Media duración: -1");}
                    break;

                case 8:
                    if (!cv) System.out.println("Introduce el bibliotecario cuyas gestiones quieres comprobar");
                    int idGestion=Integer.parseInt(sc.nextLine());
                    if (cuantosPrestamos(idGestion)!=-1){
                        System.out.println("Prestamos gestionados: "+cuantosPrestamos(idGestion));
                    }
                    else{System.out.println("Prestamos gestionados: -1");}
                    break;

                case 9:
                    System.out.println(nombreMayorDuracion());
                    break;

                case 10:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (op != 10);
    
        
    }
    
    
    public boolean anadirLibro(String tit, String autor, String isbn, int stock, int diasP){
        Libro l = new Libro(tit, autor, isbn, stock, diasP);
        for(Libro lRecorrido : libros){
            if(lRecorrido.getIsbn().equals(l.getIsbn())){return false;}
        }
        libros.add(l);
        return true;
    }
    
    public int eliminarLibro(String isbn){
        return 0;
    }
    
    public boolean devolverPrestamo(int idPrestamo, LocalDate fechaDevolucion, int idBib){
        //Primero de todo comprobamos si nos ha dado un bibliotecario
        
        Persona checkBib = buscarPersonaPorId(idBib);
        if(!(checkBib instanceof Bibliotecario) || checkBib == null){return false;}
        
        Bibliotecario bib = (Bibliotecario) checkBib;
        Prestamo p = null;
        
        Iterator<Prestamo> it = prestamos.iterator();
        
        while(it.hasNext()){
            Prestamo checkNull = it.next();
            
            if(checkNull != null && checkNull.getId() == idPrestamo){
                p = checkNull;
            }
        }
        
        if(p == null){return false;}
        
        long diasRetraso = ChronoUnit.DAYS.between(p.getFecha() , fechaDevolucion);
        
        Lector l = p.getLector();
        
        if(diasRetraso > p.getDias()){
            l.setPenalizado(true);
        }
        
        p.setDevuelto(true);
        p.getLibro().getLectores().add(l);
        p.getLibro().setStock(p.getLibro().getStock() + 1);
        
        Libro libr = p.getLibro();
        
        if(libr.getCola().peek() != null){
            Lector nuevoLector = libr.getCola().poll();
            
            registrarPrestamo(prestamos.size() + 1, libr.getIsbn(), nuevoLector.getId(), fechaDevolucion, bib.getId());
        }
        
        return true;
    }
    
    public boolean desSancionarPersona(int id){
        Persona checkSanc = buscarPersonaPorId(id);
        
        if(checkSanc instanceof Lector){
            Lector lQuitarSancion = (Lector) checkSanc;
            
            if(lQuitarSancion.getPenalizado() == true){
                lQuitarSancion.setPenalizado(false);
                return true;
            }
            else{return false;}
        }
        return false;
    }
    
    public int contarPrestamosDadasUnaFechaConBibliotecario(LocalDate fecha, int idBib){
        Persona bibPers = buscarPersonaPorId(idBib);
        int contarPrestamos = 0;
        if(bibPers instanceof Bibliotecario){
            Bibliotecario bib = (Bibliotecario) bibPers;
            
            Iterator<Prestamo> it = prestamos.iterator();
            
            while(it.hasNext()){
                Prestamo prest = it.next();
                if(prest.getBibliotecario().equals(bib) && prest.getFecha().equals(fecha)){
                    contarPrestamos++;
                }
            }
            
        }
        return contarPrestamos;
    }
    
    public int contarLibrosMayorDuracioDadaConSusLectoresPenalizados(int duracion){
        return 0;
    }
    
    public Libro libroConMasColaEspera(){
        Iterator<Libro> it = libros.iterator();
        
        Libro libMayorCola = new Libro();
        
        while(it.hasNext()){
            Libro aux = it.next();
            if(libMayorCola.getCola().size() < aux.getCola().size()){
                libMayorCola = aux;
            }
        }
        return libMayorCola;
    }
    
    public Libro libroConMasPrestamos(){
        Iterator<Libro> it = libros.iterator();
        
        Libro masPrest = null;
        int prestamosLibro = 0;
        
        while(it.hasNext()){
            int cont = 0;
            Libro l = it.next();
            
            Iterator<Prestamo> it2 = prestamos.iterator();
            while(it2.hasNext()){
                Prestamo p = it2.next();
                if(p.getLibro().equals(l)){
                    prestamosLibro++;
                }
            }
            
            if(cont > prestamosLibro){
                prestamosLibro = cont;
                masPrest = l;
            }
        }
        return l;
    }
    
    
}

