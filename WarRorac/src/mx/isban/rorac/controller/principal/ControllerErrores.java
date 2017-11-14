package mx.isban.rorac.controller.principal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;

@Controller
public class ControllerErrores extends Architech{
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 960423900408489008L;

	/**
	 * Se encarga de mostrar la pagina de error de agave.
	 * @param req Request
	 * @param res Response
	 * @return ModelAndView
	 * @throws BusinessException Exception
	 */
	@RequestMapping("errorAgave.do")
	public ModelAndView errorEBE(final HttpServletRequest req, final HttpServletResponse res) throws BusinessException {
		final String nombreParametro = "codeError";
		Map<String, String> modelo = new HashMap<String, String>();
		modelo.put(nombreParametro, req.getParameter(nombreParametro));
		return new ModelAndView("excepcionInesperadaArq", modelo);
	}
	
	/**
	 * Se encarga de mostrar la pagina de error generico de la aplicacion.
	 * @param req Request.
	 * @param res Response.
	 * @return ModenAndView
	 * @throws BusinessException Exception.
	 */
	@RequestMapping("errorGrl.do")
	public ModelAndView errorGrl(final HttpServletRequest req, final HttpServletResponse res) throws BusinessException {
		return new ModelAndView("excepcionInesperadaGrl");
	}
}