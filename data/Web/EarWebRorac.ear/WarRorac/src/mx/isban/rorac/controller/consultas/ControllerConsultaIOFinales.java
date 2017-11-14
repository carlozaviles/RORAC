/**
 *
 */
package mx.isban.rorac.controller.consultas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.servicio.consultas.BOConsultaIOFinales;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;
import mx.isban.rorac.util.general.UtileriasFront;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author everis
 */
@Controller
public class ControllerConsultaIOFinales extends Architech {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 8655241145103309290L;
	/**
	 * Pagina con el formulario para realizar las consultas de Inputs y Outpus
	 * Finales
	 */
	private static final String PAGINA_CONSULTAS_IO_FINALES = "consultaIOFinales";
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion
	 * y obtener los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "CONSULTA DE INPUTS Y OUTPUTS";
	/**
	 * Constante que contiene el nombre de la pantalla asociada
	 */
	private static final String PANTALLA = "INPUTS Y OUTPUTS POR CONTRATO";
	/**
	 * Objeto de la capa de negocio encargado de ejecutar la consulta de IO
	 * Finales por contratos.
	 */
	private BOConsultaIOFinales consultaIOFinales;

	/**
	 * Muestra el menu por medio de el cual el usuario introducira los
	 * parametros necesarios para ejecutar la consulta.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response.
	 * @return ModelAndView
	 * @throws BusinessException
	 */
	@RequestMapping("menuConsultaIOFinales.do")
	public ModelAndView muestraMenuConsulta(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "muestraMenuConsulta()";
		this.info("Inicial la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		this.info("El usuario es redirigido hacia la pagina "
				+ PAGINA_CONSULTAS_IO_FINALES);
		this.info("Termina la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_CONSULTAS_IO_FINALES);
	}

	/**
	 * Ejecuta la consulta de IO Finales, con los parametros informados por el
	 * usuario.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo String MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("consultaIOFinales.do")
	public ModelAndView ejecutaConsultaIOFinales(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "ejecutaConsultaIOFinales()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se realiza la peticion a la capa de negocio para ejecutar la consulta de IO Finales por contrato.");
		final List<HashMap<String, Object>> resultado = consultaIOFinales
				.llamaConsultaIOFinales(req.getParameter("numeroContrato"),
						req.getParameter("idArchivo"),
						req.getParameter("anio"), req.getParameter("mes"),
						this.getArchitechBean());
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		modelo.put("resultadoConsulta", resultado);
		modelo.put("consultaEjecutada", req.getParameter("idArchivo"));
		modelo.put("contrato", req.getParameter("numeroContrato"));
		this.info("El resultado de la consulta es enviado hacia la capa cliente.");
		this.info("El usuario es redirigido hacia la pagina "
				+ PAGINA_CONSULTAS_IO_FINALES);
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_CONSULTAS_IO_FINALES, modelo);
	}

	/**
	 * Manejador de errores de este controller.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param handler
	 *            Handler
	 * @param excepcion
	 *            Exception
	 * @return ModelAndView
	 */
	@ExceptionHandler
	public ModelAndView manejadorErrores(final HttpServletRequest req,
			final HttpServletResponse res, final Object handler,
			final Exception excepcion) {
		final String metodo = this.getClass().getName() + ".manejadorErrores";
		this.debug("Inicio de ejecucion de metodo " + metodo);
		String pagina = null;
		final Map<String, String> modelo = new HashMap<String, String>();
		if (handler instanceof BusinessException) {
			modelo.put("codeError", ((BusinessException) handler).getCode());
			pagina = "../errores/errorAgave.do";
			this.debug("Fue cachada una excepcion BuisinessException "
					+ handler.toString());
		} else {
			pagina = "../errores/errorGrl.do";
			this.debug("Fue cachada una excepcion " + handler.toString());
		}
		this.debug("El modelo enviado al cliente es " + modelo.toString());
		this.debug("La pagina de destino es " + pagina);
		return new ModelAndView("redirect:" + pagina, modelo);
	}

	/**
	 * @return the consultaIOFinales
	 */
	public BOConsultaIOFinales getConsultaIOFinales() {
		return consultaIOFinales;
	}

	/**
	 * @param consultaIOFinales
	 *            the consultaIOFinales to set
	 */
	public void setConsultaIOFinales(final BOConsultaIOFinales consultaIOFinales) {
		this.consultaIOFinales = consultaIOFinales;
	}
}
