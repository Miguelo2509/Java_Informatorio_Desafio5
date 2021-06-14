import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.math.BigDecimal;

public class Main {

/*  Ejercicio Propuesto #5:
Dado un archivo de texto (.txt) con el siguiente formato:
{nombre},{apellido},{fecha de nacimiento},{sueldo}

Donde:
Nombre, apellido: String
fecha de nacimiento: Fecha con formato (dia-mes-año, ejemplo: 23-05-2021)
Sueldo: es numérico con decimales.

Ejemplo:
Carlos,Sanchez,20-01-1980,45678.08

Resolución De Problema (Pasos):
Servicio Reader (ya sabemos por los ejercicios propuestos #2)
Necesitas una Clase Empleado (con los datos a guardar)
Cada línea es un --> Empleado → se almacena en una lista
Crear un método que trate cada línea (String), donde separa los campos y creará el objeto Empleado.
Necesitamos otro método que convierta la fecha String en tipo Fecha (LocalDate),
    también una clase Date Formatter (leer expresiones de formatos de fechas) para convertir String en LocalDate
También necesitaremos un método que convierte el String (sueldo) en tipos posibles double/Double, float(muy potente para lo que necesitamos), BigDecimal.

Cálculos (sobre la lista final):
Método que devuelva todos los empleados que comienzan con una letra dada en el apellido
Método que devuelva el empleado más joven y el más viejo (necesito una funcion para calcular el año basado en una fecha)
El empleado que más gana y el que menos gana.
(Opcional): Imprimir todos los empleados en orden alfabético (por nombre y por apellido).

*/
    public static void borrarConsola(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            /*No hacer nada*/
        }
    }
    public static LocalDate convertirFecha(String fecha){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fecha,format);
    }

    public static BigDecimal convertirSueldo(String sueldo){
        return new BigDecimal(sueldo);
    }

    public static void leerArchivo() throws IOException {
        String path = "C:/Cursos/Informatorio/Etapa 3/Java/empleados.txt";
        //Crear un método que trate cada línea (String), donde separa los campos y creará el objeto Empleado.
        try (BufferedReader br = new BufferedReader (new FileReader(path))) {
            String line = br.readLine();
            while (line != null) {
                String[] campos= line.split(",");
                Empleado emp = new Empleado(campos[0],campos[1],convertirFecha(campos[2]),convertirSueldo(campos[3]));
                empleados.add(emp);
                System.out.println(line);
                line = br.readLine();
            }
        }
    }

    public static void mostrarEmpleados(List<Empleado> empleados){
        for (Empleado empleado: empleados){
                System.out.println(empleado.toString());
        }
    }
    public static void comienzanCon(List<Empleado> empleados,Character letra){
        for (Empleado empleado: empleados){
            if (empleado.getApellido().charAt(0)==letra) {
                System.out.println(empleado.getApellido() + " - " + empleado.getNombre());
            }
        }
    }

    public static long edad(LocalDate fechaNac){
        long edad;
        edad = ChronoUnit.YEARS.between(fechaNac, LocalDate.now());
        return edad;
    }

    public static Empleado empleadoMasJoven(List<Empleado> empleados){
        Empleado masJoven = null;
        long edadMasJoven = 100;
        for (Empleado empleado: empleados){
            if (edad(empleado.getFechaNacimiento())<edadMasJoven) {
                masJoven=empleado;
                edadMasJoven=edad(empleado.getFechaNacimiento());
            }
        }
        return masJoven;
    }

    public static Empleado empleadoMasViejo(List<Empleado> empleados){
        Empleado masViejo = null;
        long edadMasViejo = 0;
        for (Empleado empleado: empleados){
            if (edad(empleado.getFechaNacimiento())>edadMasViejo) {
                masViejo=empleado;
                edadMasViejo=edad(empleado.getFechaNacimiento());
            }
        }
        return masViejo;
    }

    public static Empleado empleadoMasGana(List<Empleado> empleados){
        Empleado masGana = null;
        BigDecimal mayor = BigDecimal.ZERO;

        for (Empleado empleado: empleados){
            if (empleado.getSueldo().compareTo(mayor) == 1) {
                masGana=empleado;
                mayor=empleado.getSueldo();
            }
        }
        return masGana;
    }

    public static Empleado empleadoMenosGana(List<Empleado> empleados){
        Empleado menosGana = null;
        BigDecimal menor = BigDecimal.valueOf(999999);

        for (Empleado empleado: empleados){
            if (empleado.getSueldo().compareTo(menor) == -1) {
                menosGana=empleado;
                menor=empleado.getSueldo();
            }
        }
        return menosGana;
    }

    public static void ordenarEmpleados(List<Empleado> empleados) {
        Comparator<Empleado> comparararPorNombre = Comparator
                .comparing(Empleado::getNombre)
                .thenComparing(Empleado::getApellido);

        Collections.sort(empleados, comparararPorNombre);
        mostrarEmpleados(empleados);
    }
    static public void enterParaContinuar(){
          String seguir;
          Scanner teclado = new Scanner(System.in);
          System.out.println("Presione Enter para continuar...");
          try
            {
             seguir = teclado.nextLine();
            }
         catch(Exception e)
          {}
     }

    static Scanner scan = new Scanner(System.in);
    static List<Empleado> empleados = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //Set<Empleado> empleados = new HashSet<>();
        borrarConsola();
        leerArchivo();

        int opcion=1;
        while (opcion!=0){
            System.out.println("Menu:");
            System.out.println("1: Mostrar Empleados.");
            System.out.println("2: Empleados que comienzan con una letra dada en el apellido.");
            System.out.println("3: Empleado mas jover.");
            System.out.println("4: Empleado mas viejo.");
            System.out.println("5: Empleado que mas gana.");
            System.out.println("6: Empleado que menos gana.");
            System.out.println("7: Empleados ordenados por nombre y apellido.");
            System.out.println("0: Salir.");
            System.out.print("Ingrese una opción: ");
            opcion= scan.nextInt();
            while (opcion < 0 || opcion > 7){
                System.out.print("Ingrese una opción válida: ");
                opcion= scan.nextInt();
            }
            switch (opcion){
                case 1:
                    mostrarEmpleados(empleados);
                    enterParaContinuar();
                    break;
                case 2:
                    System.out.print("Ingrese la letra: ");
                    Character letra = Character.toUpperCase(scan.next().charAt(0));
                    System.out.println("Lista de Empleados que su apellido comienza con: " + letra);
                    comienzanCon(empleados, letra);
                    enterParaContinuar();
                    break;
                case 3:
                    Empleado masJoven=empleadoMasJoven(empleados);
                    System.out.println("Empleado mas Joven: " + masJoven.getApellido() + "," + masJoven.getNombre() +
                    " Fecha de Nacimiento: " + masJoven.getFechaNacimiento() + " Edad: " + edad(masJoven.getFechaNacimiento()));
                    enterParaContinuar();
                    break;
                case 4:
                    Empleado masViejo=empleadoMasViejo(empleados);
                    System.out.println("Empleado mas Viejo: " + masViejo.getApellido() + "," + masViejo.getNombre() +
                    " Fecha de Nacimiento: " + masViejo.getFechaNacimiento() + " Edad: " + edad(masViejo.getFechaNacimiento()));
                    enterParaContinuar();
                    break;
                case 5:
                    Empleado masGana=empleadoMasGana(empleados);
                    System.out.println("Empleado que mas Gana: " + masGana.getApellido() + "," + masGana.getNombre() +
                    " Sueldo: " + masGana.getSueldo());
                    enterParaContinuar();
                    break;
                case 6:
                    Empleado menosGana=empleadoMenosGana(empleados);
                    System.out.println("Empleado que menos Gana: " + menosGana.getApellido() + "," + menosGana.getNombre() +
                    " Sueldo: " + menosGana.getSueldo());
                    enterParaContinuar();
                    break;
                case 7:
                    System.out.println("Empleados ordenados por Nombre y Apellido (Orden Ascendente)");
                    ordenarEmpleados(empleados);
                    enterParaContinuar();
                    break;
                default:
                    break;
            }
        }
        scan.close();
    }
}

