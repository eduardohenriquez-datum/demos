

/**
 * CONCEPTO: Interfaz (Abstracción)
 *
 * Una interfaz define un "contrato": cualquier clase que la implemente
 * DEBE tener estos métodos. No importa cómo lo haga, solo que lo haga.
 *
 * Analogía: como un estándar de la empresa — todo sistema debe poder
 * generar un reporte, sin importar qué tipo de empleado sea.
 */
public interface Reportable {

    String generarReporte();
}
