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
import mx.isban.rorac.utilerias.general.UtileriasNegocio;

/**
 * Clase BOModuloImpl
 *
 * <P>
 * Clase de tipo SessionBean, el cual implementa la interface BOModulo, la cual
 * se encarga de realizar la logica de negocio relacionada a la administracion
 * de modulos.
 *
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOModuloImpl extends Architech implements BOModulo {

	private static final String REGEX_SPLIT = ",";

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = 5905740417795097560L;

	/**
	 * Objeto de tipo DAOModulo, el cual se encarga de realizar las consultas a
	 * la base de datos, para obtener todo lo relacionado a los modulos
	 */
	@EJB
	private DAOModulo daoModulo;

	/**
	 * Objeto de tipo DAOPantalla, el cual se encarga de realizar las consultas
	 * a la base de datos, para obtener todo lo relacionado a las pantallas
	 */
	@EJB
	private DAOPantalla daoPantalla;

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.cifrascontrol.servicio.administracion.modulo.BOModulo#
	 * obtenerModulosPorUsuarioLogueado
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public List<BeanModulo> obtenerModulosPorGrupo(
			final ArchitechSessionBean sessionBean, final String grupos)
			throws BusinessException {
		this.info("Iniciando la busqueda de modulos para mostrar las pantallas en relacion a un grupo");
		final BeanModuloRespuesta resultadoConsulta = daoModulo.obtenerModulosPorGrupos(sessionBean, grupos.split(REGEX_SPLIT));
		verificarRespuesta(resultadoConsulta);
		final List<BeanModulo> modulos = resultadoConsulta.getModulos();
		establecerPantallas(sessionBean, modulos, grupos);
		this.info("El numero de grupos administrativos encontrados:"
				+ modulos.size());
		this.info("Finaliza la ejecucion del metodo de busqueda de todos los grupos administrativos");
		return modulos;
	}

	/**
	 * Metodo encargado de establecer las pantallas relacionadas a un modulo de
	 * un usuario
	 *
	 * @param sessionBean
	 *            Un objeto de tipo ArchitechSessionBean
	 * @param modulos
	 *            Modulos de un usuario, al cual se le asignaran las pantallas
	 * @param idUsuario
	 *            Id del usuario al que se le estableceran las pantallas
	 * @throws BusinessException
	 *             En caso de presentarse un error
	 */
	private void establecerPantallas(final ArchitechSessionBean sessionBean,final List<BeanModulo> modulos, final String grupos) throws BusinessException {
		this.info("Iniciando la busqueda de todas las pantallas relacionadas a un usuario....");
		for (BeanModulo beanModulo : modulos) {
			beanModulo.setNombreModulo(UtileriasNegocio.normalizarTexto(beanModulo.getNombreModulo()));
			final BeanPantallaRespuesta respuesta = daoPantalla.obtenerPantallasPorGruposModulo(sessionBean,beanModulo.getIdModulo(), grupos.split(REGEX_SPLIT));
			verificarRespuesta(respuesta);
			final List<BeanPantalla> pantallas = respuesta.getPantallas();
			for (BeanPantalla beanPantalla : pantallas) {
				beanPantalla.setNombrePantalla(UtileriasNegocio.normalizarTexto(beanPantalla.getNombrePantalla()));
			}
			beanModulo.setPantallas(pantallas);
		}
	}

	/**
	 * Metodo encargado de verificar las respuestas obtenidas del DAO
	 *
	 * @param resultadoConsulta
	 *            Un objeto con los resultados de la consulta
	 * @throws BusinessException
	 *             En caso de presentarse un error
	 */
	private void verificarRespuesta(final BeanResultBO resultadoConsulta)
			throws BusinessException {
		if (null == resultadoConsulta) {
			this.error("Respuesta nula al consultar la base de datos");
			throw new BusinessException(
					ConstantesRorac.ERROR_RESPUESTA_NULA_IDA_PERFILAMIENTO,
					"Respuesta nula al consultar la base de datos");
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(resultadoConsulta
				.getCodError())) {
			this.error("Se ha presentado un error al momento de realizar la consulta en la base de datos :"
					+ resultadoConsulta.getCodError());
			throw new BusinessException(resultadoConsulta.getCodError(),
					resultadoConsulta.getMsgError());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.cifrascontrol.servicio.administracion.modulo.BOModulo#
	 * obtenerTodosModulos(mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanModulo> obtenerTodosModulos(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		this.info("Iniciando la busqueda de todos los modulos....");
		final BeanModuloRespuesta resultadoConsulta = daoModulo
				.obtenerTodosModulos(sessionBean);
		verificarRespuesta(resultadoConsulta);
		final List<BeanModulo> modulos = resultadoConsulta.getModulos();
		this.info("El numero de modulos administrativos encontrados:"
				+ modulos.size());
		this.info("Finaliza la ejecucion del metodo de busqueda de todos los modulos");
		return modulos;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.cifrascontrol.servicio.administracion.modulo.BOModulo#
	 * obtenerModuloPorId(mx.isban.agave.commons.beans.ArchitechSessionBean,
	 * java.lang.String)
	 */
	@Override
	public BeanModulo obtenerModuloPorId(
			final ArchitechSessionBean sessionBean, final String idModulo)
			throws BusinessException {
		this.info("Iniciando la busqueda del modulo por id....");
		final BeanModuloRespuesta resultadoConsulta = daoModulo
				.obtenerModuloPorId(sessionBean, idModulo);
		verificarRespuesta(resultadoConsulta);
		final List<BeanModulo> modulos = resultadoConsulta.getModulos();
		BeanModulo modulo = new BeanModulo();
		if (null != modulos && !modulos.isEmpty()) {
			modulo = modulos.get(0);
		}
		this.info("Finaliza la ejecucion del metodo de busqueda de la pantalla por Id");
		return modulo;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.cifrascontrol.servicio.administracion.modulo.BOModulo#
	 * modificarModulo(mx.isban.agave.commons.beans.ArchitechSessionBean,
	 * mx.isban.cifrascontrol.beans.administracion.modulo.BeanModulo)
	 */
	@Override
	public void modificarModulo(final ArchitechSessionBean sessionBean,
			final BeanModulo modulo) throws BusinessException {
		this.info("Iniciando la modificacion del modulo....");
		final BeanModuloRespuesta resultadoConsulta = daoModulo
				.modificarModulo(sessionBean, modulo);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo actualizacion de modulo");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.cifrascontrol.servicio.administracion.modulo.BOModulo#altaModulo
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean,
	 * mx.isban.cifrascontrol.beans.administracion.modulo.BeanModulo)
	 */
	@Override
	public void altaModulo(final ArchitechSessionBean sessionBean,
			final BeanModulo modulo) throws BusinessException {
		this.info("Iniciando el alta de modulo....");
		final BeanModuloRespuesta resultadoConsulta = daoModulo.guardarModulo(
				sessionBean, modulo);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo de alta de modulo");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.cifrascontrol.servicio.administracion.modulo.BOModulo#borrarModulo
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public void borrarModulo(final ArchitechSessionBean sessionBean,
			final String idModulo) throws BusinessException {
		this.info("Iniciando el alta de modulo....");
		final BeanModuloRespuesta resultadoConsulta = daoModulo.borrarModulo(
				sessionBean, idModulo);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo de alta de modulo");
	}

	/**
	 * Metodo que obtiene un objeto de tipo DAOModulo
	 *
	 * @return Un objeto de tipo DAOModulo
	 */
	public DAOModulo getDaoModulo() {
		return daoModulo;
	}

	/**
	 * Metodo que obtiene un objeto de tipo DAOModulo
	 *
	 * @param daoModulo
	 *            El objeto de tipo DAOModulo a establecer
	 */
	public void setDaoModulo(final DAOModulo daoModulo) {
		this.daoModulo = daoModulo;
	}

	/**
	 * Metodo que obtiene un objeto de tipo DAOPantalla
	 *
	 * @return Un objeto de tipo DAOPantalla
	 */
	public DAOPantalla getDaoPantalla() {
		return daoPantalla;
	}

	/**
	 * Metodo que establece un objeto de tipo DAOPantalla
	 *
	 * @param daoPantalla
	 *            El objeto de tipo DAOPantalla a establecer
	 */
	public void setDaoPantalla(final DAOPantalla daoPantalla) {
		this.daoPantalla = daoPantalla;
	}

}
