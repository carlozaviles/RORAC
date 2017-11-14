package mx.isban.rorac.servicio.consultas;

import java.util.List;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanFlagNeteo;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;

@Remote
public interface BOConsultaParametros {
	
	/**
	 * Se comunica con la capa de negocio para que se realice la consulta del tipo de parametro ADN Local.
	 * @param sessionBean Objeto de la arquitectura Agave.
	 * @return List<BeanADNLocal>
	 * @throws BusinessException Exception.
	 */
	public List<BeanADNLocal> llamaConsultaADNLocal(ArchitechSessionBean sessionBean) throws BusinessException;
	
	/**
	 * Se comunica con la capa de negocio para que se realice la consulta del tipo de parametro Producto Gestion.
	 * @param sessionBean Objeto de la arquitectura Agave.
	 * @return List<BeanProductoGestion>
	 * @throws BusinessException Exception.
	 */
	public List<BeanProductoGestion> llamaConsultaProductoGestion(ArchitechSessionBean sessionBean) throws BusinessException;
	
	/**
	 * Se comunica con la capa de negocio para que se realice la consulta del tipo de parametro ADN Retail.
	 * @param sessionBean Objeto de arquitectura Agave
	 * @return List<BeanADNRetail>
	 * @throws BusinessException Excepcion
	 */
	public List<BeanADNRetail> llamaConsultaADNRetail(ArchitechSessionBean sessionBean) throws BusinessException;
	
	/**
	 * Se comunica con la capa de negocio para que se realice la consulta del tipo de parametro FlagNeteo.
	 * @param sessionBean Objeto de arquitectura Agave.
	 * @return List<BeanFlagNeteo>
	 * @throws BusinessException Exception
	 */
	public List<BeanFlagNeteo> llamaConsultaFlagNeteo(ArchitechSessionBean sessionBean) throws BusinessException;
}
