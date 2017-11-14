package mx.isban.rorac.servicio.consultas;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanFlagNeteo;
import mx.isban.rorac.bean.consultas.BeanListaADNRetailDAO;
import mx.isban.rorac.bean.consultas.BeanListaAdnLocalDAO;
import mx.isban.rorac.bean.consultas.BeanListaFlagNeteoDAO;
import mx.isban.rorac.bean.consultas.BeanListaProductoGestionDAO;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.dao.consultas.DAOConsultaParametros;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class BOConsultaParametrosImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOConsultaParametrosImpl extends Architech implements
		BOConsultaParametros {

	private static final String ID_OPERACION_CONSULTA_PARAM = "PARAM_CONFIG_CONSULTA_TABLAS_PARAMETROS_ID_OPERACION";
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6273645933545667035L;
	/**
	 * Instancia del objeto de la capa de Acceso a Datos, DAOConsultaParametros.
	 */
	@EJB
	private transient DAOConsultaParametros consultaParametros;
	/**
	 * Manejador de Pistas de auditoria.
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistasAuditoria;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.consultas.BOConsultaParametros#llamaConsultaADNLocal
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanADNLocal> llamaConsultaADNLocal(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaADNLocal()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		this.info("Se realiza comunicacion con la capa de acceso a datos para ejecutar la consulta de ADN Local.");
		final BeanListaAdnLocalDAO respuesta = consultaParametros
				.ejecutaConsultaADNLocal(sessionBean);
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(ID_OPERACION_CONSULTA_PARAM));
		pistaAuditoria.setNombreArchivo("No Aplica");
		if (respuesta == null) {
			this.info("Se obtuvo una respuesta nula de la capa de acceso a datos.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(respuesta.getCodError())) {
			this.info("Se obtuvo un codigo de error al acceder a la capa de acceso a datos: "
					+ respuesta.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(respuesta.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Se envian los registros ADN Local al Front de la aplicacion.");
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return respuesta.getListaADNLocal();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.rorac.servicio.consultas.BOConsultaParametros#
	 * llamaConsultaProductoGestion
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanProductoGestion> llamaConsultaProductoGestion(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaProductoGestion()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		this.info("Se realiza la comunicacion con la capa de acceso a datos para ejecutar la consulta de Producto Gestion.");
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(ID_OPERACION_CONSULTA_PARAM));
		pistaAuditoria.setNombreArchivo("No Aplica");
		final BeanListaProductoGestionDAO respuesta = consultaParametros
				.ejecutaConsultaProductoGestion(sessionBean);
		if (respuesta == null) {
			this.info("Se obtuvo una respuesta nula al ejecutar la consulta de Producto Gestion");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(respuesta.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de Producto Gestion: "
					+ respuesta.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(respuesta.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Se envian los registros Producto Gestion al Front de la aplicacion.");
		this.info("Termina la ejecucion del metodo " + metodo);

		return respuesta.getListaProductoGestion();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.consultas.BOConsultaParametros#llamaConsultaADNRetail
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanADNRetail> llamaConsultaADNRetail(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaADNRetail()";
		this.info("Comienza la ejecucion del metodo: " + metodo);
		this.info("Se realiza la comunicacion con la capa de acceso a datos para ejecutar la consulta de ADN Retail.");
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(ID_OPERACION_CONSULTA_PARAM));
		pistaAuditoria.setNombreArchivo("No Aplica");
		final BeanListaADNRetailDAO respuesta = consultaParametros
				.ejecutaConsultaADNRetail(sessionBean);
		if (respuesta == null) {
			this.info("Se obtuvo una respuesta nula al ejecutar la consulta de ADN Retail.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(respuesta.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de ADN Retail y No Retail");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(respuesta.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Se envian los registros ADN Retail hacia el Front de la aplicacion.");
		this.info("Termina la ejecucion del metodo: " + metodo);
		return respuesta.getListaADNRetail();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.consultas.BOConsultaParametros#llamaConsultaFlagNeteo
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanFlagNeteo> llamaConsultaFlagNeteo(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaFlagNeteo()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("Se realiza la comunicacion con la capa de acceso a datos para ejecutar la consulta de FlagNeteo.");
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(ID_OPERACION_CONSULTA_PARAM));
		pistaAuditoria.setNombreArchivo("No Aplica");
		final BeanListaFlagNeteoDAO respuesta = consultaParametros
				.ejecutaConsultaFlagNeteo(sessionBean);
		if (respuesta == null) {
			this.info("Se obtuvo una respuesta nula al ejecutar la consulta de FlagNeteo.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(respuesta.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta FlagNeteo.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(respuesta.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Se envian los registros FlagNeteo hacia el Front de la aplicacion.");
		this.info("Finaliza la ejecucion del metodo: " + metodo);
		return respuesta.getListaFlagNeteo();
	}
}
