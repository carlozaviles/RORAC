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
package mx.isban.rorac.servicio.administracion.usuario;

import java.util.List;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuario;

/**
 * Interface BOUsuario
 * 
 * Interface encargada de definir los metodos necesarios que permitan realizar
 * las acciones relacionadas a la manipulacion de elementos tipo Usuario
 * 
 * @author everis
 * @version 1.0
 * @see www.everis.com/mexico
 * 
 */
@Remote
public interface BOUsuario {
	
	/**
	 * Metodo encargado de invocar al objeto de acceso a datos para obtener todos los usuarios
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @return Una lista de objetos de tipo BeanUsuario
	 * @throws BusinessException En caso de presentarse un error al momento de realizar la busqueda de modulos por usuario
	 */
	public List<BeanUsuario> obtenerTodosUsuarios(ArchitechSessionBean sessionBean)throws BusinessException;
	
	/**
	 * Metodo que obtiene un usuario por su id
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idUsuario ID
	 * @return Un objeto de tipo BeanUsuario que contiene los datos del usuario obtenido
	 * @throws BusinessException En caso de presentarse un error al momento de realizar la busqueda de modulos por usuario
	 */
	public BeanUsuario obtenerUsuarioPorID(ArchitechSessionBean sessionBean,String idUsuario)throws BusinessException;
	
	/**
	 * Metodo encargado de invocar al objeto de acceso a datos para modificar un usuario
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param usuario Un objeto de tipo BeanUsuario con los datos del usuario a modificar
	 * @throws BusinessException En caso de presentarse un error al momento de realizar la busqueda de modulos por usuario
	 */
	public void modificarUsuario(ArchitechSessionBean sessionBean,BeanUsuario usuario)throws BusinessException;
	
	/**
	 * Metodo encargado de invocar un objeto de acceso a datos para dar de alta un usuario
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param usuario Un objeto de tipo BeanUsaurio que contiene los datos para realizar el alta de un usuario
	 * @throws BusinessException En caso de presentarse un error al momento de realizar la busqueda de modulos por usuario
	 */
	public void altaUsuario(ArchitechSessionBean sessionBean,BeanUsuario usuario)throws BusinessException;
	

}
