package mx.isban.rorac.bean.lanzadores;

import java.io.Serializable;
import java.util.List;

import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.consultas.BeanEstatusCarga;

/**
 * @author everis
 * @version 1.0
 * @see www.everis.com
 *
 */
public class BeanResultBOListasMonitor implements BeanResultBO,
		Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Codigo de Error producido al realizar la consulta de Monitor de Cargas.
	 */
	private String codError;
	/**
	 * Mensaje de Error, en caso de que haya ocurrido al realizar la consulta de
	 * Monitor de Cargas.
	 */
	private String msgError;

	/**
	 * Resultado de la consulta de los estatus a los insumos de motor RORAC
	 */
	private List<BeanEstatusCarga> listaMonitorMotor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getCodError()
	 */
	@Override
	public String getCodError() {
		return this.codError;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getMsgError()
	 */
	@Override
	public String getMsgError() {
		return this.msgError;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.agave.commons.interfaces.BeanResultBO#setCodError(java.lang.
	 * String)
	 */
	@Override
	public void setCodError(final String codError) {
		this.codError = codError;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.agave.commons.interfaces.BeanResultBO#setMsgError(java.lang.
	 * String)
	 */
	@Override
	public void setMsgError(final String msgError) {
		this.msgError = msgError;
	}

	/**
	 * Metodo para obtener la lista de estatus de los insumos de motor RORAC
	 *
	 * @return la lista de los estatus del motor RORAC
	 */
	public List<BeanEstatusCarga> getListaMonitorMotor() {
		return listaMonitorMotor;
	}

	/**
	 * Metodo para establecer la lista de los estatus de los insumos del motor
	 * RORAC
	 * 
	 * @param listaMonitorMotor
	 *            la nueva lista de los estatus.
	 */
	public void setListaMonitorMotor(
			final List<BeanEstatusCarga> listaMonitorMotor) {
		this.listaMonitorMotor = listaMonitorMotor;
	}
}
