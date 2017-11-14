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
import mx.isban.agave.dataaccess.DataAccess;
import mx.isban.agave.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import mx.isban.agave.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import mx.isban.agave.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import mx.isban.agave.logging.Level;
import mx.isban.rorac.bean.consultas.BeanEstatusCarga;
import mx.isban.rorac.bean.lanzadores.BeanResultBOListasMonitor;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOMonitorMotorImpl extends Architech implements DAOMonitorMotor {
	/**
	 * Serial Version Default
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID_CANAL
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Consulta de estatus de Interfaces Iniciales.
	 */
	private static final String CONSULTA_ESTATUS_INSUMOS_MOTOR = "SELECT INSUMO.TXT_DESCRIPCION, ESTATUS.FLG_EST_PROC, ESTATUS.DSC_EST_PROC "
			+ "FROM RRC_MX_PRC_ESTATUS ESTATUS RIGHT JOIN "
			+ "(SELECT ID_INSUMO, TXT_DESCRIPCION "
			+ "FROM RRC_MX_CAT_INSUMO WHERE ID_PROCESO = ?) INSUMO "
			+ "ON INSUMO.ID_INSUMO = ESTATUS.ID_INSUMO";

	@Override
	public BeanResultBOListasMonitor consultaEstatusInsumosMotor(
			final String idProceso, final ArchitechSessionBean sessionBean) {
		final String metodo = "consultaEstatusInsumosMotor()";
		this.info("Inicio de ejecucucion del metodo " + metodo);
		final BeanResultBOListasMonitor respuesta = new BeanResultBOListasMonitor();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_ESTATUS_INSUMOS_MOTOR);
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
