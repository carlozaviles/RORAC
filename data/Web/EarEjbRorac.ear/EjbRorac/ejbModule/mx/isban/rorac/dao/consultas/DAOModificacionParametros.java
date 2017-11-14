package mx.isban.rorac.dao.consultas;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanFlagNeteo;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;

@Local
public interface DAOModificacionParametros {
	
	/**
	 * Modifica un registro de tipo ADN Local.
	 * @param adnLocal Contiene los datos que seran actualizados en BD.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO ejecutaModificacionADNLocal(BeanADNLocal adnLocal, ArchitechSessionBean sessionBean);
	
	/**
	 * Modifica un registro de tipo Producto Gestion.
	 * @param productoGestion Contiene los datos que seran modificados en BD.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO ejecutaModificacionProductoGestion(BeanProductoGestion productoGestion, ArchitechSessionBean sessionBean);
	
	/**
	 * Modifica un registro de tipo ADN Retail y No Retail.
	 * @param adnRetail Contiene los datos que seran modificados en BD.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO ejecutaModificacionADNRetail(BeanADNRetail adnRetail, ArchitechSessionBean sessionBean);
	
	/**
	 * Modifica un registro de tipo FlagNeteo.
	 * @param flagNeteo Registro que sera modificado.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO ejecutaModificacionFlagNeteo(BeanFlagNeteo flagNeteo, ArchitechSessionBean sessionBean);
	
	/**
	 * Ejecuta la eliminacion de los registros elegidos por el usuario.
	 * @param query Query para realizar la baja logica de los registros.
	 * @param sessionBean Objeto de la arquitectura Agave.
	 * @return BeanResultaBO
	 */
	public BeanResultBO ejecutaEliminacionRegistros(String query, ArchitechSessionBean sessionBean);
}
