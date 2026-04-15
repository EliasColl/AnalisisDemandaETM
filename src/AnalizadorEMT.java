import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


public class AnalizadorEMT {

  // PASO 1: Definimos las interfaces funcionales como constantes reutilizables

    // 1. PREDICATE (Condición: recibe un viaje, devuelve true/false)
    // Filtramos los viajes que superan los 3000 pasajeros (puedes ajustar el número)
    public final Predicate<DemandaEMT> ALTA_DEMANDA = viaje -> viaje.getTotalViajeros() > 3000;

    // 2. FUNCTION (Transformación de tipo: recibe un viaje, devuelve un entero)
    // Extrae la cantidad de viajeros usando referencia a metodo
    public final Function<DemandaEMT, Integer> OBTENER_VIAJEROS = DemandaEMT::getTotalViajeros;

    // 3. CONSUMER (Acción: recibe un viaje, no devuelve nada)
    // Imprime el viaje por la consola de forma limpia
    public final Consumer<DemandaEMT> IMPRIMIR_VIAJE = System.out::println;

    // 4. SUPPLIER (Generador: no recibe nada, devuelve un viaje)
    // Genera un objeto por defecto útil para operaciones como orElseGet()
    public final Supplier<DemandaEMT> VIAJE_POR_DEFECTO = () ->
            new DemandaEMT(LocalDate.now(), "ERROR/SIN DATOS", 0);

    // 5. UNARYOPERATOR (Transformación del mismo tipo: recibe un viaje, devuelve un viaje)
    // Simula un escenario donde los viajeros de esa línea aumentan un 20%
    public final UnaryOperator<DemandaEMT> SIMULAR_CRECIMIENTO = viaje ->
            new DemandaEMT(viaje.getFecha(), viaje.getLinea(), (int)(viaje.getTotalViajeros() * 1.20));


  // PASO 2: Metodo para mostrar el top 5 de viajes con alta demanda usando las interfaces funcionales

    // Uso de Predicate, Comparator, limit y Consumer (Operaciones intermedias: filter, sorted, limit)
    public void mostrarTop5ViajesAltaDemanda(List<DemandaEMT> datos) {
        System.out.println("\n-- Top 5 viajes con más de 3000 pasajeros (alta demanda):");

        datos.stream()
                .filter(ALTA_DEMANDA) // 1) Filtrar
                .sorted(Comparator.comparing(OBTENER_VIAJEROS).reversed()) // 2) Ordenar
                .limit(5) // 3) Limitar
                .forEach(IMPRIMIR_VIAJE); // 4) Consumir (imprimir)
    }

    // Uso de UnaryOperator (Operación intermedia: map)
    public List<DemandaEMT> simularAumentoPasajeros(List<DemandaEMT> datos, String lineaBuscada) {
        System.out.println("\n-> Simulando un crecimiento del 20\\% en los 3 primeros registros de la línea " + lineaBuscada + ":");

        return datos.stream()
                .filter(viaje -> viaje.getLinea().equals(lineaBuscada)) // 1) Filtrar
                .limit(3) // 2) Limitar
                .map(SIMULAR_CRECIMIENTO) // 3) Transformar
                .toList(); // 4) Convertir y devolver
    }

  // PASO 3: Aplicar operaciones terminales o de conversión

    // Metodo 1: Uso de map, distinct, skip y toList (Conversión)
    // Obtiene una lista con las líneas de bus únicas, eliminando duplicados y saltando las 2 primeras
    public List<String> obtenerLineasUnicas(List<DemandaEMT> datos) {
        return datos.stream()
                .map(DemandaEMT::getLinea) // Extrae solo las líneas de bus
                .distinct() // Elimina duplicados
                .skip(2) // Salta las primeras 2 líneas (si quieres)
                .toList(); // Convierte el resultado a una lista
    }

    // Metodo 2: Uso de anyMatch (Terminal booleana)
    // Comprueba si existe al menos un viaje con 0 viajeros
    public boolean huboViajesVacios(List<DemandaEMT> datos) {
        return datos.stream()
                // Devuelve true si encuentra al menos uno con 0 viajeros
                .anyMatch(viaje -> viaje.getTotalViajeros() == 0);
    }

    // Metodo 3: Uso de count
    // ¿Cuántos registros en total superan los 3000 viajeros?
    public long contarViajesAltaDemanda(List<DemandaEMT> datos) {
        return datos.stream()
                .filter(ALTA_DEMANDA) // Usamos nuestro Predicate del Paso 1
                .count(); // Operación terminal que cuenta los elementos
    }

    // Metodo 4: Uso de findFirst
    // Busca el primer registro que exista de una línea concreta
    public void buscarPrimerViajeLinea(List<DemandaEMT> datos, String lineaBuscada) {
        datos.stream()
                .filter(viaje -> viaje.getLinea().equals(lineaBuscada))
                .findFirst() // Operación terminal
                .ifPresentOrElse(
                        IMPRIMIR_VIAJE, // Si lo encuentra, usa nuestro Consumer
                        () -> System.out.println("No hay datos para la línea: " + lineaBuscada)
                );
    }

    // Metodo 5: Uso de reduce
    // Calcula la suma total histórica de viajeros de una línea específica
    public int calcularTotalViajerosLinea(List<DemandaEMT> datos, String lineaBuscada) {
        return datos.stream()
                .filter(viaje -> viaje.getLinea().equals(lineaBuscada))
                .map(OBTENER_VIAJEROS) // Transformamos el viaje en un simple número entero
                .reduce(0, Integer::sum); // Operación terminal: partimos de 0 y sumamos todo
    }


    // Metodo 6: Uso de collect explícito
    // Extrae una lista solo con los viajes que tuvieron exactamente 0 viajeros
    public List<DemandaEMT> obtenerViajesVaciosConCollect(List<DemandaEMT> datos) {
        return datos.stream()
                .filter(viaje -> viaje.getTotalViajeros() == 0)
                .collect(Collectors.toList()); // Operación terminal explícita requerida
    }

    // Metodo 7: Uso de Supplier (Operación terminal: orElseGet)
    // Busca el primer viaje de la línea indicada y, si no existe, devuelve un viaje por defecto
    public DemandaEMT obtenerViajeSeguro(List<DemandaEMT> datos, String lineaInexistente) {
        return datos.stream()
                .filter(viaje -> viaje.getLinea().equals(lineaInexistente))
                .findFirst() // Operación terminal principal
                .orElseGet(VIAJE_POR_DEFECTO); // Respaldo terminal
    }
}