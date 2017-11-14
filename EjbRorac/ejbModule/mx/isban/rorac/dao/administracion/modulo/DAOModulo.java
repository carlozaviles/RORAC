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
package mx.isban.rorac.dao.administracion.modulo;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.administracion.modulo.BeanModulo;
import mx.isban.rorac.bean.administracion.modulo.BeanModuloRespuesta;

/**
 * Interface DAOModulo
 *
 * Interface encargada de establecer los metodos necesarios para la manipulacion
 * de modulos en la base de datos
 *
 * @author everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
@Local
public interface DAOModulo {

	/**
	 * Metodo encargado de obtener todos los modulos a los que acceden la lista
	 * de grupos
	 *
	 * @param grupos
	 *            La lista de grupos de 0 a N para regresar los modulos
	 * @return Un objeto de tipo BeanModuloRespuesta con el resultado de la
	 *         operacion en base de datos
	 */
	public BeanModuloRespuesta obtenerModulosPorGrupos(
			ArchitechSessionBean sessionBean, String[] grupos);

	/**
	 * Metodo encargado de obtener todos los modulos
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @return Un objeto de tipo BeanModuloRespuesta con el resultado de la
	 *         operacion en base de datos
	 */
	public BeanModuloRespuesta obtenerTodosModulos(
			ArchitechSessionBean sessionBean);

	/**
	 * Metodo que obtiene un modulo en relacion a un id de pantalla
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idPantalla
	 *            El id de pantalla con el que se buscara el modulo relacionado
	 * @return Un objeto de tipo BeanModuloRespuesta con el resultado de la
	 *         operacion en base de datos
	 */
	public BeanModuloRespuesta obtenerModuloPorPantalla(
			ArchitechSessionBean sessionBean, String idPantalla);

	/**
	 * Metodo que obtiene un modulo en relacion a un id
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idModulo
	 *            El id del modulo a buscar
	 * @return Un objeto de tipo BeanModuloRespuesta con el resultado de la
	 *         operacion en base de datos
	 */
	public BeanModuloRespuesta obtenerModuloPorId(
			ArchitechSessionBean sessionBean, String idModulo);

	/**
	 * Metodo encargado de modificar un modulo
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param modulo
	 *            Un objeto de tipo BeanModulo con los datos a modificar
	 * @return Un objeto de tipo BeanModuloRespuesta con el resultado de la
	 *         operacion en base de datos
	 */
	public BeanModuloRespuesta modificarModulo(
			ArchitechSessionBean sessionBean, BeanModulo modulo);

	/**
	 * Metodo que guarda un modulo en la base de datos
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param modulo
	 *            Un Objeto de tipo BeanModulo con los datos a almacenar
	 * @return Un objeto de tipo BeanModuloRespuesta con el resultado de la
	 *         operacion en base de datos
	 */
	public BeanModuloRespuesta guardarModulo(ArchitechSessionBean sessionBean,
			BeanModulo modulo);

	/**
	 * Metodo que elimina un modulo en relacion a un id
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idModulo
	 *            El id del modulo que se eliminara
	 * @return Un objeto de tipo BeanModuloRespuesta con el resultado de la
	 *         operacion en base de datos
	 */
	public BeanModuloRespuesta borrarModulo(ArchitechSessionBean sessionBean,
			String idModulo);

}
