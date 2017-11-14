/**
 * 
 */
package mx.isban.rorac.bean.consultas;

import java.io.Serializable;

/**
 * @author everis
 *
 */
public class BeanConsultaMonitor implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -208494238538156268L;
	/**
	 * Indice Inicial Insumo a partir del cual se realizara esta consulta de Monitor de Carga.
	 */
	private String indiceInicialInsumo;
	/**
	 * Indice Final Insumo hasta el cual se realizara esta consulta de Monitor de Carga.
	 */
	private String indiceFinalInsumo;
	/**
	 * Mes para el cual se realizara la consulta.
	 */
	private String mes;
	/**
	 * Anio para el cual se realizara la consulta.
	 */
	private String anio;
	/**
	 * Contiene el ID de proceso de Monitor de Cargas.
	 */
	private String idProceso;
	
	/**
	 * Obtiene el valor del campo indiceInicialInsumo.
	 * @return String
	 */
	public String getIndiceInicialInsumo() {
		return indiceInicialInsumo;
	}
	
	/**
	 * Establece el valor del campo indiceInicialInsumo.
	 * @param indiceInicialInsumo Valor que sera colocado en el campo indiceInicialInsumo.
	 */
	public void setIndiceInicialInsumo(String indiceInicialInsumo) {
		this.indiceInicialInsumo = indiceInicialInsumo;
	}
	
	/**
	 * Obtiene el valor del campo indiceFinalInsumo.
	 * @return String.
	 */
	public String getIndiceFinalInsumo() {
		return indiceFinalInsumo;
	}
	
	/**
	 * Establece el valor del campo indiceFinalInsumo
	 * @param indiceFinalInsumo Valor que sera colocado en el campo indiceFinalInsumo.
	 */
	public void setIndiceFinalInsumo(String indiceFinalInsumo) {
		this.indiceFinalInsumo = indiceFinalInsumo;
	}
	
	/**
	 * Obtiene el valor del campo mes.
	 * @return String.
	 */
	public String getMes() {
		return mes;
	}
	
	/**
	 * Establece el valor del campo mes.
	 * @param mes Valor que sera colocado en el campo mes.
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}
	
	/**
	 * Obtiene el valor del campo anio.
	 * @return String
	 */
	public String getAnio() {
		return anio;
	}
	
	/**
	 * Establece el valor del campo anio.
	 * @param anio Contiene el valor que sera colocado en el campo anio.
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * Obtiene el valor del campo idProceso.
	 * @return String
	 */
	public String getIdProceso() {
		return idProceso;
	}

	/**
	 * Establece el valor del campo idProceso
	 * @param idProceso Valor que sera colocado en el campo idProceso.
	 */
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
}
