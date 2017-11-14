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
public class BeanListaADNRetailDAO implements BeanResultBO, Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 5524575433316194019L;
	/**
	 * Campo que contiene el codigo de error producto de la consulta de Adn Retial y No Retail.
	 */
	private String codError;
	/**
	 * Contiene el mensaje de error, en caso de que esta haya ocurrido, al realizar la consulta Adn Retail y No Retail.
	 */
	private String msgError;
	/**
	 * Lista de Objetos BeanADNRetail producto de la consulta Adn Retail.
	 */
	private List<BeanADNRetail> listaADNRetail;
	
	/**
	 * Obtiene el valor del campo listaADNRetail
	 * @return List<BeanADNRetail>
	 */
	public List<BeanADNRetail> getListaADNRetail() {
		return listaADNRetail;
	}

	/**
	 * Establece el valor del campo listaAdnRetial
	 * @param listaADNRetail Contiene el valor que sera colocado en el campo listaADNRetail.
	 */
	public void setListaADNRetail(List<BeanADNRetail> listaADNRetail) {
		this.listaADNRetail = listaADNRetail;
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
