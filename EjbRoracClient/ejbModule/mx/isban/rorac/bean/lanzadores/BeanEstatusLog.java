/**
 * 
 */
package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;

/**
 * @author everis
 *
 */
public class BeanEstatusLog implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2860977211597149607L;
	/**
	 * Id del registro en la tabla de estatus.
	 */
	private String idRegistroEstatus;
	/**
	 * Nombre del archivo de log de este registro.
	 */
	private String nombreLog;
	/**
	 * Contiene el id del log en la tabla de insumos.
	 */
	private String idLogInsumos;
	/**
	 * Indica si este log fue generado.
	 */
	private boolean generado;
	
	/**
	 * Obtiene el valor del campo nombreLog
	 * @return String.
	 */
	public String getNombreLog() {
		return nombreLog;
	}
	
	/**
	 * Establece el valor del campo nombreLog
	 * @param nombreLog Valor que sera colocado en el campo nombreLog.
	 */
	public void setNombreLog(String nombreLog) {
		this.nombreLog = nombreLog;
	}
	
	/**
	 * Obtiene el valor del campo generado.
	 * @return boolean
	 */
	public boolean isGenerado() {
		return generado;
	}
	
	/**
	 * Establece el valor del campo generado.
	 * @param generado Valor que sera colocado en el campo generado.
	 */
	public void setGenerado(boolean generado) {
		this.generado = generado;
	}
	
	/**
	 * Obtiene el valor del campo idRegistroEstatus.
	 * @return String
	 */
	public String getIdRegistroEstatus() {
		return idRegistroEstatus;
	}
	
	/**
	 * Establece el valor del campo idLog
	 * @param idLog Valor que sera colocado en el campo idLog.
	 */
	public void setIdRegistroEstatus(String idLog) {
		this.idRegistroEstatus = idLog;
	}
	
	/**
	 * Obtiene el valor del campo idLogInsumos
	 * @return String
	 */
	public String getIdLogInsumos() {
		return idLogInsumos;
	}
	
	/**
	 * Establece el valor del campo idLogEstatus
	 * @param idLogEstatus Valor que sera colocado en el campo idLogEstatus
	 */
	public void setIdLogInsumos(String idLogEstatus) {
		this.idLogInsumos = idLogEstatus;
	}
}
