package mx.isban.rorac.dao.lanzadores;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.ExceptionDataAccess;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.agave.dataaccess.DataAccess;
import mx.isban.agave.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import mx.isban.agave.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import mx.isban.agave.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import mx.isban.agave.logging.Level;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoMotorRorac;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoOperacion;
import mx.isban.rorac.bean.lanzadores.BeanListaLogsDAO;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class DAOCargaIOFinalesImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOLanzadorOperacionesImpl extends Architech implements
		DAOLanzadorOperaciones {
	/**
	 * Codigo para realizar insert en base de datos.
	 */
	private static final String CODIGO_INSERT = "INSERT "
			+ "INTO RRC_MX_PRC_ESTATUS (ID_ESTATUS, ID_INSUMO, FLG_EST_PROC, TXT_MES, TXT_ANIO, TXT_ACCION, ID_PROCESO) "
			+ "VALUES (FN_SIG_NUMMERO_SEQ('RRC_MX_PRC_ESTATUS'), ?, ?, ?, ?, ?, ?)";

	/**
	 * Consulta para validar si hay una ejecucion previa del motor rorac
	 */
	private static final String CONSULTA_EJECUCION_MOTOR = "SELECT COUNT(*) LMR "
			+ "FROM RRC_MX_PRC_ESTATUS WHERE ID_PROCESO = ? "
			+ "AND (FLG_EST_PROC = ? OR FLG_EST_PROC = ? OR FLG_EST_PROC = ?)";
	/**
	 * Descripcion de la operacion Procesar
	 */
	private static final String DESCRIPCION_PROCESAR = "Procesar";
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -6535307398672755162L;
	/**
	 * Canal de Isban Data Access.
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Codigo para realizar insert en base de datos.
	 */
	private static final String CODIGO_UPDATE = "UPDATE RRC_MX_PRC_ESTATUS SET FLG_EST_PROC = ?, DSC_EST_PROC = ? "
			+ "WHERE ID_PROCESO = ? AND ID_INSUMO = ? AND TXT_ANIO = ? AND TXT_MES = ? AND TXT_ACCION = ?";
	/**
	 * Query para registrar la validacion de los insumos del motor RORAC.
	 */
	private static final String REGISTRA_OPERACION_MOTOR = "UPDATE RRC_MX_PRC_ESTATUS SET FLG_EST_PROC = ? WHERE ID_PROCESO = ?";
	/**
	 * Query para registrar la validacion de los insumos del motor RORAC.
	 */
	private static final String REGISTRA_VALIDACION_MOTOR = "UPDATE RRC_MX_PRC_ESTATUS SET FLG_EST_PROC = ?,"
			+ " TXT_MES = ?, TXT_ANIO = ?, TXT_ACCION = ? WHERE ID_PROCESO = ?";
	/**
	 * Consulta para obtener el numero de peticiones abiertas
	 */
	private static final String CONSULTA_PETICIONES_ABIERTAS = "SELECT COUNT(*) SOLICITUDES FROM RRC_MX_PRC_ESTATUS "
			+ "WHERE (FLG_EST_PROC = ? OR FLG_EST_PROC = ?) AND TXT_MES = ? AND TXT_ANIO = ? AND ID_PROCESO = ?";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.lanzadores.DAOCargaIOFinales#insertaRegistroCarga(
	 * mx.isban.rorac.bean.lanzadores.BeanCargaIO,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO registraOperacion(
			final BeanLanzamientoOperacion beanCarga,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "registraOperacion()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final BeanResultBO beanResult = new BeanListaLogsDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(CODIGO_UPDATE);
		this.info("Se actualizan los parametros para realizar la solicitud de edicion de inputs finales.");

		requestDTO.addParamToSql(beanCarga.getCodigoEstatus());
		this.info("Codigo de estatus: " + beanCarga.getCodigoEstatus());

		requestDTO.addParamToSql(DESCRIPCION_PROCESAR);
		this.info("Descripcion de la operacion: " + DESCRIPCION_PROCESAR);

		requestDTO.addParamToSql(beanCarga.getIdProceso());
		this.info("Id del proceso de Carga de Inputs y Outputs Finales");

		requestDTO.addParamToSql(beanCarga.getIndiceArchivo());
		this.info("Id del insumo: " + beanCarga.getIndiceArchivo());

		requestDTO.addParamToSql(beanCarga.getAnio());
		this.info("Anio de la operacion: " + beanCarga.getAnio());
		requestDTO.addParamToSql(beanCarga.getMes());
		this.info("Mes de la operacion: " + beanCarga.getMes());

		if (beanCarga.getIdOperacion() == null
				|| ("").equals(beanCarga.getIdOperacion().trim())) {
			beanCarga.setIdOperacion("0");
		}

		requestDTO.addParamToSql(beanCarga.getIdOperacion());
		this.info("Operacion a realizar: " + beanCarga.getIdOperacion());

		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar el insert: "
						+ responseDTO.getCodeError());
				beanResult
						.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			} else {
				this.info("Se actualizo el registro con la peticion de la operacion. Registros Actualizados en Base de Datos: "
						+ responseDTO.getRecordsAffected());
				beanResult.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			beanResult
					.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			beanResult.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);

		return beanResult;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.lanzadores.DAOLanzadorOperaciones#verificaPeticionActual
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BeanResultBO obtenerNumeroPeticionesAbiertas(
			final String idOperacion, final String ano, final String mes) {
		final String metodo = "obtenerNumeroPeticionesAbiertas()";
		this.info("Se ejecuta el metodo " + metodo);
		final BeanResultBO estatus = new BeanListaLogsDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_PETICIONES_ABIERTAS);
		requestDTO.addParamToSql("00");
		requestDTO.addParamToSql("11");
		requestDTO.addParamToSql(mes);
		requestDTO.addParamToSql(ano);
		requestDTO.addParamToSql(idOperacion);
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Ocurrio un error al ejecutar la consulta de validacion de el motor rorac: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			} else {
				this.info(responseDTO.getResultQuery().toString());
				final int solicitudesRorac = Integer.parseInt(responseDTO
						.getResultQuery().get(0).get("SOLICITUDES").toString());
				if (solicitudesRorac == 4) {
					this.info("No hay solicitud de edicion inputs finales Pendiente en este momento");
					estatus.setCodError(ConstantesRorac.OPERACION_EXITOSA);
				} else {
					this.info("Hay una solicitud de edicion inputs finales pendiente en este momento.");
					estatus.setCodError(ConstantesRorac.EJECUCION_EN_PROCESO);
				}
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatus.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			estatus.setMsgError(e.getMessage());
		}
		this.info("Termina la ejecucion del metodo " + metodo);
		return estatus;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.lanzadores.DAOLanzadorOperaciones#validaEjecucionMotor
	 * ()
	 */
	@Override
	public BeanResultBO validaEjecucion(final String idProceso) {
		final String metodo = "validaEjecucionMotor()";
		this.info("Se ejecuta el metodo " + metodo);
		final BeanResultBO estatus = new BeanListaLogsDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_EJECUCION_MOTOR);
		requestDTO.addParamToSql(idProceso);
		requestDTO.addParamToSql("21");
		requestDTO.addParamToSql("22");
		requestDTO.addParamToSql("23");
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Ocurrio un error al ejecutar la consulta de validacion de el motor rorac: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			} else {
				this.info(responseDTO.getResultQuery().toString());
				final int numSolicitudes = Integer.parseInt(responseDTO
						.getResultQuery().get(0).get("LMR").toString());
				if (numSolicitudes == 0) {
					this.info("No hay solicitud de lanzador motor RORAC pendiente en este momento");
					estatus.setCodError(ConstantesRorac.OPERACION_EXITOSA);
				} else {
					this.info("Hay una solicitud de lanzador motor RORAC pendiente en este momento.");
					estatus.setCodError(ConstantesRorac.EJECUCION_EN_PROCESO);
				}
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatus.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			estatus.setMsgError(e.getMessage());
		}
		this.info("Termina la ejecucion del metodo " + metodo);
		return estatus;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.rorac.dao.lanzadores.DAOLanzadorOperaciones#
	 * registraValidacionInsumosMotor
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO updateRegistroOperacionMotor(final String idOperacion,
			final String idEstatus, final BeanLanzamientoMotorRorac beanForm,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "updateRegistroOperacionMotor()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final BeanResultBO beanResult = new BeanListaLogsDAO();
		RequestMessageDataBaseDTO requestDTO = null;
		if (beanForm == null) {
			requestDTO = actualizaOperacionMotor(idOperacion, idEstatus);
		} else {
			requestDTO = actualizaOperacionValidacionMotor(idOperacion,
					idEstatus, beanForm);
		}
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar el insert: "
						+ responseDTO.getCodeError());
				beanResult
						.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			} else {
				this.info("Se actualizo el registro con la peticion de la operacion. Registros Actualizados en Base de Datos: "
						+ responseDTO.getRecordsAffected());
				beanResult.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			beanResult
					.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			beanResult.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return beanResult;
	}

	/**
	 * Metodo encargado de ejecutar el queery
	 *
	 * @param idOperacion
	 *            operacion
	 * @param idEstatus
	 *            estatus
	 * @param requestDTO
	 *            bean resultado
	 * @return requestMessageDataBaseDTO la informacion de la BD
	 */
	private RequestMessageDataBaseDTO actualizaOperacionMotor(
			final String idOperacion, final String idEstatus) {
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(REGISTRA_OPERACION_MOTOR);
		requestDTO.addParamToSql(idEstatus);
		requestDTO.addParamToSql(idOperacion);
		return requestDTO;
	}

	/**
	 * Metodo encargado de ejecutar el queery
	 *
	 * @param idOperacion
	 *            operacion
	 * @param idEstatus
	 *            estatus
	 * @param requestDTO
	 *            bean resultado
	 * @return requestMessageDataBaseDTO la informacion de la BD
	 */
	private RequestMessageDataBaseDTO actualizaOperacionValidacionMotor(
			final String idOperacion, final String idEstatus,
			final BeanLanzamientoMotorRorac beanLanza) {
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(REGISTRA_VALIDACION_MOTOR);
		requestDTO.addParamToSql(idEstatus);
		requestDTO.addParamToSql(beanLanza.getMes());
		requestDTO.addParamToSql(beanLanza.getAnio());
		requestDTO.addParamToSql(beanLanza.getFinalidad());
		requestDTO.addParamToSql(idOperacion);
		return requestDTO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.lanzadores.DAOLanzadorOperaciones#registraOperacionAprov
	 * (mx.isban.rorac.bean.lanzadores.BeanLanzamientoOperacion,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO registraOperacionAprov(
			final BeanLanzamientoOperacion beanCarga,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "insertaRegistroCarga()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final BeanResultBO beanResult = new BeanListaLogsDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(CODIGO_INSERT);
		this.info("Se insertan los parametros para realizar en insert.");

		requestDTO.addParamToSql(beanCarga.getIndiceArchivo());
		this.info("Id del insumo: " + beanCarga.getIndiceArchivo());

		requestDTO.addParamToSql(beanCarga.getCodigoEstatus());
		this.info("Codigo de estatus: " + beanCarga.getCodigoEstatus());

		requestDTO.addParamToSql(beanCarga.getMes());
		this.info("Mes de la operacion: " + beanCarga.getMes());

		requestDTO.addParamToSql(beanCarga.getAnio());
		this.info("Anio de la operacion: " + beanCarga.getAnio());

		if (beanCarga.getIdOperacion() == null
				|| ("").equals(beanCarga.getIdOperacion().trim())) {
			beanCarga.setIdOperacion("0");
		}

		requestDTO.addParamToSql(beanCarga.getIdOperacion());
		this.info("Operacion a realizar: " + beanCarga.getIdOperacion());

		requestDTO.addParamToSql(beanCarga.getIdProceso());
		this.info("Id del proceso :" + beanCarga.getIdProceso());

		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar el insert: "
						+ responseDTO.getCodeError());
				beanResult
						.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			} else {
				this.info("Se inserto el registro con la peticion de la operacion. Registros Actualizados en Base de Datos: "
						+ responseDTO.getRecordsAffected());
				beanResult.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			beanResult
					.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_IO_FINALES);
			beanResult.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);

		return beanResult;
	}
}
