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
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanFlagNeteo;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.form.util.FormOpcionesCheckBox;
import mx.isban.rorac.servicio.consultas.BOConsultaParametros;
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
public class ControllerConsultaParametros extends Architech {

	/**
	 * Identificador en el que se guardan los resultados de la consulta de ADN
	 * Retail.
	 */
	public static final String RESULTADOS_CONSULTA_ADN_RETAIL = "resultadoAdnRetail";
	/**
	 * Identificador en el que se guardan los resultados de la consulta de ADN
	 * Local.
	 */
	public static final String RESULTADOS_CONSULTA_ADN_LOCAL = "resultadoAdnLocal";
	/**
	 * Identificador en el que se guardan los resultados de la consulta de
	 * Producto Gestion.
	 */
	public static final String RESULTADOS_CONSULTA_PRODUCTO_GESTION = "resultadoProductoGestion";
	/**
	 * Identificado con el que se guardan los resultados de la consulta
	 * FlagNeteo.
	 */
	public static final String RESULTADOS_CONSULTA_FLAGNETEO = "resultadoFlagNeteo";
	/**
	 * Nombre con el cual se envia el formulario para eliminar datos a la capa
	 * cliente.
	 */
	public static final String FORM_REGISTROS_A_ELIMINAR = "registrosAEliminar";
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 4063896055320754638L;
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "TABLAS DE PARAMETROS";
	/**
	 * Constante que contiene el nombre de la pantalla CONSULTA
	 */
	private static final String PANTALLA = "CONSULTA";
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion
	 * y obtener los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Objeto de la capa de Negocio mediante el cual se realizan las consultas
	 * de Parametros.
	 */
	private BOConsultaParametros consultaParametros;

	/**
	 * Muestra al usuario el menu principal de consultas.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Objeto en el que se registran los objetos que se mostraran en
	 *            la pagina a la cual se redirecciona el usuario.
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Excepcion que se puede presentar si el usuario no tiene
	 *             permisos de acceder a la pantalla
	 */
	@RequestMapping("consultaTablasParametros.do")
	public ModelAndView muestraMenuPrincipalConsultas(
			final HttpServletRequest req, final HttpServletResponse res,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "muestraMenuPrincipalConsultas()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String pagina = "tablasParametrosPrincipal";
		this.info("El usuario es redireccionado a la pagina " + pagina);
		modelo.put("operacion", "consulta");
		this.info("Termina la ejecucion del metodo " + metodo);
		return new ModelAndView(pagina, modelo);
	}

	/**
	 * Realiza la consulta de los Filtros de Extraccion.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("consultarFiltrosExtraccion.do")
	public ModelAndView consultaFiltrosExtraccion(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "consultaFiltrosExtraccion()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se establece comunicacion con la capa de negocio para obtener los registros de tipo Adn Local");
		List<BeanADNLocal> listaAdnLocal = consultaParametros
				.llamaConsultaADNLocal(this.getArchitechBean());
		this.info("Se establece comunicacion con la capa de negocio para obtener los registros de tipo producto gestion.");
		List<BeanProductoGestion> listaProductoGestion = consultaParametros
				.llamaConsultaProductoGestion(this.getArchitechBean());
		this.info("Se envian los resultados a la capa cliente");
		req.getSession().setAttribute(RESULTADOS_CONSULTA_ADN_LOCAL,
				listaAdnLocal);
		req.getSession().setAttribute(RESULTADOS_CONSULTA_PRODUCTO_GESTION,
				listaProductoGestion);
		modelo.put("listaAdnLocal", listaAdnLocal);
		modelo.put("listaProductoGestion", listaProductoGestion);
		modelo.put(FORM_REGISTROS_A_ELIMINAR, new FormOpcionesCheckBox());
		final String pagina = "consultaFiltrosExtraccion";
		this.info("Se redirecciona al usuario a la pagina " + pagina);
		return new ModelAndView(pagina, modelo);
	}

	/**
	 * Realiza la consulta de los filtros ADN Retail y No Retail.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("consultaAdnRetail.do")
	public ModelAndView consultaADNRetail(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "consultaADNRetail()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se realiza la peticion de la consulta de los filtros ADN Retail a la capa de negocio.");
		final List<BeanADNRetail> resultados = consultaParametros
				.llamaConsultaADNRetail(this.getArchitechBean());
		req.getSession().setAttribute(RESULTADOS_CONSULTA_ADN_RETAIL,
				resultados);
		this.info("Se recibe el siguiente numero de registros: "
				+ resultados.size());
		this.info("Se envian los resultados a la capa cliente.");
		this.info("Tamanio de modelo " + modelo.entrySet().size());
		modelo.put("listaAdnRetail", resultados);
		modelo.put(FORM_REGISTROS_A_ELIMINAR, new FormOpcionesCheckBox());
		final String pagina = "consultaFiltrosAdnRetail";
		this.info("Se redirecciona al usuario hacia la pagina " + pagina);
		this.info("Finaliza el metodo " + metodo);
		return new ModelAndView(pagina, modelo);
	}

	/**
	 * Realiza la peticion de la consulta de FlagNeteo a la capa de negocio.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("consultaFlagNeteo.do")
	public ModelAndView consultaFlagNeteo(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "consultaFlagNeteo()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se realiza la peticion a la capa de negocio para que se ejecute la consuta de FlagNeteo");
		List<BeanFlagNeteo> resultados = consultaParametros
				.llamaConsultaFlagNeteo(this.getArchitechBean());
		req.getSession()
				.setAttribute(RESULTADOS_CONSULTA_FLAGNETEO, resultados);
		this.info("Se recibe el siguiente numero de registros: "
				+ resultados.size());
		this.info("Se envian los resultados a la capa cliente.");
		modelo.put("listaFlagNeteo", resultados);
		// modelo.put(FORM_REGISTROS_A_ELIMINAR, new FormOpcionesCheckBox());
		final String pagina = "consultaFiltrosFlagNeteo";
		this.info("Se redirecciona al usuario a la pagina " + pagina);
		this.info("Termina la ejecucion del metodo " + metodo);
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
	 * @return the consultaParametros
	 */
	public BOConsultaParametros getConsultaParametros() {
		return consultaParametros;
	}

	/**
	 * @param consultaParametros
	 *            the consultaParametros to set
	 */
	public void setConsultaParametros(
			final BOConsultaParametros consultaParametros) {
		this.consultaParametros = consultaParametros;
	}
}
