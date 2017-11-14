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
package mx.isban.rorac.bean.administracion.pantalla;

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
public class BeanPantallaRespuesta implements BeanResultBO,Serializable {

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = 5462130233655064685L;
	/**
	 * Propiedad que contiene las pantallas obtenidas de la consulta a la base de datos
	 */
	private List<BeanPantalla> pantallas;
	/**
	 * Propiedad que contiene el codigo de error
	 */
	private String codError;
	/**
	 * Propiedad que contiene el mensaje de error
	 */
	private String msgError;
	
	/**
	 * Metodo encargado de obtener las pantallas 
	 * @return Las pantallas obtenidas de consultar la base de datos
	 */
	public List<BeanPantalla> getPantallas() {
		return pantallas;
	}

	/**
	 * Metodo encargado de establecer las pantallas 
	 * @param pantallas Las pantallas a establecer
	 */
	public void setPantallas(List<BeanPantalla> pantallas) {
		this.pantallas = pantallas;
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
