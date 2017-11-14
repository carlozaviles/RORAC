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
package mx.isban.rorac.bean.administracion.modulo;

import java.io.Serializable;
import java.util.List;

import mx.isban.agave.commons.interfaces.BeanResultBO;

/**
 * Clase BeanModuloRespuesta
 * 
 * Clase que contiene las propiedades relacionadas a la respuesta obtenida del IDA
 *  
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
public class BeanModuloRespuesta implements BeanResultBO, Serializable{

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -285199473834055494L;
	/**
	 * Propiedad que contiene el codigo de error
	 */
	private String codError;
	/**
	 * Propiedad que contiene el mensaje de error
	 */
	private String msgError;
	/**
	 * Propiedad que contiene la respuesta obtenida de consultar la base de datos
	 */
	private List<BeanModulo> modulos;
	
	
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

	/**
	 * Metodo encargado de obtener los modulos obtenidos de consultar la base de datos
	 * @return Los modulos obtenidos
	 */
	public List<BeanModulo> getModulos() {
		return modulos;
	}

	/**
	 * Metodo encargado de establecer los modulos
	 * @param modulos Los modulos a establecer
	 */
	public void setModulos(List<BeanModulo> modulos) {
		this.modulos = modulos;
	}

}
