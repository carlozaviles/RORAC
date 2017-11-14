package mx.isban.rorac.servicio.consultas;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanFlagNeteo;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;

@Remote
public interface BOModificacionParametros {
	
	/**
	 * Metodo de la capa de negocio que guarda las modificaciones realizadas sobre un registro ADN Local.
	 * @param registroAdnlocal Registro con las modificaciones realizadas por el usuario.
	 * @param sessionBean Objeto de la Arquitectura Agave
	 * @throws BusinessException Exception
	 */
	public void llamaModificacionADNLocal(BeanADNLocal registroAdnlocal, ArchitechSessionBean sessionBean) throws BusinessException;

	/**
	 * Metodo de la capa de negocio que guarda las modificaciones realizadas sobre un registro Producto Gestion.
	 * @param registroProductoGestion Registro con las modificaciones realizadas por el usuario.
	 * @param sessionBean Objeto de la arquitectura Agave.
	 * @throws BusinessException Exception
	 */
	public void llamaModificacionProductoGestion(BeanProductoGestion registroProductoGestion, ArchitechSessionBean sessionBean) 
			throws BusinessException;
	
	/**
	 * Metodo de la capa de negocio que guarda las modificaciones realizadas sobre un registro ADN Retail y No Retail.
	 * @param registroAdnRetail Registro con las modificaciones realizadas por el usuario.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @throws BusinessException Exception
	 */
	public void llamaModificacionADNRetail(BeanADNRetail registroAdnRetail, ArchitechSessionBean sessionBean) throws BusinessException;
	
	/**
	 * Metodo de la capa de negocio que guarda las modificaciones realizadas sobre un registro FlagNeteo.
	 * @param flagNeteo Registro con las modificaciones realizadas por el usuario.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @throws BusinessException Exception
	 */
	public void llamaModificacionFlagNeteo(BeanFlagNeteo flagNeteo, ArchitechSessionBean sessionBean) throws BusinessException;
	
	/**
	 * Metodo de la capa de negocio que se encarga de realizar los llamados necesarios para eliminar registros de base de datos
	 * de un tipo de filtro en particular.
	 * @param idRegistrosPorEliminar Lista de id de los registros que seran eliminados.
	 * @param tipoFiltro Tipo de filtro del cual seran eliminados los registros.
	 * @param sessionBean Objeto de la arquitectura Agave.
	 * @throws BusinessException Exception
	 */
	public void llamaEliminacionRegistro(String [] idRegistrosPorEliminar, String tipoFiltro, ArchitechSessionBean sessionBean) 
			throws BusinessException;
}
