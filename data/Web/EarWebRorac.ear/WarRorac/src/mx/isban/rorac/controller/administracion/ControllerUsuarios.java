/**************************************************************
* Queretaro, Qro Abril 2015
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
import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuario;
import mx.isban.rorac.servicio.administracion.grupo.BOGrupo;
import mx.isban.rorac.servicio.administracion.usuario.BOUsuario;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;

/**
* Clase ControllerUsuarios
* 
* <P>Clase encargada rebibir y procesar todas las peticiones relacionadas a la administracion de usarios.
* (Consulta, Alta y Modificacion)
*  
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
@Controller
public class ControllerUsuarios extends Architech {

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -5242442071622217987L;
	
	/**
	 * Modulo al que pertenece el controller 
	 */
	private static final String MODULO = "ADMINISTRACION";
	
	/**
	 * Constante que indica la pantalla a la que puede acceder el usuario
	 */
	private static final String PANTALLA = "USUARIOS";
	
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion y obtener 
	 * los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	
	/**
	 * Objeto de negocio de tipo BOUsuario
	 */
	private BOUsuario boUsuario;
	/**
	 * Objeto de negocio de tipo BOGrupo
	 */
	private BOGrupo boGrupo;
	
	/**
	 * Metodo encargado de consultar los usuarios disponibles y regresar a la vista los datos obtenidos
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultarUsuarios.jsp
	 * @throws BusinessException En caso de presentarse un error al momento de obtener los usuarios
	 */
	@RequestMapping("consultarUsuarios.do")
	public ModelAndView consultarUsuarios(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de consulta de usuarios...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final Map<String, Object> parametros = new HashMap<String, Object>();
		final List<BeanUsuario> listaUsuarios  = boUsuario.obtenerTodosUsuarios(getArchitechBean());
		parametros.put("registros", listaUsuarios);
		this.info("Direccionando a la vista: consultaUsuarios");
		return new ModelAndView("consultarUsuarios",parametros);
	}
	
	/**
	 * Metodo encargado de inicializar el formulario de alta de usuarios
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista altaUsuario.jsp
	 * @throws BusinessException En caso de presentarse un error al momento de obtener los usuarios
	 */
	@RequestMapping("altaUsuarioInit.do")
	public ModelAndView altaUsuarioInit(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de consulta de usuarios...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final List<BeanGrupo> listaGrupos = boGrupo.buscarTodosGrupos(getArchitechBean());
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("todosGrupos", listaGrupos);
		this.info("Direccionando a la vista: altaUsuario");
		return new ModelAndView("altaUsuario",parametros);
	}
	
	/**
	 * Metodo encargado de invocar al objeto de negocio encargado de realizar el alta de un usuario
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultarUsuarios.jsp
	 * @throws BusinessException En caso de presentarse un error al momento de realizar al alta de un usuario
	 */
	@RequestMapping("altaUsuario.do")
	public ModelAndView altaUsuario(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el alta de un usuario");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanUsuario usuario = new BeanUsuario();
		usuario.setIdUsuario(request.getParameter("idUsuario"));
		final String usuarioActivo = request.getParameter("usuarioActivo");
		if(null != usuarioActivo && "activo".equals(usuarioActivo)){
			usuario.setEstatus(true);
		}
		final String[] gruposSeleccionados = request.getParameterValues("idGrupo");
		final List<BeanGrupo> listaGrupos = new ArrayList<BeanGrupo>();
		for (int i = 0; i < gruposSeleccionados.length; i++) {
			final BeanGrupo grupo = new BeanGrupo();
			grupo.setIdGrupo(gruposSeleccionados[i]);
			listaGrupos.add(grupo);
			this.info("idGrupo:"+gruposSeleccionados[i]);
		}
		usuario.setGrupos(listaGrupos);
		boUsuario.altaUsuario(getArchitechBean(), usuario);
		this.info("Termina la ejecucion del metodo de alta de usuario");
		return this.consultarUsuarios(request, response);
	}
	
	
	/**
	 * Metodo encargado de inicializar el formulario de modificacion de usuario
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista modificarUsuario.jsp
	 * @throws BusinessException En caso de presentarse un error al momento de obtener los usuarios
	 */
	@RequestMapping("modificarUsuarioInit.do")
	public ModelAndView modificarUsuarioInit(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de consulta de usuarios...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String idUsuario = request.getParameter("idUsuario");
		final BeanUsuario usuario = boUsuario.obtenerUsuarioPorID(getArchitechBean(), idUsuario);
		final List<BeanGrupo> grupos = usuario.getGrupos();
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario", usuario);
		parametros.put("todosGrupos", grupos);
		return new ModelAndView("modificarUsuario",parametros);
	}
	
	/**
	 * Metodo encargado invocar al objeto de negocio para realizar la modificacion de un usuario
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista modificarUsuario.jsp
	 * @throws BusinessException En caso de presentarse un error al momento de obtener los usuarios
	 */
	@RequestMapping("modificarUsuario.do")
	public ModelAndView modificarUsuario(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el formulario de modificacion de usuarios");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanUsuario usuario = new BeanUsuario();
		usuario.setIdUsuario(request.getParameter("idUsuario"));
		final String usuarioActivo = request.getParameter("usuarioActivo");
		if(null != usuarioActivo && "activo".equals(usuarioActivo)){
			usuario.setEstatus(true);
		}
		final String[] gruposSeleccionados = request.getParameterValues("idGrupo");
		final List<BeanGrupo> listaGrupos = new ArrayList<BeanGrupo>();
		for (int i = 0; i < gruposSeleccionados.length; i++) {
			final BeanGrupo grupo = new BeanGrupo();
			grupo.setIdGrupo(gruposSeleccionados[i]);
			listaGrupos.add(grupo);
			this.info("idGrupo:"+gruposSeleccionados[i]);
		}
		usuario.setGrupos(listaGrupos);
		boUsuario.modificarUsuario(getArchitechBean(), usuario);
		this.info("Termina la ejecucion del metodo de actualizacion");
		return this.consultarUsuarios(request, response);
	}

	/**
	 * Metodo encargado de procecesar los errores que se pueden presentar en el modulo de Administracion - Usuarios
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
	 * Metodo encargado de obtener un objeto de tipo BOUsuario
	 * @return Un objeto de tipo BOUsuario
	 */
	public BOUsuario getBoUsuario() {
		return boUsuario;
	}

	/**
	 * Modulo encargado de establecer un objeto de tipo BOUsuario
	 * @param boUsuario Un objeto de tipo BOUsuario a establecer
	 */
	public void setBoUsuario(BOUsuario boUsuario) {
		this.boUsuario = boUsuario;
	}

	/**
	 * Metodo encargado de obtener un objeto de tipo BOGrupo
	 * @return Un objeto de tipo BOgrupo
	 */
	public BOGrupo getBoGrupo() {
		return boGrupo;
	}

	/**
	 * Metodo encargado de establecer un objeto de tipo BOGrupo
	 * @param boGrupo Un objeto de tipo BOGrupo
	 */
	public void setBoGrupo(BOGrupo boGrupo) {
		this.boGrupo = boGrupo;
	}
	
}
