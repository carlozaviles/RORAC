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

import java.util.ArrayList;
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
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;
import mx.isban.rorac.servicio.administracion.modulo.BOModulo;
import mx.isban.rorac.servicio.administracion.pantalla.BOPantalla;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;

/**
* Clase ControllerPantallas
* 
* <P>Clase encargada rebibir y procesar todas las peticiones relacionadas a la administracion de pantallas.
* (Consulta, Alta y Modificacion)
*  
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
@Controller
public class ControllerPantallas extends Architech {
	
	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -2315358143734616416L;
	
	/**
	 * Modulo al que pertenece el controller 
	 */
	private static final String MODULO = "ADMINISTRACION";
	
	/**
	 * Constante que indica la pantalla a la que puede acceder el usuario
	 */
	private static final String PANTALLA = "PANTALLAS";
	
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion y obtener 
	 * los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	
	/**
	 * Objeto de negocio de tipo BOPantalla
	 */
	private BOPantalla boPantalla;
	
	/**
	 * Objeto de negocio  de tipo BOModulo
	 */
	private BOModulo boModulo;
	
	/**
	 * Metodo encargado de consultar todas las pantallas disponibles
	 * y regresar a la vista consultarPantallas el resultado obtenido
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultarPantallas.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("consultarPantallas.do")
	public ModelAndView consultarPantallas(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de detalle de pantallas...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final List<BeanPantalla> pantallas = boPantalla.buscarTodasPantallas(getArchitechBean());
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("todasPantallas", pantallas);
		this.info("Finaliza la ejecucion del metodo consultarPantallas");
		return new ModelAndView("consultarPantallas",parametros);
	}
	
	/**
	 * Metodo encargado de inicializar la pantalla de altas
	 * 
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista altaPantalla.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("altaPantallaInit.do")
	public ModelAndView altaPantallaInit(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de alta de pantalla...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final List<BeanModulo> modulos = boModulo.obtenerTodosModulos(getArchitechBean());
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("todosModulos", modulos);
		return new ModelAndView("altaPantalla",parametros);
	}
	
	/**
	 * Metodo encargado de dar de alta una nueva pantalla
	 * y regresar a la vista consultarPantallas
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultarPantallas.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("altaPantalla.do")
	public ModelAndView altaPantalla(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de alta de pantalla...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanPantalla pantalla = new BeanPantalla();
		pantalla.setNombrePantalla(request.getParameter("nombrePantalla"));
		pantalla.setDescripcionPantalla(request.getParameter("descripcionPantalla"));
		final List<BeanModulo> moduloList = new ArrayList<BeanModulo>();
		final BeanModulo modulo = new BeanModulo();
		modulo.setIdModulo(request.getParameter("moduloActivo"));
		moduloList.add(modulo);
		pantalla.setModulos(moduloList);
		boPantalla.agregarPantalla(getArchitechBean(),pantalla);
		return this.consultarPantallas(request, response);
	}
	
	/**
	 * Metodo encargado de cargar los datos en el formulario de modificacion
	 * de pantalla
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista modificarPantallas.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("modificarPantallaInit.do")
	public ModelAndView modificarPantallaInit(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de detalle de pantalla...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String idPantalla = request.getParameter("idPantalla");
		this.info("El id a buscar es:"+idPantalla);
		final BeanPantalla pantalla = boPantalla.obtenerPantallaPorId(getArchitechBean(), idPantalla);
		final List<BeanModulo> modulos = pantalla.getModulos();
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("pantalla", pantalla);
		parametros.put("todosModulos", modulos);
		return new ModelAndView("modificarPantalla",parametros);
	}
	
	/**
	 * Metodo encargado de obtener los parametros para modificar
	 * el registro relacionado a una pantalla
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultarPantallas.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("modificarPantalla.do")
	public ModelAndView modificarPantalla(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el metodo para modificar la pantalla");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanPantalla pantalla = new BeanPantalla();
		pantalla.setIdPantalla(request.getParameter("idPantalla"));
		pantalla.setNombrePantalla(request.getParameter("nombrePantalla"));
		pantalla.setDescripcionPantalla(request.getParameter("descripcionPantalla"));
		final String idModulo = request.getParameter("moduloActivo");
		final List<BeanModulo> modulos = new ArrayList<BeanModulo>();
		final BeanModulo modulo = new BeanModulo();
		modulo.setIdModulo(idModulo);
		modulos.add(modulo);
		pantalla.setModulos(modulos);
		boPantalla.modificarPantalla(getArchitechBean(), pantalla);
		return this.consultarPantallas(request, response);
	}
	
	/**
	 * Metodo encargado de obtener el id de la pantalla que se eliminara
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultarPantallas.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("borrarPantalla.do")
	public ModelAndView borrarPantalla(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el metodo para eliminar la pantalla");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String idPantalla = request.getParameter("idPantalla");
		boPantalla.borrarPantalla(getArchitechBean(),idPantalla);
		return this.consultarPantallas(request, response);
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
	 * Metodo encargado de obtener un objeto de tipo BOPantalla
	 * @return un objeto de tipo BOPantalla
	 */
	public BOPantalla getBoPantalla() {
		return boPantalla;
	}

	/**
	 * Metodo encargado de establecer un objeto de tipo BOPantalla
	 * @param boPantalla El objeto de tipo BOPantalla a establecer
	 */
	public void setBoPantalla(BOPantalla boPantalla) {
		this.boPantalla = boPantalla;
	}

	/**
	 * Metodo encargado de obtener el objeto de negocio de tipo BOModulo
	 * @return Un objeto de tipo BOModulo
	 */
	public BOModulo getBoModulo() {
		return boModulo;
	}

	/**
	 * Metodo encargado de establecer un objeto de tipo BOModulo
	 * @param boModulo Objeto de tipo BOModulo a establecer
	 */
	public void setBoModulo(BOModulo boModulo) {
		this.boModulo = boModulo;
	}
	
}
