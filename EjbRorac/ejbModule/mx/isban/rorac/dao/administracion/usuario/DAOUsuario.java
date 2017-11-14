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
package mx.isban.rorac.dao.administracion.usuario;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuario;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuarioRespuesta;

/**
 * Interface DAOModulo
 * 
 * Interface encargada de establecer los metodos necesarios 
 * para la manipulacion de modulos en la base de datos
 * 
 * @author everis
 * @version 1.0
 * @see www.everis.com/mexico
 * 
 */
@Local
public interface DAOUsuario {
	
	/**
	 * Metodo encargado de obtener todos los usuarios
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @return Un objeto de tipo BeanUsuarioRespuesta con el resultado de la consulta a base de datos
	 */
	public BeanUsuarioRespuesta obtenerTodosUsuarios(ArchitechSessionBean sessionBean);
	/**
	 * Metodo encargado de obtener un usuario por su id
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idUsuario El id del usuario a buscar en la base de datos
	 * @return Un objeto de tipo BeanUsuarioRespuesta con el resultado de la consulta a base de datos
	 */
	public BeanUsuarioRespuesta obtenerUsuarioPorID(ArchitechSessionBean sessionBean,String idUsuario);
	/**
	 * Metodo encargado de actualizar los datos de un usuario
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param usuario Un objeto de tipo BeanUsuario que contiene los datos que permitan la modificacion de un usuario
	 * @return Un objeto de tipo BeanUsuarioRespuesta con el resultado de la consulta a base de datos
	 */
	public BeanUsuarioRespuesta modificarUsuario(ArchitechSessionBean sessionBean,BeanUsuario usuario);
	/**
	 * Metodo que permite realizar el alta de un usuario en la base de datos
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param usuario Un objeto de tipo BeanUsuario con los datos que permitan el alta de usuarios
	 * @return Un objeto de tipo BeanUsuarioRespuesta con el resultado de la consulta a base de datos
	 */
	public BeanUsuarioRespuesta altaUsuario(ArchitechSessionBean sessionBean,BeanUsuario usuario);
	
}
