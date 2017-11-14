/**
 *
 */
package mx.isban.rorac.controller.principal;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.LookAndFeel;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.agave.commons.utils.LogUtil;
import mx.isban.rorac.bean.administracion.modulo.BeanModulo;
import mx.isban.rorac.servicio.administracion.modulo.BOModulo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author everis
 *
 */
@Controller
public class ControllerPrincipal extends Architech {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Instruccion para redireccionar hacia una nueva direccion.
	 */
	private static final String INSTRUCCION_REDIRECT = "redirect:";
	/**
	 * Parametro que contiene el nombre del bean de Look And Field.
	 */
	private static final String PARAMETRO_LOOK_AND_FIELD = "LyFBean";

	/**
	 * Objeto de negocio de tipo BOModulo, encargado de obtener los modulos que
	 * un usuario puede acceder
	 */
	private BOModulo boModulo;

	/**
	 * Metodo que sirve para entrar al contexto de Spring.
	 *
	 * @param req
	 *            Request
	 * @param res
	 *            Response
	 * @return ModelAndView
	 * @throws BussinesException
	 *             Exception
	 */
	@RequestMapping("auto.do")
	public ModelAndView auto(final HttpServletRequest req,
			final HttpServletResponse res) throws BusinessException {
		String url = "inicio";
		String txtUser = req.getHeader("iv-user") == null ? "EXPC" : req
				.getHeader("iv-user").toString();
		txtUser = txtUser.substring(1);
		this.info("Usuario por header:" + txtUser);
		String user = req.getParameter("headerSAMUsuario");
		this.info("Usuario por parametro" + user == null ? "" : user);
		HttpSession lobjSession = req.getSession();
		LookAndFeel lobjLyFBean = (LookAndFeel) lobjSession
				.getAttribute(PARAMETRO_LOOK_AND_FIELD);

		if (lobjLyFBean == null) {
			lobjLyFBean = new LookAndFeel();
			lobjLyFBean.setLookAndFeel("default");
		}

		this.setNameComponent("ControllerPrincipal");
		this.setLoggingBean(LogUtil.getLoggingBean("message", "cmpName",
				this.getClass()));
		lobjSession.setAttribute(PARAMETRO_LOOK_AND_FIELD, lobjLyFBean);
		List<BeanModulo> modulos = boModulo
				.obtenerModulosPorGrupo(
						getArchitechBean(),
						req.getHeader("iv-groups") == null ? "EXPC" : req
								.getHeader("iv-groups").toString()
								.replaceAll("\"", ""));
		lobjSession.setAttribute("modulosPermitidos", modulos);

		this.setNameComponent("ControllerPrincipal");
		this.setLoggingBean(LogUtil.getLoggingBean("message", "cmpName",
				this.getClass()));

		return new ModelAndView(url);
	}

	/**
	 * Metodo para salir de la aplicacion.
	 *
	 * @param request
	 *            Request
	 * @param respones
	 *            Response
	 * @return ModelAndView
	 * @throws BusinessException
	 *             Excepcion
	 */
	@RequestMapping("salir.do")
	public ModelAndView salir(final HttpServletRequest request,
			final HttpServletResponse respones) throws BusinessException {
		this.debug("saliendo de la aplicacion");
		final LookAndFeel lyFBean = (LookAndFeel) request.getSession()
				.getAttribute(PARAMETRO_LOOK_AND_FIELD);
		final String salirUrl = lyFBean.getLinkSalirSAM();
		request.getSession().invalidate();
		this.debug("Enviando a hacerlogut a SAM[" + salirUrl + "]...");
		return new ModelAndView(INSTRUCCION_REDIRECT + salirUrl);
	}

	/**
	 * Manejador de excepciones de este controller.
	 *
	 * @param request
	 *            Request
	 * @param response
	 *            Response
	 * @param handler
	 *            Handler
	 * @param ex
	 *            Excepcion
	 * @return ModelAndView
	 */
	@ExceptionHandler
	public ModelAndView resolveException(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler,
			final Exception ex) {

		LookAndFeel lobjLyFBean = (LookAndFeel) request.getSession()
				.getAttribute(PARAMETRO_LOOK_AND_FIELD);

		this.debug("°°Sucedio un error inesperado...");
		this.debug("°°Origen      :" + request.getRequestURL());
		this.debug("°°HandlerError:" + handler.getClass().getName());
		showException(ex);

		String lstrPaginaException = "";
		String lstrContextPath = "";
		HashMap<String, String> lhsmParametros = new HashMap<String, String>();

		lhsmParametros.put("paginaError", request.getRequestURL().toString());
		if (lobjLyFBean != null) {
			if (handler instanceof BusinessException) {
				// Pagina de error a mostrar cuando ocirre un error en los BO
				lstrPaginaException = lobjLyFBean.getPaginaExceptionArq();
				lhsmParametros.put("codeError",
						((BusinessException) handler).getCode());
			} else {
				// Pagina de error a mostrar cuando ocirre un error en los
				// Controllers
				lstrPaginaException = lobjLyFBean.getPaginaExceptionGral();
			}
		}
		lstrContextPath = request.getContextPath();
		this.debug("ContextPath   :" + lstrContextPath);
		this.debug("Paginadestino :" + lstrPaginaException);
		this.debug("Se dirige a   :" + lstrContextPath + lstrPaginaException);
		this.debug("Redirect      :" + "redirect:/" + lstrPaginaException);

		return new ModelAndView(INSTRUCCION_REDIRECT + lstrPaginaException,
				lhsmParametros);
	}

	/**
	 * Obtiene la instancia boModulo
	 *
	 * @return BOModulo
	 */
	public BOModulo getBoModulo() {
		return boModulo;
	}

	/**
	 * Establece la instancia de boModulo
	 *
	 * @param boModulo
	 *            Objeto de tipo BOModulo
	 */
	public void setBoModulo(final BOModulo boModulo) {
		this.boModulo = boModulo;
	}

}
