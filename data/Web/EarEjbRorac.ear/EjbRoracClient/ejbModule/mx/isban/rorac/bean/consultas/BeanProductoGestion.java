/**
 * 
 */
package mx.isban.rorac.bean.consultas;

import java.io.Serializable;

/**
 * @author everis
 *
 */
public class BeanProductoGestion implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 5174223706693125970L;
	/**
	 * Identificador de este registro de producto gestion.
	 */
	private String idProductoGestion;
	/**
	 * Descripcion de este campo de producto gestion.
	 */
	private String descripcion;
	/**
	 * Valor de la bandera de Activo.
	 */
	private String flagActivo;
	/**
	 * Valor de la bander de Pasivo.
	 */
	private String flagPasivo;
	/**
	 * Valor de la bandera de fondos. 
	 */
	private String flagFondos;
	/**
	 * Valor de la bandera de comiciones.
	 */
	private String flagComiciones;
	/**
	 * Valor de la bandera de contingentes.
	 */
	private String flagContingentes;
	/**
	 * Valor de la bandera de Ajustes.
	 */
	private String flagAjustes;
	/**
	 * Valor de la bandera de Internegocios.
	 */
	private String flagInternegocios;
	
	/**
	 * Obtiene el valor del campo idProductoGestion.
	 * @return String.
	 */
	public String getIdProductoGestion() {
		return idProductoGestion;
	}
	
	/**
	 * Establece el valor del campo idProductoGestion.
	 * @param idProductoGestion Valor que sera colocado en el campo idProductoGestion.
	 */
	public void setIdProductoGestion(String idProductoGestion) {
		this.idProductoGestion = idProductoGestion;
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
	 * Obtiene el valor del campo flagActivo
	 * @return String
	 */
	public String getFlagActivo() {
		return flagActivo;
	}
	
	/**
	 * Establece el valor del campo flagActivo.
	 * @param flagActivo Valor que sera colocado en el campo flagActivo.
	 */
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}
	
	/**
	 * Retorna el valor del campo flagPasivo.
	 * @return String
	 */
	public String getFlagPasivo() {
		return flagPasivo;
	}
	
	/**
	 * Establece el valor del campo flagPasivo
	 * @param flagPasivo Valor que sera colocado en el campo flagPasivo.
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
	 * Establece el valor del campo flagFondos.
	 * @param flagFondos Valor que sera colocado en el campo flagFondos.
	 */
	public void setFlagFondos(String flagFondos) {
		this.flagFondos = flagFondos;
	}
	
	/**
	 * Obtiene el valor del campo flagComiciones.
	 * @return String
	 */
	public String getFlagComiciones() {
		return flagComiciones;
	}
	
	/**
	 * Establece el valor del campo flagComiciones.
	 * @param flagComiciones Valor que sera colocado en el campo flagComiciones.
	 */
	public void setFlagComiciones(String flagComiciones) {
		this.flagComiciones = flagComiciones;
	}
	
	/**
	 * Obtiene el valor de campo flagContingentes.
	 * @return String
	 */
	public String getFlagContingentes() {
		return flagContingentes;
	}
	
	/**
	 * Establece el valor del campo flagContingenes
	 * @param flagContingentes Valor que sera colocado en el campo flagContingentes.
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
