/**
 *
 */
package mx.isban.rorac.controller.lanzadores;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.servicio.lanzadores.BOLanzadores;
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
public class ControllerCargaIOFinales extends Architech {

	private static final String ELIMINAR = "E";
	private static final String ACTUALIZAR = "A";
	private static final String ID_ARCHIVO_PASIVO = "2";
	private static final String ID_ARCHIVO_ACTIVO = "1";
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -174727414448538623L;
	/**
	 * Nombre de la pagina con el formulario de carga de Inputs/Outputs.
	 */
	private static final String PAGINA_CARGA_IO = "cargaIOFinales";
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
	private static final String PANTALLA = "EDICION DE INPUTS Y OUTPUTS FINALES";
	/**
	 * Instancia del objeto de la capa de negocio BOCargaIOFinales.
	 */
	private BOLanzadores boCargaIO;

	/**
	 * Muestra la pagina con el formulario para carga de Inputs/Outputs finales.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Excepcion
	 */
	@RequestMapping("cargaIOFinales.do")
	public ModelAndView cargaFormulario(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "cargaFormulario()";
		this.info("Inicio de ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("Se direcciona al usuario hacia la pagina " + PAGINA_CARGA_IO);
		this.info("Fin de ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_CARGA_IO);
	}

	/**
	 * Registra una operacion de actualizacion o descarga para uno de los
	 * inputs/outputs.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Exception
	 */
	@RequestMapping("actualizaInterfaz.do")
	public ModelAndView registraOperacionCarga(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		final String metodo = "registraOperacionCarga()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		Map<String, Object> modelo = new HashMap<String, Object>();
		if (boCargaIO.validaPeticion(this.getArchitechBean())) {
			String activoActualizar = req.getParameter("activoActualizar");
			String activoEliminar = req.getParameter("activoEliminar");
			String pasivoActualizar = req.getParameter("pasivoActualizar");
			String pasivoEliminar = req.getParameter("pasivoEliminar");
			if (activoActualizar != null) {
				this.info("Se registra la actualizacion del input activo");
				boCargaIO.registraCargaArchivo(ID_ARCHIVO_ACTIVO, ACTUALIZAR,
						this.getArchitechBean());
			}
			if (activoEliminar != null) {
				this.info("Se registra la eliminacion del input activo");
				boCargaIO.registraCargaArchivo(ID_ARCHIVO_ACTIVO, ELIMINAR,
						this.getArchitechBean());
			}
			if (pasivoActualizar != null) {
				this.info("Se registra la actualizacion del input pasivo");
				boCargaIO.registraCargaArchivo(ID_ARCHIVO_PASIVO, ACTUALIZAR,
						this.getArchitechBean());
			}
			if (pasivoEliminar != null) {
				this.info("Se registra la eliminacion del input pasivo");
				boCargaIO.registraCargaArchivo(ID_ARCHIVO_PASIVO, ELIMINAR,
						this.getArchitechBean());
			}
			modelo.put("success", true);
		} else {
			modelo.put("failed", true);
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_CARGA_IO, modelo);
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
	 * @return the boCargaIO
	 */
	public BOLanzadores getBoCargaIO() {
		return boCargaIO;
	}

	/**
	 * @param boCargaIO
	 *            the boCargaIO to set
	 */
	public void setBoCargaIO(final BOLanzadores boCargaIO) {
		this.boCargaIO = boCargaIO;
	}
}
