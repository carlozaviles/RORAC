/**
 *
 */
package mx.isban.rorac.controller.lanzadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.consultas.BeanEstatusCarga;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoMotorRorac;
import mx.isban.rorac.bean.lanzadores.BeanMonitorMotor;
import mx.isban.rorac.servicio.lanzadores.BOLanzadores;
import mx.isban.rorac.servicio.lanzadores.BOMonitorMotor;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;
import mx.isban.rorac.util.general.UtileriasFront;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author everis
 *
 */
@Controller
public class ControllerMotorRorac extends Architech {

	/**
	 * Variable para determinar que se requiere validacion
	 */
	private static final String VALIDACION_MOTOR = "2";
	/**
	 * Variable para determinar cuando se solicita una ejecucion del motor
	 */
	private static final String EJECUCION_MOTOR = "1";
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 3186991635272495539L;
	/**
	 * Pagina para el lanzamiento del motor RORAC.
	 */
	private static final String PAGINA_MOTOR_RORAC = "lanzadorMotor";
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion
	 * y obtener los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "EDICION,CARGA Y EJECUCION DE INPUTS Y OUTPUTS FINALES";
	/**
	 * Constante que contiene el nombre de la pantalla asociada
	 */
	private static final String PANTALLA = "LANZADOR DE MOTOR RORAC";
	/**
	 * Objeto de la capa de negocio que sirve para lanzar la ejecucion del motor
	 * rorac.
	 */
	private BOLanzadores lanzador;
	/**
	 * Objeto encargado de obtener los estatus para el monitor del motor
	 */
	private BOMonitorMotor monitorMotor;

	/**
	 * Muestra el formulario para que el usuario lance la ejecucion del motor
	 * RORAC.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 */
	@RequestMapping("menuMotorRorac.do")
	public ModelAndView muestraFormularioMotorRorac(
			final HttpServletRequest req, final HttpServletResponse res,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "muestraFormularioMotorRorac()";
		this.info("Inicia ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		modelo.put("insumos", obtieneEstatusInsumos());
		modelo.put("ejecucionMotorForm", new BeanLanzamientoMotorRorac());
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		this.info("Se redirecciona al usuario hacia la pagina "
				+ PAGINA_MOTOR_RORAC);
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_MOTOR_RORAC, modelo);
	}

	/**
	 * @throws IOException
	 *             Lanza la ejecucion del motor RORAC.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC.
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("ejecutaMotorRorac.do")
	public ModelAndView ejecutaMotorRorac(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo)
			throws BusinessException, IOException {
		final String metodo = "ejecutaMotorRorac()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		if (req.getParameter("tipoEjecucion").equalsIgnoreCase(EJECUCION_MOTOR)) {
			this.info("Se solicita a la capa de negocio el lanzamiento de ejecucion del motor RORAC.");
			lanzador.lanzaMotorRorac(this.getArchitechBean());
			modelo.put("ejecucionMotorForm", new BeanLanzamientoMotorRorac());
			modelo.put("confirmacion", "ok");
			this.info("El lanzamiento de ejecucion del motor RORAC ha sido registrado.");
		} else {
			this.info("Consultando el estatus de los insumos para el motor RORAC.");
			BeanLanzamientoMotorRorac beanForm = obtieneValoresDelFormulario(req);
			if (req.getParameter("tipoEjecucion").equalsIgnoreCase(VALIDACION_MOTOR)) {
				if (lanzador.hayEjecucionEnProgreso(this.getArchitechBean())) {
					beanForm = new BeanLanzamientoMotorRorac();
					modelo.put("validacion", "fail");
				} else {
					lanzador.executeValidacionMotor(this.getArchitechBean(),beanForm);
					modelo.put("validacion", "ok");
				}
			}
			modelo.put("ejecucionMotorForm", beanForm);
		}
		modelo.put("tipoEjecucion", req.getParameter("tipoEjecucion"));
		modelo.put("insumos", obtieneEstatusInsumos());
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		this.info("Se redirecciona al usuario a la pagina "
				+ PAGINA_MOTOR_RORAC);
		this.info("Termina la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_MOTOR_RORAC, modelo);
	}

	/**
	 * Metodo encargado de obtener las listas de los estatus de los insumos para
	 * el motor
	 *
	 * @return la lista de los estatus para los insumos del motor
	 * @throws BusinessException
	 *             lanzada al ocurrir una excepcion
	 */
	private List<List<BeanEstatusCarga>> obtieneEstatusInsumos()
			throws BusinessException {
		final BeanMonitorMotor beanMonitorMotor = monitorMotor.obtenerEstatusMotor(this.getArchitechBean());
		final List<List<BeanEstatusCarga>> insumos = new ArrayList<List<BeanEstatusCarga>>();
		insumos.add(beanMonitorMotor.getInsumosMotorRorac());
		insumos.add(beanMonitorMotor.getInsumosCorporativos());
		insumos.add(beanMonitorMotor.getLanzamientoMotorRorac());
		return insumos;
	}

	/**
	 * Obtiene los valores de la pagina que fueron enviados
	 *
	 * @param req
	 *            el objeto request de la peticion
	 * @return el bean que corresponde al modelo de la pantalla
	 */
	private BeanLanzamientoMotorRorac obtieneValoresDelFormulario(
			final HttpServletRequest req) {
		BeanLanzamientoMotorRorac beanLMR = new BeanLanzamientoMotorRorac();
		beanLMR.setAnio(req.getParameter("anio"));
		beanLMR.setMes(req.getParameter("mes"));
		beanLMR.setFinalidad(req.getParameter("finalidad"));
		beanLMR.setDivisa(req.getParameter("divisa"));
		beanLMR.setValorN(req.getParameter("valorN"));
		return beanLMR;
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
	 * @return the lanzador
	 */
	public BOLanzadores getLanzador() {
		return lanzador;
	}

	/**
	 * @param lanzador
	 *            the lanzador to set
	 */
	public void setLanzador(final BOLanzadores lanzador) {
		this.lanzador = lanzador;
	}

	public BOMonitorMotor getMonitorMotor() {
		return monitorMotor;
	}

	public void setMonitorMotor(final BOMonitorMotor monitorMotor) {
		this.monitorMotor = monitorMotor;
	}

}
