/**
 * 
 */
package mx.isban.rorac.bean.consultas;

import java.io.Serializable;
import java.util.List;

import mx.isban.agave.commons.interfaces.BeanResultBO;

/**
 * @author everis
 *
 */
public class BeanListaMonitorCargasDAO implements BeanResultBO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Codigo de Error producido al realizar la consulta de Monitor de Cargas.
	 */
	private String codError;
	/**
	 * Mensaje de Error, en caso de que haya ocurrido al realizar la consulta de Monitor de Cargas.
	 */
	private String msgError;
	/**
	 * Lista de BeanEstatusCargas, producto de la consulta de Monitor de Cargas.
	 */
	private List<BeanEstatusCarga> listaMonitor;

	/**
	 * Obtiene el campo listaMonitor.
	 * @return List<BeanEstatusCarga>
	 */
	public List<BeanEstatusCarga> getListaMonitor() {
		return listaMonitor;
	}

	/**
	 * Establece el valor del campo listaMonitor.
	 * @param listaMonitor Valor que sera colocado en el campo listaMonitor.
	 */
	public void setListaMonitor(List<BeanEstatusCarga> listaMonitor) {
		this.listaMonitor = listaMonitor;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getCodError()
	 */
	@Override
	public String getCodError() {
		return this.codError;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getMsgError()
	 */
	@Override
	public String getMsgError() {
		return this.msgError;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#setCodError(java.lang.String)
	 */
	@Override
	public void setCodError(String codError) {
		this.codError = codError;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#setMsgError(java.lang.String)
	 */
	@Override
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
}
