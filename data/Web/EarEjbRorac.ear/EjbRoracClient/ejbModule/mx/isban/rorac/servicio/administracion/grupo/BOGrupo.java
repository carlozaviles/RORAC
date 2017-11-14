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
package mx.isban.rorac.servicio.administracion.grupo;

import java.util.List;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;

/**
 * Interface BOGrupo
 * 
 * Interface encargada de definir los metodos necesarios que permitan realizar
 * las acciones relacionadas a la manipulacion de elementos Grupo
 * 
 * @author everis
 * @version 1.0
 * @see www.everis.com/mexico
 * 
 */
@Remote
public interface BOGrupo {
	
	/**
	 * Metodo encargado de buscar todos los grupos
	 * @param sessionBean Objeto de tipo ArchitechSessionBean
	 * @return Una lista con todos los grupos
	 * @throws BusinessException En caso de presentarse un error
	 */
	public List<BeanGrupo> buscarTodosGrupos(ArchitechSessionBean sessionBean)throws BusinessException;
	
	/**
	 * Metodo encargado de agregar un nuevo grupo
	 * @param grupo Un objeto de tipo BeanGrupo
	 * @param sessionBean Objeto de tipo ArchitechSessionBean
	 * @throws BusinessException En caso de presentarse un error
	 */
	public void agregarGrupo(BeanGrupo grupo, ArchitechSessionBean sessionBean) throws BusinessException;
	
	/**
	 * Metodo encargado de modificar un grupo
	 * @param grupo Un objeto de tipo BeanGrupo
	 * @param sessionBean Objeto de tipo ArchitechSessionBean
	 * @throws BusinessException En caso de presentarse un error
	 */
	public void modificarGrupo(BeanGrupo grupo, ArchitechSessionBean sessionBean)throws BusinessException;
	
	/**
	 * Metodo encargado de borrar un grupo 
	 * @param idGrupo El id del grupo a dar de baja
	 * @param sessionBean Objeto de tipo ArchitechSessionBean
	 * @throws BusinessException En caso de presentarse un error
	 */
	public void borrarGrupo(String idGrupo, ArchitechSessionBean sessionBean)throws BusinessException;
	
	/**
	 * Metodo encargado de buscar un Grupo, en relacion a su id
	 * @param idGrupo El id del grupo a buscar
	 * @param sessionBean Objeto de tipo ArchitechSessionBean
	 * @return Un objeto de tipo BeanGrupo
	 * @throws BusinessException En caso de presentarse un error
	 */
	public BeanGrupo consultarGrupo(String idGrupo, ArchitechSessionBean sessionBean) throws BusinessException;
	
}
