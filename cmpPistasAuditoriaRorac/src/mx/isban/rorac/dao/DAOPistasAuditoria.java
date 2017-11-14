/**
 * 
 */
package mx.isban.rorac.dao;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.LoggingBean;
import mx.isban.agave.commons.beans.MessageBean;
import mx.isban.agave.commons.exception.ExceptionDataAccess;
import mx.isban.agave.commons.interfaces.EjecutorDAO;
import mx.isban.agave.dataaccess.DataAccess;
import mx.isban.agave.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import mx.isban.agave.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import mx.isban.agave.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import mx.isban.rorac.bean.BeanPistasAuditoria;

/**
 * @author everis
 *
 */
public class DAOPistasAuditoria extends Architech implements EjecutorDAO{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2550444227339690701L;
	/**
	 * Canal.
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Codigo para insertar Pista de Auditoria en base de datos.
	 */
	private static final String INSERT_PISTA_AUDITORIA = "INSERT INTO RRC_MX_PISTAS_AUDITORIA " +
														 "(ID_PISTAS_AUDITORIA, FECHA, HORA, DIRIPTERMINAL, IDUSUARIO, APLICACION, " +
														 "     CODOPERACION, IDOPERACION, ESTATUSOPR, IDSESION, NOMARCH, " +
														 "     IDWEB, HOSTNAMEWEB, NOMTRANSACCION) " +
														 "VALUES (?, ?, TO_DATE(?, 'yyyy-mm-dd HH24:MI:SS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'No Aplica')";

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.EjecutorDAO#ejecuta(mx.isban.agave.commons.beans.MessageBean)
	 */
	@Override
	public void ejecuta(MessageBean message) {
		this.setLoggingBean(message.getLogginBean());
		this.setNameComponent(this.getClass().getSimpleName());
		final long idOperacion = obtenerIdOperacion(message.getLogginBean());
		if(idOperacion == -1){
			this.warn("No fue posible obtener el id de operacion por lo que es posible guardar la pista de auditoria.");
			return;
		}
		final BeanPistasAuditoria pistaAuditoria = (BeanPistasAuditoria)message.getObjetoEjecutor();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(INSERT_PISTA_AUDITORIA);
		requestDTO.addParamToSql(String.valueOf(idOperacion));
		requestDTO.addParamToSql(pistaAuditoria.getFecha());
		requestDTO.addParamToSql(pistaAuditoria.getHora());
		requestDTO.addParamToSql(pistaAuditoria.getDireccionIp());
		requestDTO.addParamToSql(pistaAuditoria.getUsuario());
		requestDTO.addParamToSql(pistaAuditoria.getAplicacion());
		requestDTO.addParamToSql(pistaAuditoria.getCodigoOperacion());
		requestDTO.addParamToSql(String.valueOf(idOperacion));
		requestDTO.addParamToSql(pistaAuditoria.getEstatusOperacion());
		requestDTO.addParamToSql(pistaAuditoria.getIdSesion());
		requestDTO.addParamToSql(pistaAuditoria.getNombreArchivo());
		requestDTO.addParamToSql(pistaAuditoria.getIdInstanciaWeb());
		requestDTO.addParamToSql(pistaAuditoria.getNombreServidor());
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error("Se obtuvo un codigo de error al intentar insertar una pista de auditoria: " + responseDTO.getCodeError());
				
			}
		}catch(ExceptionDataAccess e){
			this.error("Se genero una excepcion al intentar guardar en BD una pista de auditoria: " + e);
		}
	}
	
	/**
	 * Obtiene el id de operacion que sera identificara la pista de auditoria a insertar.
	 * @param loggin LogginBean
	 * @return long
	 */
	private long obtenerIdOperacion(LoggingBean loggin){
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setQuery("SELECT FN_SIG_NUMMERO_SEQ('RRC_MX_PISTAS_AUDITORIA') ID_OPERACION FROM DUAL");
		long respuesta = -1;
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, loggin);
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.warn("Se obtuvo un codigo de error al realizar la consulta del codigo de operacion: " + responseDTO.getCodeError());
				return respuesta;
			}
			this.trace("Respuesta del SP: " + responseDTO.getResultQuery().toString());
			if(responseDTO.getResultQuery().size() >= 1){
				respuesta = Long.parseLong(responseDTO.getResultQuery().get(0).get("ID_OPERACION").toString());
			}
		}catch(ExceptionDataAccess e){
			this.error("Se genero una excepcion al realizar la consulta del codigo de operacion: " + e);
		}
		return respuesta;
	}

}