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
import mx.isban.rorac.bean.consultas.BeanFlagNeteo;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.dao.consultas.DAOModificacionParametros;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class BOModificacionParametrosImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOModificacionParametrosImpl extends Architech implements
		BOModificacionParametros {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 243730728430401968L;
	/**
	 * Contiene el id de operacion de Cambios de Parametros.
	 */
	private static final String PARAM_CAMBIOS_TABLAS_PARAMETROS_ID_OPERACION = "PARAM_CONFIG_CAMBIOS_TABLAS_PARAMETROS_ID_OPERACION";
	/**
	 * Contiene el id de operacion de Baja de Parametros.
	 */
	private static final String PARAM_BAJA_TABLAS_PARAMETROS_ID_OPERACION = "PARAM_CONFIG_BAJA_TABLAS_PARAMETROS_ID_OPERACION";
	/**
	 * Instancia del objeto de capa de negocio que realiza las modificaciones de
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
	 * @see mx.isban.rorac.servicio.consultas.BOModificacionParametros#
	 * llamaModificacionADNLocal(mx.isban.rorac.bean.consultas.BeanADNLocal,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaModificacionADNLocal(final BeanADNLocal registroAdnlocal,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaModificacionADNLocal()";
		this.info("Inicio de ejecucion del metodo " + metodo);
		this.info("El idAdnLocal del registro que sera modificado es: "
				+ registroAdnlocal.getIdAdnLocal());
		this.info("Se realiza la comunicacion con la capa de acceso a datos para reliazar la acutalizacion del registro AdnLocal");

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_CAMBIOS_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		final BeanResultBO estatus = modificacionParametros
				.ejecutaModificacionADNLocal(registroAdnlocal, sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un estatus al realizar la modificacion del registro ADN Local");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se recibio un codigo de error al intentar actualizar el registro ADN Local: "
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
	 * @see mx.isban.rorac.servicio.consultas.BOModificacionParametros#
	 * llamaModificacionProductoGestion
	 * (mx.isban.rorac.bean.consultas.BeanProductoGestion,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaModificacionProductoGestion(
			final BeanProductoGestion registroProductoGestion,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaModificacionProductoGestion()";
		this.info("Inicia ejecucion del metodo " + metodo);
		this.info("El registro Producto Gestion que sera modificado tiene un idProductoGestion: "
				+ registroProductoGestion.getIdProductoGestion());

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_CAMBIOS_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		this.info("Se realiza la comunicacion con la capa de acceso a datos para realizar la actualizacion del registro Producto Gestion.");
		final BeanResultBO estatus = modificacionParametros
				.ejecutaModificacionProductoGestion(registroProductoGestion,
						sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un codigo de estatus al realizar la actualizacion del registro Producto Gestion");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se recibio un codigo de error al intentar actualizar el registro Producto Gestion: "
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
	 * @see mx.isban.rorac.servicio.consultas.BOModificacionParametros#
	 * llamaModificacionADNRetail(mx.isban.rorac.bean.consultas.BeanADNRetail,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaModificacionADNRetail(
			final BeanADNRetail registroAdnRetail,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaModificacionADNRetail()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		this.info("El registro ADN Retail que sera modificado tiene un idSegmentoLocal: "
				+ registroAdnRetail.getIdSegmentoLocal());
		this.info("Se realiza la comunicacion con la capa de acceso a datos para realizar la actualizacion del registro AdnRetail.");

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_CAMBIOS_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		final BeanResultBO estatus = modificacionParametros
				.ejecutaModificacionADNRetail(registroAdnRetail, sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un codigo de estatus al realizar la actualizacion del registro ADN Retail.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se recibio un codigo de error al intentar actualizar el registro ADN Retail: "
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
		this.info("Termina la ejecucion del metodo  " + metodo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.servicio.consultas.BOModificacionParametros#
	 * llamaModificacionFlagNeteo(mx.isban.rorac.bean.consultas.BeanFlagNeteo,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaModificacionFlagNeteo(final BeanFlagNeteo flagNeteo,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaModificacionFlagNeteo()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("El valor con el que se modificara el registro FlagNeteo es "
				+ flagNeteo.getValor());
		this.info("Se realiza la comunicacion con la capa de acceso a datos para realizar la actualizacion del registro FlagNeteo");

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_CAMBIOS_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		final BeanResultBO estatus = modificacionParametros
				.ejecutaModificacionFlagNeteo(flagNeteo, sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un codigo de estatus al realizar la actualizacion del registro FlagNeteo");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se recibio un codigo de error al intentar actualizar el registro FlagNeteo: "
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.servicio.consultas.BOModificacionParametros#
	 * llamaEliminacionRegistro(java.lang.String[], java.lang.String,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void llamaEliminacionRegistro(final String[] idRegistrosPorEliminar,
			final String tipoFiltro, final ArchitechSessionBean sessionBean)
			throws BusinessException {
		final String metodo = "llamaEliminacionRegistro()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		String nombreId = null;
		if ("adnLocal".equals(tipoFiltro)) {
			query.append("RRC_AUX_FIL_ADNLOCAL");
			nombreId = "IDAREADENEGOCIOLOCAL";
		} else if ("productoGestion".equals(tipoFiltro)) {
			query.append("RRC_AUX_FIL_PROGEST");
			nombreId = "IDPRODUCTOGEST";
		} else if ("adnRetail".equals(tipoFiltro)) {
			query.append("RRC_AUX_FIL_ADNSRET");
			nombreId = "IDSEGMENTOLOCAL";
		} else {
			this.info("El tipo de filtro enviado no es valido: " + tipoFiltro);
			throw new BusinessException(
					ConstantesRorac.ERROR_VALORES_ENTRADA_TABLAS_PARAMETROS);
		}
		query.append(" WHERE ");
		for (int i = 0; i < idRegistrosPorEliminar.length; i++) {
			query.append(nombreId).append(" = '")
					.append(idRegistrosPorEliminar[i]).append("'");
			if (i < (idRegistrosPorEliminar.length - 1)) {
				query.append(" OR ");
			}
		}
		this.trace("Se enviara la siguiente consulta a la capa de acceso a datos para hacer la baja logica de los registros: "
				+ query.toString());

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_BAJA_TABLAS_PARAMETROS_ID_OPERACION));
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);

		final BeanResultBO estatus = modificacionParametros
				.ejecutaEliminacionRegistros(query.toString(), sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un codigo de estatus al ejecutar la baja logica de los registros.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_TABLAS_PARAMETROS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se recibio un codigo de error al intentar ejecutar la baja logica de los registros: "
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
		this.info("Termina la ejecucion del metodo " + metodo);
	}

}
