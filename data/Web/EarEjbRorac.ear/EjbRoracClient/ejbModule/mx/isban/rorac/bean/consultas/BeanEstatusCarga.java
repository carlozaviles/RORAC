package mx.isban.rorac.bean.consultas;

import java.io.Serializable;

public class BeanEstatusCarga implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -214214976863511430L;
	/**
	 * Nombre de la interfaz que se esta monitoreando.
	 */
	private String nombreInterfaz;
	/**
	 * Indica el estatus de la interfaz que se esta monitoreando.
	 */
	private String estatus;
	/**
	 * Fecha en que se cargo por primera vez esta interfaz. Este campo solo aplica para
	 * los insumos iniciales.
	 */
	private String fechaAlta;
	/**
	 * Contiene el detalle del error en caso de que haya ocurrido al procesar la interfaz.
	 */
	private String detalleError;
	/**
	 * Operacion realizada sobre esta interfaz, este campo solo se utiliza para el monitor de Interfaces.
	 */
	private String operacion;
	
	/**
	 * Obtiene el valor del campo nombreInterfaz.
	 * @return String.
	 */
	public String getNombreInterfaz() {
		return nombreInterfaz;
	}
	
	/**
	 * Establece el valor del campo nombreInterfaz
	 * @param nombreInterfaz Contiene el valor del campo Interfaz.
	 */
	public void setNombreInterfaz(String nombreInterfaz) {
		this.nombreInterfaz = nombreInterfaz;
	}
	
	/**
	 * Obtiene el valor del campo estatus.
	 * @return String.
	 */
	public String getEstatus() {
		return estatus;
	}
	
	/**
	 * Establece el valor del campo estatus.
	 * @param estatus Contiene el valor del campo estatus.
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	/**
	 * Obtiene el valor del campo fechaAlta.
	 * @return String.
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}
	
	/**
	 * Establece el valor del campo fechaAlta.
	 * @param fechaAlta Valor que se coloca en el campo fechaAlta.
	 */
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	/**
	 * Obtiene el valor del campo detalleError.
	 * @return String
	 */
	public String getDetalleError() {
		return detalleError;
	}
	
	/**
	 * Establece el valor del campo detalleError.
	 * @param detalleError Valor que sera colocado en el campo detalleError.
	 */
	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}

	/**
	 * Obtiene el valor del campo operacion.
	 * @return String.
	 */
	public String getOperacion() {
		return operacion;
	}

	/**
	 * Establece el valor del campo operacion.
	 * @param operacion Valor que sera colocado en el campo operacion.
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
}
