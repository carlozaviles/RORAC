package mx.isban.rorac.controller.consultas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.consultas.BeanEstatusCarga;
import mx.isban.rorac.bean.consultas.BeanMonitorCargas;
import mx.isban.rorac.bean.consultas.BeanMonitorCargasRestateo;
import mx.isban.rorac.servicio.consultas.BOMonitorCargas;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerMonitorCargas extends Architech {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion
	 * y obtener los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "CARGAS MANUALES";
	/**
	 * Constante que contiene el nombre de la pantalla monitor de cargas
	 */
	private static final String PANTALLA = "MONITOR DE CARGAS";

	/**
	 * Objeto de la capa de negocio que sirve para obtener el estatus de las
	 * cargas.
	 */
	private BOMonitorCargas monitorCargas;

	/**
	 * Muestra el formulario que permite al usuario la operacion de ejecucion de
	 * Motor Rorac.
	 *
	 * @param request
	 *            Request
	 * @param response
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Excepcion
	 */
	@RequestMapping("monitorCargas.do")
	public ModelAndView consultaMonitorCargas(final HttpServletRequest request,
			final HttpServletResponse response, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "consultaMonitorCargas()";
		this.info("Iniciando la ejecucion del metodo " + metodo);
		final Object objeto = request.getSession().getAttribute(
				MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se reliaza la peticion a la capa de negocio para obtener el estatus de las cargas.");
		final BeanMonitorCargas beanMonitor = monitorCargas
				.obtenerEstatusCargas(this.getArchitechBean());
		modelo.put("estatusCargas", beanMonitor);
		final List<List<BeanEstatusCarga>> insumos = new ArrayList<List<BeanEstatusCarga>>();
		insumos.add(beanMonitor.getCargasManuales());
		insumos.add(beanMonitor.getInterfacesProcesadas());
		modelo.put("insumos", insumos);
		final String pagina = "monitorCargas";
		this.info("Se redirecciona al usuario hacia la pagina " + pagina);
		this.info("Finaliza la ejecucion del metodo " + metodo);
		for (BeanEstatusCarga b : beanMonitor.getCargasManuales()) {
			this.info(b.getNombreInterfaz() + " " + b.getDetalleError());
		}
		return new ModelAndView(pagina, modelo);
	}

	/**
	 * Muestra el formulario que permite al usuario la operacion de ejecucion de
	 * Motor Rorac.
	 *
	 * @param request
	 *            Request
	 * @param response
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView objeto que contiene el modelo y la vista
	 * @throws BusinessException
	 *             Excepcion lanzada al ocurrir un error
	 */
	@RequestMapping("monitorCargasRestateo.do")
	public ModelAndView consultaMonitorCargasRestateo(
			final HttpServletRequest request,
			final HttpServletResponse response, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "consultaMonitorCargasRestateo()";
		this.info("Iniciando la ejecucion del metodo " + metodo);
		final Object objeto = request.getSession().getAttribute(
				MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se reliaza la peticion a la capa de negocio para obtener el estatus de las cargas.");
		final BeanMonitorCargasRestateo beanMonitor = monitorCargas
				.obtenerEstatusCargasRestateo(this.getArchitechBean());
		final List<List<BeanEstatusCarga>> insumos = new ArrayList<List<BeanEstatusCarga>>();
		insumos.add(beanMonitor.getRestCliente());
		insumos.add(beanMonitor.getRestActivo());
		insumos.add(beanMonitor.getRestPasivo());
		insumos.add(beanMonitor.getRestContingentes());
		insumos.add(beanMonitor.getRestFondos());
		insumos.add(beanMonitor.getRestComisiones());
		modelo.put("insumos", insumos);
		final String pagina = "monitorCargasRestateo";
		this.info("Se redirecciona al usuario hacia la pagina " + pagina);
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(pagina, modelo);
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
		this.info("Inicio de ejecucion de metodo " + metodo);
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
	 * @return the monitorCargas
	 */
	public BOMonitorCargas getMonitorCargas() {
		return monitorCargas;
	}

	/**
	 * @param monitorCargas
	 *            the monitorCargas to set
	 */
	public void setMonitorCargas(final BOMonitorCargas monitorCargas) {
		this.monitorCargas = monitorCargas;
	}
}
