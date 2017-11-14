package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;
import java.util.List;

import mx.isban.rorac.bean.consultas.BeanEstatusCarga;

public class BeanMonitorMotor implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de los insumos
	 * del motor rorac.
	 */
	private List<BeanEstatusCarga> insumosMotorRorac;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus de los insumos
	 * corporativos.
	 */
	private List<BeanEstatusCarga> insumosCorporativos;
	/**
	 * Lista de objetos BeanEstatusCarga, que contiene el estatus del
	 * lanzamiento Motor RORAC.
	 */
	private List<BeanEstatusCarga> lanzamientoMotorRorac;

	/**
	 * Metodo para obtener la lista de los objetos estatus para los insumos del
	 * motor RORAC.
	 * 
	 * @return insumosMotorRorac la lista de objetos estatus para los insumos
	 *         Motor RORAC
	 */
	public List<BeanEstatusCarga> getInsumosMotorRorac() {
		return insumosMotorRorac;
	}

	/**
	 * Metodo para establecer los objetos estatus de los insumos motor rorac
	 *
	 * @param insumosMotorRorac
	 *            la lista de objetos estatus para los insumos motor rorac
	 */
	public void setInsumosMotorRorac(
			final List<BeanEstatusCarga> insumosMotorRorac) {
		this.insumosMotorRorac = insumosMotorRorac;
	}

	/**
	 * Metodo para obtener los objetos estatus de los insumos corporativos
	 *
	 * @return insumosCorporativos la lista de objetos estatus para los insumos
	 *         corporativos
	 */
	public List<BeanEstatusCarga> getInsumosCorporativos() {
		return insumosCorporativos;
	}

	/**
	 * Metodo para establecer los objetos estatus de los insumos corporativos
	 *
	 * @param insumosCorporativos
	 *            lista de objetos estatus para los insumos corporativos
	 */
	public void setInsumosCorporativos(
			final List<BeanEstatusCarga> insumosCorporativos) {
		this.insumosCorporativos = insumosCorporativos;
	}

	/**
	 * Metoddo para obtener los objetos de estatus lanzamiento motor rorac
	 *
	 * @return lista de objetos estatus para lanzamiento motor rorac
	 */
	public List<BeanEstatusCarga> getLanzamientoMotorRorac() {
		return lanzamientoMotorRorac;
	}

	/**
	 * Metodo para establecer la lista de objetos status para el lanzamiento
	 * motor rorac
	 *
	 * @param lanzamientoMotorRorac
	 *            lista de objetos estatus para lanzamiento motor rorac
	 */
	public void setLanzamientoMotorRorac(
			final List<BeanEstatusCarga> lanzamientoMotorRorac) {
		this.lanzamientoMotorRorac = lanzamientoMotorRorac;
	}
}
