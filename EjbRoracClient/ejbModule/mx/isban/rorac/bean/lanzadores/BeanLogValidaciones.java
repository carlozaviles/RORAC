package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;
import java.util.List;

public class BeanLogValidaciones implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -1745083690725404367L;
	/**
	 * Lista de validaciones a nivel contrato del input de activo.
	 */
	private List<BeanEstatusLog> validacionesContratoActivo;
	/**
	 * Lista de validaciones a nivel total sobre el input final.
	 */
	private List<BeanEstatusLog> validacionesTotalFinal;
	/**
	 * Lista de validaciones a nivel total sobre el input de activo.
	 */
	private List<BeanEstatusLog> validacionesTotalActivo;
	/**
	 * Lista de validaciones a nivel contrato sobre el input de pasivo.
	 */
	private List<BeanEstatusLog> validacionesContratoPasivo;

	
	/**
	 * Obtiene el valor del campo validacionesContratoActivo.
	 * @return List<BeanEstatusLog>
	 */
	public List<BeanEstatusLog> getValidacionesContratoActivo() {
		return validacionesContratoActivo;
	}

	/**
	 * Establece el valor del campo validacionesContratoActivo
	 * @param validacionesContratoActivo Valor que sera colocado en el campo validacionesContratoActivo.
	 */
	public void setValidacionesContratoActivo(
			List<BeanEstatusLog> validacionesContratoActivo) {
		this.validacionesContratoActivo = validacionesContratoActivo;
	}

	/**
	 * Obtiene el valor del campo validacionesTotalFinal
	 * @return List<BeanEstatusLogs>
	 */
	public List<BeanEstatusLog> getValidacionesTotalFinal() {
		return validacionesTotalFinal;
	}

	/**
	 * Establece el valor del campo validacionesTotalFinal.
	 * @param validacionesTotalFinal Valor que sera colocado en el campo validacionesTotalFinal
	 */
	public void setValidacionesTotalFinal(
			List<BeanEstatusLog> validacionesTotalFinal) {
		this.validacionesTotalFinal = validacionesTotalFinal;
	}

	/**
	 * Obtiene el valor del campo validacionesTotalActivo
	 * @return List<BeanEstatusLog>
	 */
	public List<BeanEstatusLog> getValidacionesTotalActivo() {
		return validacionesTotalActivo;
	}

	/**
	 * Establece el valor del campo validacionesTotalActivo
	 * @param validacionesTotalActivo Valor que sera colocado en el campo validacionesTotalActivo.
	 */
	public void setValidacionesTotalActivo(
			List<BeanEstatusLog> validacionesTotalActivo) {
		this.validacionesTotalActivo = validacionesTotalActivo;
	}

	/**
	 * Obtiene el valor del campo validacionesContratoPasivo.
	 * @return List<BeanEstatusLog>
	 */
	public List<BeanEstatusLog> getValidacionesContratoPasivo() {
		return validacionesContratoPasivo;
	}

	/**
	 * Establece el valor del campo validacionesContratoPasivo.
	 * @param validacionesContratoPasivo Valor que sera colocado en el campo validacionesContratoPasivo.
	 */
	public void setValidacionesContratoPasivo(
			List<BeanEstatusLog> validacionesContratoPasivo) {
		this.validacionesContratoPasivo = validacionesContratoPasivo;
	}
}
