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
import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;
import mx.isban.rorac.servicio.administracion.grupo.BOGrupo;
import mx.isban.rorac.servicio.administracion.pantalla.BOPantalla;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;

/**
* Clase ControllerGrupos
* 
* <P>Clase encargada recibir y procesar todas las peticiones relacionadas a la administracion de grupos.
* (Consulta, Alta, Modificacion y Baja)
*  
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
@Controller
public class ControllerGrupos extends Architech {

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = 7231526297814880039L;

	/**
	 * Modulo al que pertenece el controller 
	 */
	private static final String MODULO = "ADMINISTRACION";
	
	/**
	 * Constante que indica la pantalla a la que puede acceder el usuario
	 */
	private static final String PANTALLA = "GRUPOS";
	
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion y obtener 
	 * los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	
	/**
	 * Objeto de negocio de tipo BOGrupo
	 */
	private BOGrupo boGrupo;
	
	/**
	 * Objeto de negocio de tipo BoPantalla
	 */
	private BOPantalla boPantalla;
	

	/**
	 * Metodo encargado de consultar los grupos disponibles y 
	 * regresar a la vista "consultarPerfiles" con los datos obtenidos.
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultarPerfiles.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de los grupos disponibles
	 */
	@RequestMapping("consultarGrupos.do")
	public ModelAndView consultarGrupos(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el metodo consultarGrupos...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final Map<String, Object> parametros = new HashMap<String, Object>();
		final List<BeanGrupo> gruposList  = boGrupo.buscarTodosGrupos(getArchitechBean());
		parametros.put("registros", gruposList);
		this.info("Finaliza el metodo consultarGrupos.- Direccionando a la vista: consultaPerfiles");
		return new ModelAndView("consultarGrupo",parametros);
	}
	
	/**
	 * Metodo encargado de inicializar el formulario para dar de alta un nuevo perfil
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista altaPerfil.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("altaGrupoInit.do")
	public ModelAndView altaGrupoInit(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el metodo altaGrupoInit...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final List<BeanPantalla> pantallasList = boPantalla.buscarTodasPantallas(getArchitechBean());
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("todasPantallas", pantallasList);
		return new ModelAndView("altaGrupo",parametros);
	}
	
	/**
	 * Metodo encargado de obtener los parametros que permitan dar de alta un nuevo Grupo
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultaPerfiles.jsp
	 * @throws BusinessException En caso de presentarse un error en la consulta de las pantallas disponibles
	 */
	@RequestMapping("altaGrupo.do")
	public ModelAndView altaGrupo(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el metodo altaGrupo");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanGrupo grupo = new BeanGrupo();
		grupo.setNombreGrupo(request.getParameter("nombreGrupo"));
		grupo.setDescripcionGrupo(request.getParameter("descripcionGrupo"));
		final String[] pantallasSeleccionadas = request.getParameterValues("pantallaActiva");
		final List<BeanPantalla> pantallasList = new ArrayList<BeanPantalla>();
		for (int i = 0; i < pantallasSeleccionadas.length; i++) {
			final BeanPantalla pantalla = new BeanPantalla();
			pantalla.setIdPantalla(pantallasSeleccionadas[i]);
			pantallasList.add(pantalla);
		}
		grupo.setPantallas(pantallasList);
		boGrupo.agregarGrupo(grupo, getArchitechBean());
		this.info("Termina la ejecucion del metodo altaGrupo");
		return this.consultarGrupos(request, response);
	}
	
	/**
	 * Metodo encargado de borrar un registro relacionado a un Grupo, por su id
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultaPerfiles.jsp
	 * @throws BusinessException En caso de presentarse un error al momento de eliminar un grupo.
	 */
	@RequestMapping("borrarGrupo.do")
	public ModelAndView borrarGrupo(HttpServletRequest request, HttpServletResponse response)throws BusinessException{
		this.info("Iniciando el metodo borrarGrupo...");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String idGrupo = request.getParameter("idGrupo");
		boGrupo.borrarGrupo(idGrupo, getArchitechBean());
		return this.consultarGrupos(request, response);
	}
	
	/**
	 * Metodo encargado de obtener los datos que se modificaran para un grupo determinado.
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultaPerfiles.jsp
	 * @throws BusinessException Excepcion que se presenta si ocurre un error al momento de modificar un grupo
	 */
	@RequestMapping("modificarGrupo.do")
	public ModelAndView modificarGrupo(HttpServletRequest request, HttpServletResponse response) throws BusinessException{
		this.info("Iniciando el metodo modificarGrupo");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final BeanGrupo grupo = new BeanGrupo();
		grupo.setIdGrupo(request.getParameter("idGrupo"));
		grupo.setNombreGrupo(request.getParameter("nombreGrupo"));
		grupo.setDescripcionGrupo(request.getParameter("descripcionGrupo"));
		final String[] pantallasSeleccionadas = request.getParameterValues("pantallaActiva");
		final List<BeanPantalla> pantallasList = new ArrayList<BeanPantalla>();
		for (int i = 0; i < pantallasSeleccionadas.length; i++) {
			final BeanPantalla pantalla = new BeanPantalla();
			pantalla.setIdPantalla(pantallasSeleccionadas[i]);
			pantallasList.add(pantalla);
		}
		grupo.setPantallas(pantallasList);
		boGrupo.modificarGrupo(grupo, getArchitechBean());
		this.info("Finaliza la ejecucion del metodo modificarGrupo");
		return this.consultarGrupos(request, response);
	}
	
	/**
	 * Metodo encargado de inicializar el formulario de modificacion de grupo.
	 * @param request Un objeto de tipo {@link HttpServletRequest}
	 * @param response Un objeto de tipo {@link HttpServletResponse}
	 * @return Un objeto de tipo {@link ModelAndView} que direcciona a la vista consultaPerfiles.jsp
	 * @throws BusinessException Excepcion que se presenta si ocurre un error al momento de modificar un grupo
	 */
	@RequestMapping("modificarGrupoInit.do")
	public ModelAndView modificarGrupoInit(HttpServletRequest request, HttpServletResponse response) throws BusinessException{
		this.info("Iniciando el metodo modificarGrupoInit");
		final Object objeto = request.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String idGrupo = request.getParameter("idGrupo");
		final BeanGrupo grupo = boGrupo.consultarGrupo(idGrupo, getArchitechBean());
		final List<BeanPantalla> pantallasList = grupo.getPantallas();
		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("grupo", grupo);
		parametros.put("todasPantallas", pantallasList);
		return new ModelAndView("modificarGrupo",parametros);
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
		this.info("Inicio de ejecucion de metodo de manejo de errores");
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
	 * Metodo que obtiene un objeto de tipo BOGrupo
	 * @return un objeto de tipo BOGrupo
	 */
	public BOGrupo getBoGrupo() {
		return boGrupo;
	}

	/**
	 * Metodo que establece un objeto de tipo BOGrupo
	 * @param boGrupo El objeto a establecer
	 */
	public void setBoGrupo(BOGrupo boGrupo) {
		this.boGrupo = boGrupo;
	}

	/**
	 * Metodo que obtiene un objeto de tipo BOPantalla
	 * @return un objeto de tipo BOPantalla
	 */
	public BOPantalla getBoPantalla() {
		return boPantalla;
	}

	/**
	 * Establece un objeto de tipo BOPantalla
	 * @param boPantalla El objeto a establecer
	 */
	public void setBoPantalla(BOPantalla boPantalla) {
		this.boPantalla = boPantalla;
	}
	
}
