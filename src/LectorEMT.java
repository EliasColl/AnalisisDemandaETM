import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
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
            System.out.println("¡DATOS CARGADOS CORRECTAMENTE!" +
                    "\nTotal de registros: " + viajes.size() + "\n");

            AnalizadorEMT analizador = new AnalizadorEMT();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("""
                        
                        
                        - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                        Elige la fase de pruebas a ejecutar:
                        1: Ejecutar todo el análisis (Fases 2, 3 y 4)
                        2: Fase 2 (Operaciones intermedias)
                        3: Ejecutar Fase 3 (Operaciones terminales y conversión)
                        4: Ejecutar Fase 4 (Validación de hipótesis y estadísticas)
                        0: Salir del programa
                        """);

                String entrada = scanner.nextLine().trim();
                int opcion;

                try {
                    opcion = Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    System.out.println("Opción no válida.\n");
                    continue;
                }

                switch (opcion) {
                    case 1 -> {
                        ejecutarFase2(analizador, viajes);
                        ejecutarFase3(analizador, viajes);
                        ejecutarFase4(analizador, viajes);
                    }
                    case 2 -> ejecutarFase2(analizador, viajes);
                    case 3 -> ejecutarFase3(analizador, viajes);
                    case 4 -> ejecutarFase4(analizador, viajes);
                    case 0 -> {
                        System.out.println("Saliendo del programa...");
                        return;
                    }
                    default -> System.out.println("Opción no válida.\n");
                }
            }

            // Excepción de la lectura de archivos
        } catch (Exception e) {
            System.out.println("ERROR al procesar el archivo: " + e.getMessage());
        }
    }

    private static void ejecutarFase2(AnalizadorEMT analizador, List<DemandaEMT> viajes) {
        System.out.println("""
                                
                                
                                ==================================================
                                2. OPERACIONES INTERMEDIAS (FILTER, SORTED, LIMIT)
                                ==================================================
                                """);

        // y ejecutamos el análisis sobre los datos cargados
        analizador.mostrarTop5ViajesAltaDemanda(viajes);

        // Uso del UnaryOperator colocado en su fase correcta
        List<DemandaEMT> viajesSimulados = analizador.simularAumentoPasajeros(viajes, "001");
        viajesSimulados.forEach(System.out::println);
    }

    private static void ejecutarFase3(AnalizadorEMT analizador, List<DemandaEMT> viajes) {
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
    }

    private static void ejecutarFase4(AnalizadorEMT analizador, List<DemandaEMT> viajes) {
        System.out.println("""
                                
                                
                                ==================================================
                                4. VALIDACIÓN DE HIPÓTESIS Y VISUALIZACIÓN
                                ==================================================
                                """);

        // --- HIPÓTESIS 1: Fines de semana vs Laborables ---
        double mediaLab = analizador.mediaLaborables(viajes);
        double mediaFin = analizador.mediaFinesDeSemana(viajes);

        System.out.println("--- HIPÓTESIS 1: El uso de autobuses disminuye drásticamente los fines de semana.");
        double maxH1 = Math.max(mediaLab, mediaFin);
        imprimirBarraConsola("Laborables", mediaLab, maxH1);
        imprimirBarraConsola("Fin de Sem.", mediaFin, maxH1);

        if (mediaLab > mediaFin) {
            System.out.println("✅ VALIDADA: La demanda cae durante el fin de semana.\n");
        } else {
            System.out.println("❌ RECHAZADA: La demanda sube o se mantiene el fin de semana.\n");
        }


        // --- HIPÓTESIS 2: Primera vs Segunda Quincena ---
        double mediaQ1 = analizador.mediaPrimeraQuincena(viajes);
        double mediaQ2 = analizador.mediaSegundaQuincena(viajes);

        System.out.println("--- HIPÓTESIS 2: Se viaja más en la primera quincena del mes.");
        double maxH2 = Math.max(mediaQ1, mediaQ2);
        imprimirBarraConsola("1ª Quincena", mediaQ1, maxH2);
        imprimirBarraConsola("2ª Quincena", mediaQ2, maxH2);

        if (mediaQ1 > mediaQ2) {
            System.out.println("✅ VALIDADA: Hay más movimiento de viajeros en los primeros 15 días del mes.\n");
        } else {
            System.out.println("❌ RECHAZADA: La segunda mitad del mes registra más o igual demanda.\n");
        }


        // --- HIPÓTESIS 3: Líneas Minoritarias ---
        long viajesFantasma = analizador.contarViajesFantasma(viajes);
        double porcentajeFantasma = (double) viajesFantasma / viajes.size() * 100;

        System.out.println("--- HIPÓTESIS 3: Más del 5% de los registros diarios de la EMT mueven menos de 500 pasajeros.");
        System.out.printf("-> Viajes totales analizados: %d\n", viajes.size());
        System.out.printf("-> Viajes con < 500 pasajeros: %d (%.2f%% del total)\n", viajesFantasma, porcentajeFantasma);

        if (porcentajeFantasma > 5.0) {
            System.out.println("✅ VALIDADA: Existe un volumen significativo de rutas con demanda extremadamente baja.\n");
        } else {
            System.out.println("❌ RECHAZADA: Las rutas de la EMT son mayoritariamente eficientes.\n");
        }
    }

    // GRÁFICO: Metodo auxiliar estático para dibujar barras proporcionales en la consola
    private static void imprimirBarraConsola(String etiqueta, double valor, double maximo) {
        int limiteCaracteres = 40;
        int longitudBarra = (int) ((valor / maximo) * limiteCaracteres);
        String grafica = "█".repeat(Math.max(1, longitudBarra));
        System.out.printf("%-12s | %s (%d)\n", etiqueta, grafica, Math.round(valor));
    }
}
