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
package mx.isban.rorac.dao.administracion.pantalla;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantallaRespuesta;

/**
 * Interface DAOPantalla
 *
 * Interface encargada de establecer los metodos necesarios para la manipulacion
 * de pantallas en la base de datos
 *
 * @author everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
@Local
public interface DAOPantalla {

	/**
	 * Metodo que obtiene todas las pantallas, realizando la consulta en la base
	 * de datos
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @return Un objeto de tipo BeanPantallaRespuesta
	 */
	public BeanPantallaRespuesta obtenerTodasPantallas(
			ArchitechSessionBean sessionBean);

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idGrupo
	 *            El id del grupo a buscar
	 * @return Un objeto de tipo BeanPantallaRespuesta
	 */
	public BeanPantallaRespuesta obtenerPantallasPorGrupo(
			ArchitechSessionBean sessionBean, String idGrupo);

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idPantalla
	 *            El id de la pantalla a buscar
	 * @return Un objeto de tipo BeanPantallaRespuesta
	 */
	public BeanPantallaRespuesta obtenerPantallaPorId(
			ArchitechSessionBean sessionBean, String idPantalla);

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param pantalla
	 *            Un objeto de tipo BeanPantalla, el cual contiene los elementos
	 *            a modificar
	 * @return Un objeto de tipo BeanPantallaRespuesta
	 */
	public BeanPantallaRespuesta modificarPantalla(
			ArchitechSessionBean sessionBean, BeanPantalla pantalla);

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param pantalla
	 *            Un objeto de tipo BeanPantalla, con los datos a almacenar en
	 *            la base de datos
	 * @return Un objeto de tipo BeanPantallaRespuesta
	 */
	public BeanPantallaRespuesta altaPantalla(ArchitechSessionBean sessionBean,
			BeanPantalla pantalla);

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idPantalla
	 *            El id de la pantalla que sera eliminada
	 * @return Un objeto de tipo BeanPantallaRespuesta
	 */
	public BeanPantallaRespuesta borrarPantalla(
			ArchitechSessionBean sessionBean, String idPantalla);

	/**
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param idModulo
	 *            El id del modulo al que pertenece el usuario para obtener las
	 *            pantallas
	 * @param grupos
	 *            los grupos para identificar pantallas disponibles
	 * @return Un objeto de tipo BeanPantallaRespuesta
	 */
	public BeanPantallaRespuesta obtenerPantallasPorGruposModulo(
			ArchitechSessionBean sessionBean, String idModulo, String[] grupos);

}
