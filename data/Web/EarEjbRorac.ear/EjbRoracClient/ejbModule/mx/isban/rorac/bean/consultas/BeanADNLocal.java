/**
 * 
 */
package mx.isban.rorac.bean.consultas;

import java.io.Serializable;

/**
 * @author everis
 *
 */
public class BeanADNLocal implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2110286132703656082L;
	/**
	 * Id para este registro del tipo de parametro Adn Local y No Local.
	 */
	private String idAdnLocal;
	/**
	 * Descripcion de este registro del tipo de parametro Adn Local y No Local.
	 */
	private String descripcion;
	/**
	 * Tipo de Banca de este registro.
	 */
	private String banca;
	/**
	 * Valor de la bandera de Activo..
	 */
	private String flagActivo;
	/**
	 * Valor de la bandera de Pasivo..
	 */
	private String flagPasivo;
	/**
	 * Valor de la bandera de Fondos.
	 */
	private String flagFondos;
	/**
	 * Valor de la bandera de Comiciones.
	 */
	private String flagComiciones;
	/**
	 * Valor de la bandera de Contingentes.
	 */
	private String flagContingentes;
	/**
	 * Valor de la bandera de Ajustes
	 */
	private String flagAjustes;
	/**
	 * Valor de la bandera de Internegocios.
	 */
	private String flagInternegocios;
	
	/**
	 * Obtiene el valor del campo idAdnLocal.
	 * @return String
	 */
	public String getIdAdnLocal() {
		return idAdnLocal;
	}
	
	/**
	 * Establece el valor del campo idAdnLocal.
	 * @param idAdnLocal Contiene el valor que sera colocado en el campo idAdnLocal.
	 */
	public void setIdAdnLocal(String idAdnLocal) {
		this.idAdnLocal = idAdnLocal;
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
	 * @param descripcion Contiene el valor que sera colocado en el campo descripcion.
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
	 * @param banca Contiene el valor que sera colocado en el campo banca.
	 */
	public void setBanca(String banca) {
		this.banca = banca;
	}
	
	/**
	 * Obtiene el valor del campo flagActivo.
	 * @return String
	 */
	public String getFlagActivo() {
		return flagActivo;
	}
	
	/**
	 * Establece el valor del campo flagActivo.
	 * @param flagActivo Contiene el valor que sera colocado en el campo flagActivo.
	 */
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}
	
	/**
	 * Obtiene el valor del campo flagPasivo.
	 * @return String
	 */
	public String getFlagPasivo() {
		return flagPasivo;
	}
	
	/**
	 * Establece el valor del campo flagPasivo.
	 * @param flagPasivo Contiene el valor del campo flagPasivo.
	 */
	public void setFlagPasivo(String flagPasivo) {
		this.flagPasivo = flagPasivo;
	}
	
	/**
	 * Obtiene el valor del campo flagFondos.
	 * @return String
	 */
	public String getFlagFondos() {
		return flagFondos;
	}
	
	/**
	 * Establece el valor de flagFondos.
	 * @param flagFondos Contiene el valor que sera colocado en el campo flagFondos.
	 */
	public void setFlagFondos(String flagFondos) {
		this.flagFondos = flagFondos;
	}
	
	/**
	 * Obtiene el valor de flagComiciones.
	 * @return String
	 */
	public String getFlagComiciones() {
		return flagComiciones;
	}
	
	/**
	 * Establece el valor de flagComiciones.
	 * @param flagComiciones Contiene el valor de flagComiciones.
	 */
	public void setFlagComiciones(String flagComiciones) {
		this.flagComiciones = flagComiciones;
	}
	
	/**
	 * Obtiene el valor del campo flagContingentes.
	 * @return String
	 */
	public String getFlagContingentes() {
		return flagContingentes;
	}
	
	/**
	 * Establece el valor de flagContingentes.
	 * @param flagContingentes Contiene el valor que sera establecido en el campo flagContingentes.
	 */
	public void setFlagContingentes(String flagContingentes) {
		this.flagContingentes = flagContingentes;
	}

	/**
	 * @return the flagAjustes
	 */
	public String getFlagAjustes() {
		return flagAjustes;
	}

	/**
	 * @param flagAjustes the flagAjustes to set
	 */
	public void setFlagAjustes(String flagAjustes) {
		this.flagAjustes = flagAjustes;
	}

	/**
	 * @return the flagInternegocios
	 */
	public String getFlagInternegocios() {
		return flagInternegocios;
	}

	/**
	 * @param flagInternegocios the flagInternegocios to set
	 */
	public void setFlagInternegocios(String flagInternegocios) {
		this.flagInternegocios = flagInternegocios;
	}
}
