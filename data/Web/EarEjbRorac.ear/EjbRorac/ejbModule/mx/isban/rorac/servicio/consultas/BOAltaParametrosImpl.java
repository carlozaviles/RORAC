package mx.isban.rorac.servicio.consultas;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.dao.consultas.DAOAltaParametros;
import mx.isban.rorac.dao.consultas.DAOModificacionParametros;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class BOAltaParametrosImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOAltaParametrosImpl extends Architech implements BOAltaParametros {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1266036813057366039L;
	/**
	 * Parametro que contiene el id de operacion del alta de parametros.
	 */
	private static final String PARAM_ALTA_TABLAS_PARAMETROS_ID_OPERACION = "PARAM_CONFIG_ALTA_TABLAS_PARAMETROS_ID_OPERACION";

	/**
	 * Objeto de la capa de acceso a datos que realiza las altas de nuevos
	 * registros.
	 */
	@EJB
	private transient DAOAltaParametros altaParametros;
	/**
	 * Objeto de la capa de acceso a datos que realiza las modificaciones de
	 * registros.
	 */
	@EJB
	private transient DAOModificacionParametros modificacionParametros;
	/**
	 * Manejador de Pistas de Auditoria
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistasAuditoria;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.servicio.consultas.BOAltaParametros#llamaAltaADNLocal(
	 * mx.isban.rorac.bean.consultas.BeanADNLocal,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaAltaADNLocal(final BeanADNLocal nuevoRegistro,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaAltaADNLocal()";
		this.info("Inicio de ejecucion del metodo " + metodo);
		this.info("El idAdnLocal del registro que se dara de alta es: "
				+ nuevoRegistro.getIdAdnLocal());
		this.info("Se realiza la comunicacion con la capa de acceso a datos para realizar el alta de ADN Local.");

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_ALTA_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		BeanResultBO estatus = altaParametros.ejecutaAltaADNLocal(
				nuevoRegistro, sessionBean);
		if (estatus == null) {
			this.info("Se obtuvo una respuesta nula por parte de la capa de la capa de acceso a datos al intentar realizar el alta de ADN Local");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (ConstantesRorac.ERROR_ID_REPETIDO_TABLAS_PARAMETROS.equals(estatus
				.getCodError())) {
			this.info("Se ha intentado dar de alta un registro con un idAdnLocal existente.");
			estatus = modificacionParametros.ejecutaModificacionADNLocal(
					nuevoRegistro, sessionBean);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se obtuvo un codigo de error al intentar realizar el alta de ADN Local: "
					+ estatus.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(estatus.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Finaliza la ejecucion del metodo " + metodo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.servcio.consultas.BOAltaParametros#llamaAltaProductoGestion
	 * (mx.isban.rorac.bean.consultas.BeanProductoGestion,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaAltaProductoGestion(
			final BeanProductoGestion nuevoRegistro,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaAltaProductoGestion()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("El idProductoGestion del registro que se dara de alta es: "
				+ nuevoRegistro.getIdProductoGestion());
		this.info("Se realiza la comunicacion con la capa de acceso a datos para realizar el alta de Producto Gestion.");

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_ALTA_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		BeanResultBO estatus = altaParametros.ejecutaAltaProductoGestion(
				nuevoRegistro, sessionBean);
		if (estatus == null) {
			this.info("Se obtuvo respuesta nula al realizar el alta de Producto Gestion.");
			pistaAuditoria.setEstatusOperacion("Error");
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (ConstantesRorac.ERROR_ID_REPETIDO_TABLAS_PARAMETROS.equals(estatus
				.getCodError())) {
			this.info("Se ha intentado dar de alta un registro con idProductoGestion existente.");
			estatus = modificacionParametros
					.ejecutaModificacionProductoGestion(nuevoRegistro,
							sessionBean);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se obtuvo un codigo de error al intentar dar de alta el registro Producto Gestion: "
					+ estatus.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(estatus.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Finaliza la ejecucion del metodo: " + metodo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.servicio.consultas.BOAltaParametros#llamaAltaADNRetail
	 * (mx.isban.rorac.bean.consultas.BeanADNRetail,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaAltaADNRetail(final BeanADNRetail nuevoRegistro,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaAltaADNRetail()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		this.info("El idSegmentoLocal del registro que se va a dar de alta es: "
				+ nuevoRegistro.getIdSegmentoLocal());
		this.info("Se realiza la cominicacion con la capa de acceso a datos para dar de alta el registro ADN Retail");

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_ALTA_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		BeanResultBO estatus = altaParametros.ejecutaAltaADNRetail(
				nuevoRegistro, sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un codigo de estatus al realizar el alta del registro ADN Retail");
			pistaAuditoria.setEstatusOperacion("Error");
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (ConstantesRorac.ERROR_ID_REPETIDO_TABLAS_PARAMETROS.equals(estatus
				.getCodError())) {
			this.info("Se intento dar de alta un registro con idSegmentoLocal existente.");
			estatus = modificacionParametros.ejecutaModificacionADNRetail(
					nuevoRegistro, sessionBean);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se obtuvo un codigo de error al intentar dar de alta el registro ADN Retail: "
					+ estatus.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(estatus.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Termina la ejecucion del metodo: " + metodo);
	}
}
