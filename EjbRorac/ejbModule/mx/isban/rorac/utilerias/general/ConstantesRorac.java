/**
 *
 */
package mx.isban.rorac.utilerias.general;

/**
 * @author everis
 *
 */
public final class ConstantesRorac {
	/**
	 * Indica que una operacion fue realizada con exito.
	 */
	public static final String OPERACION_EXITOSA = "0";
	/**
	 * Error de validacion en inputs complementarios.
	 */
	public static final String ERROR_VALIDACION_IC = "1001";
	/**
	 * Error al guardar un input complementario.
	 */
	public static final String ERROR_GUARDADO_IC = "1002";
	/**
	 * Input complementario excede el tamanio permitido.
	 */
	public static final String ERROR_EXCEDE_TAMANIO_IC = "1003";
	/**
	 * La ruta donde se guarda el archivo no ha sido configurada.
	 */
	public static final String ERROR_NO_RUTA_CARGAS_MANUALES = "1004";
	/**
	 * Error al cargar la configuracion.
	 */
	public static final String ERROR_CONFIGURACION_CARGAS_MANUALES = "1005";
	/**
	 * Error al realizar la consulta de logs y validaciones.
	 */
	public static final String ERROR_CONSULTA_LOGS = "2001";
	/**
	 * Codigo de respuesta nula al realizar la consulta de logs y validaciones.
	 */
	public static final String ERROR_NO_RESPUESTA_LOGS = "2002";
	/**
	 * Error al cargar configuracion en en el proceso de consulta de logs y
	 * validaciones.
	 */
	public static final String ERROR_CONFIGURACION_LOGS = "2003";
	/**
	 * Error al registrar un log para descarga.
	 */
	public static final String ERROR_REGISTRO_DESCARGA_LOGS = "2004";
	/**
	 * Error al ejecutar el IDA para actualizacion de Inputs/Outputs finales
	 */
	public static final String ERROR_EJECUCION_IDA_IO_FINALES = "3001";
	/**
	 * Error de respuesta nula de IDA para actualizacion de Inputs/Outputs
	 * finales.
	 */
	public static final String ERROR_NO_RESPUESTA_IO_FINALES = "3002";
	/**
	 * Error de configuracion para actualizacion de Inputs/Outputs finales.
	 */
	public static final String ERROR_CONFIGURACION_IO_FINALES = "3003";
	/**
	 * Error en los parametros de entrada para Inputs/Outputs finales.
	 */
	public static final String ERROR_PARAMETROS_ENTRADA_IO_FINALES = "3004";
	/**
	 * Error al cargar configuracion para ejecucion de motor rorac.
	 */
	public static final String ERROR_CONFIGURACION_MOTOR_RORAC = "3005";
	/**
	 * Error al intentar realizar el lanzamiento de motor rorac cuando ya hay
	 * una solicitud pendiente.
	 */
	public static final String ERROR_VALIDACION_MOTOR_RORAC = "3006";
	/**
	 * Error al cargar los parametros necesarios para la ejecucion de reproceso.
	 */
	public static final String ERROR_CONFIGURACION_REPROCESOS = "3007";
	/**
	 * Los parametros de entrada para reprocesos no son correctos.
	 */
	public static final String ERROR_PARAMETROS_ENTRADA_REPROCESO = "3008";
	/**
	 * Error al cargar los parametros necesarios para el registro de la
	 * operacion de Aprovisionamiento Historico.
	 */
	public static final String ERROR_CONFIGURACION_AH = "3009";
	/**
	 * Error en los parametros de entrada para Aprovisionamiento historico.
	 */
	public static final String ERROR_PARAMETROS_ENTRADA_AH = "3010";
	/**
	 * Error al ejecutar el IDA para tablas de parametros.
	 */
	public static final String ERROR_IDA_TABLAS_PARAMETROS = "4001";
	/**
	 * Error de respuesta nula por parta de DAO en Tablas de Parametros.
	 */
	public static final String ERROR_NO_RESPUESTA_TABLAS_PARAMETROS = "4002";
	/**
	 * Error al intentar guardar un registro con un ID repetido.
	 */
	public static final String ERROR_ID_REPETIDO_TABLAS_PARAMETROS = "4003";
	/**
	 * Error en parametros de entrada.
	 */
	public static final String ERROR_VALORES_ENTRADA_TABLAS_PARAMETROS = "4004";
	/**
	 * Error al ejecutar el IDA en el Monitor de Cargas
	 */
	public static final String ERROR_EJECUCION_IDA_MONITOR_CARGAS = "5001";
	/**
	 * Error al cargar las configuracione necesarias para Monitor de Cargas.
	 */
	public static final String ERROR_CONFIGURACION_MONITOR_CARGAS = "5002";
	/**
	 * Error de ejecucion de IDA para Consulta de Inputs/Outpus finales por
	 * contrato.
	 */
	public static final String ERROR_IDA_CONSULTA_POR_CONTRATO = "6001";
	/**
	 * Error en los parametros de entrada para Consulta de Inputs/Outputs
	 * finales por contrato.
	 */
	public static final String ERROR_PARAMETROS_ENTRADA_CONSULTA_POR_CONTRATO = "6002";
	/**
	 * Error de ejecucion al guardar pistas de auditoria.
	 */
	public static final String ERROR_GENERAR_PISTA_AUDITORIA = "7001";
	/**
	 * Error al ejecutar IDA en el modulo de perfilamiento.
	 */
	public static final String ERROR_EJECUCION_IDA_PERFILAMIENTO = "8001";
	/**
	 * Respuesta nula por parte IDA.
	 */
	public static final String ERROR_RESPUESTA_NULA_IDA_PERFILAMIENTO = "8002";
	/**
	 * Constante para indicar que existe un error de tipo IDA
	 */
	public static final String MENSAJE_ERROR_IDA = "Error al realizar una consulta en el componente DataAcces";

	/**
	 * Estatus Operacion OK para pistas auditoria.
	 */
	public static final String PISTAS_AUDITORIA_ESTATUS_OK = "OK";
	/**
	 * Estatus de Error para pistas de auditoria.
	 */
	public static final String PISTAS_AUDITORIA_ESTATUS_ERROR = "ERROR";
	/**
	 * Constante con valor de No aplica, para crear pistas de auditoria.
	 */
	public static final String PISTAS_AUDITORIA_NO_APLICA = "NO APLICA";
	/**
	 * Constante para indicar que existe una soliccitud de edicion inputs
	 * finales en proceso
	 */
	public static final String EJECUCION_EN_PROCESO = "01";
}
