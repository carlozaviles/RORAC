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

import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;

/**
 * Clase BeanModulo
 * 
 * Clase que contiene las propiedades relacionadas a un modulo
 *  
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
public class BeanModulo implements Serializable {

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -9105818015915010700L;
	/**
	 * Propiedad que contiene el id del modulo
	 */
	private String idModulo;
	/**
	 * Propiedad que contiene el nombre del modulo
	 */
	private String nombreModulo;
	/**
	 * Propiedad que contiene la descripcion del modulo
	 */
	private String descripcionModulo;
	/**
	 * Propiedad que indica si un modulo se encuentra seleccionado
	 */
	private boolean moduloSeleccionado;
	/**
	 * Listado de pantallas relacionadas a un modulo
	 */
	private List<BeanPantalla> pantallas;

	/**
	 * Metodo encargado de obtener el id del modulo
	 * @return El id del modulo
	 */
	public String getIdModulo() {
		return idModulo;
	}

	/**
	 * Metodo encargado de establecer el id del modulo
	 * @param idModulo El id del modulo a establecer
	 */
	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}

	/**
	 * Metodo encargado de obtener el nombre del modulo
	 * @return El nombre del modulo
	 */
	public String getNombreModulo() {
		return nombreModulo;
	}

	/**
	 * Metodo encargado de establecer el nombre del modulo
	 * @param nombreModulo El nombre del modulo
	 */
	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	/**
	 * Metodo encargado de obtener las pantallas relacionadas a un modulo
	 * @return Listado de pantallas relacionadas a un modulo
	 */
	public List<BeanPantalla> getPantallas() {
		return pantallas;
	}

	/**
	 * Metodo encargado de establecer las pantallas relacionadas a un modulo
	 * @param pantallas Las pantallas relacionadas a un modulo
	 */
	public void setPantallas(List<BeanPantalla> pantallas) {
		this.pantallas = pantallas;
	}

	/**
	 * Metodo que indica si el modulo se encuentra seleccionado 
	 * @return true si el modulo esta seleccionado
	 */
	public boolean isModuloSeleccionado() {
		return moduloSeleccionado;
	}

	/**
	 * Metodo encargado de establecer un valor booleano que indica si el modulo se encuentra seleccionado
	 * @param moduloSeleccionado Valor booleano a establecer
	 */
	public void setModuloSeleccionado(boolean moduloSeleccionado) {
		this.moduloSeleccionado = moduloSeleccionado;
	}

	/**
	 * Metodo encargado de obtener la descripcion del modulo
	 * @return La descripcion del modulo
	 */
	public String getDescripcionModulo() {
		return descripcionModulo;
	}

	/**
	 * Metodo encargado de establecer la descripcion del modulo
	 * @param descripcionModulo Descripcion del modulo a establecer
	 */
	public void setDescripcionModulo(String descripcionModulo) {
		this.descripcionModulo = descripcionModulo;
	}
	
	
}
