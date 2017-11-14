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
public class BeanListaProductoGestionDAO implements BeanResultBO, Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -1568339534128598951L;
	/**
	 * Codigo de Error, producido al realizar la consulta de Producto Gestion.
	 */
	private String codError;
	/**
	 * Mensaje de Error, en caso de que haya ocurrido, al realizar la consulta de Producto Gestion.
	 */
	private String msgError;
	/**
	 * Lista de BeanProductoGestion, producto de la consulta de Producto Gestion.
	 */
	private List<BeanProductoGestion> listaProductoGestion;
	
	/**
	 * Obtiene el valor del campo listaProductoGestion.
	 * @return List<BeanProductoGestion>
	 */
	public List<BeanProductoGestion> getListaProductoGestion() {
		return listaProductoGestion;
	}

	/**
	 * Establece el valor del campo listaProductoGestion.
	 * @param listaProductoGestion Valor que sera colocado en el campo listaProductoGestion.
	 */
	public void setListaProductoGestion(
			List<BeanProductoGestion> listaProductoGestion) {
		this.listaProductoGestion = listaProductoGestion;
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
