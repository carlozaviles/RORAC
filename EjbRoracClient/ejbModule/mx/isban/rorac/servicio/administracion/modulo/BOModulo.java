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
package mx.isban.rorac.servicio.administracion.modulo;

import java.util.List;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.administracion.modulo.BeanModulo;

/**
 * Interface BOModulo
 *
 * Interface encargada de definir los metodos necesarios que permitan realizar
 * las acciones relacionadas a la manipulacion de elementos tipo Modulo
 *
 * @author everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
@Remote
public interface BOModulo {

	/**
	 * Metodo encargado de obtener todos los modulos en relacion a un usuario
	 * 
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idUsuario
	 *            El id del usuario con el que se buscaran sus modulos
	 *            relacionados
	 * @return Una lista con objetos de tipo BeanModulo
	 * @throws BusinessException
	 *             En caso de presentarse un error al momento de realizar la
	 *             busqueda de modulos por usuario
	 */
	public List<BeanModulo> obtenerModulosPorGrupo(
			ArchitechSessionBean sessionBean, String grupos)
			throws BusinessException;

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @return List<BeanModulo>
	 * @throws BusinessException
	 *             En caso de presentarse un error al momento de realizar la
	 *             busqueda de todos los modulos
	 */
	public List<BeanModulo> obtenerTodosModulos(ArchitechSessionBean sessionBean)
			throws BusinessException;

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idModulo
	 *            ID.
	 * @return BeanModulo
	 * @throws BusinessException
	 *             En caso de presentarse un error al momento de realizar la
	 *             busqueda de un modulo por id
	 */
	public BeanModulo obtenerModuloPorId(ArchitechSessionBean sessionBean,
			String idModulo) throws BusinessException;

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param modulo
	 *            Modulo.
	 * @throws BusinessException
	 *             En caso de presentarse un error al momento de realizar la
	 *             modificacion de un modulo
	 */
	public void modificarModulo(ArchitechSessionBean sessionBean,
			BeanModulo modulo) throws BusinessException;

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param modulo
	 *            Modulo
	 * @throws BusinessException
	 *             En caso de presentarse un error al momento de realizar el
	 *             alta de un modulo
	 */
	public void altaModulo(ArchitechSessionBean sessionBean, BeanModulo modulo)
			throws BusinessException;

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idModulo
	 *            ID.
	 * @throws BusinessException
	 *             En caso de presentarse un error al momento de eliminar un
	 *             modulo de la base de datos
	 */
	public void borrarModulo(ArchitechSessionBean sessionBean, String idModulo)
			throws BusinessException;
}
