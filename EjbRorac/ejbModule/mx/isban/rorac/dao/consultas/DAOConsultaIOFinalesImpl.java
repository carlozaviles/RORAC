package mx.isban.rorac.dao.consultas;

import java.util.HashMap;
import java.util.Map.Entry;

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
import mx.isban.rorac.bean.consultas.BeanConsultaIOFinalesDAO;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class DAOConsultaIOFinalesImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaIOFinalesImpl extends Architech implements
		DAOConsultaIOFinales {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 7088890278908541381L;
	/**
	 * ID_CANAL
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOConsultaIOFinales#ejecutaConsultaPorContrato
	 * (java.lang.String, java.lang.String,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanConsultaIOFinalesDAO ejecutaConsultaPorContrato(
			final String contrato, final String consulta,
			final String fechaInicial, final String fechaFin,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaConsultaPorContrato()";
		this.info("Se ejecuta el metodo " + metodo);
		final BeanConsultaIOFinalesDAO respuesta = new BeanConsultaIOFinalesDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(consulta);
		requestDTO.addParamToSql(contrato);
		requestDTO.addParamToSql(fechaInicial);
		requestDTO.addParamToSql(fechaFin);
		this.info("Se inserta el parametro contrato: " + contrato);
		try {
			this.info("Se ejecuta la consulta por contrato de una de las interfaces finales.");
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar la consulta por contrato: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_IDA_CONSULTA_POR_CONTRATO);
			} else {
				this.info("El resultado de la consulta es: ");
				this.info(responseDTO.getResultQuery().toString());
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
				if (responseDTO.getResultQuery() != null) {
					for (HashMap<String, Object> registro : responseDTO
							.getResultQuery()) {
						for (Entry<String, Object> campo : registro.entrySet()) {
							if (campo.getValue() instanceof oracle.sql.TIMESTAMP) {
								campo.setValue(((oracle.sql.TIMESTAMP) campo
										.getValue()).stringValue());
							}
						}
					}
					respuesta.setResultadosConsulta(responseDTO
							.getResultQuery());
				}
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta
					.setCodError(ConstantesRorac.ERROR_IDA_CONSULTA_POR_CONTRATO);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo: " + metodo);
		return respuesta;
	}
}
