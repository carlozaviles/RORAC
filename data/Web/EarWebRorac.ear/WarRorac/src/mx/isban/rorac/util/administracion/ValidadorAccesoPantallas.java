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
package mx.isban.rorac.util.administracion;

import java.util.ArrayList;
import java.util.List;

import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.administracion.modulo.BeanModulo;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;

/**
* Clase ValidadorAccesoPantallas
* 
* <P>Clase encargada de verificar que la pantalla a la que desea acceder un usuario es valida, de lo
* contrario regresa un error de tipo BusinesException
*   
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
public final class ValidadorAccesoPantallas {
	
	/**
	 * Constante que tiene el codigo de error de acceso
	 */
	private static final String ERROR_ACCESO = "ERROR_ACCESO";
	/**
	 * Constante que tiene el mensaje de error de no acceso a las pantallas
	 */
	private static final String MENSAJE_ERROR = "Usted no tiene permitido acceder a esta pantalla";
	
	/**
	 * Constructor vacio de tipo privado
	 */
	private ValidadorAccesoPantallas() {
		super();
	}
	
	/**
	 * Metodo que valida si el acceso a una pantalla es valido, de lo contrario se crea una excepcion de tipo BussinesException
	 * @param objeto La lista que se encuentra en sesion, con los valores del usuario
	 * @param nombreModulo Nombre del modulo a validar
	 * @param nombrePantalla (Opcional)Nombre de la pantalla a validar
	 * @throws BusinessException En caso de presentarse un error de validacion
	 */
	public static void validarAcceso(Object objeto,String nombreModulo, String nombrePantalla)throws BusinessException{
		List<BeanModulo> modulos = obtenerListaModulos(objeto);
		boolean isModuloValido = false;
		for (BeanModulo beanModulo : modulos) {
			if(beanModulo.getNombreModulo().equals(nombreModulo)){
				isModuloValido = true;
				if(null != nombrePantalla){
					verificarPantallas(beanModulo.getPantallas(), nombrePantalla);
				}
			}
		}
		if(!isModuloValido){
			throw new BusinessException(ERROR_ACCESO,MENSAJE_ERROR);
		}
		
	}
	
	/**
	 * Metodo que obtiene la lista de modulos, al realizar la transformacion del objeto obtenido en session con
	 * el listado de modulos que puede un usuario acceder
	 * @param objeto El objeto a transformar en la lista de tipo BeanModulo
	 * @return Una lista de objetos de tipo BeanModulo
	 * @throws BusinessException En caso de presentarse un error al momento de realizar la conversion a la lista
	 */
	private static List<BeanModulo> obtenerListaModulos(Object objeto)throws BusinessException {
		final List<BeanModulo> listaModulos = new ArrayList<BeanModulo>();
		if(null == objeto){
			throw new BusinessException(ERROR_ACCESO,MENSAJE_ERROR);
		}else{
			if(objeto instanceof List){
				for (int i = 0; i < ((List<?>)objeto).size(); i++) {
					final Object item =  ((List<?>)objeto).get(i);
					if(item instanceof BeanModulo){
						listaModulos.add((BeanModulo)item);
					}
				}
			}
		}
		return listaModulos;
	}

	/**
	 * Metodo encargado de verificar si el usuario tiene permitido acceder a una pantalla
	 * @param pantallas Listado de pantallas a las que puede acceder el usuario
	 * @param nombrePantalla El nombre de la pantalla a la que puede acceder el usuario
	 * @throws BusinessException En caso de que el usuario no tenga permitido acceder a cierta pantalla
	 */
	private static void verificarPantallas(List<BeanPantalla> pantallas,String nombrePantalla)throws BusinessException{
		boolean isPantallaValida = false;
		for (BeanPantalla beanPantalla : pantallas) {
			if(beanPantalla.getNombrePantalla().equals(nombrePantalla)){
				isPantallaValida = true;
			}
		}
		if(!isPantallaValida){
			throw new BusinessException(ERROR_ACCESO,MENSAJE_ERROR);
		}
	}
}
