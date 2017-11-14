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
package mx.isban.rorac.bean.administracion.usuario;

import java.io.Serializable;
import java.util.List;

import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;

/**
 * Clase BeanUsuario
 * 
 * Clase que contiene las propiedades relacionadas a un usuario
 *  
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
*/
public class BeanUsuario implements Serializable {

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = 5652615958432805120L;
	/**
	 * Propiedad que contiene el id del usuario
	 */
	private String idUsuario;
	/**
	 * Propiedad que contiene el estatus del usuario 
	 */
	private boolean estatus;
	/**
	 * Propiedad que contiene los grupos relacionados a un usuario
	 */
	private List<BeanGrupo> grupos;
	
	/**
	 * Metodo encargado de obtener el id del usuario
	 * @return El id del usuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo encargado de establecer el id del usuario
	 * @param idUsuario El id del usuario a establecer
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo encargado de obtener el estatus del usuario,
	 * true si es activo
	 * @return true si el estatus del usuario es activo
	 */
	public boolean isEstatus() {
		return estatus;
	}

	/**
	 * Metodo encargado de establecer el estatus del usuario
	 * @param estatus El estatus del usaurio
	 */
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	/**
	 * Metodo encargado de obtener los grupos que un usuario tiene asignado
	 * @return El listado de grupos que contiene un usuario
	 */
	public List<BeanGrupo> getGrupos() {
		return grupos;
	}

	/**
	 * Metodo encargado de establecer los grupos relacionados a un usuario
	 * @param grupos Los grupos a establecer
	 */
	public void setGrupos(List<BeanGrupo> grupos) {
		this.grupos = grupos;
	}
	
	/**
	 * Metodo encargado de obtener los grupos asignados a un usuario
	 * en una cadena String
	 * @return Una cadena con el listado de grupos asigandos a un usuario
	 */
	public String getGruposToString(){
		if(null == grupos){
			return "";
		}else{
			StringBuilder grupos = new StringBuilder();
			for (int i = 0; i < this.grupos.size(); i++) {
				if(i == (this.grupos.size()-1)){
					grupos.append(this.grupos.get(i).getNombreGrupo());
				}else{
					grupos.append(this.grupos.get(i).getNombreGrupo()).append(", ");
				}
			}
			return grupos.toString();
		}
	}
	
	/**
	 * Metodo que obtiene en una cadena, las pantallas que un usuario tiene 
	 * asignadas 
	 * @return Una cadena con el listado de pantallas asignadas a un usuario
	 */
	public String getPantallasToString(){
		if(null == grupos){
			return "";
		}else{
			StringBuilder grupos = new StringBuilder();
			for (int i = 0; i < this.grupos.size(); i++) {
				if(i == (this.grupos.size()-1)){
					grupos.append(this.grupos.get(i).pantallasToString());
				}else{
					grupos.append(this.grupos.get(i).pantallasToString()).append(", ");
				}
			}
			return grupos.toString();
		}
	}
	
}
