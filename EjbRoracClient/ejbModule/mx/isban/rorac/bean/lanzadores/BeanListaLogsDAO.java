/**
 * 
 */
package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;
import java.util.List;

import mx.isban.agave.commons.interfaces.BeanResultBO;

/**
 * @author everis
 *
 */
public class BeanListaLogsDAO implements Serializable, BeanResultBO {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7642288210857198616L;
	/**
	 * Codigo de error generado al ejecutar la consulta de logs.
	 */
	private String codError;
	/**
	 * Mensaje de error,  que se informa en caso de que haya ocurrido un error al ejecutar la consuta de log.
	 */
	private String msgError;
	/**
	 * Lista de objetos BeanEstatusLog, generada al realizar la consulta de logs.
	 */
	private List<BeanEstatusLog> listaLogs;
	
	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getCodError()
	 */
	@Override
	public String getCodError() {
		// TODO Auto-generated method stub
		return codError;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getMsgError()
	 */
	@Override
	public String getMsgError() {
		return msgError;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#setCodError(java.lang.String)
	 */
	@Override
	public void setCodError(String codError) {
		this.codError = codError;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#setMsgError(java.lang.String)
	 */
	@Override
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	/**
	 * Obtiene el valor del campo listaLogs.
	 * @return List<BeanEstatusLog>
	 */
	public List<BeanEstatusLog> getListaLogs() {
		return listaLogs;
	}

	/**
	 * Establece el valor del campo listaLogs
	 * @param logsCruces Valor que sera colocado en el campo listaLogs
	 */
	public void setListaLogs(List<BeanEstatusLog> logsCruces) {
		this.listaLogs = logsCruces;
	}
}
