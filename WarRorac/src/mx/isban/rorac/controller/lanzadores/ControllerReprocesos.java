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
public class ControllerReprocesos extends Architech {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6613213547980393637L;
	/**
	 * Pagina que contiene el formulario que permite al usuario lanzar un reproceso.
	 */
	private static final String PAGINA_REPROCESOS = "lanzadorReproceso";
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion y obtener 
	 * los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "REPROCESO INPUTS FINALES";
	/**
	 * Constante que contiene el nombre de la pantalla asociada
	 */
	private static final String PANTALLA = "LANZADOR DE REPROCESOS";
	/**
	 * Instancia del objeto de la capa de negocio encargado de registrar el reproceso.
	 */
	private BOLanzadores lanzadorReproceso;
	
	/**
	 * Muestra al usuario el menu de reproceso.
	 * @param req Request
	 * @param res Response
	 * @param modelo Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException 
	 */
	@RequestMapping("muestraMenuReproceso.do")
	public ModelAndView muestraMenuReproceso(HttpServletRequest req, HttpServletResponse res, Map<String, Object> modelo) throws BusinessException{
		final String metodo = "muestraMenuReproceso()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final Object objeto = req.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		this.info("El usuario es redirigido hacia la pagina " + PAGINA_REPROCESOS);
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		modelo.put("reprocesoForm", new BeanLanzamientoOperacion());
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_REPROCESOS, modelo);
	}
	
	/**
	 * Se comunica con la capa de negocio para realizar el registro del reproceso.
	 * @param beanReproceso Contiene los datos introducidos por el usuario.
	 * @param modelo Modelo Spring MVC
	 * @return ModelAndView
	 * @throws BusinessException Exception
	 */
	@RequestMapping("lanzaReproceso.do")
	public ModelAndView lanzaReproceso(@ModelAttribute("reprocesoForm") BeanLanzamientoOperacion beanReproceso, Map<String, Object> modelo) 
			throws BusinessException {
		final String metodo = "lanzaReproceso()";
		this.info("Se inicia la ejecucion del metodo " + metodo);
		this.info("Los parametros elegidos por el usuario son.");
		this.info("Archivo: " + beanReproceso.getIndiceArchivo());
		this.info("Mes: " + beanReproceso.getMes());
		this.info("Anio: " + beanReproceso.getAnio());
		this.info("Se realiza la solicitud a la capa de negocio para que se registre la operacion de reproceso.");
		lanzadorReproceso.lanzaReproceso(beanReproceso, this.getArchitechBean());
		modelo.put("listaAnios", UtileriasFront.establecerAniosDisponibles());
		modelo.put("reprocesoForm", new BeanLanzamientoOperacion());
		modelo.put("periodo", beanReproceso.getAnio() + "/" + beanReproceso.getMes());
		modelo.put("archivo", beanReproceso.getIndiceArchivo());
		this.info("Se redirecciona al usuario a la pagina " + PAGINA_REPROCESOS);
		this.info("Termina le ejecucion del metodo " + metodo);
		return new ModelAndView(PAGINA_REPROCESOS, modelo);
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
	 * @return the lanzadorReproceso
	 */
	public BOLanzadores getLanzadorReproceso() {
		return lanzadorReproceso;
	}

	/**
	 * @param lanzadorReproceso the lanzadorReproceso to set
	 */
	public void setLanzadorReproceso(BOLanzadores lanzadorReproceso) {
		this.lanzadorReproceso = lanzadorReproceso;
	}
}
