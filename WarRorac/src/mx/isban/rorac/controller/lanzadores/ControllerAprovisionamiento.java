/**
 * 
 */
package mx.isban.rorac.controller.lanzadores;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoOperacion;
import mx.isban.rorac.servicio.lanzadores.BOLanzadores;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;
import mx.isban.rorac.util.general.UtileriasFront;

/**
 * @author everis
 *
 */
@Controller
public class ControllerAprovisionamiento extends Architech {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -9182535206408321444L;
	/**
	 * Pagina que contiene el formulario de Aprovisonamiento Historico.
	 */
	private static final String PAGINA_APROV_HISTORICO = "lanzadorAprovHistorico";
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion y obtener 
	 * los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "APROVISIONAMIENTO HISTORICO";
	/**
	 * Constante que contiene el nombre de la pantalla asociada
	 */
	private static final String PANTALLA = "LANZADOR APROVISIONAMIENTO HISTORICO";
	/**
	 * Objeto de la capa de negocio que se encarga de registrar la operacion de 
	 * aprovisionamiento historico.
	 */
	private BOLanzadores lanzadorAprovisionamiento;

	/**
	 * Muestra el formulario de aprovisionamiento historico.
	 * @param req Request
	 * @param res Response
	 * @param modelo Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException 
	 */
	@RequestMapping("muestraMenuAprovisionamiento.do")
	public ModelAndView muestraMenuAprovisionamiento(HttpServletRequest req, HttpServletResponse res, Map<String, Object> modelo) throws BusinessException{
		final String metodo = "muestraMenuAprovisionamiento()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("El usuario es redireccionado a la pagina " + PAGINA_APROV_HISTORICO);
		modelo.put("aprovisionamientoForm", new BeanLanzamientoOperacion());
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		this.info("Finaliza el metodo " + metodo);
		return new ModelAndView(PAGINA_APROV_HISTORICO, modelo);
	}
	
	/**
	 * Realiza la peticion a la capa de negocio para registrar la operacion de aprovisionamiento historico.
	 * @param beanAprov Contiene los parametros introducidos por el usuario.
	 * @param modelo Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException Exception
	 */
	@RequestMapping("lanzaAprovisionamiento.do")
	public ModelAndView lanzaAprovisionamiento(@ModelAttribute("aprovisionamientoForm") BeanLanzamientoOperacion beanAprov, Map<String, Object> modelo) 
			throws BusinessException {
		final String metodo = "lanzaAprovisionamiento()";
		this.info("Inicial la ejecucion del metodo: " + metodo);
		this.info("Los parametros introducidos por el usuario son.");
		this.info("idArchivo: " + beanAprov.getIndiceArchivo());
		this.info("Mes: " + beanAprov.getMes());
		this.info("Anio: " + beanAprov.getAnio());
		this.info("Se realiza la peticion de registrar la operacion de aprovisionamiento a la capa de negocio.");
		lanzadorAprovisionamiento.lanzaAprovisionamiento(beanAprov, this.getArchitechBean());
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		modelo.put("aprovisionamientoForm", new BeanLanzamientoOperacion());
		modelo.put("periodo", beanAprov.getAnio() + "/" + beanAprov.getMes());
		modelo.put("archivo", beanAprov.getIndiceArchivo());
		this.info("El usuario es redirigido hacia la pagina: " + PAGINA_APROV_HISTORICO);
		this.info("Termina la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_APROV_HISTORICO, modelo);
	}
	
	/**
	 * Manejador de errores de este controller.
	 * @param req Request
	 * @param res Response
	 * @param handler Handler.
	 * @param exception Exception.
	 * @return ModelAndView
	 */
	@ExceptionHandler
	public ModelAndView manejoErrores(HttpServletRequest req, HttpServletResponse res, Object handler, Exception exception){
		final String metodo = this.getClass().getName() + ".manejadorErrores";
		this.info("Inicio de ejecucion de metodo " + metodo);
		String pagina = null;
		final Map<String, String> modelo = new HashMap<String, String>();
		if(handler instanceof BusinessException){
			modelo.put("codeError", ((BusinessException)handler).getCode());
			pagina = "../errores/errorAgave.do";
			this.info("Fue cachada una excepcion BuisinessException " + handler.toString());
		}else{
			pagina = "../errores/errorGrl.do";
			this.info("Fue cachada una excepcion " + handler.toString());
		}
		this.info("El modelo enviado al cliente es " + modelo.toString());
		this.info("La pagina de destino es " + pagina);
		return new ModelAndView("redirect:" + pagina, modelo);
	}

	/**
	 * @return the lanzadorAprovisionamiento
	 */
	public BOLanzadores getLanzadorAprovisionamiento() {
		return lanzadorAprovisionamiento;
	}

	/**
	 * @param lanzadorAprovisionamiento the lanzadorAprovisionamiento to set
	 */
	public void setLanzadorAprovisionamiento(BOLanzadores lanzadorAprovisionamiento) {
		this.lanzadorAprovisionamiento = lanzadorAprovisionamiento;
	}
}
