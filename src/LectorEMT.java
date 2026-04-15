import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;


// CLASE MAIN: LECTOR CON JAVA.NIO
public class LectorEMT {
    public static void main(String[] args) {
        // 1. Definición de la ruta del archivo
        Path ruta = Paths.get("datos/demandadialinea_2025.csv");

        // 2. Uso de try-with-resources para leer el archivo java.nio
        try (Stream<String> lineas = Files.lines(ruta)) {
            // 3. Procesamos el Stream
            List<DemandaEMT> viajes = lineas
            // Operación Intermedia: Saltamos la primera línea (cabecera)
            .skip(1)
            // Transformación de cada Steam en un objeto DemandaETM.
            .map(linea -> {
                // Añadimos el separador: en nuestro caso ";"
                String[] partes = linea.split(";");

                // Convierte el texto a datos reales
                LocalDate fecha = LocalDate.parse(partes[0]);
                String lineaBus = partes[1];
                int viajerosBus = Integer.parseInt(partes[2]);

                // Devuelve el resultado del .map en forma de objeto DemandaEMT
                return new DemandaEMT(fecha, lineaBus, viajerosBus);

            })
            // Conversión - Guardamos el resultado en una Lista
            .toList();


        // --- FASE 1: LECTURA Y CARGA DE DATOS ---
            System.out.println("""
                                
                                ==================================================
                                1. LECTURA Y MODELADO DE DATOS (JAVA.NIO)
                                ==================================================
                                
                                """);

            // Impresión y carga de resultados
            System.out.println( "¡DATOS CARGADOS CORRECTAMENTE!" +
                                "\nTotal de registros: " + viajes.size() + "\n");


        // --- FASE 2: OPERACIONES INTERMEDIAS ---
            System.out.println("""
                                
                                ==================================================
                                2. OPERACIONES INTERMEDIAS (FILTER, SORTED, LIMIT)
                                ==================================================
                                
                                """);

            // Instanciamos el analizador para el resto de fases
            AnalizadorEMT analizador = new AnalizadorEMT();
            // y ejecutamos el análisis sobre los datos cargados
            analizador.mostrarTop5ViajesAltaDemanda(viajes);

            // Uso del UnaryOperator colocado en su fase correcta
            List<DemandaEMT> viajesSimulados = analizador.simularAumentoPasajeros(viajes, "001");
            viajesSimulados.forEach(System.out::println);


        // --- FASE 3: OPERACIONES TERMINALES Y DE CONVERSIÓN ---
            System.out.println("""
                                
                                ==================================================
                                3. OPERACIONES TERMINALES Y DE CONVERSIÓN
                                ==================================================
                                
                                """);

            // 1. Uso de count
            long totalAltaDemanda = analizador.contarViajesAltaDemanda(viajes);
            System.out.println("-> Total de viajes que superaron los 3000 pasajeros: " + totalAltaDemanda);

            // 2. Uso de findFirst
            System.out.println("\n-> Buscando el primer registro del día para la línea 001:");
            analizador.buscarPrimerViajeLinea(viajes, "001");

            // 3. Uso de reduce
            int totalViajerosL1 = analizador.calcularTotalViajerosLinea(viajes, "001");
            System.out.println("\n-> Suma total de viajeros transportados por la línea 001: " + totalViajerosL1);

            // 4. Uso de collect
            List<DemandaEMT> viajesVacios = analizador.obtenerViajesVaciosConCollect(viajes);
            System.out.println("\n-> Viajes completamente vacíos (0 pasajeros) registrados: " + viajesVacios.size());

            // 5. Uso de anyMatch
            boolean hayVacios = analizador.huboViajesVacios(viajes);
            System.out.println("\n-> ¿Hubo algún viaje con 0 pasajeros en todo el dataset? " + (hayVacios ? "Sí" : "No"));

            // 6. Uso de map, distinct, skip y toList
            List<String> lineasUnicas = analizador.obtenerLineasUnicas(viajes);
            System.out.println("\n-> Muestra de las líneas únicas operando este día (saltando las 2 primeras):");
            // Usamos subList para imprimir solo las primeras 15 y no inundar toda la consola
            System.out.println(lineasUnicas.subList(0, Math.min(15, lineasUnicas.size())) + " ... (y más)");

            // 7. Uso del Supplier colocado en su fase correcta
            System.out.println("\n-> Buscando una línea inventada ('999') usando Supplier para evitar errores:");
            DemandaEMT viajeSeguro = analizador.obtenerViajeSeguro(viajes, "999");
            System.out.println(viajeSeguro);

        // Excepción
        } catch (Exception e) {
            System.out.println("ERROR al procesar el archivo: " + e.getMessage());
        }
    }
}
