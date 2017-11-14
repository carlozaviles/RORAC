package mx.isban.rorac.dao.consultas;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;

@Local
public interface DAOAltaParametros {

	/**
	 * Guarda un nuevo registro de tipo ADN Local.
	 * 
	 * @param adnLocal
	 *            Contiene los datos del registro que sera dado de alta.
	 * @param sessionBean
	 *            Objeto de Arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO ejecutaAltaADNLocal(BeanADNLocal adnLocal,ArchitechSessionBean sessionBean);

	/**
	 * Guarda un nuevo registro de tipo Producto Gestion.
	 * 
	 * @param productoGestion
	 *            Contiene los datos del registro que sera dado de alta.
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO ejecutaAltaProductoGestion(BeanProductoGestion productoGestion, ArchitechSessionBean sessionBean);

	/**
	 * Guarda un nuevo registro de tipo ADN Retail.
	 * 
	 * @param adnRetail
	 *            Contiene los datos del registro que sera dado de alta.
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO ejecutaAltaADNRetail(BeanADNRetail adnRetail,
			ArchitechSessionBean sessionBean);
}
