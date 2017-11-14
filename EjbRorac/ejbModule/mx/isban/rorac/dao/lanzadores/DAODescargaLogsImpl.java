package mx.isban.rorac.dao.lanzadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import mx.isban.rorac.bean.lanzadores.BeanEstatusLog;
import mx.isban.rorac.bean.lanzadores.BeanListaLogsDAO;
import mx.isban.rorac.bean.lanzadores.BeanParametrosLogs;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class DAODescargaLogsImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAODescargaLogsImpl extends Architech implements DAODescargaLogs {

	/**
	 * Estatus del log generado
	 */
	private static final String LOG_GENERADO = "21";
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -4475351357909377101L;
	/**
	 * Canal de Isban Data Access.
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Consulta el estatus de los logs de validacion para restateo
	 */
	private static final String CONSULTA_ESTATUS_LOGS_RESTATEO = "SELECT INSUMO.ID_INSUMO,INSUMO.TXT_DESCRIPCION, "
			+ "ESTATUS.TXT_ANIO || ESTATUS.TXT_MES AS FECHA, ESTATUS.FLG_EST_PROC, ESTATUS.ID_ESTATUS "
			+ "FROM RRC_MX_PRC_ESTATUS ESTATUS RIGHT JOIN (SELECT ID_INSUMO, TXT_DESCRIPCION "
			+ "FROM RRC_MX_CAT_INSUMO WHERE ID_PROCESO = ?) INSUMO "
			+ "ON INSUMO.ID_INSUMO = ESTATUS.ID_INSUMO";
	/**
	 * Consulta de estatus de logs.
	 */
	private static final String CONSULTA_ESTATUS_LOGS = "SELECT INSUMO.ID_INSUMO, INSUMO.TXT_DESCRIPCION nombre, PROCESO.FLG_EST_PROC estatus, "
			+ "      PROCESO.ID_ESTATUS id_Registro"
			+ "	FROM(SELECT  ID_INSUMO, TXT_DESCRIPCION"
			+ "		 FROM RRC_MX_CAT_INSUMO"
			+ "		 WHERE ID_INSUMO >= ? AND ID_INSUMO <= ?) INSUMO "
			+ "LEFT JOIN (SELECT ID_INSUMO, FLG_EST_PROC, ID_ESTATUS"
			+ "           FROM RRC_MX_PRC_ESTATUS"
			+ "			WHERE ID_PROCESO = ? AND TXT_ANIO = ? AND TXT_MES = ?) PROCESO "
			+ "ON  INSUMO.ID_INSUMO = PROCESO.ID_INSUMO "
			+ "ORDER BY INSUMO.ID_INSUMO ";
	/**
	 * Codigo SQL para realizar el registro de descarga de un log.
	 */
	private static final String REGISTRO_LOG = "UPDATE RRC_MX_PRC_ESTATUS "
			+ "SET FLG_EST_PROC = ? " + "WHERE ID_ESTATUS = ?";
	/**
	 * Nombre del campo de descripcion
	 */
	private static final String DESCRIPCION = "NOMBRE";
	/**
	 * Nombre del campo de estatus.
	 */
	private static final String ESTATUS = "ESTATUS";
	/**
	 * Nombre del campo de ID Registro.
	 */
	private static final String ID_REGISTRO = "ID_REGISTRO";
	/**
	 * Nombre del campo que contiene el id en la tabla de insumos.
	 */
	private static final String ID_INSUMO = "ID_INSUMO";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.dao.lanzadores.DAODescargaLogs#consultaEstatusLogs(mx.
	 * isban.rorac.bean.lanzadores.BeanParametrosLogs,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaLogsDAO consultaEstatusLogs(
			final BeanParametrosLogs beanParametros,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "consultaEstatusLogs";
		final BeanListaLogsDAO resultado = new BeanListaLogsDAO();
		this.info("Inicio de ejecucion del metodo " + metodo);
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_ESTATUS_LOGS);
		requestDTO.addParamToSql(beanParametros.getIdLogInicial());
		this.info("Se inserta el parametro de indice inicial "
				+ beanParametros.getIdLogInicial());
		requestDTO.addParamToSql(beanParametros.getIdLogFinal());
		this.info("Se inserta el parametro de indice final "
				+ beanParametros.getIdLogFinal());
		requestDTO.addParamToSql(beanParametros.getIdProceso());
		this.info("Se inserta el parametro id de proceso "
				+ beanParametros.getIdProceso());
		requestDTO.addParamToSql(beanParametros.getAnio());
		this.info("Se inserta el parametro de anio " + beanParametros.getAnio());
		requestDTO.addParamToSql(beanParametros.getMes());
		this.info("Se inserta el parametro de mes " + beanParametros.getMes());
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar la consulta: "
						+ responseDTO.getCodeError());
				resultado.setCodError(ConstantesRorac.ERROR_CONSULTA_LOGS);
			} else {
				final List<BeanEstatusLog> listaLogs = new ArrayList<BeanEstatusLog>();
				this.info(responseDTO.getResultQuery().toString());
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					BeanEstatusLog log = new BeanEstatusLog();
					log.setNombreLog((String) registro.get(DESCRIPCION));
					log.setGenerado(beanParametros.getCodigoLogGenerado()
							.equals((String) registro.get(ESTATUS)));
					log.setIdRegistroEstatus(registro.get(ID_REGISTRO)
							.toString());
					log.setIdLogInsumos((String) registro.get(ID_INSUMO));
					listaLogs.add(log);
				}
				resultado.setListaLogs(listaLogs);
				resultado.setCodError(ConstantesRorac.OPERACION_EXITOSA);
				this.info("Los resultados de la consulta se envian a la capa de negocio.");
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			resultado.setCodError(ConstantesRorac.ERROR_CONSULTA_LOGS);
			resultado.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.dao.lanzadores.DAODescargaLogs#registraLogParaDescarga
	 * (java.lang.String,
	 * java.lang.String,mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO registraLogParaDescarga(final String idRegistro,
			final String codigoDescarga, final ArchitechSessionBean bean) {
		final String metodo = "registraLogParaDescarga()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final BeanResultBO estatusResultado = new BeanListaLogsDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE_PARAMS);
		requestDTO.setQuery(REGISTRO_LOG);
		requestDTO.addParamToSql(codigoDescarga);
		requestDTO.addParamToSql(idRegistro);

		this.info("Se actualiza el codigo de estatus para el log con id "
				+ idRegistro);
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Ocurrio un error al ejecutar la actualizacion mediante el IDA. Codigo de error: "
						+ responseDTO.getCodeError());
				estatusResultado
						.setCodError(ConstantesRorac.ERROR_REGISTRO_DESCARGA_LOGS);
			} else {
				this.info("Se han actualizado el siguiente numero de registros "
						+ responseDTO.getRecordsAffected());
				this.info("Se ha registrado la operacion de descarga para el log con codigo "
						+ idRegistro);
				estatusResultado.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatusResultado
					.setCodError(ConstantesRorac.ERROR_REGISTRO_DESCARGA_LOGS);
			estatusResultado.setMsgError(e.getMessage());
		}
		this.info("Termina la ejecucion del metodo " + metodo);
		return estatusResultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.dao.lanzadores.DAODescargaLogs#consultaEstatusLogsRestateo
	 * (java.lang.String, mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaLogsDAO consultaEstatusLogsRestateo(final String idProceso,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "consultaEstatusLogsRestateo()";
		this.info("Inicio de ejecucucion del metodo " + metodo);
		final BeanListaLogsDAO respuesta = new BeanListaLogsDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_ESTATUS_LOGS_RESTATEO);
		requestDTO.addParamToSql(idProceso);
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Ocurrio un error al ejecutar la consulta estatus insumos motor rorac: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_REGISTRO_DESCARGA_LOGS);
			} else {
				this.info("Resultado de la consulta estatus insumos motor rorac:");
				this.info(responseDTO.getResultQuery().toString());
				final List<BeanEstatusLog> listaMonitorMotor = new ArrayList<BeanEstatusLog>();
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanEstatusLog log = new BeanEstatusLog();
					log.setNombreLog(registro.get("TXT_DESCRIPCION") + "_"
							+ registro.get("FECHA"));
					log.setIdLogInsumos((String) registro.get("ID_INSUMO"));
					log.setIdRegistroEstatus((String) registro
							.get("ID_ESTATUS"));
					log.setGenerado(LOG_GENERADO
							.equalsIgnoreCase((String) registro
									.get("FLG_EST_PROC")));
					listaMonitorMotor.add(log);
				}
				respuesta.setListaLogs(listaMonitorMotor);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta.setCodError(ConstantesRorac.ERROR_REGISTRO_DESCARGA_LOGS);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return respuesta;
	}
}
