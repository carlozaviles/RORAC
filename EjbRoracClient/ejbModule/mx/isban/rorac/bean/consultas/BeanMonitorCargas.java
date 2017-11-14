package mx.isban.rorac.bean.consultas;

import java.io.Serializable;
import java.util.List;

public class BeanMonitorCargas implements Serializable{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8476029226327929921L;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas manuales.
	 */
	private List<BeanEstatusCarga> cargasManuales;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas iniciales.
	 */
	private List<BeanEstatusCarga> cargasIniciales;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las interfaces procesadas.
	 */
	private List<BeanEstatusCarga> interfacesProcesadas;
	
	/**
	 * Retorna el valor del campo cargasManuales.
	 * @return List<BeanEstatusCarga>
	 */
	public List<BeanEstatusCarga> getCargasManuales() {
		return cargasManuales;
	}
	
	/**
	 * Establece el valor del campo cargasManuales.
	 * @param cargasManuales Valor que sera colocado en el campo cargasManuales.
	 */
	public void setCargasManuales(List<BeanEstatusCarga> cargasManuales) {
		this.cargasManuales = cargasManuales;
	}
	
	/**
	 * Obtiene el valor del campo cargasIniciales.
	 * @return List<BeanEstatusCarga>
	 */
	public List<BeanEstatusCarga> getCargasIniciales() {
		return cargasIniciales;
	}
	
	/**
	 * Establece el valor del campo cargasIniciales.
	 * @param cargasIniciales Valor que se colocara en el campo cargasIniciales.
	 */
	public void setCargasIniciales(List<BeanEstatusCarga> cargasIniciales) {
		this.cargasIniciales = cargasIniciales;
	}
	
	/**
	 * Retorna el valor del campo interfacesProcesadas.
	 * @return List<BeanEstatusCarga>
	 */
	public List<BeanEstatusCarga> getInterfacesProcesadas() {
		return interfacesProcesadas;
	}
	
	/**
	 * Establece el valor del campo interfacesProcesadas.
	 * @param interfacesProcesadas Valor que sera colocado en el campo interfacesProcesadas.
	 */
	public void setInterfacesProcesadas(List<BeanEstatusCarga> interfacesProcesadas) {
		this.interfacesProcesadas = interfacesProcesadas;
	}
}
