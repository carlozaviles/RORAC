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
public class BeanListaFlagNeteoDAO implements BeanResultBO, Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 4827435871356309319L;
	/**
	 * Codigo de Error producto de la consulta de FlagNeteo.
	 */
	private String codError;
	/**
	 * Mensaje de Error, en caso de haber ocurrido, al realizar la consulta de FlagNeteo.
	 */
	private String msgError;
	/**
	 * Lista de objetos BeanFlagNeteo, producto de la consulta de FlagNeteo.
	 */
	private List<BeanFlagNeteo> listaFlagNeteo;

	/**
	 * Establece el valor del campo listaFlagNeteo.
	 * @return List<BeanFlagNeteo>
	 */
	public List<BeanFlagNeteo> getListaFlagNeteo() {
		return listaFlagNeteo;
	}

	/**
	 * Establece el valor del campo BeanFlagNeteo.
	 * @param listaFlagNeteo Valor que sera colocado en el campo listaFlagNeteo.
	 */
	public void setListaFlagNeteo(List<BeanFlagNeteo> listaFlagNeteo) {
		this.listaFlagNeteo = listaFlagNeteo;
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
