import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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

}