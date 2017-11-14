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
public class BeanListaAdnLocalDAO implements BeanResultBO, Serializable {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6347210956046178002L;
	/**
	 * Contiene el codigo de error, producido al ejecutar la consulta de Adn Local.
	 */
	private String codError;
	/**
	 * Contiene el mensaje de error, en caso de que este se haya generado, al realizar la consulta de Adn Local. 
	 */
	private String msgError;
	/**
	 * Lista de objetos ADN Local, producto de la consulta de Adn Local.
	 */
	private List<BeanADNLocal> listaADNLocal;
	

	/**
	 * Obtiene el valor del campo listaADNLocal
	 * @return List<BeanADNLocal>
	 */
	public List<BeanADNLocal> getListaADNLocal() {
		return listaADNLocal;
	}

	/**
	 * Establece el valor del campo listaADNLocal
	 * @param listaADNLocal Valor que sera colocad en el campo listaADNLocal.
	 */
	public void setListaADNLocal(List<BeanADNLocal> listaADNLocal) {
		this.listaADNLocal = listaADNLocal;
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
