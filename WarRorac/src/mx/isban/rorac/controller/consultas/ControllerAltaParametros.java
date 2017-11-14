/**
 *
 */
package mx.isban.rorac.controller.consultas;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.servicio.consultas.BOAltaParametros;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author everis
 *
 */
@Controller
public class ControllerAltaParametros extends Architech {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 3548905251499446088L;
	/**
	 * Constante que contiene el nombre de la pagina de alta Filtros Extraccion.
	 */
	private static final String PAGINA_ALTA_FILTROS_EXTRACCION = "altaFiltrosExtraccion";
	/**
	 * Constante que contiene el nombre de la pagina de alta ADN Retail.
	 */
	private static final String PAGINA_ALTA_ADN_RETAIL = "altaFiltrosAdnRetail";
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion
	 * y obtener los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "TABLAS DE PARAMETROS";
	/**
	 * Constante que contiene el nombre de la pantalla asociada
	 */
	private static final String PANTALLA = "ALTA";
	/**
	 * Nombre de parametro con el que se indicara la operacion a la capa
	 * cliente.
	 */
	private static final String OPERACION = "operacion";
	/**
	 * Identificador con el cual se le indica a la capa cliente que la operacion
	 * a realizar es Alta.
	 */
	private static final String ALTA_PARAMETRO = "alta";
	/**
	 * Nombre del form de ADN Local que se envia a la capa cliente.
	 */
	private static final String NOMBRE_FORM_ADN_LOCAL = "adnLocalForm";
	/**
	 * Nombre del form de Producto Gestion que se envia a la capa cliente.
	 */
	private static final String NOMBRE_FORM_PRODUCTO_GESTION = "productoGestionForm";
	/**
	 * Componente de la capa de negocio.
	 */
	private BOAltaParametros altaParametros;

	/**
	 * Redirige al usuario al menu principal de altas.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             En caso de presentarse un error
	 */
	@RequestMapping("altaTablasParametros.do")
	public ModelAndView muestraMenuPrincipalAlta(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "muestraMenuPrincipalAlta()";
		this.info("Se ejecuta el metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String pagina = "tablasParametrosPrincipal";
		final Map<String, Object> modelo = new HashMap<String, Object>();
		modelo.put(OPERACION, ALTA_PARAMETRO);
		this.info("Se redirige al usuario a la pagina " + pagina);
		this.info("Finaliza la ejecucion del metodo " + metodo);

		return new ModelAndView(pagina, modelo);
	}

	/**
	 * Muestra al usuario una pagina de alta de Filtros, de acuerdo al parametro
	 * enviado desde la capa cliente.
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
	 *             En caso de presentarse un error de acceso a las pantallas
	 */
	@RequestMapping("altaParametros")
	public ModelAndView muestraPaginaAlta(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo)
			throws BusinessException {
		final String metodo = "muestraPaginaAlta()";
		this.info("Inicia ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String codigoTipoParametro = req.getParameter("param");
		this.info("El codigo de tipo de parametro que se recibio es: "
				+ codigoTipoParametro);
		String pagina = null;
		modelo.put(OPERACION, ALTA_PARAMETRO);
		if ("fe".equals(codigoTipoParametro)) {
			pagina = PAGINA_ALTA_FILTROS_EXTRACCION;
			final BeanADNLocal adnLocal = new BeanADNLocal();
			modelo.put(NOMBRE_FORM_ADN_LOCAL, adnLocal);
			final BeanProductoGestion productoGestion = new BeanProductoGestion();
			modelo.put(NOMBRE_FORM_PRODUCTO_GESTION, productoGestion);
		} else if ("ar".equals(codigoTipoParametro)) {
			pagina = PAGINA_ALTA_ADN_RETAIL;
			final BeanADNRetail beanAdn = new BeanADNRetail();
			modelo.put("adnRetailForm", beanAdn);
		} else {
			this.info("El parametro recibido desde la capa cliente es incorrecto: "
					+ codigoTipoParametro);
			throw new IllegalArgumentException();
		}

		this.info("El usuario sera redireccionado a la pagina: " + pagina);
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(pagina, modelo);
	}

	/**
	 * Recibe la peticion de la capa cliente para realizar el alta de un
	 * registro de tipo Adn Local.
	 *
	 * @param adnLocal
	 *            Contiene los datos del registro que sera dado de alta.
	 * @param modelo
	 *            Objeto que representa el modelo de Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("altaAdnLocal.do")
	public ModelAndView altaADNLocal(
			@ModelAttribute(NOMBRE_FORM_ADN_LOCAL) final BeanADNLocal adnLocal,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "altaADNLocal()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("Los parametros del nuevo registro ADN Local son:");
		this.info("idAdnLocal: " + adnLocal.getIdAdnLocal());
		this.info("Descripcion: " + adnLocal.getDescripcion());
		this.info("Banca: " + adnLocal.getBanca());
		this.info("FlagActivo: " + adnLocal.getFlagActivo());
		this.info("FlagPasivo: " + adnLocal.getFlagPasivo());
		this.info("FlagFondos: " + adnLocal.getFlagFondos());
		this.info("FlagComiciones: " + adnLocal.getFlagComiciones());
		this.info("FlagContingentes: " + adnLocal.getFlagContingentes());
		this.info("FlagAjustes: " + adnLocal.getFlagAjustes());
		this.info("FlagInternegocios: " + adnLocal.getFlagInternegocios());
		this.info("Se realiza la comunicacion con la capa de negocio para realizar el alta del parametro de tipo ADN Local");
		altaParametros.llamaAltaADNLocal(adnLocal, this.getArchitechBean());
		modelo.put(OPERACION, ALTA_PARAMETRO);
		modelo.put("confirmacionAltaAdnLocal", adnLocal.getIdAdnLocal());
		modelo.put(NOMBRE_FORM_ADN_LOCAL, new BeanADNLocal());
		modelo.put(NOMBRE_FORM_PRODUCTO_GESTION, new BeanProductoGestion());
		this.info("Se redirecciona al usuario hacia la pagina "
				+ PAGINA_ALTA_FILTROS_EXTRACCION);
		return new ModelAndView(PAGINA_ALTA_FILTROS_EXTRACCION, modelo);
	}

	/**
	 * Recibe la peticion de la capa cliente para realizar el alta de un
	 * registro de tipo Producto Gestion.
	 *
	 * @param productoGestion
	 *            Contiene la informacion del registro de Producto Gestion que
	 *            va a ser dado de alta.
	 * @param modelo
	 *            Modelo de Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("altaProductoGestion.do")
	public ModelAndView altaProductoGestion(
			@ModelAttribute(NOMBRE_FORM_PRODUCTO_GESTION) final BeanProductoGestion productoGestion,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "altaProductoGestion()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		this.info("Los parametros del nuevo registro de Producto Gestion son:");
		this.info("idProductoGestion: "
				+ productoGestion.getIdProductoGestion());
		this.info("Descripcion: " + productoGestion.getDescripcion());
		this.info("FlagActivo: " + productoGestion.getFlagActivo());
		this.info("FlagPasivo: " + productoGestion.getFlagPasivo());
		this.info("FlagFondos: " + productoGestion.getFlagFondos());
		this.info("FlagComiciones: " + productoGestion.getFlagComiciones());
		this.info("FlagContingentes: " + productoGestion.getFlagContingentes());
		this.info("FlagAjustes:  " + productoGestion.getFlagAjustes());
		this.info("FlagInternegocios:  "
				+ productoGestion.getFlagInternegocios());
		this.info("Se realiza la comunicacion con la capa de negocio para realizar el alta del nuevo registro Producto Gestion");
		altaParametros.llamaAltaProductoGestion(productoGestion,
				this.getArchitechBean());
		modelo.put(OPERACION, ALTA_PARAMETRO);
		modelo.put("confirmacionAltaProductoGestion",
				productoGestion.getIdProductoGestion());
		modelo.put(NOMBRE_FORM_PRODUCTO_GESTION, new BeanProductoGestion());
		modelo.put(NOMBRE_FORM_ADN_LOCAL, new BeanADNLocal());
		this.info("El usuario sera redireccionado a la pagina "
				+ PAGINA_ALTA_FILTROS_EXTRACCION);
		this.info("Termina la ejecucion del metodo " + metodo);

		return new ModelAndView(PAGINA_ALTA_FILTROS_EXTRACCION, modelo);
	}

	/**
	 * Recibe la peticion de la capa cliente para realizar el alta de un
	 * registro de tipo Adn Retail y No Retail.
	 *
	 * @param adnRetail
	 *            Contiene los datos ingresados por el usuario.
	 * @param modelo
	 *            Objeto en el que se registran los objetos que se mostraran en
	 *            la pagina a la cual se redirecciona el usuario.
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("altaAdnRetail.do")
	public ModelAndView altaADNRetail(
			@ModelAttribute("adnRetailForm") final BeanADNRetail adnRetail,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "altaADNRetail()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		this.info("Los parametros recibidos de la capa cliente son:");
		this.info("IdSegmentoLocal: " + adnRetail.getIdSegmentoLocal());
		this.info("Descripcion: " + adnRetail.getDescripcion());
		this.info("Banca: " + adnRetail.getBanca());
		this.info("FlagRetail: " + adnRetail.getFlagRetail());
		this.info("Ser realiza la solicitud a la capa de negocio para realizar el alta del parametro.");
		altaParametros.llamaAltaADNRetail(adnRetail, this.getArchitechBean());
		modelo.put(OPERACION, ALTA_PARAMETRO);
		modelo.put("confirmacionAlta", adnRetail.getIdSegmentoLocal());
		modelo.put("adnRetailForm", new BeanADNRetail());
		this.info("El usuario es redireccionado a la pagina "
				+ PAGINA_ALTA_ADN_RETAIL);
		return new ModelAndView(PAGINA_ALTA_ADN_RETAIL, modelo);
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
	 * @return the altaParametros
	 */
	public BOAltaParametros getAltaParametros() {
		return altaParametros;
	}

	/**
	 * @param altaParametros
	 *            the altaParametros to set
	 */
	public void setAltaParametros(final BOAltaParametros altaParametros) {
		this.altaParametros = altaParametros;
	}
}
