package mx.isban.rorac.dao.util;

import java.util.HashMap;
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

/**
 * Session Bean implementation class DAOPistasAuditoriaImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOUtilPistasAuditoriaImpl extends Architech implements DAOUtilPistasAuditoria {
       
    /**
	 * Serial.
	 */
	private static final long serialVersionUID = -188646588909071176L;
	/**
	 * Canal
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Cosulta de nombre de archivo.
	 */
	private static final String CONSULTA_NOMBRE_ARCHIVO = "SELECT TXT_DESCRIPCION " +
														  "FROM RRC_MX_CAT_INSUMO " +
														  "WHERE ID_INSUMO = ?";
	/**
	 * Consulta para obtener info de operacion.
	 */
	private static final String CONSULTA_INFO_OPERACION = "SELECT ESTATUS.ID_PROCESO, INSUMO.TXT_DESCRIPCION " +
														  "FROM (SELECT ID_ESTATUS, ID_INSUMO, ID_PROCESO " +
														  "      FROM RRC_MX_PRC_ESTATUS " +
														  "      WHERE ID_ESTATUS = ?) ESTATUS " + 
														  "INNER JOIN RRC_MX_CAT_INSUMO INSUMO " +
														  "ON ESTATUS.ID_INSUMO = INSUMO.ID_INSUMO";

	/* (non-Javadoc)
	 * @see mx.isban.rorac.dao.util.DAOPistasAuditoria#obtenerNombreInsumo(java.lang.String)
	 */
	@Override
	public String obtenerNombreInsumo(String idInsumo, ArchitechSessionBean sessionBean) {
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_NOMBRE_ARCHIVO);
		requestDTO.addParamToSql(idInsumo);
		String resultado = null;
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.warn("Se obtuvo un codigo de error al realizar la consulta de nombre de archivo: " + responseDTO.getCodeError());
			}
			if(responseDTO.getResultQuery().size() >= 1){
				resultado = responseDTO.getResultQuery().get(0).get("TXT_DESCRIPCION").toString();
			}
		}catch(ExceptionDataAccess e){
			this.warn("Se genero una excepcion al realizar la consulta de nombre de archivo: " + e);
		}
		return resultado;
	}

	/* (non-Javadoc)
	 * @see mx.isban.rorac.dao.util.DAOPistasAuditoria#obtenerInfoOperacion(java.lang.String)
	 */
	@Override
	public Map<String, String> obtenerInfoOperacion(String idEstatus, ArchitechSessionBean sessionBean) {
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(CONSULTA_INFO_OPERACION);
		requestDTO.addParamToSql(idEstatus);
		Map<String, String> respuesta = new HashMap<String, String>();
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.warn("Se genero un codigo de error al realizar la consulta de informacion de operacion: " 
						+ responseDTO.getCodeError());
			}
			if(responseDTO.getResultQuery().size() >= 1){
				respuesta.put(DAOUtilPistasAuditoria.ID_OPERACION, responseDTO.getResultQuery().get(0).get("ID_PROCESO").toString());
				respuesta.put(DAOUtilPistasAuditoria.NOMBRE_ARCHIVO, responseDTO.getResultQuery().get(0).get("TXT_DESCRIPCION").toString());
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
		}
		return respuesta;
	}

}
