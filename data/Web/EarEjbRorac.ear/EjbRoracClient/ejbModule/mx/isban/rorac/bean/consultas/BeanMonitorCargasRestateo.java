package mx.isban.rorac.bean.consultas;

import java.io.Serializable;
import java.util.List;

public class BeanMonitorCargasRestateo implements Serializable {

	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas
	 * de activo para restateo.
	 */
	private List<BeanEstatusCarga> restActivo;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas
	 * manuales.
	 */
	private List<BeanEstatusCarga> restPasivo;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas
	 * manuales.
	 */
	private List<BeanEstatusCarga> restComisiones;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas
	 * manuales.
	 */
	private List<BeanEstatusCarga> restFondos;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas
	 * manuales.
	 */
	private List<BeanEstatusCarga> restContingentes;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de las cargas
	 * manuales.
	 */
	private List<BeanEstatusCarga> restCliente;

	/**
	 * Metodo para obtener el estatus de los insumos activo
	 *
	 * @return the restActivo estatus
	 */
	public List<BeanEstatusCarga> getRestActivo() {
		return restActivo;
	}

	/**
	 * Metodo para establecer el estatus de los insumos activo
	 *
	 * @param restActivo
	 *            the restActivo to set
	 */
	public void setRestActivo(final List<BeanEstatusCarga> restActivo) {
		this.restActivo = restActivo;
	}

	/**
	 * Metodo para obtener el estatus de los insumos pasivo
	 *
	 * @return the restPasivo estatus
	 */
	public List<BeanEstatusCarga> getRestPasivo() {
		return restPasivo;
	}

	/**
	 * Metodo para establecer el estatus de los insumos pasivo
	 *
	 * @param restPasivo
	 *            the restPasivo to set
	 */
	public void setRestPasivo(final List<BeanEstatusCarga> restPasivo) {
		this.restPasivo = restPasivo;
	}

	/**
	 * Metodo para obtener el estatus de los insumos comisiones
	 *
	 * @return the restComisiones estatus
	 */
	public List<BeanEstatusCarga> getRestComisiones() {
		return restComisiones;
	}

	/**
	 * Metodo para establecer el estatus de los insumos comisiones
	 *
	 * @param restComisiones
	 *            the restComisiones to set
	 */
	public void setRestComisiones(final List<BeanEstatusCarga> restComisiones) {
		this.restComisiones = restComisiones;
	}

	/**
	 * Metodo para obtener el estatus de los insumos fondos
	 *
	 * @return the restFondos estatus
	 */
	public List<BeanEstatusCarga> getRestFondos() {
		return restFondos;
	}

	/**
	 * Metodo para establecer el estatus de los insumos fondos
	 *
	 * @param restFondos
	 *            the restFondos to set
	 */
	public void setRestFondos(final List<BeanEstatusCarga> restFondos) {
		this.restFondos = restFondos;
	}

	/**
	 * Metodo para obtener el estatus de los insumos contingentes
	 *
	 * @return the restContingentes estatus
	 */
	public List<BeanEstatusCarga> getRestContingentes() {
		return restContingentes;
	}

	/**
	 * Metodo para establecer el estatus de los insumos contingentes
	 *
	 * @param restContingentes
	 *            the restContingentes to set
	 */
	public void setRestContingentes(
			final List<BeanEstatusCarga> restContingentes) {
		this.restContingentes = restContingentes;
	}

	/**
	 * Metodo para establecer el estatus de los insumos cliente
	 *
	 * @param restCliente
	 *            the restCliente to set
	 */
	public void setRestCliente(final List<BeanEstatusCarga> restCliente) {
		this.restCliente = restCliente;
	}

	/**
	 * Metodo para obtener el estatus de los insumos cliente
	 *
	 * @return los insumos de cliente
	 */
	public List<BeanEstatusCarga> getRestCliente() {
		return restCliente;
	}
}
