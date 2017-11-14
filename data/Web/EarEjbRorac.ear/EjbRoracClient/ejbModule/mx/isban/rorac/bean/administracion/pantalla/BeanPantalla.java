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

import mx.isban.rorac.bean.administracion.modulo.BeanModulo;

/**
 * Clase BeanPantalla
 * 
 * Clase que contiene las propiedades relacionadas a una pantalla
 *  
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
*/
public class BeanPantalla implements Serializable {

	
	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = 6926947889028550818L;
	/**
	 * Propiedad que contiene el id de la pantalla
	 */
	private String idPantalla;
	/**
	 * Propiedad que contiene el nombre de la pantalla
	 */
	private String nombrePantalla;
	/**
	 * Propiedad que contiene la descripcion de la pantalla
	 */
	private String descripcionPantalla;
	/**
	 * Propiedad que contiene el valor booleano que indica si una pantalla
	 * esta seleccionada
	 */
	private boolean pantallaSeleccionada;
	/**
	 * Listado de modulos relacionados a una pantalla
	 */
	private List<BeanModulo> modulos;
	
	/**
	 * Metodo que obtiene el id de la pantalla
	 * @return El id de la pantalla
	 */
	public String getIdPantalla() {
		return idPantalla;
	}



	/**
	 * Metodo que establece el id de la pantalla
	 * @param idPantalla El id de la pantalla
	 */
	public void setIdPantalla(String idPantalla) {
		this.idPantalla = idPantalla;
	}



	/**
	 * Metodo que obtiene el nombre de la pantalla
	 * @return El nombre de la pantalla
	 */
	public String getNombrePantalla() {
		return nombrePantalla;
	}

	/**
	 * Metodo que establece el nombre de la pantalla
	 * @param nombrePantalla El nombre de la pantalla
	 */
	public void setNombrePantalla(String nombrePantalla) {
		this.nombrePantalla = nombrePantalla;
	}

	/**
	 * Metodo que obtiene la descripcion de la pantalla
	 * @return La descripcion de la pantalla
	 */
	public String getDescripcionPantalla() {
		return descripcionPantalla;
	}

	/**
	 * Metodo que establece la descripcion de la pantalla
	 * @param descripcionPantalla La descripcion de la pantalla a establecer
	 */
	public void setDescripcionPantalla(String descripcionPantalla) {
		this.descripcionPantalla = descripcionPantalla;
	}



	/**
	 * Metodo que regresa un valor booleano si la pantalla se encuentra
	 * seleccionada
	 * @return true si la pantalla se encuentra seleccionada
	 */
	public boolean isPantallaSeleccionada() {
		return pantallaSeleccionada;
	}



	/**
	 * Metodo para establecer true o false, en relaciona a si la pantalla se
	 * encuentra seleccionada
	 * @param pantallaSeleccionada El valor booleano indicando si la pantalla
	 * se encuentra seleccionada
	 */
	public void setPantallaSeleccionada(boolean pantallaSeleccionada) {
		this.pantallaSeleccionada = pantallaSeleccionada;
	}

	/**
	 * Metodo que obtiene los modulos relacionados a una pantalla
	 * @return Los modulos relacionados a una pantalla
	 */
	public List<BeanModulo> getModulos() {
		return modulos;
	}



	/**
	 * Metodo que establece los modulos relacionados a una pantalla
	 * @param modulos Los modulos a establecer
	 */
	public void setModulos(List<BeanModulo> modulos) {
		this.modulos = modulos;
	}
	
}
