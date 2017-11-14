/**
 * 
 */
package mx.isban.rorac.controller.inputs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.servicio.inputs.BOCargaManual;
import mx.isban.rorac.util.administracion.ValidadorAccesoPantallas;
import mx.isban.rorac.util.general.UtileriasFront;

/**
 * @author everis
 *
 */
@Controller
public class ControllerCargaManual extends Architech {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constante que tiene la cadena para acceder a los parametros de la sesion y obtener 
	 * los modulos permitidos por el usuario logueado
	 */
	private static final String MODULOS_PERMITIDOS = "modulosPermitidos";
	/**
	 * Constante que contiene el nombre del modulo
	 */
	private static final String MODULO = "CARGAS MANUALES";
	/**
	 * Constante que contiene el nombre de la pantalla carga de insumos
	 */
	private static final String PANTALLA = "CARGA DE INSUMOS";
	
	
	/**
	 * Interfaz de negocio para el modulo de carga manual.
	 */
	private transient BOCargaManual boCarga;
	
	/**
	 * Muestra la pantalla de Cargas Manuales
	 * @param rq Request
	 * @param res Response
	 * @return ModelAndView
	 * @throws BusinessException 
	 */
	@RequestMapping("cargasManuales.do")
	public ModelAndView muestraFormularioCarga(HttpServletRequest rq, HttpServletResponse res) throws BusinessException{
		final String metodo = this.getClass().getName() + ".muestraFormularioCarga()";
		this.debug("Inicio de metodo: " + metodo);
		final Object objeto = rq.getSession().getAttribute(MODULOS_PERMITIDOS);
		ValidadorAccesoPantallas.validarAcceso(objeto, MODULO, PANTALLA);
		final String recurso = "cargasManuales";
		this.debug("Se cargara al pagina " + recurso);
		this.debug("Fin de metodo " + metodo);
		return new ModelAndView(recurso);
	}
	
	/**
	 * Se encarga de realizar la peticion de carga de un input enviado por el usuario.
	 * @param input Archivo cargado por el usuario.
	 * @return ModelandView
	 * @throws BusinessException Excepcion lanzada desde la capa de negocio en caso de error.
	 * @throws IOException Se lanza cuando existe un error a leer el archivo cargado por el usuario.
	 */
	@RequestMapping("cargarInput.do")
	public ModelAndView cargaArchivo(@RequestParam("file") MultipartFile input) throws BusinessException, IOException{
		final String metodo = this.getClass().getName() + ".cargaArchivo()";
		this.debug("Inicio de metodo " + metodo);
		final Map<String, String> modelo = new HashMap<String, String>();
		final String pagina = "cargasManuales";
		final boolean esNombreValido = boCarga.validarNombreInput(input.getOriginalFilename(), 
				this.getArchitechBean()); 
		if(esNombreValido){
			this.debug("El nombre del archivo es valido ::: " + input.getOriginalFilename());
			try {
				this.debug("El archivo contienen la siguiente cantidad de bytes: " + input.getBytes().length);
				final String ruta = this.getConfigDeCmpAplicacion("PARAM_CONFIG_RUTA_INPUTS");
				this.debug("La ruta en la que se cargara el archivo es: " +  ruta + File.separator + input.getOriginalFilename());
				UtileriasFront.guardaArchivo(ruta + File.separator + input.getOriginalFilename(), input.getBytes());
				modelo.put("inputCargado", input.getOriginalFilename());
			}catch(IOException e){
				this.debug("Ocurrio una excepcion al guardar el archivo: " + e);
				boCarga.registraCargaInput(input.getOriginalFilename(), false, this.getArchitechBean());
				throw e;
			}
			boCarga.registraCargaInput(input.getOriginalFilename(), true, this.getArchitechBean());
			this.debug("Finalizo la carga del archivo " + input.getOriginalFilename());
		}else{
			modelo.put("codigoError", "1001");
			this.debug("El nombre del archivo no es valido :::" + input.getOriginalFilename());
		}
		this.debug("El modelo enviado al cliente es " + modelo.toString());
		this.debug("La pagina de destino es " + pagina);
		this.debug("Fin de ejecucion de metodo " + metodo);
		return new ModelAndView(pagina, modelo);
	}
	
	/**
	 * Manejador de errores de este controller.
	 * @param req Request
	 * @param res Response
	 * @param handler Handler
	 * @param excepcion Exception
	 * @return ModelAndView
	 */
	@ExceptionHandler
	public ModelAndView manejadorErrores(HttpServletRequest req, HttpServletResponse res, Object handler, Exception excepcion){
		final String metodo = this.getClass().getName() + ".manejadorErrores";
		this.debug("Inicio de ejecucion de metodo " + metodo);
		String pagina = null;
		final Map<String, String> modelo = new HashMap<String, String>();
		if(handler instanceof BusinessException){
			modelo.put("codeError", ((BusinessException)handler).getCode());
			pagina = "../errores/errorAgave.do";
			this.debug("Fue cachada una excepcion BuisinessException " + handler.toString());
		}else{
			pagina = "../errores/errorGrl.do";
			this.debug("Fue cachada una excepcion " + handler.toString());
		}
		this.debug("El modelo enviado al cliente es " + modelo.toString());
		this.debug("La pagina de destino es: " + pagina);
		return new ModelAndView("redirect:" + pagina, modelo);
	}
	
	/**
	 * @param boCarga the boCarga to set
	 */
	public void setBoCarga(BOCargaManual boCarga) {
		this.boCarga = boCarga;
	}
}
