package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;

public class BeanParametrosLogs implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -300034096228584848L;
	/**
	 * Id inicial a partir del cual se reliazara la consulta de Estatus de Logs.
	 */
	private String idLogInicial;
	/**
	 * Id final hasta el cual se relizara la consulta de Estatus de Logs.
	 */
	private String idLogFinal;
	/**
	 * Id de proceso de Consulta de Logs.
	 */
	private String idProceso;
	/**
	 * Mes para el cual se realiza la busqueda.
	 */
	private String mes;
	/**
	 * Anio para el cual se realiza la busqueda.
	 */
	private String anio;
	/**
	 * Envia el codigo que debe ser colocado a los logs que sean generados..
	 */
	private String codigoLogGenerado;
	
	/**
	 * Obtiene el valor del campo idLogInicial
	 * @return String
	 */
	public String getIdLogInicial() {
		return idLogInicial;
	}
	
	/**
	 * Establece el valor del campo idLogInicial.
	 * @param idLogInicial Valor que sera colocado en el campo idLogInicial.
	 */
	public void setIdLogInicial(String idLogInicial) {
		this.idLogInicial = idLogInicial;
	}
	/**
	 * Obtiene el valor del campo idLogFinal
	 * @return String
	 */
	public String getIdLogFinal() {
		return idLogFinal;
	}
	/**
	 * Establece el valor del campo idLogFinal
	 * @param idLogFinal Valor del campo idLogFinal
	 */
	public void setIdLogFinal(String idLogFinal) {
		this.idLogFinal = idLogFinal;
	}
	/**
	 * Obtiene el valor del campo idProceo.
	 * @return String
	 */
	public String getIdProceso() {
		return idProceso;
	}
	/**
	 * Establece el valor del campo idProceo.
	 * @param idProceso Valor que sera colocado en el campo idProceo.
	 */
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	/**
	 * Obtiene el valor del campo mes.
	 * @return String
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
	 * @param anio Valor que sera colocado en el campo anio.
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}
	/**
	 * Obtiene el valor del campo codigoLogGenerado.
	 * @return String.
	 */
	public String getCodigoLogGenerado() {
		return codigoLogGenerado;
	}
	/**
	 * Establece el valor del campo codigoLogGenerado
	 * @param codigoLogGenerado Valor que sera colocado en el campo codigoLogGenerado.
	 */
	public void setCodigoLogGenerado(String codigoLogGenerado) {
		this.codigoLogGenerado = codigoLogGenerado;
	}
	
}
