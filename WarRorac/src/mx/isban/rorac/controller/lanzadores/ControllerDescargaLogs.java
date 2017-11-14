/**
 *
 */
package mx.isban.rorac.controller.lanzadores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.lanzadores.BeanEstatusLog;
import mx.isban.rorac.bean.lanzadores.BeanLogValidaciones;
import mx.isban.rorac.bean.lanzadores.BeanLogValidacionesRestateo;
import mx.isban.rorac.servicio.lanzadores.BODescargaLogs;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author everis
 *
 */
@Controller
public class ControllerDescargaLogs extends Architech {

	/**
	 * Constante que contiene el nombre de la pagina de logs de validaciones
	 * para restateo
	 */
	private static final String PAGINA_VALIDACIONES_RESTETO = "descargaLogValidacionesRestateo";
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constante que contiene el nombre de la pagina de logs de errores.
	 */
	private static final String PAGINA_LOGS_ERRORES = "descargaLogCruces";
	/**
	 * Constante que contiene el nombre de la pagina de logs de validaciones
	 */
	private static final String PAGINA_LOGS_VALIDACIONES = "descargaLogValidaciones";
	/**
	 * Constante que contiene el nombre de la pagina de logs de validaciones
	 * para restateo
	 */
	private static final String PAGINA_LOGS_VALIDACIONES_RESTATEO = "descargaLogValidacionesRestateo";
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion
	 * y obtener los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "DESCARGA DE LOGS DE ERROR";
	/**
	 * Constante que contiene el nombre de la pantalla LOGS
	 */
	private static final String PANTALLA = "LOGS";
	/**
	 * Constante que contiene el nombre de la pantalla VALIDACIONES
	 */
	private static final String PANTALLA_VALIDACIONES = "VALIDACIONES";
	/**
	 * Cadena que se utiliza para obtener el nombre del log a descargar desde
	 * los parametros enviados de la capa cliente.
	 */
	private static final String PARAMETRO_NOMBRE_LOG = "nombreLog";
	/**
	 * Interfaz de la capa de negocio para el modulo de descarga de logs.
	 */
	private BODescargaLogs boDescargas;

	/**
	 * Obtiene la lista de logs de errores de cruces junto con su estatus.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("logsErrores.do")
	public ModelAndView muestraLogsError(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "muestraLogsError()";
		this.info("Inicia ejecucion de metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se solicita a la capa de negocio la lista de logs de cruces.");
		List<BeanEstatusLog> listaLogErrores = boDescargas
				.obtenerEstatusLogsCruces(this.getArchitechBean());
		this.info("El numero de registros obtenidos es "
				+ listaLogErrores.size());
		final Map<String, Object> modelo = new HashMap<String, Object>();
		modelo.put("logsCruces", listaLogErrores);
		this.info("Se envia la lista de logs de errores de cruces a la capa cliente.");
		this.info("Se redirecciona al usuario a la pagina "
				+ PAGINA_LOGS_ERRORES);
		this.info("Fin de ejecucucion del metodo " + metodo);
		return new ModelAndView(PAGINA_LOGS_ERRORES, modelo);
	}

	/**
	 * Obtiene la lista de logs de validaciones junto con su estatus.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("logsValidaciones.do")
	public ModelAndView muestraLogsValidacion(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "muestraLogsValidacion()";
		this.info("Inicio de ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO,
				PANTALLA_VALIDACIONES);
		this.info("Se solicitan a la capa de negocio los logs de validaciones.");
		BeanLogValidaciones beanValidaciones = boDescargas
				.obtenerEstatusLogsValidaciones(this.getArchitechBean());
		this.info("Se envia la lista de validaciones a la capa cliente.");
		final Map<String, Object> modelo = new HashMap<String, Object>();
		modelo.put("contratoActivo",
				beanValidaciones.getValidacionesContratoActivo());
		modelo.put("contratoPasivo",
				beanValidaciones.getValidacionesContratoPasivo());
		modelo.put("totalActivo", beanValidaciones.getValidacionesTotalActivo());
		modelo.put("totalFinal", beanValidaciones.getValidacionesTotalFinal());
		this.info("La pagina destino es: " + PAGINA_LOGS_VALIDACIONES);
		this.info("Finaliza ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_LOGS_VALIDACIONES, modelo);
	}

	/**
	 * Obtiene la lista de logs de validaciones para el proceso de restateo
	 * junto con su estatus.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("logsValidacionesRestateo.do")
	public ModelAndView logsValidacionRestateo(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "logsValidacionRestateo()";
		this.info("Inicio de ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO,
				PANTALLA_VALIDACIONES);
		this.info("Se obtienen los logs de validaciones para Restateo.");
		BeanLogValidacionesRestateo beanValidaciones = boDescargas
				.obtenerLogsValidacionesRestateo(this.getArchitechBean());
		final Map<String, Object> modelo = new HashMap<String, Object>();
		modelo.put("contratoActivo",
				beanValidaciones.getValidacionesContratoActivo());
		modelo.put("contratoPasivo",
				beanValidaciones.getValidacionesContratoPasivo());
		modelo.put("adnActivo", beanValidaciones.getValidacionesAdNActivo());
		modelo.put("adnPasivo", beanValidaciones.getValidacionesAdNPasivo());
		this.info("Redireccionando el usuario a la pagina: "
				+ PAGINA_VALIDACIONES_RESTETO);
		this.info("Finaliza ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_LOGS_VALIDACIONES_RESTATEO, modelo);
	}

	/**
	 * Registra un log para que sea descargado.
	 *
	 * @param req
	 *            Request.
	 * @param res
	 *            Response.
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("descargaLogError.do")
	public ModelAndView descargaLogError(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "descargaLogError";
		this.info("Inicio del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String log = req.getParameter("log");
		final String nombreLog = req.getParameter(PARAMETRO_NOMBRE_LOG);
		this.info("El id del log a descargar es " + log);
		boDescargas.lanzaDescargaLog(log, this.getArchitechBean());
		this.info("Fue lanzada la operacion de descarga para el log" + log);
		final Map<String, Object> modelo = new HashMap<String, Object>();
		modelo.put(PARAMETRO_NOMBRE_LOG, nombreLog);
		List<BeanEstatusLog> listaLogErrores = boDescargas
				.obtenerEstatusLogsCruces(this.getArchitechBean());
		modelo.put("logsCruces", listaLogErrores);
		this.info("La pagina de destino es " + PAGINA_LOGS_ERRORES);
		return new ModelAndView(PAGINA_LOGS_ERRORES, modelo);
	}

	/**
	 * Registra un log de validacion para ser descargado.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("descargarLogValidacion.do")
	public ModelAndView descargaLogValidacion(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "descargaLogValidacion";
		this.info("Inicia ejecucion del metodo: " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO,
				PANTALLA_VALIDACIONES);
		final String log = req.getParameter("log");
		final String nombreLog = req.getParameter(PARAMETRO_NOMBRE_LOG);
		this.info("El id del log que sera descargado es " + log);
		boDescargas.lanzaDescargaLog(log, this.getArchitechBean());
		this.info("Se lanzao la operacion de descarga para el log " + log);
		final BeanLogValidaciones beanValidaciones = boDescargas
				.obtenerEstatusLogsValidaciones(this.getArchitechBean());
		final Map<String, Object> modelo = new HashMap<String, Object>();
		modelo.put("contratoActivo",
				beanValidaciones.getValidacionesContratoActivo());
		modelo.put("contratoPasivo",
				beanValidaciones.getValidacionesContratoPasivo());
		modelo.put("totalActivo", beanValidaciones.getValidacionesTotalActivo());
		modelo.put("totalFinal", beanValidaciones.getValidacionesTotalFinal());
		modelo.put(PARAMETRO_NOMBRE_LOG, nombreLog);
		this.info("La pagina destino es " + PAGINA_LOGS_VALIDACIONES);
		this.info("Ha finalizado la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_LOGS_VALIDACIONES, modelo);
	}

	/**
	 * Registra un log de validacion para ser descargado.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("descargarLogValidacionRestateo.do")
	public ModelAndView descargaLogValidacionRestateo(
			final HttpServletRequest req, final HttpServletResponse res)
			throws BusinessException {
		final String metodo = "descargaLogValidacionRestateo";
		this.info("Inicia ejecucion del metodo: " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO,
				PANTALLA_VALIDACIONES);
		final String log = req.getParameter("log");
		final String nombreLog = req.getParameter(PARAMETRO_NOMBRE_LOG);
		this.info("El id del log que sera descargado es " + log);
		boDescargas.lanzaDescargaLog(log, this.getArchitechBean());
		this.info("Se lanzo la operacion de descarga para el log " + log);
		BeanLogValidacionesRestateo beanValidaciones = boDescargas
				.obtenerLogsValidacionesRestateo(this.getArchitechBean());
		final Map<String, Object> modelo = new HashMap<String, Object>();
		modelo.put("contratoActivo",
				beanValidaciones.getValidacionesContratoActivo());
		modelo.put("contratoPasivo",
				beanValidaciones.getValidacionesContratoPasivo());
		modelo.put("adnActivo", beanValidaciones.getValidacionesAdNActivo());
		modelo.put("adnPasivo", beanValidaciones.getValidacionesAdNPasivo());
		modelo.put(PARAMETRO_NOMBRE_LOG, nombreLog);
		this.info("La pagina destino es " + PAGINA_LOGS_VALIDACIONES);
		this.info("Ha finalizado la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_LOGS_VALIDACIONES_RESTATEO, modelo);
	}

	/**
	 * Manejador de errores de este controller.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param handler
	 *            Handler.
	 * @param exception
	 *            Exception.
	 * @return ModelAndView
	 */
	@ExceptionHandler
	public ModelAndView manejoErrores(final HttpServletRequest req,
			final HttpServletResponse res, final Object handler,
			final Exception exception) {
		final String metodo = this.getClass().getName() + ".manejadorErrores";
		this.info("Inicio de ejecucion de metodo  " + metodo);
		String pagina = null;
		final Map<String, String> modelo = new HashMap<String, String>();
		if (handler instanceof BusinessException) {
			modelo.put("codeError", ((BusinessException) handler).getCode());
			pagina = "../errores/errorAgave.do";
			this.info("Fue cachada una excepcion BuisinessException "
					+ handler.toString());
		} else {
			pagina = "../errores/errorGrl.do";
			this.info("Fue cachada una excepcion " + handler.toString());
		}
		this.info("El modelo enviado al cliente es " + modelo.toString());
		this.info("La pagina de destino es " + pagina);
		return new ModelAndView("redirect:" + pagina, modelo);
	}

	/**
	 * @return the boDescargas
	 */
	public BODescargaLogs getBoDescargas() {
		return boDescargas;
	}

	/**
	 * @param boDescargas
	 *            the boDescargas to set
	 */
	public void setBoDescargas(final BODescargaLogs boDescargas) {
		this.boDescargas = boDescargas;
	}
}
