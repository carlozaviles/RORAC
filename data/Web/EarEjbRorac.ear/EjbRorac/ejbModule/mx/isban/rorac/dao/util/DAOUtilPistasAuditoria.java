package mx.isban.rorac.dao.util;

import java.util.Map;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;

@Local
public interface DAOUtilPistasAuditoria {
	
	/**
	 * Constante que servira para acceder al resultado del metodo obtenerInfoOperacion
	 */
	public static final String ID_OPERACION = "ID_OPERACION";
	/**
	 * Constante que servira para acceder al resultado de metodo obtenerInfoOperacion
	 */
	public static final String NOMBRE_ARCHIVO = "NOMBRE_ARCHIVO";
	
	/**
	 * Obtiene nombre de insumo, dado su ID.
	 * @param idInsumo Id del insumo a consultar.
	 * @param sessionBean Objeto de la arquitectura Agave.
	 * @return String
	 */
	public String obtenerNombreInsumo(String idInsumo, ArchitechSessionBean sessionBean);
	
	/**
	 * Obtiene el nombre del archivo y el id de operacion al que hace referencia el id estatus.
	 * @param idEstatus Parametro de consulta
	 * @param sessionBean Objeto de la arquitectura Agave.
	 * @return Map<String, String>
	 */
	public Map<String, String> obtenerInfoOperacion(String idEstatus, ArchitechSessionBean sessionBean);
}
