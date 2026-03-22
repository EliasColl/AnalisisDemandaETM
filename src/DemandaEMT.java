import java.time.LocalDate;
import java.time.LocalTime;

public class DemandaEMT {
    // Guardado en LocalDate por el formato de las fechas
    private final LocalDate fecha;
    // Mejor String por si se añaden letras a futuro
    private final String linea;
    private final int totalViajeros;

    public DemandaEMT(LocalDate fecha, String linea, int totalViajeros) {
        this.fecha = fecha;
        this.linea = linea;
        this.totalViajeros = totalViajeros;
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }
    public String getLinea() {
        return linea;
    }
    public int getTotalViajeros() {
        return totalViajeros;
    }

    // ToString
    @Override
    public String toString() {
        return "DemandaEMT{" +
                "fecha=" + fecha +
                ", linea='" + linea + '\'' +
                ", totalViajeros=" + totalViajeros +
                '}';
    }
}
