// ==========================
// CLASE PRINCIPAL: BIBLIOTECA
// ==========================

import java.time.LocalDate;
import java.util.Scanner;

public class Biblioteca {
    boolean cv = false; // Variable de control para el menú. True cuando se suba VPL y false para pruebas locales
    private final Scanner sc = new Scanner(System.in);

    //TODO 
    // agregar atributos necesarios para almacenar personas y constructores
    private Persona[] personas=new Persona[25];
    private int totalPersonas;

    //TODO
    //Agrear métodos necesarios para gestionar la biblioteca (añadir persona, buscar por ID, contar personas, registrar préstamo, etc.)

    // =====================================================        
    // (1) AÑADIR PERSONA 
    // =====================================================
    public boolean anadirPersona() {
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
        if (!cv) System.out.print("Fecha de registro(AAAA-MM-DD): ");
        LocalDate fecha=LocalDate.parse(sc.nextLine());
            if (!cv)System.out.println("¿Está sancionado?(true/false): ");
            boolean penalizado=Boolean.parseBoolean(sc.nextLine());
            personas[totalPersonas]=new Lector(id, nombre, edad, fecha, penalizado);
        } else {
            if(!cv) System.out.println("Cual es la seccion de trabajo?");
            String seccion=sc.nextLine();
            personas[totalPersonas]=new Bibliotecario(id, nombre, edad, seccion);
        }
        totalPersonas++;
        //TODO
        return true;
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
    public boolean registrarPrestamo(LocalDate fecha, int dD, boolean d, int idLector, Bibliotecario b){
        Lector l=(Lector) buscarPersonaPorId(idLector);
        if (l instanceof Lector){
        Prestamo p = new Prestamo (fecha, dD, d, b);
        return l.anadirPrestamo(p);
        }
        return false;
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
                            if (l.getPrestamos()[j]!=null && l.getPrestamos()[j].getBib()==b){
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
                    if (anadirPersona()==true){
                        System.out.println("Miembro añadido correctamente con id: "+personas[totalPersonas-1].getId());
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
                    if (!cv){System.out.println("Introduce el id del lector");}
                    int idL=Integer.parseInt(sc.nextLine());
                    
                    Persona checkExiste = buscarPersonaPorId(idL);
                    
                    if (checkExiste==null || !(checkExiste instanceof Lector)){
                    System.out.println("Error: Lector no encontrado");
                    break;
                    }
                    
                    if(!cv){System.out.println("Introduce la fecha del prestamo");}
                    LocalDate f=LocalDate.parse(sc.nextLine());
                    
                    if(!cv){System.out.println("Cuantos dias durará el prestamo");}
                    int dD=Integer.parseInt(sc.nextLine());
                    
                    if(!cv){System.out.println("Lo ha devuelto?");}
                    boolean d=Boolean.parseBoolean(sc.nextLine());
                    
                    if(!cv){System.out.println("Introduce el id del bibliotecario que se lo ha gestionado");}
                    int idBib=Integer.parseInt(sc.nextLine());
                    
                    Persona bibliotecario=buscarPersonaPorId(idBib);
                    Bibliotecario b=null;
                    
                    if(bibliotecario instanceof Bibliotecario){
                        b= (Bibliotecario) bibliotecario;
                    }
                    
                    if (bibliotecario==null) System.out.println("Error. Bibliotecario no encontrado.");
                    
                    else if (registrarPrestamo(f, dD, d, idL, b)==false){
                        System.out.println("Error: Lector no encontrado.");
                    }
                    else{
                        System.out.println("Bibliotecario: "+b.getNombre()+" Días: "+dD);
                    }
                    break;

                case 5:
                    float media=calcularPromedioEdad();
                    String mediaFormat= String.format("%.2f", media).replace('.',',');
                    System.out.println("Promedio edad: " + mediaFormat);
                    break;

                case 6:
                    if (!cv) System.out.println("Que edad quieres contar?");
                    int contEdad=0;
                    int edad=Integer.parseInt(sc.nextLine());
                    contEdad=contarPorEdad(edad);
                    System.out.println("Total: "+contEdad);
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
}

