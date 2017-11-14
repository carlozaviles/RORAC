package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;

/**
 * @author everis
 * @version 1.0
 * @see www.everis.com
 *
 */
public class BeanLanzamientoMotorRorac implements Serializable {
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Anio en que se registra la operacion.
	 */
	private String anio;
	/**
	 * Mes en el que se realiza la operacion.
	 */
	private String mes;

	/**
	 * Finalidad de la ejecucion
	 */
	private String finalidad;
	/**
	 * Divisa siempre MXN
	 */
	private String divisa;
	/**
	 * Parametro N para el motor
	 */
	private String valorN;

	/**
	 * Obtiene el valor del campo anio.
	 *
	 * @return String el año.
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * Establece el valor del campo anio.
	 *
	 * @param anio
	 *            Valor que sera colocado en el campo anio.
	 */
	public void setAnio(final String anio) {
		this.anio = anio;
	}

	/**
	 * Obtiene el valor del campo mes.
	 *
	 * @return String el mes.
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * Establece el valor del campo mes.
	 *
	 * @param mes
	 *            Valor que sera colocado en el campo mes.
	 */
	public void setMes(final String mes) {
		this.mes = mes;
	}

	/**
	 * Obtiene el valor del campo finalidad
	 *
	 * @return el valor de finalidad
	 */
	public String getFinalidad() {
		return finalidad;
	}

	/**
	 * Establece el valor del campo finalidad
	 *
	 * @param finalidad
	 *            el nuevo valor de finalidad
	 */
	public void setFinalidad(final String finalidad) {
		this.finalidad = finalidad;
	}

	/**
	 * Obtiene el valor del campo divisa
	 *
	 * @return el valor de divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * Establece el valor del campo divisa
	 *
	 * @param divisa
	 *            el nuevo valor de divisa
	 */
	public void setDivisa(final String divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el valor del campo N
	 *
	 * @return el valor N
	 */
	public String getValorN() {
		return valorN;
	}

	/**
	 * Establece el valor del campo N
	 *
	 * @param n
	 *            el nuevo valor de N
	 */
	public void setValorN(final String n) {
		this.valorN = n;
	}
}
