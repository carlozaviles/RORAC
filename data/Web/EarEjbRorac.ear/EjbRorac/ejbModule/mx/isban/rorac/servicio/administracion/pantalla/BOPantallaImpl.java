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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.administracion.modulo.BeanModulo;
import mx.isban.rorac.bean.administracion.modulo.BeanModuloRespuesta;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantallaRespuesta;
import mx.isban.rorac.dao.administracion.modulo.DAOModulo;
import mx.isban.rorac.dao.administracion.pantalla.DAOPantalla;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
* Clase BOPantallaImpl
* 
* <P>Clase de tipo SessionBean, el cual implementa la interface BOPantalla, 
* la cual se encarga de realizar la logica de negocio relacionada a la administracion de 
* pantallas.
* 
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOPantallaImpl extends Architech implements BOPantalla {
       
	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = 1663783981536130744L;

	/**
	 * Objeto de tipo DAOPantalla, el cual se encarga de realizar las consultas a la
	 * base de datos, para obtener todo lo relacionado a las pantallas 
	 */
	@EJB
	private DAOPantalla daoPantalla;
	
	/**
	 * Objeto de tipo DAOModulo, el cual se encarga de realizar las consultas a la
	 * base de datos, para obtener todo lo relacionado a los modulos 
	 */
	@EJB
	private DAOModulo daoModulo;


	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.pantalla.BOPantalla#obtenerPantallaPorId(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public BeanPantalla obtenerPantallaPorId(ArchitechSessionBean sessionBean,
			String idPantalla) throws BusinessException {
		this.info("Iniciando la busqueda de la pantalla por id....");
		final BeanPantallaRespuesta resultadoConsulta = daoPantalla.obtenerPantallaPorId(sessionBean, idPantalla);
		verificarRespuesta(resultadoConsulta);
		final List<BeanPantalla> pantallas = resultadoConsulta.getPantallas();
		BeanPantalla pantalla = new BeanPantalla();
		if(null != pantallas && !pantallas.isEmpty()){
			pantalla = pantallas.get(0);
			final List<BeanModulo> modulos = establecerModuloSeleccionado(sessionBean, idPantalla);
			pantalla.setModulos(modulos);
		}
		this.info("Finaliza la ejecucion del metodo de busqueda de la pantalla por Id");
		return pantalla;
	}

	/**
	 * Metodo que establece el modulo seleccionado en relacion a un id de pantalla
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idPantalla El id de la pantalla a relacionar el modulo
	 * @return Regresa una lista con los modulos encontrados
	 * @throws BusinessException En caso de ocurrir un error al momento de consultar la base de datos
	 */
	private List<BeanModulo> establecerModuloSeleccionado(
			ArchitechSessionBean sessionBean, String idPantalla)throws BusinessException {
		this.info("Iniciando la busqueda de los modulos relacionados a una pantalla con id:"+idPantalla);
		final BeanModuloRespuesta modulosSeleccionadosRespuesta = daoModulo.obtenerModuloPorPantalla(sessionBean, idPantalla);
		verificarRespuesta(modulosSeleccionadosRespuesta);
		final List<BeanModulo> modulosSeleccionados = modulosSeleccionadosRespuesta.getModulos();
		final BeanModuloRespuesta todosModulos = daoModulo.obtenerTodosModulos(sessionBean);
		verificarRespuesta(todosModulos);
		final List<BeanModulo>modulos = todosModulos.getModulos();
		for (BeanModulo beanModulo : modulos) {
			for (BeanModulo beanModuloSeleccionado : modulosSeleccionados) {
				if(beanModulo.getIdModulo().equals(beanModuloSeleccionado.getIdModulo())){
					beanModulo.setModuloSeleccionado(true);
				}
			}
		}
		this.info("Finaliza la ejecucion del metodo de establecimiento de modulos seleccionados.");
		return modulos;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.pantalla.BOPantalla#buscarTodasPantallas(mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanPantalla> buscarTodasPantallas(
			ArchitechSessionBean sessionBean)throws BusinessException{
		this.info("Iniciando la busqueda de todas las pantallas....");
		final BeanPantallaRespuesta resultadoConsulta = daoPantalla.obtenerTodasPantallas(sessionBean);
		verificarRespuesta(resultadoConsulta);
		final List<BeanPantalla> pantallas = resultadoConsulta.getPantallas();
		this.info("El numero de pantallas es:"+pantallas.size());
		this.info("Finaliza la ejecucion del metodo de busqueda de todas las pantallas");
		return pantallas;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.pantalla.BOPantalla#modificarPantalla(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.pantalla.BeanPantalla)
	 */
	@Override
	public void modificarPantalla(ArchitechSessionBean sessionBean,
			BeanPantalla pantalla) throws BusinessException {
		this.info("Iniciando la modificacion de la pantalla....");
		final BeanPantallaRespuesta resultadoConsulta = daoPantalla.modificarPantalla(sessionBean, pantalla);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo actualizacion de pantalla");
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.pantalla.BOPantalla#agregarPantalla(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.pantalla.BeanPantalla)
	 */
	@Override
	public void agregarPantalla(ArchitechSessionBean sessionBean,
			BeanPantalla pantalla) throws BusinessException {
		this.info("Iniciando el alta de la pantalla....");
		final BeanPantallaRespuesta resultadoConsulta = daoPantalla.altaPantalla(sessionBean, pantalla);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo de alta de pantalla");
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.pantalla.BOPantalla#borrarPantalla(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public void borrarPantalla(ArchitechSessionBean sessionBean,
			String idPantalla) throws BusinessException {
		this.info("Iniciando la baja de la pantalla....");
		final BeanPantallaRespuesta resultadoConsulta = daoPantalla.borrarPantalla(sessionBean, idPantalla);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo de alta de pantalla");
	}

	/**
	 * Metodo encargado de verificar las respuestas obtenidas del DAO
	 * @param resultadoConsulta Un objeto con los resultados de la consulta
	 * @throws BusinessException En caso de presentarse un error
	 */
	private void verificarRespuesta(BeanResultBO resultadoConsulta)throws BusinessException{
		if(null == resultadoConsulta){
			this.error("Respuesta nula al consultar la base de datos");
			throw new BusinessException(ConstantesRorac.ERROR_RESPUESTA_NULA_IDA_PERFILAMIENTO,
					"Respuesta nula al consultar la base de datos");
		}
		if(!ConstantesRorac.OPERACION_EXITOSA.equals(resultadoConsulta.getCodError())){
			this.error("Se ha presentado un error al momento de realizar la consulta en la base de datos :"+resultadoConsulta.getCodError());
			throw new BusinessException(resultadoConsulta.getCodError(),resultadoConsulta.getMsgError());
		}
	}

	/**
	 * Metodo que obtiene un objeto de tipo DAOPantalla
	 * @return Un objeto de tipo DAOPantalla
	 */
	public DAOPantalla getDaoPantalla() {
		return daoPantalla;
	}

	/**
	 * Metodo que establece un objeto de tipo DAOPantalla
	 * @param daoPantalla El objeto a establecer
	 */
	public void setDaoPantalla(DAOPantalla daoPantalla) {
		this.daoPantalla = daoPantalla;
	}

	/**
	 * Metodo para obtener un objeto de tipo DAOModulo
	 * @return Un objeto de tipo DAOModulo
	 */
	public DAOModulo getDaoModulo() {
		return daoModulo;
	}

	/**
	 * Metodo para establecer un objeto de tipo DAOModulo
	 * @param daoModulo El objeto de tipo DAOModulo a establecer
	 */
	public void setDaoModulo(DAOModulo daoModulo) {
		this.daoModulo = daoModulo;
	}
	
}
