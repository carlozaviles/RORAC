package mx.isban.rorac.dao.consultas;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.consultas.BeanListaADNRetailDAO;
import mx.isban.rorac.bean.consultas.BeanListaAdnLocalDAO;
import mx.isban.rorac.bean.consultas.BeanListaFlagNeteoDAO;
import mx.isban.rorac.bean.consultas.BeanListaProductoGestionDAO;

@Local
public interface DAOConsultaParametros {
	
	/**
	 * Ejecuta la consulta del tipo de parametro ADN Local.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @return BeanListaAdnLocalDAO
	 */
	public BeanListaAdnLocalDAO ejecutaConsultaADNLocal(ArchitechSessionBean sessionBean);
	
	/**
	 * Ejecuta la consulta del tipo de parametro Producto Gestion.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @return BeanListaProductoGestionDAO
	 */
	public BeanListaProductoGestionDAO ejecutaConsultaProductoGestion(ArchitechSessionBean sessionBean);
	
	/**
	 * Ejecuta la consulta del tipo de parametro ADN Retail y No Retail.
	 * @param sessionBean Objeto de la Arquitectura Agave.
	 * @return BeanListaADNRetailDAO
	 */
	public BeanListaADNRetailDAO ejecutaConsultaADNRetail(ArchitechSessionBean sessionBean);
	
	/**
	 * Ejecuta la consulta de tipo FlagNeteo.
	 * @param sessionBean Objeto de la arquitectura Agave
	 * @return BeanListaFlagNeteoDAO
	 */
	public BeanListaFlagNeteoDAO ejecutaConsultaFlagNeteo(ArchitechSessionBean sessionBean);
}
