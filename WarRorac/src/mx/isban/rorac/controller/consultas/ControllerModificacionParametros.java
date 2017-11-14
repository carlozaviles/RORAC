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
import mx.isban.rorac.servicio.consultas.BOModificacionParametros;

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
public class ControllerModificacionParametros extends Architech {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2310207579648833480L;
	/**
	 * Pagina alta de filtros Extraccion.
	 */
	private static final String PAGINA_ALTA_FILTROS_EXTRACCION = "altaFiltrosExtraccion";
	/**
	 * Pagina de alta para filtros Retail y No Retail
	 */
	private static final String PAGINA_ALTA_ADN_RETAIL = "altaFiltrosAdnRetail";
	/**
	 * Pagina de alta para FlagNeteo
	 */
	private static final String PAGINA_FLAGNETEO = "altaFiltroFlagNeteo";
	/**
	 * Valor con el cual se le indica a la capa cliente el tipo de operacion que
	 * se realiza.
	 */
	private static final String OPERACION = "operacion";
	/**
	 * Valor con el cual se indica a la capa cliente que la operacion es de
	 * actualizacion.
	 */
	private static final String ACTUALIZA_PARAMETRO = "modifica";
	/**
	 * Nombre del warning que se suprime.
	 */
	private static final String UNCHECKED = "unchecked";
	/**
	 * Objeto de la capa de negocio que contiene los metodos para modificacion y
	 * eliminacion de los diferentes filtros.
	 */
	private BOModificacionParametros modificacionParametros;

	/**
	 * Muestra el formulario para que el usuario edite un registro Adn Local.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 */
	@RequestMapping("muestraAdnLocal.do")
	public ModelAndView muestraFormularioAdnLocal(final HttpServletRequest req,
			final HttpServletResponse res, final Map<String, Object> modelo) {
		final String metodo = "muestraFormularioAdnLocal()";
		this.info("Inicio de ejecucion del metodo " + metodo);
		final String idRegistro = req.getParameter("id");
		this.info("El registro que el usuario desea modificar tiene un idADNLocal: "
				+ idRegistro);
		BeanADNLocal registroAdnLocal = null;
		@SuppressWarnings(UNCHECKED)
		final List<BeanADNLocal> resultadosConsultaAdnLocal = (List<BeanADNLocal>) req
				.getSession()
				.getAttribute(
						ControllerConsultaParametros.RESULTADOS_CONSULTA_ADN_LOCAL);
		for (BeanADNLocal registro : resultadosConsultaAdnLocal) {
			if (idRegistro.trim().equals(registro.getIdAdnLocal())) {
				registroAdnLocal = registro;
			}
		}
		modelo.put("adnLocalForm", registroAdnLocal);
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		modelo.put("valorIdAdnLocal", registroAdnLocal.getIdAdnLocal());
		this.info("Se redirecciona al usuario hacia la pagina: "
				+ PAGINA_ALTA_FILTROS_EXTRACCION);
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_ALTA_FILTROS_EXTRACCION, modelo);
	}

	/**
	 * Se comunica con la capa de negocio para que se realice la actualizacion
	 * en BD del registro editado por el usuario.
	 *
	 * @param adnLocal
	 *            Registro AdnLocal editado por el usuario.
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("modificaAdnLocal.do")
	public ModelAndView modificaAdnLocal(
			@ModelAttribute("adnLocalForm") final BeanADNLocal adnLocal,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "modificaAdnLocal()";
		this.info("Inicia la ejecucucion del metodo " + metodo);
		this.info("El registro ADN Local editado por el usuario contiene los siguientes valores:");
		this.info("idAdnLocal: " + adnLocal.getIdAdnLocal());
		this.info("Descripcion: " + adnLocal.getDescripcion());
		this.info("Banca: " + adnLocal.getBanca());
		this.info("FlagActivo: " + adnLocal.getFlagActivo());
		this.info("FlagPasivo: " + adnLocal.getFlagPasivo());
		this.info("FlagFondos: " + adnLocal.getFlagFondos());
		this.info("FlagComiciones: " + adnLocal.getFlagComiciones());
		this.info("FlagContingentes: " + adnLocal.getFlagContingentes());
		modificacionParametros.llamaModificacionADNLocal(adnLocal,
				this.getArchitechBean());
		this.info("Se redirecciona al usuario hacia la pagina "
				+ PAGINA_ALTA_FILTROS_EXTRACCION);
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		modelo.put("confirmacionModificacionAdnLocal", adnLocal.getIdAdnLocal());
		modelo.put("valorIdAdnLocal", adnLocal.getIdAdnLocal());
		return new ModelAndView(PAGINA_ALTA_FILTROS_EXTRACCION, modelo);
	}

	/**
	 * Muestra el formulario para que el usuario edite el registro de Producto
	 * Gestion.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 */
	@RequestMapping("muestraProductoGestion.do")
	public ModelAndView muestraFormularioProductoGestion(
			final HttpServletRequest req, final HttpServletResponse res,
			final Map<String, Object> modelo) {
		final String metodo = "muestraFormularioProductoGestion()";
		this.info("Comienza a ejecutarse el metodo " + metodo);
		final String idRegistro = req.getParameter("id");
		this.info("El registro que el usuario eligio tiene un idProductoGestion: "
				+ idRegistro);

		@SuppressWarnings(UNCHECKED)
		final List<BeanProductoGestion> consultaProductoGestion = (List<BeanProductoGestion>) req
				.getSession()
				.getAttribute(
						ControllerConsultaParametros.RESULTADOS_CONSULTA_PRODUCTO_GESTION);
		BeanProductoGestion registroPorEditar = null;
		for (BeanProductoGestion registro : consultaProductoGestion) {
			if (idRegistro.trim().equals(registro.getIdProductoGestion())) {
				registroPorEditar = registro;
			}
		}
		modelo.put("productoGestionForm", registroPorEditar);
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		modelo.put("valorIdProductoGestion",
				registroPorEditar.getIdProductoGestion());
		this.info("Se redirecciona al usuario hacia la pagina "
				+ PAGINA_ALTA_FILTROS_EXTRACCION);
		this.info("Termina la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_ALTA_FILTROS_EXTRACCION, modelo);
	}

	/**
	 * Se comunica con la capa de negocio para actualizar el registro Producto
	 * Gestion actualizado por el usuario.
	 *
	 * @param productoGestion
	 *            Registro editado por el usuario.
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("modificaProductoGestion.do")
	public ModelAndView modificaProductoGestion(
			@ModelAttribute("productoGestionForm") final BeanProductoGestion productoGestion,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "modificaProductoGestion()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		this.info("Los datos del registro Producto Gestion que el usuario edito son los siguientes: ");
		this.info("idProductoGestion: "
				+ productoGestion.getIdProductoGestion());
		this.info("Descripcion: " + productoGestion.getDescripcion());
		this.info("FlagActivo: " + productoGestion.getFlagActivo());
		this.info("FlagPasivo: " + productoGestion.getFlagPasivo());
		this.info("FlagFondos: " + productoGestion.getFlagFondos());
		this.info("FlagComiciones: " + productoGestion.getFlagComiciones());
		this.info("FlagContingentes: " + productoGestion.getFlagContingentes());
		modificacionParametros.llamaModificacionProductoGestion(
				productoGestion, this.getArchitechBean());
		this.info("Se redirecciona al usuario a la pagina "
				+ PAGINA_ALTA_FILTROS_EXTRACCION);
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		modelo.put("confirmacionModificacionProductoGestion",
				productoGestion.getIdProductoGestion());
		modelo.put("valorIdProductoGestion",
				productoGestion.getIdProductoGestion());
		this.info("Termina la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_ALTA_FILTROS_EXTRACCION, modelo);
	}

	/**
	 * Muestra el formulario para que el usuario edite un registro ADNRetail
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring
	 * @return ModelAndView
	 */
	@RequestMapping("muestraAdnRetail.do")
	public ModelAndView muestraFormularioAdnRetail(
			final HttpServletRequest req, final HttpServletResponse res,
			final Map<String, Object> modelo) {
		final String metodo = "muestraFormularioAdnRetail()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final String idRegistro = req.getParameter("id");
		this.info("El campo idSegmento Local que el usuario desea modificar es: "
				+ idRegistro);
		BeanADNRetail registroAdnRetail = null;
		@SuppressWarnings(UNCHECKED)
		final List<BeanADNRetail> resultadosConsultaAdnRetail = (List<BeanADNRetail>) req
				.getSession()
				.getAttribute(
						ControllerConsultaParametros.RESULTADOS_CONSULTA_ADN_RETAIL);
		for (BeanADNRetail registro : resultadosConsultaAdnRetail) {
			if (idRegistro.trim().equals(registro.getIdSegmentoLocal())) {
				registroAdnRetail = registro;
			}
		}
		modelo.put("adnRetailForm", registroAdnRetail);
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		modelo.put("valorIdSegmento", registroAdnRetail.getIdSegmentoLocal());
		final String pagina = PAGINA_ALTA_ADN_RETAIL;
		this.info("Se redirecciona al usuario a la pagina " + pagina);
		return new ModelAndView(pagina, modelo);
	}

	/**
	 * Se comunica con la capa de negocio para realizar la modificacion de un
	 * registro ADN Retail y No Retail.
	 *
	 * @param adnRetail
	 *            Continen el registro modificado por el usuario de Adn Retail.
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("modificaAdnRetail.do")
	public ModelAndView modificaAdnRetail(
			@ModelAttribute("adnRetailForm") final BeanADNRetail adnRetail,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "modificaAdnRetail()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("Los datos del registro ADN Retail y No Retail actualizado se muestran a continuacion");
		this.info("IdSegmentoLocal: " + adnRetail.getIdSegmentoLocal());
		this.info("Descripcion: " + adnRetail.getDescripcion());
		this.info("Banca: " + adnRetail.getBanca());
		this.info("FlagRetail: " + adnRetail.getFlagRetail());
		modificacionParametros.llamaModificacionADNRetail(adnRetail,
				this.getArchitechBean());
		this.info("Se redirecciona al usuario a la pagina "
				+ PAGINA_ALTA_ADN_RETAIL);
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		modelo.put("confirmacionModificacion", adnRetail.getIdSegmentoLocal());
		modelo.put("valorIdSegmento", adnRetail.getIdSegmentoLocal());
		this.info("Se direcciona al usuario hacia la pagina: "
				+ PAGINA_ALTA_ADN_RETAIL);
		return new ModelAndView(PAGINA_ALTA_ADN_RETAIL, modelo);
	}

	/**
	 * Muestra el formulario para que el usuario edite un registro FlagNeteo.
	 *
	 * @param req
	 *            Request.
	 * @param res
	 *            Response
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 */
	@RequestMapping("muestraFlagNeteo")
	public ModelAndView muestraFormularioFlagNeteo(
			final HttpServletRequest req, final HttpServletResponse res,
			final Map<String, Object> modelo) {
		final String metodo = "muestraFormularioFlagNeteo()";
		this.info("Se ejecuta el metodo " + metodo);
		final String idRegistro = req.getParameter("id");
		this.info("El id del registro FlagNeteo que el usuario va a modificar es "
				+ idRegistro);
		@SuppressWarnings(UNCHECKED)
		final List<BeanFlagNeteo> listaFlagNeteo = (List<BeanFlagNeteo>) req
				.getSession()
				.getAttribute(
						ControllerConsultaParametros.RESULTADOS_CONSULTA_FLAGNETEO);
		BeanFlagNeteo flagNeteo = null;
		for (BeanFlagNeteo registro : listaFlagNeteo) {
			if (idRegistro.equals(registro.getIdRegistro())) {
				flagNeteo = registro;
			}
		}
		modelo.put("flagNeteoForm", flagNeteo);
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		this.info("El usuario es redireccionado a la pagina "
				+ PAGINA_FLAGNETEO);
		this.info("Termina la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_FLAGNETEO, modelo);
	}

	/**
	 * Se comunica con la capa de negocio para realizar la modificacion de un
	 * registro FlagNeteo
	 *
	 * @param flagNeteo
	 *            Contiene los datos editados por el usuario.
	 * @param modelo
	 *            Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("modificaFlagNeteo.do")
	public ModelAndView modificaFlagNeteo(
			@ModelAttribute("flagNeteoForm") final BeanFlagNeteo flagNeteo,
			final Map<String, Object> modelo) throws BusinessException {
		final String metodo = "modificaFlagNeteo()";
		this.info("Se ejecuta el metodo " + metodo);
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("El nuevo valor del registro editado es "
				+ flagNeteo.getValor());
		modificacionParametros.llamaModificacionFlagNeteo(flagNeteo,
				this.getArchitechBean());
		modelo.put(OPERACION, ACTUALIZA_PARAMETRO);
		modelo.put("confirmacionModificacion", flagNeteo.getValor());
		this.info("Se redirecciona al usuario hacia la pagina "
				+ PAGINA_FLAGNETEO);
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_FLAGNETEO, modelo);
	}

	/**
	 * Se comunica con la capa de negocio, y envia una serie de ID de los
	 * registros que seran eliminados.
	 *
	 * @param registrosPorEliminar
	 *            Foma que contiene los id de los registros a eliminar y un
	 *            codigo de fitro que indica de que tipo de filtro se estan
	 *            eliminando registros.
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception.
	 */
	@RequestMapping("eliminaRegistro.do")
	public ModelAndView eliminaAdnRetail(
			@ModelAttribute("registrosAEliminar") final FormOpcionesCheckBox registrosPorEliminar,
			final HttpServletRequest req, final HttpServletResponse res)
			throws BusinessException {
		final String metodo = "eliminaAdnRetail()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("Los registros a eliminar son de tipo "
				+ registrosPorEliminar.getIdFiltro());
		final StringBuilder cadenaRegistrosEliminar = new StringBuilder();
		cadenaRegistrosEliminar.append("[");
		for (String registro : registrosPorEliminar.getListaOpciones()) {
			cadenaRegistrosEliminar.append(registro + " ");
		}
		cadenaRegistrosEliminar.append("]");
		this.info("Los id de los registros a eliminar son "
				+ cadenaRegistrosEliminar);
		this.info("Se envian los id hacia la capa de negocio para que sus respectivos registros sean eliminados.");
		modificacionParametros.llamaEliminacionRegistro(
				registrosPorEliminar.getListaOpciones(),
				registrosPorEliminar.getIdFiltro(), this.getArchitechBean());
		String url = null;
		if ("adnLocal".equals(registrosPorEliminar.getIdFiltro())
				|| "productoGestion".equals(registrosPorEliminar.getIdFiltro())) {
			url = "consultarFiltrosExtraccion.do";
		} else if ("adnRetail".equals(registrosPorEliminar.getIdFiltro())) {
			url = "consultaAdnRetail.do";
		} else if ("flagNeteo".equals(registrosPorEliminar.getIdFiltro())) {
			url = "consultaFlagNeteo.do";
		}
		this.info("Se redirecciona al usuario hacia la url " + url);
		this.info("Termina ejecucion del metodo " + metodo);
		return new ModelAndView("redirect:" + url);
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
	 * @return the modificacionParametros
	 */
	public BOModificacionParametros getModificacionParametros() {
		return modificacionParametros;
	}

	/**
	 * @param modificacionParametros
	 *            the modificacionParametros to set
	 */
	public void setModificacionParametros(
			final BOModificacionParametros modificacionParametros) {
		this.modificacionParametros = modificacionParametros;
	}
}
