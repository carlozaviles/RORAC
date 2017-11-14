package mx.isban.rorac.dao.consultas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.ExceptionDataAccess;
import mx.isban.agave.dataaccess.DataAccess;
import mx.isban.agave.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import mx.isban.agave.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import mx.isban.agave.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import mx.isban.agave.logging.Level;
import mx.isban.rorac.bean.consultas.BeanConsultaMonitor;
import mx.isban.rorac.bean.consultas.BeanEstatusCarga;
import mx.isban.rorac.bean.consultas.BeanListaMonitorCargasDAO;
import mx.isban.rorac.bean.lanzadores.BeanResultBOListasMonitor;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class DAOMonitorCargasImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOMonitorCargasImpl extends Architech implements DAOMonitorCargas {

	/**
	 * Consulta el monitor para los insumos de un proceso en especifico
	 */
	private static final String CONSULTA_MONITOR_PROCESO = "SELECT INSUMO.TXT_DESCRIPCION,ESTATUS.TXT_ANIO || ESTATUS.TXT_MES AS FECHA, "
			+ "ESTATUS.FLG_EST_PROC, ESTATUS.DSC_EST_PROC "
			+ "FROM RRC_MX_PRC_ESTATUS ESTATUS RIGHT JOIN "
			+ "(SELECT ID_INSUMO, TXT_DESCRIPCION "
			+ "FROM RRC_MX_CAT_INSUMO WHERE ID_PROCESO = ?) INSUMO "
			+ "ON INSUMO.ID_INSUMO = ESTATUS.ID_INSUMO";
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1044127608579445731L;
	/**
	 * ID_CANAL
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Consulta de Cargas Manuales
	 */
	private static final String CONSULTA_CARGAS_MANUALES = "SELECT INSUMO.TXT_DESCRIPCION, ESTATUS.FLG_EST_PROC, ESTATUS.DSC_EST_PROC "
			+ "FROM (SELECT ID_INSUMO, TXT_DESCRIPCION "
			+ "      FROM RRC_MX_CAT_INSUMO "
			+ "      WHERE ID_INSUMO >= ? AND ID_INSUMO <= ?) INSUMO "
			+ "LEFT JOIN (SELECT ID_INSUMO, FLG_EST_PROC, DSC_EST_PROC "
			+ "           FROM RRC_MX_PRC_ESTATUS "
			+ "           WHERE ID_PROCESO = ? AND TXT_MES = ? AND TXT_ANIO = ?) ESTATUS "
			+ "ON INSUMO.ID_INSUMO = ESTATUS.ID_INSUMO";
	/**
	 * Consulta de Cargas Iniciales.
	 */
	private static final String CONSULTA_CARGAS_INICIALES = "SELECT INSUMO.TXT_DESCRIPCION, ESTATUS.FLG_EST_PROC, ESTATUS.DSC_EST_PROC, ESTATUS.FECHA "
			+ "FROM (SELECT ID_INSUMO, TXT_DESCRIPCION "
			+ "      FROM RRC_MX_CAT_INSUMO "
			+ "      WHERE ID_INSUMO >= ? AND ID_INSUMO <= ?) INSUMO "
			+ "LEFT JOIN (SELECT ID_INSUMO, FLG_EST_PROC, DSC_EST_PROC, TXT_ANIO || '/' || TXT_MES FECHA, TXT_ANIO, TXT_MES "
			+ "           FROM RRC_MX_PRC_ESTATUS "
			+ "           WHERE ID_PROCESO = ? AND FLG_EST_PROC <> '12') ESTATUS "
			+ "ON INSUMO.ID_INSUMO = ESTATUS.ID_INSUMO ORDER BY TXT_ANIO, TXT_MES DESC";
	/**
	 * Consulta de estatus de Interfaces Iniciales.
	 */
	private static final String CONSULTA_ESTATUS_INTERFACES_FINALES = "SELECT INSUMO.TXT_DESCRIPCION, ESTATUS.FLG_EST_PROC, ESTATUS.DSC_EST_PROC, ESTATUS.TXT_ACCION "
			+ "FROM (SELECT ID_INSUMO, TXT_DESCRIPCION "
			+ "      FROM RRC_MX_CAT_INSUMO "
			+ "      WHERE ID_INSUMO >= ? AND ID_INSUMO <= ?) INSUMO "
			+ "LEFT JOIN (SELECT ID_INSUMO, FLG_EST_PROC, DSC_EST_PROC, TXT_ACCION "
			+ "           FROM RRC_MX_PRC_ESTATUS "
			+ "           WHERE ID_PROCESO = ? AND TXT_MES = ? AND TXT_ANIO = ?) ESTATUS "
			+ "ON INSUMO.ID_INSUMO = ESTATUS.ID_INSUMO";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOMonitorCargas#consultaCargasManuales(
	 * mx.isban.rorac.bean.consultas.BeanConsultaMonitor,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaMonitorCargasDAO consultaCargasManuales(
			final BeanConsultaMonitor beanConsulta,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "consultaCargasEstatusInsumos()";
		this.info("Inicio de ejecucucion del metodo " + metodo);
		final BeanListaMonitorCargasDAO respuesta = new BeanListaMonitorCargasDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_CARGAS_MANUALES);
		this.info("Se insertan los parametros para la consulta de cargas manuales.");
		this.info("Indice inicial Insumo: "
				+ beanConsulta.getIndiceInicialInsumo());
		requestDTO.addParamToSql(beanConsulta.getIndiceInicialInsumo());
		this.info("Indice Final Insumo: " + beanConsulta.getIndiceFinalInsumo());
		requestDTO.addParamToSql(beanConsulta.getIndiceFinalInsumo());
		this.info("ID Proceso: " + beanConsulta.getIdProceso());
		requestDTO.addParamToSql(beanConsulta.getIdProceso());
		this.info("Mes: " + beanConsulta.getMes());
		requestDTO.addParamToSql(beanConsulta.getMes());
		this.info("Anio: " + beanConsulta.getAnio());
		requestDTO.addParamToSql(beanConsulta.getAnio());
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Ocurrio un error al ejecutar la consulta de Carga de Interfaces: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			} else {
				this.info("Resultado de la consulta de Cargas Manuales:");
				this.info(responseDTO.getResultQuery().toString());
				final List<BeanEstatusCarga> listaMonitor = new ArrayList<BeanEstatusCarga>();
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanEstatusCarga monitor = new BeanEstatusCarga();
					monitor.setNombreInterfaz((String) registro
							.get("TXT_DESCRIPCION"));
					monitor.setEstatus((String) registro.get("FLG_EST_PROC"));
					monitor.setDetalleError((String) registro
							.get("DSC_EST_PROC"));
					listaMonitor.add(monitor);
				}
				respuesta.setListaMonitor(listaMonitor);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta
					.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOMonitorCargas#consultaInsumosIniciales
	 * (mx.isban.rorac.bean.consultas.BeanConsultaMonitor,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaMonitorCargasDAO consultaInsumosIniciales(
			final BeanConsultaMonitor beanConsulta,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "consultaInsumosIniciales()";
		this.info("Se ejecuta el metodo " + metodo);
		final BeanListaMonitorCargasDAO respuesta = new BeanListaMonitorCargasDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_CARGAS_INICIALES);
		this.info("Se insertan los parametros para la consulta de insumos iniciales.");
		this.info("Indice Insumo Inicial: "
				+ beanConsulta.getIndiceInicialInsumo());
		requestDTO.addParamToSql(beanConsulta.getIndiceInicialInsumo());
		this.info("Indice Insumo Final: " + beanConsulta.getIndiceFinalInsumo());
		requestDTO.addParamToSql(beanConsulta.getIndiceFinalInsumo());
		this.info("ID Proceso: " + beanConsulta.getIdProceso());
		requestDTO.addParamToSql(beanConsulta.getIdProceso());
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar la consulta de Insumos Iniciales: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			} else {
				this.info("Resultado de la consulta de Cargas Iniciales:");
				this.info(responseDTO.getResultQuery().toString());
				final List<BeanEstatusCarga> listaMonitor = new ArrayList<BeanEstatusCarga>();
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanEstatusCarga monitor = new BeanEstatusCarga();
					monitor.setNombreInterfaz((String) registro
							.get("TXT_DESCRIPCION"));
					monitor.setEstatus((String) registro.get("FLG_EST_PROC"));
					monitor.setDetalleError((String) registro
							.get("DSC_EST_PROC"));
					monitor.setFechaAlta((String) registro.get("FECHA"));
					listaMonitor.add(monitor);
				}
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
				respuesta.setListaMonitor(listaMonitor);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta
					.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Termina la ejecucion del metodo " + metodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.rorac.dao.consultas.DAOMonitorCargas#
	 * consultaEstatusInterfacesFinales
	 * (mx.isban.rorac.bean.consultas.BeanConsultaMonitor,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaMonitorCargasDAO consultaEstatusInterfacesFinales(
			final BeanConsultaMonitor beanConsulta,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "consultaEstatusInterfacesFinales()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final BeanListaMonitorCargasDAO respuesta = new BeanListaMonitorCargasDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_ESTATUS_INTERFACES_FINALES);
		this.info("Se insertan los parametros para la consulta de estatus de interfaces finales.");
		this.info("Indice Inicial Insumo: "
				+ beanConsulta.getIndiceInicialInsumo());
		requestDTO.addParamToSql(beanConsulta.getIndiceInicialInsumo());
		this.info("Indice Final Insumo: " + beanConsulta.getIndiceFinalInsumo());
		requestDTO.addParamToSql(beanConsulta.getIndiceFinalInsumo());
		this.info("ID Proceso: " + beanConsulta.getIdProceso());
		requestDTO.addParamToSql(beanConsulta.getIdProceso());
		this.info("Mes: " + beanConsulta.getMes());
		requestDTO.addParamToSql(beanConsulta.getMes());
		this.info("Anio: " + beanConsulta.getAnio());
		requestDTO.addParamToSql(beanConsulta.getAnio());
		try {
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se presento un error al ejecutar la consulta del estatus de Interfaces Finales: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			} else {
				this.info("Resultado de la consulta de Estatus se Interfaces Finales:");
				this.info(responseDTO.getResultQuery().toString());
				final List<BeanEstatusCarga> listaMonitor = new ArrayList<BeanEstatusCarga>();
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanEstatusCarga monitor = new BeanEstatusCarga();
					monitor.setNombreInterfaz((String) registro
							.get("TXT_DESCRIPCION"));
					monitor.setEstatus((String) registro.get("FLG_EST_PROC"));
					monitor.setDetalleError((String) registro
							.get("DSC_EST_PROC"));
					monitor.setOperacion((String) registro.get("TXT_ACCION"));
					listaMonitor.add(monitor);
				}
				respuesta.setListaMonitor(listaMonitor);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta
					.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo: " + metodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOMonitorCargas#consultaEstatusInsumos(
	 * java.lang.String, mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBOListasMonitor consultaEstatusInsumos(
			final String idProceso, final ArchitechSessionBean sessionBean) {
		final String metodo = "consultaEstatusInsumos()";
		this.info("Inicio de ejecucucion del metodo " + metodo);
		final BeanResultBOListasMonitor respuesta = new BeanResultBOListasMonitor();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_MONITOR_PROCESO);
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
						.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			} else {
				this.info("Resultado de la consulta estatus insumos motor rorac:");
				this.info(responseDTO.getResultQuery().toString());
				final List<BeanEstatusCarga> listaMonitorMotor = new ArrayList<BeanEstatusCarga>();
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanEstatusCarga monitor = new BeanEstatusCarga();
					monitor.setNombreInterfaz((String) registro
							.get("TXT_DESCRIPCION"));
					monitor.setFechaAlta((String) registro.get("FECHA"));
					monitor.setEstatus((String) registro.get("FLG_EST_PROC"));
					monitor.setDetalleError((String) registro
							.get("DSC_EST_PROC"));
					listaMonitorMotor.add(monitor);
				}
				respuesta.setListaMonitorMotor(listaMonitorMotor);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta
					.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_MONITOR_CARGAS);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return respuesta;
	}
}
