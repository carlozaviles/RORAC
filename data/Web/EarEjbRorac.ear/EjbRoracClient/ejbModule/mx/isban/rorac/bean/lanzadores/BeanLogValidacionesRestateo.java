package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;
import java.util.List;

public class BeanLogValidacionesRestateo implements Serializable {

	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista de validaciones a nivel contrato del input de activo.
	 */
	private List<BeanEstatusLog> validacionesContratoActivo;
	/**
	 * Lista de validaciones a nivel contrato del input de activo.
	 */
	private List<BeanEstatusLog> validacionesContratoPasivo;
	/**
	 * Lista de validaciones a nivel contrato del input de activo.
	 */
	private List<BeanEstatusLog> validacionesAdNActivo;
	/**
	 * Lista de validaciones a nivel contrato del input de activo.
	 */
	private List<BeanEstatusLog> validacionesAdNPasivo;

	/**
	 * Metodo para obtener las validaciones de contrato activo
	 *
	 * @return the validacionesContratoActivo
	 */
	public List<BeanEstatusLog> getValidacionesContratoActivo() {
		return validacionesContratoActivo;
	}

	/**
	 * Metodo para establecer las validaciones de contrato activo
	 *
	 * @param validacionesContratoActivo
	 *            the validacionesContratoActivo to set
	 */
	public void setValidacionesContratoActivo(
			final List<BeanEstatusLog> validacionesContratoActivo) {
		this.validacionesContratoActivo = validacionesContratoActivo;
	}

	/**
	 * Metodo para obtener las validaciones de contrato pasivo
	 *
	 * @return the validacionesContratoPasivo
	 */
	public List<BeanEstatusLog> getValidacionesContratoPasivo() {
		return validacionesContratoPasivo;
	}

	/**
	 * Metodo para establecer las validaciones de contrato pasivo
	 *
	 * @param validacionesContratoPasivo
	 *            the validacionesContratoPasivo to set
	 */
	public void setValidacionesContratoPasivo(
			final List<BeanEstatusLog> validacionesContratoPasivo) {
		this.validacionesContratoPasivo = validacionesContratoPasivo;
	}

	/**
	 * Metodo para obtener las validaciones de area de negocio activo
	 *
	 * @return the validacionesAdNActivo
	 */
	public List<BeanEstatusLog> getValidacionesAdNActivo() {
		return validacionesAdNActivo;
	}

	/**
	 * Metodo para establecer las validaciones de area de negocio activo
	 * 
	 * @param validacionesAdNActivo
	 *            the validacionesAdNActivo to set
	 */
	public void setValidacionesAdNActivo(
			final List<BeanEstatusLog> validacionesAdNActivo) {
		this.validacionesAdNActivo = validacionesAdNActivo;
	}

	/**
	 * Metodo para obtener las validaciones de area de negocio pasivo
	 *
	 * @return the validacionesAdNPasivo
	 */
	public List<BeanEstatusLog> getValidacionesAdNPasivo() {
		return validacionesAdNPasivo;
	}

	/**
	 * Metodo para establecer las validaciones de area de negocio pasivo
	 * 
	 * @param validacionesAdNPasivo
	 *            the validacionesAdNPasivo to set
	 */
	public void setValidacionesAdNPasivo(
			final List<BeanEstatusLog> validacionesAdNPasivo) {
		this.validacionesAdNPasivo = validacionesAdNPasivo;
	}
}
