/**************************************************************
* Queretaro, Qro Mayo 2015
*
* La redistribucion y el uso en formas fuente y binario, 
* son responsabilidad del propietario.
* 
* Este software fue elaborado en @Everis
* 
* Para mas informacion, consulte <www.everis.com/mexico>
***************************************************************/
package mx.isban.rorac.bean.administracion.grupo;

import java.io.Serializable;
import java.util.List;

import mx.isban.agave.commons.interfaces.BeanResultBO;

/**
 * Clase BeanGrupoRespuesta
 * 
 * Clase que contiene las propiedades relacionadas a la respuesta obtenida del IDA
 *  
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
public class BeanGrupoRespuesta implements Serializable, BeanResultBO {
	
	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = 2628156608839748461L;
	/**
	 * Propiedad que contiene el codigo de error generado
	 */
	private String codError;
	/**
	 * Propiedad que contiene el mensaje de error generado
	 */
	private String msgError;
	/**
	 * Resultado de la consulta a base de datos
	 */
	private List<BeanGrupo> grupos;
	
	/**
	 * Metodo que obtiene los grupos resultantes de la consulta a Base de datos
	 * @return List<BeanGrupo>
	 */
	public List<BeanGrupo> getGrupos() {
		return grupos;
	}

	/**
	 * Metodo que establece los grupos obtenidos de consultar la base de datos
	 * @param grupos Los grupos a establecer
	 */
	public void setGrupos(List<BeanGrupo> grupos) {
		this.grupos = grupos;
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
