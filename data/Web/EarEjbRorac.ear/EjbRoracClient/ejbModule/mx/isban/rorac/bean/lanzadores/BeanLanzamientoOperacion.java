/**
 * 
 */
package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;

/**
 * @author everis
 *
 */
public class BeanLanzamientoOperacion implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7278264086663217568L;
	/**
	 * Codigo con el que se guarda un registro en la tabla de estatus. 
	 */
	private String codigoEstatus;
	/**
	 * Indice del archivo para el cual se registrara la orden de operacion.
	 */
	private String indiceArchivo;
	/**
	 * Codigo de operacion que indica se se realizara una Actualizacion o Eliminacion.
	 */
	private String idOperacion;
	/**
	 * Anio en que se registra la operacion.
	 */
	private String anio; 
	/**
	 * Mes en el que se realiza la operacion.
	 */
	private String mes;
	/**
	 * Indica de que tipo de proceso es esta registro.
	 */
	private String idProceso;
	
	/**
	 * Obtiene el valor del campo codigoEstatus.
	 * @return String.
	 */
	public String getCodigoEstatus() {
		return codigoEstatus;
	}
	
	/**
	 * Establece el valor del campo codigoEstatus.
	 * @param codigoEstatus Valor que sera colocado en el campo codigoEstatus.
	 */
	public void setCodigoEstatus(String codigoEstatus) {
		this.codigoEstatus = codigoEstatus;
	}
	
	/**
	 * Obtiene el valor de campo indiceArchivo.
	 * @return String
	 */
	public String getIndiceArchivo() {
		return indiceArchivo;
	}
	
	/**
	 * Establece el valor del campo indiceArchivo.
	 * @param indiceArchivo Valor que sera colocado en el campo indiceArchivo.
	 */
	public void setIndiceArchivo(String indiceArchivo) {
		this.indiceArchivo = indiceArchivo;
	}
	
	/**
	 * Obtiene el valor del campo idOperacion.
	 * @return String
	 */
	public String getIdOperacion() {
		return idOperacion;
	}
	
	/**
	 * Establece el valor del campo idOperacion
	 * @param idOperacion Valor que sera colocado en el campo idOperacion.
	 */
	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}
	
	/**
	 * Obtiene el valor del campo anio.
	 * @return String.
	 */
	public String getAnio() {
		return anio;
	}
	
	/**
	 * Establece el valor del campo anio.
	 * @param anio Valor que sera colocado en el campo anio.
	 */
	public void setAnio(String anio) {
		this.anio = anio;
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
	 * Obtiene el valor del campo idProceso.
	 * @return String
	 */
	public String getIdProceso() {
		return idProceso;
	}

	/**
	 * Establece el valor del campo idProceso.
	 * @param idProceso Valor que sera colocado en el campo idProceso.
	 */
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	
}
