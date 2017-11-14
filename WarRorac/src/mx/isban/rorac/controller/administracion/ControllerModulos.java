/**************************************************************
* Queretaro, Qro Mayo 2015
*
* La redistribucion y el uso en formas fuente y binario, 
* son responsabilidad del propietario.
* 
* Este software fue elaborado en @Everis
* 
* Para mas informacion, consulte <www.everis.com/mexico>
***************************************************************/
package mx.isban.rorac.controller.administracion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.administracion.modulo.BeanModulo;
import mx.isban.rorac.servicio.administracion.modulo.BOModulo;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;

/**
* Clase ControllerModulos
* 
* <P>Clase encargada rebibir y procesar todas las peticiones relacionadas a la administracion de modulos.
* (Consulta, Alta y Modificacion)
*  
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
@Controller
public class ControllerModulos extends Architech {

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -1068719745844853102L;
	
	/**
	 * Modulo al que pertenece el controller 
	 */
	private static final String MODULO = "ADMINISTRACION";
	
	/**
	 * Pantalla relacionada al controller  
	 */
	private static final String PANTALLA = "MODULOS";
	
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion y obtener 
	 * los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";

	/**
	 * Objeto de negocio de tipo BOModulo
	 */
	private BOModulo boModulo;
	
	/**
	 * Metodo encargado de invocar el objeto de negocio boModulo
	 * para consultar todos los modulos disponibles, ademas de direccionar 
	 * a la pantalla consultarModulos.jsp
	 * @param request Objeto de tipo HttpServletRequest
	 * @param response Objeto de tipo HttpServletResponse
	 * @return Un objeto de tipo ModelAndView con la vista consultarModulos.jsp
	 * @throws BusinessException En caso de presentarse un error en el proceso de consulta de modulos
	 */
	@RequestMapping("consultarModulos.do")
	public ModelAndView consultarModulos(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de detalle de modulos...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final List<BeanModulo> modulos = boModulo.obtenerTodosModulos(getArchitechBean());
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("todosModulos", modulos);
		this.info("Finaliza la ejecucion del metodo consultarModulos");
		return new ModelAndView("consultarModulos",parametros);
	}

	/**
	 * Metodo encargado de inicializar los datos para el formulario de consulta/modificacion
	 * de modulos
	 * @param request Objeto de tipo HttpServletRequest
	 * @param response Objeto de tipo HttpServletResponse
	 * @return Un objeto de tipo ModelAndView con la vista modificarModulo.jsp
	 * @throws BusinessException En caso de presentarse un error en el proceso de consulta de modulos
	 */
	@RequestMapping("modificarModuloInit.do")
	public ModelAndView modificarModuloInit(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de detalle de modulo...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String idModulo = request.getParameter("idModulo");
		this.info("El id a buscar es:"+idModulo);
		final BeanModulo modulo = boModulo.obtenerModuloPorId(getArchitechBean(), idModulo);
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("modulo", modulo);
		return new ModelAndView("modificarModulo",parametros);
	}
	
	
	/**
	 * Metodo encargado de modificar un modulo, mediante la invocacion de un objeto de negocio de tipo BOModulo
	 * @param request Objeto de tipo HttpServletRequest
	 * @param response Objeto de tipo HttpServletResponse
	 * @return Un objeto de tipo ModelAndView con la vista consultarModulos.jsp
	 * @throws BusinessException En caso de presentarse un error en el proceso de consulta de modulos
	 */
	@RequestMapping("modificarModulo.do")
	public ModelAndView modificarModulo(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de modificacion del modulo...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanModulo modulo = new BeanModulo();
		modulo.setIdModulo(request.getParameter("idModulo"));
		modulo.setNombreModulo(request.getParameter("nombreModulo"));
		modulo.setDescripcionModulo(request.getParameter("descripcionModulo"));
		boModulo.modificarModulo(getArchitechBean(), modulo);
		return this.consultarModulos(request, response);
	}
	
	/**
	 * Metodo encargado de invocar al objeto de negocio de tipo BOModulo para dar de alta
	 * en la base de datos un nuevo modulo.
	 * @param request Objeto de tipo HttpServletRequest
	 * @param response Objeto de tipo HttpServletResponse
	 * @return Un objeto de tipo ModelAndView con la vista consultarModulos.jsp
	 * @throws BusinessException En caso de presentarse un error en el proceso de consulta de modulos
	 */
	@RequestMapping("altaModulo.do")
	public ModelAndView altaModulo(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de modificacion del modulo...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanModulo modulo = new BeanModulo();
		modulo.setNombreModulo(request.getParameter("nombreModulo"));
		modulo.setDescripcionModulo(request.getParameter("descripcionModulo"));
		boModulo.altaModulo(getArchitechBean(), modulo);
		return this.consultarModulos(request, response);
	}
	
	/**
	 * Metodo que se encarga de inicializar el formulario de alta de modulo
	 * @param request Objeto de tipo HttpServletRequest
	 * @param response Objeto de tipo HttpServletResponse
	 * @return Un objeto de tipo ModelAndView con la vista altaModulo.jsp
	 * @throws BusinessException En caso de presentarse un error en el proceso de consulta de modulos
	 */
	@RequestMapping("altaModuloInit.do")
	public ModelAndView altaModuloInit(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de modificacion del modulo...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		return new ModelAndView("altaModulo");
	}
	
	/**
	 * Metodo encargado de invocar al objeto de tipo BOModulo para eliminar un modulo
	 * de la base de datos.
	 * @param request Objeto de tipo HttpServletRequest
	 * @param response Objeto de tipo HttpServletResponse
	 * @return Un objeto de tipo ModelAndView con la vista consultarModulos.jsp
	 * @throws BusinessException En caso de presentarse un error en el proceso de consulta de modulos
	 */
	@RequestMapping("borrarModulo.do")
	public ModelAndView borrarModulo(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando la baja de un modulo...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String idModulo = request.getParameter("idModulo");
		boModulo.borrarModulo(getArchitechBean(), idModulo);
		return this.consultarModulos(request, response);
	}

	/**
	 * Metodo encargado de procecesar los errores que se pueden presentar en el modulo de Administracion - Grupos
	 * @param req Un objeto de tipo {@link HttpServletRequest}
	 * @param res Un objeto de tipo {@link HttpServletResponse}
	 * @param handler Un objeto con la excepcion a procesar
	 * @param exception Un objeto de tipo {@link Exception}
	 * @return Un objeto de tipo {@link ModelAndView} con la pantalla de manejo de errores
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
	 * Metodo que obtiene un objeto de tipo BOModulo
	 * @return Un objeto de tipo BOModulo
	 */
	public BOModulo getBoModulo() {
		return boModulo;
	}

	/**
	 * Metodo encargado de establecer un objeto de tipo BOModulo
	 * @param boModulo El objeto de tipo BOModulo a establecer
	 */
	public void setBoModulo(BOModulo boModulo) {
		this.boModulo = boModulo;
	}
	
	

}
