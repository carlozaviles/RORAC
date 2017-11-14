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
package mx.isban.rorac.servicio.administracion.pantalla;

import java.util.List;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;


/**
 * Interface BOPantalla
 * 
 * Interface encargada de definir los metodos necesarios que permitan realizar
 * las acciones relacionadas a la manipulacion de elementos tipo pantalla
 * 
 * @author everis
 * @version 1.0
 * @see www.everis.com/mexico
 * 
 */
@Remote
public interface BOPantalla {
	
	/**
	 * Metodo que invoca a la capa de datps para buscar todas las pantallas 
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @return Una lista con las pantallas disponibles
	 * @throws BusinessException En caso de presentarse un error al momento de consultar la base de datos
	 */
	public List<BeanPantalla> buscarTodasPantallas(ArchitechSessionBean sessionBean)throws BusinessException;
	
	/**
	 * Metodo que invoca a la capa de datos para obtener una pantalla por su id
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idPantalla El id de la pantalla a buscar
	 * @return La pantalla encontrada
	 * @throws BusinessException En caso de presentarse un error al momento de consultar la base de datos
	 */
	public BeanPantalla obtenerPantallaPorId(ArchitechSessionBean sessionBean, String idPantalla) throws BusinessException;
	
	/**
	 * Metodo que invoca a la capa de datos para ponder modificar una pantalla
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param pantalla Un objeto de tipo BeanPantalla con los parametros a actualizar
	 * @throws BusinessException En caso de presentarse un error al momento de consultar la base de datos
	 */
	public void modificarPantalla(ArchitechSessionBean sessionBean,BeanPantalla pantalla)throws BusinessException;
	
	/**
	 * Metodo encargado de invocar a la capa de datos para agregar una pantalla
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param pantalla Un objeto de tipo BeanPantalla con los datos a almacenar en la base de datos
	 * @throws BusinessException En caso de presentarse un error al momento de consultar la base de datos
	 */
	public void agregarPantalla(ArchitechSessionBean sessionBean,BeanPantalla pantalla)throws BusinessException;
	
	/**
	 * Metodo encargado de invocar la capa de datos para eliminar un registro de la base de datos en relacion a un id
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idPantalla El id de la pantalla a eliminar
	 * @throws BusinessException En caso de presentarse un error al momento de consultar la base de datos.
	 */
	public void borrarPantalla(ArchitechSessionBean sessionBean,String idPantalla)throws BusinessException;
}
