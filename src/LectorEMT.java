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

        // Impresión y carga de resultados
        System.out.println("¡DATOS CARGADOS CORRECTAMENTE!\nTotal de registros: " + viajes.size() + "\n");

        // Pruebas en AnalizadorEMT:
        System.out.println("Prueba con el primer registro: " + viajes.get(0));

        System.out.println("¡Datos cargados con éxito! Total de registros: " + viajes.size());


        // Excepción
        } catch (Exception e) {
            System.out.println("ERROR al procesar el archivo: " + e.getMessage());
        }
    }
}
