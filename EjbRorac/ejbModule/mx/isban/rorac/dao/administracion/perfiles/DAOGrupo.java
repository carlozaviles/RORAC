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
package mx.isban.rorac.dao.administracion.perfiles;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;
import mx.isban.rorac.bean.administracion.grupo.BeanGrupoRespuesta;

/**
 * Interface DAOGrupo
 * 
 * Interface encargada de establecer los metodos necesarios 
 * para la manipulacion de Grupos en la base de datos
 * 
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
@Local
public interface DAOGrupo {

	/**Metodo encargado de buscar todos los grupos
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @return Un objeto de tipo BeanGrupoRespuesta con los codigos de error(En caso de presentarse)
	 */
	public BeanGrupoRespuesta buscarTodosGrupos(ArchitechSessionBean sessionBean);
	/**
	 * Metodo encargado de consultar un grupo por su Id
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idGrupo String con el id del grupo
	 * @return Un objeto de tipo BeanGrupoRespuesta con los codigos de error(En caso de presentarse)
	 */
	public BeanGrupoRespuesta consultarGrupoPorId(ArchitechSessionBean sessionBean, String idGrupo);
	/**
	 * Metodo encargado de consultar un grupo por su nombre
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param nombreGrupo String con el nombre del grupo a buscar
	 * @return Un objeto de tipo BeanGrupoRespuesta con los codigos de error(En caso de presentarse)
	 */
	public BeanGrupoRespuesta consultarGrupoPorNombre(ArchitechSessionBean sessionBean, String nombreGrupo);
	/**
	 * Metodo encargado de modificar un grupo
	 * @param sessionBean Objeto de tipo ArchitechSessionBean
	 * @param grupo Grupo a modificar
	 * @return Un objeto de tipo BeanGrupoRespuesta con los codigos de error(En caso de presentarse)
	 */
	public BeanGrupoRespuesta modificarGrupo(ArchitechSessionBean sessionBean,BeanGrupo grupo);
	/**
	 * Metodo encargado de dar de alta un grupo
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param grupo Grupo a dar de alta
	 * @return Un objeto de tipo BeanGrupoRespuesta con los codigos de error(En caso de presentarse)
	 */
	public BeanGrupoRespuesta altaGrupo(ArchitechSessionBean sessionBean,BeanGrupo grupo);
	/**
	 * Metodo encargado de borrar un Grupo
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idGrupo String con el id del grupo a borrar
	 * @return Un objeto de tipo BeanGrupoRespuesta con los codigos de error(En caso de presentarse)
	 */
	public BeanGrupoRespuesta borrarGrupo(ArchitechSessionBean sessionBean, String idGrupo);
	/**
	 * Metodo encargado de obtener todos los grupos relacionados a un usaurio
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idUsuario Id del usuario a obtener los grupos que tiene
	 * @return Un objeto de tipo BeanGrupoRespuesta con los codigos de error(En caso de presentarse)
	 */
	public BeanGrupoRespuesta obtenerGrupoPorUsuario(ArchitechSessionBean sessionBean, String idUsuario);
}
