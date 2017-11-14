/**
 * 
 */
package mx.isban.rorac.bean.consultas;

import java.io.Serializable;

/**
 * @author everis
 *
 */
public class BeanFlagNeteo implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -2962964677143494822L;
	/**
	 * ID de este registro de tipo FlagNeteo.
	 */
	private String idRegistro;
	/**
	 * Valor del este regitro de tipo FlagNeteo.
	 */
	private String valor;
	
	/**
	 * Obtiene el valor del campo idRegistro.
	 * @return String
	 */
	public String getIdRegistro() {
		return idRegistro;
	}
	
	/**
	 * Establece el valor del campo idRegistro
	 * @param idRegistro Valor que sera colocado en el campo idRegistro.
	 */
	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}
	
	/**
	 * Obtiene el campo valor
	 * @return String
	 */
	public String getValor() {
		return valor;
	}
	
	/**
	 * Establece el contenido del campo valor.
	 * @param valor Es colocado en el campo valor.
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
