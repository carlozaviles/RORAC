/**
 * 
 */
package mx.isban.rorac.bean.consultas;

import java.io.Serializable;

/**
 * @author everis
 *
 */
public class BeanADNRetail implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 8441224092330609941L;
	/**
	 * ID para este registro de tipo Adn Retail y No Retail.
	 */
	private String idSegmentoLocal;
	/**
	 * Descripcion de este registro.
	 */
	private String descripcion;
	/**
	 * Tipo de banca para este registro de tipo Adn Retail y No Retail.
	 */
	private String banca;
	/**
	 * Valor de la bandera de Retail para este registro.
	 */
	private String flagRetail;
	
	/**
	 * Obtiene el valor del campo idSegmentoLocal.
	 * @return String
	 */
	public String getIdSegmentoLocal() {
		return idSegmentoLocal;
	}
	
	/**
	 * Establece el valor para el campo idSegmentoLocal.
	 * @param idSegmentoLocal Contiene el valor que sera colocado en el campo idSegmentoLocal.
	 */
	public void setIdSegmentoLocal(String idSegmentoLocal) {
		this.idSegmentoLocal = idSegmentoLocal;
	}
	/**
	 * Obtiene el valor del campo descripcion.
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Establece el valor del campo descripcion.
	 * @param descripcion Valor que sera colocado en el campo descripcion.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * Obtiene el valor del campo banca.
	 * @return String
	 */
	public String getBanca() {
		return banca;
	}
	/**
	 * Establece el valor del campo banca.
	 * @param banca Valor que sera colocado en el campo banca.
	 */
	public void setBanca(String banca) {
		this.banca = banca;
	}
	/**
	 * Obtiene el valor del campo flagRetail.
	 * @return String
	 */
	public String getFlagRetail() {
		return flagRetail;
	}
	/**
	 * Establece el valor del campo flagRetail.
	 * @param flagRetail Valor que sera colocado en el campo flagRetail.
	 */
	public void setFlagRetail(String flagRetail) {
		this.flagRetail = flagRetail;
	}
}
