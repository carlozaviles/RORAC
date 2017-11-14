package mx.isban.rorac.servicio.inputs;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;

@Remote
public interface BOCargaManual {

	/**
	 * Guarada el input que fue cargado por el usuario.
	 * @param nombreInput Nombre del input.
	 * @param cargado Indica si el archivo fue cargado.
	 * @param sessionBean Componente Agave.
	 * @throws BusinessException Excepcion.
	 */
	public void registraCargaInput(String nombreInput, Boolean cargado, 
			ArchitechSessionBean sessionBean) throws BusinessException;
	
	/**
	 * Valida el nombre del input.
	 * @param nombreInput Cadena que sera validada.
	 * @param sessionBean Componente Agave.
	 * @return Boolean
	 * @throws BusinessException Excepcion.
	 */
	public Boolean validarNombreInput(String nombreInput, 
			ArchitechSessionBean sessionBean) throws BusinessException;
}
