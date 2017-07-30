package cepa.lote.lote.tools;

/**
 * Clase que contiene los códigos usados  para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = "";
    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "184.171.250.45";
    /**
     * URLs del Web Service
     */

    public static final String GET_DATA = "http://" + IP + PUERTO_HOST + "/apiSMS/ObtenerData.php";
    public static final String UPDATE_PAS = "http://" + IP + PUERTO_HOST + "/apiSMS/ActualizarPas.php";


    /**
     * Clave para el valor extra que representa al identificador de un registro
     */
    public static final String EXTRA_ID = "IDEXTRA";

}
